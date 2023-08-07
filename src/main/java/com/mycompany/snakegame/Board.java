package com.mycompany.snakegame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Objects;

public class Board extends JPanel implements ActionListener {
    int B_HEIGHT = 400;
    int B_WIDTH = 400;

    int MAX_DOTS = 1600;
    int DOT_SIZE = 10;
    int DOTS;

    int[] x = new int[MAX_DOTS];
    int[] y = new int[MAX_DOTS];

    int apple_x;
    int apple_y;

    Image body, head, apple;

    Timer timer;
    int Delay = 200;
    char direction;
    boolean inGame = false;

    Board(){
        TAdapter tAdapter = new TAdapter();
        addKeyListener(tAdapter);
        setFocusable(true);

        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        setBackground(Color.BLACK);

        initGame();
        loadImages();
    }

    public void initGame(){
        // initialize snake size
        DOTS = 3;

        // initialize snake size
        direction = 'l';

        // initialize snake head position
        x[0] = 200;
        y[0] = 50;

        // initialize snake body
        for(int i = 1; i<DOTS; i++){
            x[i] = x[0] + DOT_SIZE*i;
            y[i] = y[0];
        }

        // initialize apple position
        locateApple();

        // create and start timer
        timer = new Timer(Delay, this);
        timer.start();
    }

    // Load the images from resources folder
    public void loadImages(){
        ImageIcon bodyIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/dot.png")));
        body = bodyIcon.getImage();

        ImageIcon headIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/head.png")));
        head = headIcon.getImage();

        ImageIcon appleIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/apple.png")));
        apple = appleIcon.getImage();
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        doDrawing(g);
    }

    public void doDrawing(Graphics g)
    {
        g.drawImage(apple, apple_x, apple_y, this);
        for (int i = 1; i < DOTS; i++) {
            g.drawImage(body, x[i], y[i], this);
        }
        g.drawImage(head, x[0], y[0], this);
        if(!inGame) {
            gameOver(g);
            timer.stop();
        }
    }

    // Show game over and score
    public void gameOver(Graphics g){
        String msg = "GAME  OVER";
        int score = (DOTS-3)*100;
        String scoremsg = "SCORE  :  "+ score;
        Font small = new Font("Chiller", Font.BOLD, 26);
        FontMetrics fontMetrics = getFontMetrics(small);

        g.setColor(Color.WHITE);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH-fontMetrics.stringWidth(msg))/2, B_HEIGHT/4);
        g.drawString(scoremsg, (B_WIDTH-fontMetrics.stringWidth(scoremsg))/2, 3*B_HEIGHT/4);
    }

    // locate random position for apple
    public void locateApple(){
        apple_x = (int)(Math.random()*39)*DOT_SIZE;
        apple_y = (int)(Math.random()*39)*DOT_SIZE;
    }

    // Check collision
    public void checkCollision(){
        // Body collision
        for(int i = 4; i<DOTS; i++){
            if(x[0] == x[i] && y[0] == y[i]){
                inGame = false;
                break;
            }
        }

        // Border Collision
        if(x[0]<0 || x[0]>B_WIDTH || y[0]<0 || y[0]>B_HEIGHT)
            inGame = false;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(inGame){
            checkApple();
            move();
            checkCollision();
        }
        repaint();
    }

    // to eat apple
    public void checkApple(){
        if(apple_x == x[0] && apple_y == y[0]){
            DOTS++;
            if(Delay>=50) Delay-=50;
            locateApple();
        }
    }

    // snake move
    public void move(){
        for(int i = DOTS-1; i>=1; i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        switch (direction) {
            case 'l' -> x[0] -= DOT_SIZE;
            case 'r' -> x[0] += DOT_SIZE;
            case 'u' -> y[0] -= DOT_SIZE;
            case 'd' -> y[0] += DOT_SIZE;
        }
    }

    // Snake controlling
    private class TAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent keyEvent){
            int key = keyEvent.getKeyCode();

            if(key == KeyEvent.VK_LEFT && direction!='r')
                direction = 'l';
            else if(key == KeyEvent.VK_RIGHT && direction!='l')
                direction = 'r';
            else if(key == KeyEvent.VK_UP && direction!='d')
                direction = 'u';
            else if(key == KeyEvent.VK_DOWN && direction!='u')
                direction = 'd';
        }
    }
}