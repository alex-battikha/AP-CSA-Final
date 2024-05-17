import java.awt.Graphics;

public class CellGUI extends Cell{
	public int x;
	public int y;
	
	public CellGUI(int xi, int yi, int v, boolean r, boolean f) {
		super(v, r, f);
		x = xi;
		y = yi;
	}
	
	public void paint(Graphics g) {
		
	}
	
}
