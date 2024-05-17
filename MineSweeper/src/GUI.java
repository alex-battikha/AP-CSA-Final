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

    public static void main(String[] args) { // Added static modifier
        GUI gui = new GUI(); // Create an instance of GUI
        gui.createAndShowGUI(); // Call the method to create and show the GUI
    }

    public void createAndShowGUI() { // Renamed from main to createAndShowGUI
    	frame = new JFrame("MineSweeper");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        //textPanel = new JPanel();
        //frame.add(textPanel, BorderLayout.NORTH);

        dropdownGen();

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
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
                mapDifficulty(c1.getItemAt(c1.getSelectedIndex()));
            }
        });
    }

    @SuppressWarnings({"deprecation"})
    public void mapGenerator(int s) {
        // Remove the old buttonPanel if it exists
        if (buttonPanel != null) {
            frame.remove(buttonPanel);
        }
        
        // Create a new buttonPanel
        buttonPanel = new JPanel();
        buttonPanel.setVisible(true);
        buttonPanel.setLayout(new GridLayout(s, s));

        buttons = new JButton[s][s];

        System.out.println("Generation Size: " + s);

        // Set frame size based on difficulty
        if(s == 8) {
            frame.setSize(400, 400); // Changed resize to setSize
        }
        else if(s == 14) {
            frame.setSize(700, 700);
        }
        else if(s == 20) {
            frame.setSize(1000, 1000);
        }

        // Create buttons and add them to buttonPanel
        for (int row = 0; row < buttons.length; row++) {
            for (int col = 0; col < buttons[0].length; col++) {
                buttons[row][col] = new JButton();
                buttons[row][col].setFocusable(false);
                buttons[row][col].setFont(new Font("HV Boli", Font.BOLD, 12));
                buttons[row][col].addActionListener(this);
                buttons[row][col].setText("");
                buttonPanel.add(buttons[row][col]);
            }
        }
        
        // Add buttonPanel to frame
        frame.add(buttonPanel);
        
        // Revalidate frame to update layout
        frame.revalidate();
        frame.repaint(); // Repaint to ensure changes are visible
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
