package me.goddragon.teaseai.utils.media.image;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.stream.MemoryCacheImageOutputStream;

import com.twelvemonkeys.image.ResampleOp;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

public class ImageScaler implements AutoCloseable {
	private final ImageInputStream stream;
	private final ImageReader reader;

	private class ScalingInformation {
		private final int width;
		private final int heigth;
		private final boolean needsScaling;

		public ScalingInformation(int width, int heigth, boolean needsScaling) {
			super();
			this.width = width;
			this.heigth = heigth;
			this.needsScaling = needsScaling;
		}

		public int getWidth() {
			return width;
		}

		public int getHeigth() {
			return heigth;
		}

		public boolean isNeedsScaling() {
			return needsScaling;
		}
	}

	public ImageScaler(File file) throws IOException {
		this.stream = ImageIO.createImageInputStream(file);
		this.reader = this.getReader();
		this.reader.setInput(this.stream);
	}

	private ImageReader getReader() {
		Iterator<ImageReader> it = ImageIO.getImageReaders(stream);

		if (!it.hasNext()) {
			throw new RuntimeException("Unsupported format");
		}

		return it.next();
	}

	private String getFormatName() throws IOException {
		return reader.getFormatName();
	}

	private boolean isGif() throws IOException {
		return this.getFormatName().equals("gif");
	}

	private ScalingInformation determineScaleBounds(BufferedImage originalImage, double paneWidth, double paneHeight) {
		int originalImageHeight = originalImage.getHeight();
		int originalImageWidth = originalImage.getWidth();
		double scaleFactorWidth = originalImageWidth / paneWidth;
		double scaleFactorHeight = originalImageHeight / paneHeight;
		boolean needsScaling = true;
		int newWidth = 0;
		int newHeight = 0;

		if (scaleFactorHeight > scaleFactorWidth) {
			if (scaleFactorHeight <= 2.0) {
				needsScaling = false;
			} else {
				newWidth = (int) (originalImageWidth / scaleFactorHeight);
				newHeight = (int) (originalImageHeight / scaleFactorHeight);
			}
		} else {
			if (scaleFactorWidth <= 2.0) {
				needsScaling = false;
			} else {
				newWidth = (int) (originalImageWidth / scaleFactorWidth);
				newHeight = (int) (originalImageHeight / scaleFactorWidth);
			}
		}

		return new ScalingInformation(newWidth, newHeight, needsScaling);
	}

	public Image getImage(double paneWidth, double paneHeight) throws IOException {
		BufferedImage[] frames = this.scale(paneWidth, paneHeight);

		if (isGif()) {
			return createGif(frames);
		} else {
			return SwingFXUtils.toFXImage(frames[0], null);
		}
	}

	private Image createGif(BufferedImage[] frames) throws IOException {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		try (ImageOutputStream output = new MemoryCacheImageOutputStream(bytes)) {
			try (var gifWriter = new GifSequenceWriter(output, BufferedImage.TYPE_INT_ARGB, GifUtil.getDelay(reader), true)) {
				for(var frame : frames) {
					gifWriter.writeToSequence(frame);
				}
			}
		}
		
		return new Image(new ByteArrayInputStream(bytes.toByteArray()));
	}

	private BufferedImage[] scale(double paneWidth, double paneHeight) throws IOException {
		BufferedImage[] frames = this.getFrames();
		BufferedImage[] scaledFrames = new BufferedImage[frames.length];

		ScalingInformation info = determineScaleBounds(frames[0], paneWidth, paneHeight);

		for (int i = 0; i < frames.length; ++i) {
			scaledFrames[i] = scale(frames[i], info);
		}

		return scaledFrames;
	}

	private BufferedImage scale(BufferedImage input, ScalingInformation info) {
		if(!info.isNeedsScaling()) {
			return input;
		}
		
		ResampleOp resizeOp = new ResampleOp(info.getWidth(), info.getHeigth(), ResampleOp.FILTER_LANCZOS);
		return resizeOp.filter(input, null);
	}

	private BufferedImage[] getFrames() throws IOException {
		final int frameCount = reader.getNumImages(true);

		if (frameCount != 1 && !isGif()) {
			throw new RuntimeException("Unsupported format!");
		}

		var frames = new BufferedImage[frameCount];

		for (int i = 0; i < frameCount; i++) {
			frames[i] = reader.read(i);
		}

		return frames;
	}

	@Override
	public void close() throws IOException {
		reader.dispose();
		stream.close();
	}
}
