/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import Model.Bird;
import Model.Pipe;
import Model.Sound;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 *
 * @author ADMIN
 */
public class PipeArray extends Thread {

    private ArrayList<Pipe> pipes;
    Random random = new Random();
    Image topPipeImg;
    Image bottomPipeImg;
    private int pipeX;
    private int pipeY;
    private int pipeWidth;
    private int pipeHeight;
    private int velocityX = -4;// moves pipes to the left speed (simulates bird moving right)
    public double score = 0;

    // am thanh
    private Sound pointSound;

    public PipeArray(int pipeX, int pipeY, int pipeWidth, int pipeHeight) {

        pipes = new ArrayList<Pipe>();

        try {
            topPipeImg = new ImageIcon("./Assert/toppipe.png").getImage();
            bottomPipeImg = new ImageIcon("./Assert/bottompipe.png").getImage();
            pointSound = new Sound("./Assert/Point.wav");
        } catch (Exception ex) {
            System.out.println("Not found file.");
        }

        this.pipeX = pipeX;
        this.pipeY = pipeY;
        this.pipeWidth = pipeWidth;
        this.pipeHeight = pipeHeight;
    }

    public ArrayList<Pipe> getPipes() {
        return pipes;
    }

    public void setPipes(ArrayList<Pipe> pipes) {
        this.pipes = pipes;
    }

    public double Score(Bird bird) {
        for (int i = 0; i < pipes.size(); i++)
            if (!pipes.get(i).isPass() && bird.getX() > pipes.get(i).getX() + pipes.get(i).getPipeWidth() / 2) {
                pointSound.play();
                score += 0.5;
                pipes.get(i).setPass(true);
            }
        return score;
    }

    public void placePipe() {
        // Math.random() {0,1} * pipeHeight/2 -> (0-256)
        // pipeHeight/4 = 128
        // 0 - 128 - (0-256) -> (pipeHeight/4 - pipeHeight/2) = -3/4 pipeHeight
        // --> toa độ cột trên trong khoảng [-384,-128]

        int randomPipeY = (int) (pipeY - pipeHeight / 4 - Math.random() * (pipeHeight / 2));
        int openingSpace = 600 / 5; // khoảng cách giữa 2 cột = chiều_cộng_màn_hinh/4
        Pipe topPipe = new Pipe(topPipeImg, pipeX, pipeY, pipeWidth, pipeHeight);
        topPipe.setY(randomPipeY);// cột trên
        pipes.add(topPipe);

        Pipe bottomPipe = new Pipe(bottomPipeImg, pipeX, pipeY, pipeWidth, pipeHeight);
        bottomPipe.setY(topPipe.getY() + pipeHeight + openingSpace);
        // lay toa do y cua topPipe + chieu cao cua pipe + khoang trong ra toa do cua
        // bottomPipe
        pipes.add(bottomPipe);
    }

    public boolean Collision(Bird a, Pipe b) {
        return a.getY() <= 0 || a.getY() >= 530 ||
                (a.getX() < b.getX() + b.getPipeWidth() && // a's top left corner doesn't reach b's top right corner
                // con chim o phia ben trai cua cot
                        a.getX() + a.getWidth() > b.getX() && // a's top right corner passes b's top left corner
                        // ca con chim ma lon hon -> cham vao than cot -> dam vao cot
                        a.getY() < b.getY() + b.getPipeHeight() && // a's top left corner doesn't reach b's bottom left
                                                                   // corner
                        // dau con chim cham vao day cai cot phia tren
                        a.getY() + a.getHeight() > b.getY() // a's bottom left corner passes b's top left corner
                // mong con chim cham vao dinh cai cot phia duoi ;
                );
    }

    public boolean Collision(Bird a) {
        boolean b = false;
        for (int i = 0; i < pipes.size(); i++) {
            if (Collision(a, pipes.get(i))) {
                b = true;
                // trả về bird die --> không con đập cánh
                a.setLive(false);
            }
        }
        return b;
    }

    public void restart() {
        // pipes = new ArrayList<Pipe>();
        pipes.clear();
        score = 0;
    }

    public void updatePipe() {
        for (int i = 0; i < pipes.size(); i++) {
            pipes.get(i).update();
        }

    }

    public void paint(Graphics2D g2) {
        for (int i = 0; i < pipes.size(); i++)
            pipes.get(i).paint(g2);
    }

    @Override
    public void run() {

        while (true) {
            long start = System.currentTimeMillis();
            placePipe();
            long end = System.currentTimeMillis();

            long sleeptime = end - start;
            if (sleeptime < 1200)
                sleeptime = 1200 - (end - start);
            else
                sleeptime = 0;

            try {
                Thread.sleep(sleeptime);
            } catch (InterruptedException ex) {
                Logger.getLogger(PipeArray.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
}
