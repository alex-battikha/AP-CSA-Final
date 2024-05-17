import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class GUI implements ActionListener {
	JFrame frame;
	JPanel textPanel;
	JPanel buttonPanel;
	JButton[][] buttons;
	JLabel textField;
	JComboBox c1;
	
	static int size = 0;
	
// 	TODO: generate map of size A x A as specified by player
	
	
	public void main(String[] args) {
		frame = new JFrame("MineSweeper");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 1000);
		frame.setVisible(true);
		frame.setLayout(null);
		frame.setResizable(true);
			
		dropdownGen();
	}
	
	public void dropdownGen() {
		String s1[] = {"Easy", "Medium","Hard"};
		
		c1 = new JComboBox(s1);
	    c1.setBounds(15,15,75,35);
	    c1.setVisible(true);
		frame.add(c1);
		frame.setVisible(true);
		
		c1.addActionListener(new ActionListener() {  
			public void actionPerformed(ActionEvent e) {       
				//System.out.println(c1.getItemAt(c1.getSelectedIndex()));  
				mapDifficulty(c1.getItemAt(c1.getSelectedIndex()));
			}
		});
	}

	
	@SuppressWarnings({ "deprecation"})
	public void mapGenerator(int s) {
		buttonPanel = new JPanel();
		buttonPanel.setVisible(true);
		buttonPanel.setLayout(new GridLayout(s, s));
		
		buttons = new JButton[s][s];
		
		System.out.println("Generation Size: " + s);
		
		//fix since resize() is depracated
		if(s == 8) {
			frame.resize(650, 650);
		}
		else if(s == 14) {
			frame.resize(850, 850);
		}
		else if(s == 20) {
			frame.resize(1050, 1050);
		}
		
		for (int row = 0; row < buttons.length; row++) {
			for (int col = 0; col < buttons[0].length; col++) {
				buttons[row][col] = new JButton();
				buttons[row][col].setFocusable(false);
				buttons[row][col].addActionListener(this);
				buttonPanel.add(buttons[row][col]);
			}
		}
		frame.add(buttonPanel);
		
		frame.revalidate();
		frame.setLocationRelativeTo(null);
				
	}
	
	public void mapDifficulty(Object object) {
		if (object.equals("Easy")) {
			size = 8;
		}
		else if(object.equals("Medium")) {
			size = 14;
		}
		else if(object.equals("Hard")) {
			size = 20;
		}
		
		mapGenerator(size);
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for(int i = 0; i < buttons.length; i++) {
			for(int j = 0; j < buttons[0].length; j++) {
				if(e.getSource() == buttons[i][j]) {
					System.out.println("Clicked");
				}
			}
		}
		
	}
	
}
