import javax.swing.*;

import java.awt.*;
import java.awt.event.*;


public class GUI implements ActionListener, MouseListener, KeyListener {
    JFrame frame;
    JPanel buttonPanel;
    JButton[][] buttons;
    JComboBox<String> c1;
    int numBombs;
    int x;
    int y;
    
    CellGUI[][] cellGUIs;
    Icon flagIcon = new ImageIcon("assets/flag-80.png");
    Icon flagIcon2 = new ImageIcon("assets/flag-50.png");
    Icon flagIcon3 = new ImageIcon("assets/flag-20.png");
    
    Icon bombIcon= new ImageIcon("assets/bomb-80.png");
    Icon bombIcon2 = new ImageIcon("assets/bomb-50.png");
    Icon bombIcon3 = new ImageIcon("assets/bomb-20.png");
    
    Icon startscreen = new ImageIcon("assets/start screen.png");
    boolean startGame = false;
    
    JLabel screen;
    
    SimpleAudioPlayer bgMusic = new SimpleAudioPlayer("assets/audio/bg.wav", true);
    
    Moves listOfMoves = new Moves(3);
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
        screen = new JLabel();
        screen.setIcon(startscreen);
        
        frame.addKeyListener(this);

        frame.add(screen, BorderLayout.CENTER);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
        
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
        
        
        if(s==8) {
        	numBombs = 10;
        }
        if(s==14) {
        	numBombs = 40;
        }
        
        if(s==20) {
        	numBombs = 99;
        }
        
        for (int row = 0; row < s; row++) {
            for (int col = 0; col < s; col++) {
                buttons[row][col] = new JButton();
                buttons[row][col].setFocusable(false);
                buttons[row][col].setFont(new Font("HV Boli", Font.BOLD, 12));
                buttons[row][col].addMouseListener(this);

                buttons[row][col].putClientProperty("row", row);
                buttons[row][col].putClientProperty("column", col); 
                
//                buttons[row][col].addActionListener(this);
                buttons[row][col].setText("");
                buttonPanel.add(buttons[row][col]);
                
//                cellGUIs[row][col] = new CellGUI(0);

            }
        }
        
        while(numBombs > 0) {
        	x = (int)(Math.random()*(s));
        	y = (int)(Math.random()*(s));

        	if(cellGUIs[x][y] == null) {
        		cellGUIs[x][y] = new CellGUI(-1);
        		numBombs -=1;
        		System.out.println("X: " + x + " Y: " + y);
        		System.out.println(numBombs);
        	}
        	else {
        		continue;
        	}
        }
        
        for (int row = 0; row < s; row++) {
            for (int col = 0; col < s; col++) {
            	if(cellGUIs[row][col]==null) {
            		int num = 0;
            		for(int i = row-1; i<=row+1 ; i++) {
            			for(int j = col-1; j<=col+1; j++) {
            				if((i>= 0 && j>=0)&&(i<s && j<s)) {
            					if(cellGUIs[i][j] != null) {
                					num +=1;
                				}
            				}
            				
            			}
            		}
            		cellGUIs[row][col] = new CellGUI(num);
            	}

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
        JButton button = (JButton) e.getSource();
        button.requestFocusInWindow();
        int row = (int) button.getClientProperty("row");
        int column = (int) button.getClientProperty("column");
        
        
        
        if (e.getButton() == 3) {
        	
            
            if(cellGUIs[row][column].getState() == CellGUI.States.HIDDEN) {
            	cellGUIs[row][column].flag();
            	if(size == 8) {
                	buttons[row][column].setIcon(flagIcon);
            	}
            	else if (size == 14) {
                	buttons[row][column].setIcon(flagIcon2);
            	}
            	else {
                	buttons[row][column].setIcon(flagIcon3);
            	}
                listOfMoves.makeMove((int) button.getClientProperty("row"), (int) button.getClientProperty("column"), true);
            }
            else if(cellGUIs[row][column].getState() == CellGUI.States.FLAGGED) {
            	cellGUIs[row][column].hide();
            	buttons[row][column].setIcon(null);
                listOfMoves.makeMove((int) button.getClientProperty("row"), (int) button.getClientProperty("column"), true);
            }
            
            System.out.println("Right Clicked on row: " + button.getClientProperty("row") +
                    ", column: " + button.getClientProperty("column"));
        }
        
        else if (e.getButton() == 1) {
        	
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
            
            if (cellGUIs[row][column].getState() == CellGUI.States.HIDDEN) {
            	listOfMoves.makeMove(row, column, false);
            	cellGUIs[row][column].reveal();
                System.out.println("Clicked");
                cellGUIs[row][column].reveal();
            	switch (cellGUIs[row][column].getValue()) {
            	case 1:
            		if(size == 8) {
                    	buttons[row][column].setIcon(flagIcon);
                	}
                	else if (size == 14) {
                    	buttons[row][column].setIcon(flagIcon2);
                	}
                	else {
                    	buttons[row][column].setIcon(flagIcon3);
                	}
	            case 2:
	        		if(size == 8) {
	                	buttons[row][column].setIcon(flagIcon);
	            	}
	            	else if (size == 14) {
	                	buttons[row][column].setIcon(flagIcon2);
	            	}
	            	else {
	                	buttons[row][column].setIcon(flagIcon3);
	            	}
	            case 3:
	        		if(size == 8) {
	                	buttons[row][column].setIcon(flagIcon);
	            	}
	            	else if (size == 14) {
	                	buttons[row][column].setIcon(flagIcon2);
	            	}
	            	else {
	                	buttons[row][column].setIcon(flagIcon3);
	            	}
	            case 5:
	        		if(size == 8) {
	                	buttons[row][column].setIcon(flagIcon);
	            	}
	            	else if (size == 14) {
	                	buttons[row][column].setIcon(flagIcon2);
	            	}
	            	else {
	                	buttons[row][column].setIcon(flagIcon3);
	            	}
	            case 6:
	        		if(size == 8) {
	                	buttons[row][column].setIcon(flagIcon);
	            	}
	            	else if (size == 14) {
	                	buttons[row][column].setIcon(flagIcon2);
	            	}
	            	else {
	                	buttons[row][column].setIcon(flagIcon3);
	            	}
	            case 7:
	        		if(size == 8) {
	                	buttons[row][column].setIcon(flagIcon);
	            	}
	            	else if (size == 14) {
	                	buttons[row][column].setIcon(flagIcon2);
	            	}
	            	else {
	                	buttons[row][column].setIcon(flagIcon3);
	            	}
	            case 8:
	        		if(size == 8) {
	                	buttons[row][column].setIcon(flagIcon);
	            	}
	            	else if (size == 14) {
	                	buttons[row][column].setIcon(flagIcon2);
	            	}
	            	else {
	                	buttons[row][column].setIcon(flagIcon3);
	            	}
            }
        	}
        }
    }
    
    private void undo() {
    	Move m = listOfMoves.lastMove();
    	int row = m.x;
    	int col = m.y;
    	if (cellGUIs[row][col].getValue() == -1) {
    		listOfMoves.lives--;
    		if (listOfMoves.lives<0) {
    			//todo activate game over
    		}
    	}
    	cellGUIs[row][col].hide();
    	buttons[row][col].setIcon(null);
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

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
    	if (e.getKeyCode() == 90) {
    		startGame = true;
    		screen.setIcon(null);
	    	dropdownGen();
	        mapGenerator(size);
    	}
    	System.out.println(e.getKeyCode());
		
	}
}
