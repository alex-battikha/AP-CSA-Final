import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI2 implements ActionListener {
    JFrame frame;
    JPanel buttonPanel;
    CellGUI2[][] cells; // Array of CellGUI instances
    JComboBox<String> c1;
    
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

        buttonPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (int row = 0; row < s; row++) {
                    for (int col = 0; col < s; col++) {
                        cells[row][col].draw(g); // Draw each CellGUI instance
                    }
                }
            }
        };

        buttonPanel.setPreferredSize(new Dimension(s * 50, s * 50)); // Set preferred size
        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.revalidate(); // Revalidate the frame
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == c1) {
            mapDifficulty(c1.getSelectedItem());
        } else {
            for (int i = 0; i < cells.length; i++) {
                for (int j = 0; j < cells[0].length; j++) {
                    if (e.getSource() == cells[i][j]) {
                        
                        cells[i][j].reveal(); 
                        //cells[i][j].flag(); 
                        // System.out.println("Clicked");
                    }
                }
            }
        }
    }
}
