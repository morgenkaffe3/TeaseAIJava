package me.goddragon.teaseai.utils.media.image;

import java.awt.image.RenderedImage;
import java.io.IOException;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOInvalidTreeException;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageOutputStream;

class GifSequenceWriter implements AutoCloseable {
	private ImageWriter writer;
	private ImageWriteParam params;
	private IIOMetadata metadata;

	public GifSequenceWriter(ImageOutputStream out, int imageType, int delay, boolean loop) throws IOException {
		writer = ImageIO.getImageWritersBySuffix("gif").next();
		params = writer.getDefaultWriteParam();

		ImageTypeSpecifier imageTypeSpecifier = ImageTypeSpecifier.createFromBufferedImageType(imageType);
		metadata = writer.getDefaultImageMetadata(imageTypeSpecifier, params);

		configureRootMetadata(delay, loop);

		writer.setOutput(out);
		writer.prepareWriteSequence(null);
	}

	private void configureRootMetadata(int delay, boolean loop) throws IIOInvalidTreeException {
		String metaFormatName = metadata.getNativeMetadataFormatName();
		IIOMetadataNode root = (IIOMetadataNode) metadata.getAsTree(metaFormatName);

		IIOMetadataNode graphicsControlExtensionNode = GifUtil.getNode(root, "GraphicControlExtension");
		graphicsControlExtensionNode.setAttribute("disposalMethod", "none");
		graphicsControlExtensionNode.setAttribute("userInputFlag", "FALSE");
		graphicsControlExtensionNode.setAttribute("transparentColorFlag", "FALSE");
		graphicsControlExtensionNode.setAttribute("delayTime", Integer.toString(delay / 10));
		graphicsControlExtensionNode.setAttribute("transparentColorIndex", "0");

		IIOMetadataNode appExtensionsNode = GifUtil.getNode(root, "ApplicationExtensions");
		IIOMetadataNode child = new IIOMetadataNode("ApplicationExtension");
		child.setAttribute("applicationID", "NETSCAPE");
		child.setAttribute("authenticationCode", "2.0");

		int loopContinuously = loop ? 0 : 1;
		child.setUserObject(
				new byte[] { 0x1, (byte) (loopContinuously & 0xFF), (byte) ((loopContinuously >> 8) & 0xFF) });
		appExtensionsNode.appendChild(child);
		metadata.setFromTree(metaFormatName, root);
	}

	public void writeToSequence(RenderedImage img) throws IOException {
		writer.writeToSequence(new IIOImage(img, null, metadata), params);
	}

	@Override
	public void close() throws IOException {
		writer.endWriteSequence();
	}
}
