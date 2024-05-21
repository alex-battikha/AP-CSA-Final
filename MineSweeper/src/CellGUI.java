import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CellGUI{
	public int value;
	public enum States {
		HIDDEN,
		REVEALED,
		FLAGGED
	}
	States state = States.HIDDEN;
	
	public CellGUI(int v) {
		value = v;
	}
	
	public void setValue(int v) {
		value = v;
	}
	
	public void reveal() {
		state = States.REVEALED;
	}
	
	public void flag() {
		state = States.FLAGGED;
	}
	
	public void hide() {
		state = States.HIDDEN;
	}
	
	public States getState() {
		return state;
	}
	
	
	public int getValue() {
		return value;
	}
}

