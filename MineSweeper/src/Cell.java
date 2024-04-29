
public class Cell {
	
	private int value;
	private boolean revealed;
	private boolean flag;
	//value = -1: bomb 
	//value != -1: number of mines around the cell
	//revealed: true = revealed cell
	//revealed: false = unrevealed cell
	//flag: true = there is a flag on cell
	//flag: false = no flag on cell
	public Cell(int v, boolean r, boolean f) {
		value = v;
		revealed = r;
		flag = f;
	}
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int v) {
		value = v;
	}
	
	public boolean getReveal() {
		return revealed;
	}
	
	public void setReveal(boolean r) {
		revealed = r;
	}
	
	public boolean getFlag() {
		return flag;
	}
	
	public void setFlag(boolean f) {
		flag = f;
	}
}
