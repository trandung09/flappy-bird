/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import controller.PipeArray;
import Model.Bird;
import Model.Ground;
import Model.Sound;
import controller.Controller;
import controller.Score;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 *
 * @author ADMIN
 */
public class FlappyBird extends JPanel implements Runnable {
    private int boardWidth = 600;
    private int boardHeight = 600;

    // load backgound img
    private Image backgroundImg;
    private Image beginImg;
    private Image groundImg;

    Thread gameThread;
    // tao object bird
    private Bird bird;
    private Ground ground;
    private Image overGame;
    private Image birdcoin;
    private Image scoreboard;
    private Image newscoreboard;
    private Image crown;
    private ImageIcon button;

    // PIPE
    int pipeX = 600;
    // pipes o goc tren ben phai cua man hinh
    int pipeY = 0;
    int pipeWidth = 80; // scaled by 1/6
    int pipeHeight = 500; // 472
    PipeArray pipes;

    // am thanh
    private Sound hitSound, wingSound, newgameSound;

    // score
    Score max;
    private double score = 0;
    private double maxScore;

    // BOOLEAN
    public boolean play = false;
    public int BEGIN_SCREEN = 0;
    public int PLAY_SCREEN = 1;
    public int GAMEOVER_SCREEN = 2;
    public int CURRENT_SCREEN = BEGIN_SCREEN;

    // button
    private JButton restart;
    private JButton quit;

    // Listener
    Controller controller = new Controller(this);

    public FlappyBird() throws IOException {
        this.setPreferredSize(new Dimension(boardWidth, boardHeight));

        try {
            // am thanh
            wingSound = new Sound("./Assert/Wing.wav");
            hitSound = new Sound("./Assert/Hit.wav");
            newgameSound = new Sound("./Assert/Congratulation.wav");

            beginImg = new ImageIcon("./Assert/message.png").getImage();
            backgroundImg = new ImageIcon("./Assert/Background.png").getImage();
            groundImg = new ImageIcon("./Assert/Ground.png").getImage();

            // over game
            overGame = new ImageIcon("./Assert/gameover.png").getImage();
            birdcoin = new ImageIcon("./Assert/birdcoin.png").getImage();
            scoreboard = new ImageIcon("./Assert/scoreboard.png").getImage();
            newscoreboard = new ImageIcon("./Assert/newscore.png").getImage();
            crown = new ImageIcon("./Assert/crown.png").getImage();
            button = new ImageIcon("./Assert/scoreboard.png");

        } catch (Exception ex) {
            System.out.println("Not found file.");
        }

        // thiet lap kich thuoc cho bird
        int birdX = boardWidth / 8; // toa do x
        int birdY = boardHeight / 2;
        int birdWidth = 34;
        int birdHeight = 24;
        float velocity = 0;
        bird = new Bird(birdX, birdY, birdWidth, birdHeight, velocity);

        // pipe
        pipes = new PipeArray(pipeX, pipeY, pipeWidth, pipeHeight);

        // ground
        ground = new Ground(groundImg);

        // score
        max = new Score();

        // button
        Font b = new Font("Consolas", Font.BOLD, 11);
        restart = new JButton("restart");
        restart.setFont(b);
        restart.setIcon(button);
        restart.setHorizontalTextPosition(JButton.CENTER);
        restart.setBounds(200, 450, 80, 35);
        restart.addActionListener(controller);

        quit = new JButton("quit");
        quit.setIcon(button);
        quit.setHorizontalTextPosition(JButton.CENTER);
        quit.setFont(b);
        quit.setBounds(300, 450, 80, 35);
        quit.addActionListener(controller);

        // add Key Listener
        this.addKeyListener(controller);
        this.setFocusable(true);

        bird.start();
        pipes.start();
        this.GameStart();
    }

    // Khoi chay luong
    public void GameStart() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    // Reset game
    public void ResetGame() {
        // Đặt lại vị trí ban đâu cho con chim
        bird.setX(boardWidth / 8);
        bird.setY(boardHeight / 2);
        bird.setLive(true);

        // đặt lại vận tốc
        bird.setVelocity(0);
        // resart lại đặt cột
        pipes.restart();

    }

