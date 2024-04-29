
public class Cell {
	
	private int value;
	private boolean unrevealed;
	//value = -1: bomb 
	//value != -1: number of mines around the cell
	//unrevealed: true = unrevealed cell
	//unrevealed: false = revealed cell
	public Cell(int v, boolean r) {
		value = v;
		unrevealed = r;
	}
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int v) {
		value = v;
	}
	
	public boolean getReveal() {
		return unrevealed;
	}
	
	public void setReveal(boolean r) {
		unrevealed = r;
	}
}
