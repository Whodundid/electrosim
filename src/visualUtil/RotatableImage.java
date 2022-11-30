package visualUtil;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;

//Last edited: 3-9-17
//Author: Hunter Troy Bragg

@SuppressWarnings("serial")
public class RotatableImage extends ImageIcon{
	
	ImageIcon img;
	double degrees;
	String rotation;
	
	public RotatableImage(String image, String rotation) {
		this.img = new ImageIcon(image);
		this.rotation = rotation;
		switch (rotation) {
			case "north": degrees = 90; break;
			case "east": degrees = 0; break;
			case "south": degrees = 270; break;
			case "west": degrees = 180; break;
		}
	}
	
	@Override
	public int getIconHeight() {
		return img.getIconHeight();
	}

	@Override
	public int getIconWidth() {
		return img.getIconWidth();
	}

	@Override
	public void paintIcon(Component obj, Graphics g, int x, int y) {	
		Graphics2D g2 = (Graphics2D)g.create();
		switch (rotation) {
			case "north": g2.translate(x - 2, y + img.getIconHeight() + 2); break;
			case "east": g2.translate(x, y); break;
			case "south": g2.translate(x + img.getIconWidth() - 3, y - 2); break;
			case "west": g2.translate(x + img.getIconWidth(), y + img.getIconHeight()); break;
		}
		g2.rotate(Math.toRadians(-degrees));
		img.paintIcon(obj, g2, x, y);
	}
}