import java.awt.Color;
import java.awt.Graphics;
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
		g.drawRect(x, y, 50, 50);
		//todo draw flag
	}
	
	public void hide() {
		g.setColor(Color.green);
		g.drawRect(x, y, 50, 50);
	}
}

