package logic;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import model.Command;

public class AsciiConverter {

	private static BufferedImage image;
	private static double pixval;
	private static final int RESIZE = 100;

	public static void convertToAscii(String imageName, Command command) throws Exception {
		try {
			image = resizeImage(ImageIO.read(new File(imageName)));
		} catch (IOException ex) {
			throw new Exception("Can not read or found file: " + imageName);
		}
		for (int i = 0; i < image.getHeight(); i++) {
			String line = "";
			for (int j = 0; j < image.getWidth(); j++) {
				Color pixcol = new Color(image.getRGB(j, i));
				pixval =
						(((pixcol.getRed() * 0.3) + (pixcol.getBlue() * 0.59) + (pixcol.getGreen() * 0.11)));
				line += strChar(pixval);
			}
			command.addText(line);
		}
	}

	private static String strChar(double value) {
		String result;
		if (value >= 240)
			result = " ";
		else if (value >= 210)
			result = ".";
		else if (value >= 190)
			result = "*";
		else if (value >= 170)
			result = "+";
		else if (value >= 120)
			result = "~";
		else if (value >= 110)
			result = "-";
		else if (value >= 80)
			result = ",";
		else if (value >= 60)
			result = "?";
		else
			result = "@";
		return result;
	}

	private static BufferedImage resizeImage(BufferedImage originImage) {
		int type = originImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originImage.getType();
		BufferedImage resizedImage = new BufferedImage(RESIZE, RESIZE, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originImage, 0, 0, RESIZE, RESIZE, null);
		g.dispose();
		return resizedImage;
	}

}
