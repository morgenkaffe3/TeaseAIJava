package me.goddragon.teaseai.utils.media.image;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.stream.MemoryCacheImageOutputStream;

import org.junit.jupiter.api.Test;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

public class ImageScalerTest {

	@Test
	public void test() throws IOException {
		try (var scaler = new ImageScaler(new File("Images/test.gif"))) {
			var image = scaler.getImage(100, 100);
			
			
			
		}
	}

	@Test
	public void test2() throws IOException {
		BufferedImage[] frames = new BufferedImage[] {
				ImageIO.read(new File("Images/test.jpg")),
				ImageIO.read(new File("Images/test2.jpg"))
		};
		
		try (ImageOutputStream output = new FileImageOutputStream(new File("out3.gif"))) {
			try (var gifWriter = new GifSequenceWriter(output, frames[0].getType(), 1000, true)) {
				for(var frame : frames) {
					gifWriter.writeToSequence(frame);
				}
			}
		}
	}
	
	@Test
	public void testSame() throws IOException {
		BufferedImage image = ImageIO.read(new File("Images/test.gif"));
		ImageIO.write(image, "gif", new File("out2.gif"));

	}

}
