import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.TimeUnit;


public class GUI implements ActionListener, MouseListener, KeyListener {
    JFrame frame;
    JPanel buttonPanel;
    JButton[][] buttons;
    JComboBox<String> c1;
    int numBombs;
    int x;
    int y;
    //variables for gui and positioning
    
    CellGUI[][] cellGUIs;
    Icon flagIcon = new ImageIcon("assets/flag-80.png");
    Icon flagIcon2 = new ImageIcon("assets/flag-50.png");
    Icon flagIcon3 = new ImageIcon("assets/flag-20.png");
    
    //bombIcon assets for easy/med/hard map
    Icon bombIcon= new ImageIcon("assets/bomb-80.png");
    Icon bombIcon2 = new ImageIcon("assets/bomb-50.png");
    Icon bombIcon3 = new ImageIcon("assets/bomb-20.png");
    
    //80 is for the easy map
    Icon oneIcon80 = new ImageIcon("assets/nums/1-80.png");
    Icon oneIcon50 = new ImageIcon("assets/nums/1-50.png");
    Icon oneIcon20 = new ImageIcon("assets/nums/1-20.png");
    
    //50 is for the med map
    Icon twoIcon80 = new ImageIcon("assets/nums/2-80.png");
    Icon twoIcon50 = new ImageIcon("assets/nums/2-50.png");
    Icon twoIcon20 = new ImageIcon("assets/nums/2-20.png");

    //30 is for the hard map
    Icon threeIcon80 = new ImageIcon("assets/nums/3-80.png");
    Icon threeIcon50 = new ImageIcon("assets/nums/3-50.png");
    Icon threeIcon20 = new ImageIcon("assets/nums/3-20.png");
    
    Icon fourIcon80 = new ImageIcon("assets/nums/4-80.png");
    Icon fourIcon50 = new ImageIcon("assets/nums/4-50.png");
    Icon fourIcon20 = new ImageIcon("assets/nums/4-20.png");
    
    Icon fiveIcon80 = new ImageIcon("assets/nums/5-80.png");
    Icon fiveIcon50 = new ImageIcon("assets/nums/5-50.png");
    Icon fiveIcon20 = new ImageIcon("assets/nums/5-20.png");

    Icon sixIcon80 = new ImageIcon("assets/nums/6-80.png");
    Icon sixIcon50 = new ImageIcon("assets/nums/6-50.png");
    Icon sixIcon20 = new ImageIcon("assets/nums/6-20.png");
    
    Icon sevenIcon80 = new ImageIcon("assets/nums/7-80.png");
    Icon sevenIcon50 = new ImageIcon("assets/nums/7-50.png");
    Icon sevenIcon20 = new ImageIcon("assets/nums/7-20.png");
   
    //number assets for revealed
    Icon eightIcon80 = new ImageIcon("assets/nums/8-80.png");
    Icon eightIcon50 = new ImageIcon("assets/nums/8-50.png");
    Icon eightIcon20 = new ImageIcon("assets/nums/8-20.png");
    
    Icon startscreen = new ImageIcon("assets/start screen.png");
    Icon gamescreen = new ImageIcon("assets/gameOver.png");
    //asset creation for different difficulties/icon sizes
    boolean startGame = false;
    boolean lose = false;
    //variables for controlling game creation
    JLabel screen;
    
