package me.goddragon.teaseai.utils.media.image;

import java.io.IOException;

import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;

class GifUtil {
	static IIOMetadataNode getNode(IIOMetadataNode rootNode, String nodeName) {
		int nNodes = rootNode.getLength();
		for (int i = 0; i < nNodes; i++) {
			if (rootNode.item(i).getNodeName().equalsIgnoreCase(nodeName)) {
				return (IIOMetadataNode) rootNode.item(i);
			}
		}
		IIOMetadataNode node = new IIOMetadataNode(nodeName);
		rootNode.appendChild(node);
		return (node);
	}
	
	static int getDelay(ImageReader reader) throws IOException {
		IIOMetadata imageMetaData = reader.getImageMetadata(0);
		String metaFormatName = imageMetaData.getNativeMetadataFormatName();
		IIOMetadataNode root = (IIOMetadataNode) imageMetaData.getAsTree(metaFormatName);
		IIOMetadataNode graphicsControlExtensionNode = GifUtil.getNode(root, "GraphicControlExtension");

		String delay = graphicsControlExtensionNode.getAttribute("delayTime");
		return Integer.parseInt(delay);
	}
}
