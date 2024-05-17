import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI implements ActionListener {
    JFrame frame;
    JPanel buttonPanel;
    JButton[][] buttons;
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
        mapGenerator(size); // Call mapGenerator with the default size

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

        for (int row = 0; row < s; row++) {
            for (int col = 0; col < s; col++) {
                buttons[row][col] = new JButton();
                buttons[row][col].setFocusable(false);
                buttons[row][col].setFont(new Font("HV Boli", Font.BOLD, 12));
                buttons[row][col].addActionListener(this);
                buttons[row][col].setText("");
                buttonPanel.add(buttons[row][col]);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == c1) {
            mapDifficulty(c1.getSelectedItem());
        } else {
            for (int i = 0; i < buttons.length; i++) {
                for (int j = 0; j < buttons[0].length; j++) {
                    if (e.getSource() == buttons[i][j]) {
                        System.out.println("Clicked");
                    }
                }
            }
        }
    }
}
