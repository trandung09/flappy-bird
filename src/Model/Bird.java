/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import controller.PipeArray;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author ADMIN
 */
public class Bird extends Thread {

    private BufferedImage[] birdimg;

    private int x; // toa do x
    private int y; // toa do y
    private int width; // chieu rong dai
    private int height; // chie rong
    private float velocityY; // van toc bay len
    float gravity = 0.25f;
    private boolean live = true;
    // đánh dấu bird còn sống hay không

    // chi so anh
    int indeximg = 0;

    public Bird(int birdX, int birdY, int birdWidth, int birdHeidgt, float v) {
        this.x = birdX;
        this.y = birdY;
        this.width = birdWidth;
        this.height = birdHeidgt;
        this.velocityY = v;

        BufferedImage img1 = null, img2 = null, img3 = null;

        try {
            img1 = ImageIO.read(new File("./Assert/yellowbird-upflap.png"));
            img2 = ImageIO.read(new File("./Assert/yellowbird-upflap.png"));
            img3 = ImageIO.read(new File("./Assert/yellowbird-downflap.png"));
        } catch (IOException ex) {
            Logger.getLogger(Bird.class.getName()).log(Level.SEVERE, null, ex);
        }

        birdimg = new BufferedImage[] { img1, img2, img3, img2 };

    }

    public int getX() {
        return x;
    }

    public void setX(int birdX) {
        this.x = birdX;
    }

    public int getY() {
        return y;
    }

    public void setY(int birdY) {
        this.y = birdY;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int birdWidth) {
        this.width = birdWidth;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int birdHeidgt) {
        this.height = birdHeidgt;
    }

    public void setVelocity(float v) {
        this.velocityY = v;
    }

    public Bird getBird() {
        return this;
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public void setImageIndex() {
        if (indeximg == 3) {
            indeximg = 0;
        } else {
            indeximg++;
        }
    }

    public void update() {
        velocityY += gravity;
        this.y += this.velocityY;
        // y = Math.max(y, 0);
        // y = Math.min(y, 530);
    }

    public void Draw(Graphics2D g2) {

        // vận tốc nhỏ hơn 0 là chim đang bay lên
        if (velocityY < 0) {
            // sử dụng AffineTransform để xoay một ảnh
            AffineTransform tx = new AffineTransform();
            tx.rotate(Math.toRadians(-20), width / 2, height / 2);
            // rotate chuyền vào độ xoay ảnh dưới dạng radian
            // width/2, height/2 để xác định tâm xoay --> tại tâm của ảnh

            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
            BufferedImage img = op.filter(birdimg[indeximg], null);
            g2.drawImage(img, x, y, null);

            // chim đi xuống
        } else if (velocityY > 0) {
            AffineTransform tx = new AffineTransform();
            tx.rotate(Math.toRadians(20), width / 2, height / 2);

            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
            BufferedImage img = op.filter(birdimg[indeximg], null);
            g2.drawImage(img, x, y, null);
        } else {
            // bay ngang
            g2.drawImage(birdimg[indeximg], x, y, null);
        }
    }

    @Override
    public void run() {
        while (true) {
            long start = System.currentTimeMillis();
            if (isLive()) {
                setImageIndex();
            }

            long end = System.currentTimeMillis();

            long sleeptime = end - start;
            if (sleeptime < 1000 / 10)
                sleeptime = (1000 / 10) - (end - start);
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
