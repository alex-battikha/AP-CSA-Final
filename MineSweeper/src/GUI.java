import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class GUI implements ActionListener, MouseListener {
    JFrame frame;
    JPanel buttonPanel;
    JButton[][] buttons;
    JComboBox<String> c1;

    CellGUI[][] cellGUIs;
    Icon flagIcon = new ImageIcon("assets/flag-80.png");
    

    Moves listOfMoves = new Moves();
    static int size = 8; // Initialize size with a default value

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GUI gui = new GUI();
            gui.createAndShowGUI();
        });
    }

    public void createAndShowGUI() {
        frame = new JFrame("MineSweeper");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        dropdownGen();
        mapGenerator(size);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        frame.setSize(800, 800);

    }

    public void dropdownGen() {
        String[] s1 = {"Easy", "Medium", "Hard"};
        c1 = new JComboBox<>(s1);
        c1.addActionListener(this);

        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(c1, BorderLayout.NORTH);

        frame.add(northPanel, BorderLayout.NORTH);
    }

    public void mapGenerator(int s) {
        if (buttonPanel != null) {
            frame.remove(buttonPanel);
        }

        buttonPanel = new JPanel(new GridLayout(s, s));
        buttons = new JButton[s][s];
        cellGUIs = new CellGUI[s][s];

        for (int row = 0; row < s; row++) {
            for (int col = 0; col < s; col++) {
                buttons[row][col] = new JButton();
                buttons[row][col].setFocusable(false);
                buttons[row][col].setFont(new Font("HV Boli", Font.BOLD, 12));
                buttons[row][col].addMouseListener(this);

                buttons[row][col].putClientProperty("row", row);
                buttons[row][col].putClientProperty("column", col); 
                
                buttons[row][col].addActionListener(this);
                buttons[row][col].setText("");
                buttonPanel.add(buttons[row][col]);
                
                cellGUIs[row][col] = new CellGUI(0);

            }
        }

        frame.add(buttonPanel, BorderLayout.CENTER);
    }

    public void mapDifficulty(Object object) {
        if (object.equals("Easy")) {
            size = 8;
        } else if (object.equals("Medium")) {
            size = 14;
        } else if (object.equals("Hard")) {
            size = 20;
        }
        mapGenerator(size);
        frame.revalidate();
    }

    //right mouse click
    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)) {
        	
            JButton button = (JButton) e.getSource();
            listOfMoves.makeMove((int) button.getClientProperty("row"), (int) button.getClientProperty("column"));
            int row = (int) button.getClientProperty("row");
            int column = (int) button.getClientProperty("column");
            
            if(cellGUIs[row][column].getState() == CellGUI.States.HIDDEN) {
            	cellGUIs[row][column].flag();
            	buttons[row][column].setIcon(flagIcon);
            }
            else if(cellGUIs[row][column].getState() == CellGUI.States.FLAGGED) {
            	cellGUIs[row][column].hide();
            	buttons[row][column].setIcon(null);
            }
            
            System.out.println("Right Clicked on row: " + button.getClientProperty("row") +
                    ", column: " + button.getClientProperty("column"));
        }
    }
    
    //left mouse click    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == c1) {
            mapDifficulty(c1.getSelectedItem());
        } else if (e.getSource() instanceof JButton) {
            JButton button = (JButton) e.getSource();
            int row = (int) button.getClientProperty("row");
            int column = (int) button.getClientProperty("column");
            
            System.out.println("Clicked");
            cellGUIs[row][column].reveal();
        }
    }


	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
