package me.goddragon.teaseai.utils.media;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import me.goddragon.teaseai.utils.TeaseLogger;
import me.goddragon.teaseai.utils.media.image.ImageScaler;

import com.twelvemonkeys.image.ResampleOp;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class ImageUtils {
	public static void setImageInView(File image, ImageView imageView) {
		if (image == null) {
			TeaseLogger.getLogger().log(Level.SEVERE, "Can't set image to null (should be handled by MediaHandler)");
		}

		double paneWidth = ((Region) imageView.getParent()).getWidth();
		double paneHeight = ((Region) imageView.getParent()).getHeight();

		Image scaledImage;
		try {
			scaledImage = ImageUtils.resizeImage(image, paneWidth, paneHeight);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		imageView.setImage(scaledImage);
	}

	private static Image resizeImage(File image, double paneWidth, double paneHeight) throws IOException {
		try(var scaler = new ImageScaler(image)) {
			return scaler.getImage(paneWidth, paneHeight);
		}
	}

}
