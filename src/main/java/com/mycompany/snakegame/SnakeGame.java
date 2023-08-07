package com.mycompany.snakegame;

import javax.swing.*;
import java.util.Objects;

public class SnakeGame extends JFrame {
    Board board;

    public SnakeGame(int a, int b, boolean inGame){
        setTitle("Snake Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setLocation(a, b);

        board = new Board();
        board.inGame = inGame;
        add(board);
        pack();

        setResizable(false);

        ImageIcon logo = new ImageIcon(Objects.requireNonNull(getClass().getResource("/icon.png")));
        setIconImage(logo.getImage());
    }
}