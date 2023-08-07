package com.mycompany.snakegame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

public class StartPage extends JFrame {
    JPanel bgPanel, mainPanel;

    public StartPage(){
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((dim.width/2)-200, (dim.height/2)-200);

        setTitle("Snake Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        ImageIcon logo = new ImageIcon(Objects.requireNonNull(getClass().getResource("/icon.png")));
        setIconImage(logo.getImage());

        this.bgPanel = createHomepagePanel();

        mainPanel = new JPanel(new CardLayout());
        mainPanel.add(bgPanel, "HomepagePanel");

        setContentPane(mainPanel);
        pack();
        setLocationRelativeTo(null);
    }

    public JPanel createHomepagePanel(){
        JPanel bgPanel = new JPanel(new BorderLayout());
        bgPanel.setOpaque(false);

        // Create a panel to hold the play button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        // Create a play button label
        JLabel playButton = new JLabel();
        ImageIcon playButtonImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/button.png")));
        playButton.setIcon(playButtonImage);

        // Adding the mouse click event listener
        playButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                startGame();
            }
        });

        buttonPanel.add(playButton);

        // Create a background label
        JLabel backgroundLabel = new JLabel();
        ImageIcon backgroundImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/bg.png")));
        backgroundLabel.setIcon(backgroundImage);

        // Add the button panel and background label to the homepage panel
        bgPanel.add(backgroundLabel, BorderLayout.CENTER);
        bgPanel.add(buttonPanel, BorderLayout.SOUTH);
        return bgPanel;
    }

    public void startGame() {
        // Turn off the visibility of start page
        setVisible(false);

        // creating the game object and turning on the visibility
        new SnakeGame(getLocation().x, getLocation().y, true).setVisible(true);
    }

    public static void main(String[] args) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StartPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold

        /* Create and display the form */
        java.awt.EventQueue.invokeLater (() -> new StartPage().setVisible(true));
    }
}