    public JButton restart() {
        return restart;
    }

    public JButton quit() {
        return quit;
    }

    public void paintScore(Graphics2D g2) {
        Font font = new Font("Broadway", Font.BOLD, 30);
        g2.setColor(Color.white);
        g2.setFont(font);

        // score
        g2.drawImage(birdcoin, 30 - 30 / 2, 30 - 30 / 2, 30, 30, null);
        g2.drawString(" " + (int) score, 50, 40);

        maxScore = max.readScore();
        g2.drawImage(crown, 180 - 35 / 2, 30 - 40 / 2, 35, 35, null);
        g2.drawString(" " + (int) maxScore, 200, 40);
    }

    public void endgameScore(Graphics2D g2) {
        Font font = new Font("Broadway", Font.BOLD, 75);
        g2.setColor(Color.white);
        g2.setFont(font);

        if (score <= maxScore) {
            g2.drawImage(scoreboard, 300 - 300 / 2, 300 - 100 / 2, 300, 150, null);
            g2.drawImage(birdcoin, (300 - 300 / 2) + 35, (300 - 100 / 2) + 35, 65, 65, null);
            g2.drawString(" " + (int) score, (300 - 300 / 2) + 120, (300 - 100 / 2) + 95);

        } else {
            g2.setColor(Color.RED);
            g2.drawImage(newscoreboard, 300 - 300 / 2, 300 - 100 / 2, 300, 150, null);
            g2.drawImage(crown, (300 - 300 / 2) + 35, (300 - 100 / 2) + 30, 70, 70, null);
            g2.drawString(" " + (int) score, (300 - 300 / 2) + 120, (300 - 100 / 2) + 95);
        }

    }

    // ham de ve cac hinh len panel
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        // draw background
        g2.drawImage(backgroundImg, 0, 0, boardWidth, boardHeight, null);

        if (CURRENT_SCREEN == BEGIN_SCREEN) {
            bird.Draw(g2);
            ground.paint(g2);
            g2.drawImage(beginImg, (600 - 200) / 2, (600 - 400) / 2, 200, 300, null);
            ResetGame();
        } else if (CURRENT_SCREEN == PLAY_SCREEN) {
            pipes.paint(g2);
            bird.Draw(g2);
            ground.paint(g2);
            paintScore(g2);

        } else if (CURRENT_SCREEN == GAMEOVER_SCREEN) {
            pipes.paint(g2);
            bird.Draw(g2);
            ground.paint(g2);
            g2.drawImage(overGame, 300 - 300 / 2, 600 / 4, 300, 68, null);
            endgameScore(g2);

        }

    }

    public void setVelocity(int v) {
        wingSound.play();
        this.bird.setVelocity(v);
    }

    public void Update() {

        if (CURRENT_SCREEN == BEGIN_SCREEN) {
            this.remove(restart);
            this.remove(quit);
            // man hinh begin
            ground.update();
        } else if (CURRENT_SCREEN == PLAY_SCREEN) {
            // man hinh play game
            ground.update();
            if (play == true) {
                this.bird.update();
            }
            pipes.updatePipe();
            score = pipes.Score(bird);

            if (pipes.Collision(bird)) {
                hitSound.play();
                if (score > maxScore)
                    newgameSound.play();
                CURRENT_SCREEN = GAMEOVER_SCREEN;
            }
        } else if (CURRENT_SCREEN == GAMEOVER_SCREEN) {
            max.writeScore(score);
            this.add(restart);
            this.add(quit);
        }
    }

    @Override
    public void run() {
        // thiet lap thoi gian ve lai
        double FPS1 = 1000 / 60;
        double delta = 0;
        long lastTime = System.currentTimeMillis();
        long currentTime;

        while (gameThread != null) {
            // game loop
            currentTime = System.currentTimeMillis();
            delta += (currentTime - lastTime) / FPS1;
            // curentTime - lastTime de tinh thoi gian da qua
            // chia cho FPS1 de tinh delta --> delta la so frame
            // se ve trong thoi gian vua roi

            lastTime = currentTime;
            if (delta >= 1) {
                Update();
                repaint();
                delta--;
            }

        }

    }

}
