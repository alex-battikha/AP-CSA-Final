import java.awt.Color;
import java.awt.Graphics;

public class CellGUI{
	public int x;
	public int y;
	public int value;
	private enum State {
		UNKNOWN,
		REVEAL,
		FLAG,
	}
	State state = State.UNKNOWN;
	
	public CellGUI(int xi, int yi, int v) {
		x = xi;
		y = yi;
		value = v;
	}
	
	public void reveal() {
		state = State.REVEAL;
	}
	
	public void flag() {
		state = State.FLAG;
	}
	
	public void hide() {
		state = State.UNKNOWN;
	}
	
	public void paint(Graphics g) {

		g.setColor(Color.green);
		g.drawRect(x, y, 50, 50);
		switch (state) {
		case UNKNOWN:
			break;
		case REVEAL:
			g.setColor(Color.gray);
			g.drawRect(x, y, 50, 50);
			if (value == -1) {
				g.setColor(Color.black);
				g.drawOval(x+25, y+25, 10, 10);
			}
			else {
				g.drawString("" + value, x, y);
			}
			break;
		case FLAG:
			g.drawRect(x, y, 50, 50);
			//todo draw flag
		}
	}
	
}
