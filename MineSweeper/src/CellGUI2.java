import java.awt.Color;
import java.awt.Graphics;

public class CellGUI2 {
    public int x;
    public int y;
    public int state; // State of the cell: 0 for unrevealed, 1 for revealed, 2 for flagged

    public CellGUI2(int xi, int yi) {
        x = xi;
        y = yi;
        state = 0; // Initially, the cell is unrevealed
    }

    public void draw(Graphics g) {
        switch (state) {
            case 0: // Unrevealed state
                g.setColor(Color.green);
                g.fillRect(x, y, 50, 50);
                break;
            case 1: // Revealed state
                g.setColor(Color.gray);
                g.drawRect(x, y, 50, 50);
                
                g.setColor(Color.black);
                g.drawLine(x, y, x + 50, y + 50);
                g.drawLine(x + 50, y, x, y + 50);
                break;
            case 2:
                g.setColor(Color.red);
                g.fillRect(x, y, 50, 50);
                
                break;
        }
    }

    public void reveal() {
        state = 1; // Set the state to revealed
    }

    public void flag() {
        state = 2; // Set the state to flagged
    }
}