    SimpleAudioPlayer bgMusic = new SimpleAudioPlayer("assets/audio/bg.wav", true);
    //music player
    Moves listOfMoves = new Moves(3);
    static int size = 8; // Initialize size with a default value

    
    //main function that runs gui
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
        //creates all frames
    }
    
    
    //creates drop down generation for difficulties 
    public void dropdownGen() {
        String[] s1 = {"Easy", "Medium", "Hard"};
        c1 = new JComboBox<>(s1);
        c1.addActionListener(this);

        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(c1, BorderLayout.NORTH);

        frame.add(northPanel, BorderLayout.NORTH);
        
    }
    // creates map with bombs and portrays it
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
        
        //generates the buttons using Jbutton and Jframe
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
        //generates all bombs on empty 2d array
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
        
        //generates all other cells that aren't bombs with values(that indicate number of bombs around cell)
        boolean firstSquare = false;
        for (int row = 0; row < s; row++) {
            for (int col = 0; col < s; col++) {
            	if(cellGUIs[row][col]==null) {
            		int num = 0;
            		for(int i = row-1; i<=row+1 ; i++) {
            			for(int j = col-1; j<=col+1; j++) {
            				if((i>= 0 && j>=0)&&(i<s && j<s)) {
            					if(cellGUIs[i][j] != null && cellGUIs[i][j].getValue() == -1) {
                					num +=1;
                				}
            				}
            				
            			}
            		}
            		cellGUIs[row][col] = new CellGUI(num);
					if (!firstSquare && cellGUIs[row][col].getValue() == 0) {
						firstSquare = true;
						cellGUIs[row][col].reveal();
	                	buttons[row][col].setBackground(Color.white);
						
					}
            	}

            }
        }

        frame.add(buttonPanel, BorderLayout.CENTER);
    }
    //generates map based on difficulty
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
        
        
        
        if (e.getButton() == 3 && !lose) {
        	
            //right mouse click for flag button
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
        	//System.out.println("Left Clicked");
            
            if (cellGUIs[row][column].getState() == CellGUI.States.HIDDEN && !lose) {
            	listOfMoves.makeMove(row, column, false);
            	cellGUIs[row][column].reveal();
                System.out.println("Clicked");
                cellGUIs[row][column].reveal();
                System.out.println("value: " + cellGUIs[row][column].getValue());
                //all the different cases for different values portrayed on cell
                //different cases for different sizes/difficulty of map
            	switch (cellGUIs[row][column].getValue()) {
            	case 0:
            		
                	buttons[row][column].setBackground(Color.white);
            		break;
            		
                //different cases for different sizes/difficulty of map
            	case 1:
            		if(size == 8) {
                    	buttons[row][column].setIcon(oneIcon80);
                	}
                	else if (size == 14) {
                    	buttons[row][column].setIcon(oneIcon50);
                	}
                	else {
                    	buttons[row][column].setIcon(oneIcon20);
                	}
            		break;
                //different cases for different sizes/difficulty of map
            	case 2:
	        		if(size == 8) {
	                	buttons[row][column].setIcon(twoIcon80);
	            	}
	            	else if (size == 14) {
	                	buttons[row][column].setIcon(twoIcon50);
	            	}
	            	else {
	                	buttons[row][column].setIcon(twoIcon20);
	            	}
	        		break;
	        	//different cases for different sizes/difficulty of map
            	case 3:
	        		if(size == 8) {
	                	buttons[row][column].setIcon(threeIcon80);
	            	}
	            	else if (size == 14) {
	                	buttons[row][column].setIcon(threeIcon50);
	            	}
	            	else {
	                	buttons[row][column].setIcon(threeIcon20);
	            	}
	        		break;
	        	//different cases for different sizes/difficulty of map
            	case 4:
	        		if(size == 8) {
	                	buttons[row][column].setIcon(fourIcon80);
	            	}
	            	else if (size == 14) {
	                	buttons[row][column].setIcon(fourIcon50);
	            	}
	            	else {
	                	buttons[row][column].setIcon(fourIcon20);
	            	}
	        		break;
	        	//different cases for different sizes/difficulty of map
            	case 5:
	        		if(size == 8) {
	                	buttons[row][column].setIcon(fiveIcon80);
	            	}
	            	else if (size == 14) {
	                	buttons[row][column].setIcon(fiveIcon50);
	            	}
	            	else {
	                	buttons[row][column].setIcon(fiveIcon20);
	            	}
	        		break;
	        	//different cases for different sizes/difficulty of map
            	case 6:
	        		if(size == 8) {
	                	buttons[row][column].setIcon(sixIcon80);
	            	}
	            	else if (size == 14) {
	                	buttons[row][column].setIcon(sixIcon50);
	            	}
	            	else {
	                	buttons[row][column].setIcon(sixIcon20);
	            	}
	        		break;
	        	//different cases for different sizes/difficulty of map
            	case 7:
	        		if(size == 8) {
	                	buttons[row][column].setIcon(sevenIcon80);
	            	}
	            	else if (size == 14) {
	                	buttons[row][column].setIcon(sevenIcon50);
	            	}
	            	else {
	                	buttons[row][column].setIcon(sevenIcon20);
	            	}
	        		break;
	        	//different cases for different sizes/difficulty of map
            	case 8:
	        		if(size == 8) {
	                	buttons[row][column].setIcon(eightIcon80);
	            	}
	            	else if (size == 14) {
	                	buttons[row][column].setIcon(eightIcon50);
	            	}
	            	else {
	                	buttons[row][column].setIcon(eightIcon20);
	            	}
	        		break;
	        	//different cases for different sizes/difficulty of map
            	case -1:

	        		if(size == 8) {
	                	buttons[row][column].setIcon(bombIcon);
	            	}
	            	else if (size == 14) {
	                	buttons[row][column].setIcon(bombIcon2);
	            	}
	            	else {
	                	buttons[row][column].setIcon(bombIcon3);
	            	}
	        		try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	        		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));

	        		lose = true;
            	}
        	}
        } 
    }
    
    //left mouse click    
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == c1) {
            lose = false;
            mapDifficulty(c1.getSelectedItem());
        }
    }
    
    //uses data structures to implement an undo method
    
    private void undo() {
    	Move m = listOfMoves.lastMove();
    	int row = m.x;
    	int col = m.y;
    	if (lose) {
    		listOfMoves.lives--;
    		if (listOfMoves.lives<0) {
    			//todo activate game over
    		}
    		else {
    			lose = false;
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
	//checks to see if the Z button is clicked for start screen
	public void keyPressed(KeyEvent e) {
    	if (e.getKeyCode() == 90) {
    		startGame = true;
    		screen.setIcon(null);
	    	dropdownGen();
	        mapGenerator(size);
    	}
    	//System.out.println(e.getKeyCode());
		
	}
}
