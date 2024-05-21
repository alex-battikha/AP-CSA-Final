import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLayeredPane;
public class CellGUI{
	public int x;
	public int y;
	public int value;
	Graphics g;
	
	public CellGUI(int xi, int yi, int v, Graphics g) {
		x = xi;
		y = yi;
		value = v;
		this.g = g;
	}
	
	public void reveal() {
		g.setColor(Color.gray);
		g.drawRect(x, y, 50, 50);
		if (value == -1) {
			g.setColor(Color.black);
			g.drawOval(x+25, y+25, 10, 10);
		}
		else {
			g.drawString(value + "", x+20, y+20);
		}
	}
	
	public void flag() { 
		JLayeredPane EverythingButPlayer = new JLayeredPane();

		BufferedImage img = null;
	    try {
	        img = ImageIO.read(new File("flag.png"));
	    } catch (IOException e) {
	    }
	    Graphics g = img.getGraphics();
	    g.drawImage(img,0, 0, EverythingButPlayer);
		}
	
	public void hide() {
		g.setColor(Color.green);
		g.drawRect(x, y, 50, 50);
	}
}

