/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.awt.Graphics2D;
import java.awt.Image;

/**
 *
 * @author ADMIN
 */
public class Pipe extends Thread {

    private Image image;

    private int x, y;
    private int pipeWidth, pipeHeight;
    private boolean passed = false;

    public Pipe(Image img, int x, int y, int pipeWidth, int pipeHeight) {
        this.image = img;
        this.x = x;
        this.y = y;
        this.pipeWidth = pipeWidth;
        this.pipeHeight = pipeHeight;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getPipeWidth() {
        return pipeWidth;
    }

    public void setPipeWeight(int pipeWidth) {
        this.pipeWidth = pipeWidth;
    }

    public int getPipeHeight() {
        return pipeHeight;
    }

    public void setPipeHeight(int pipeHeight) {
        this.pipeHeight = pipeHeight;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public boolean isPass() {
        return passed;
    }

    public void setPass(boolean passed) {
        this.passed = passed;
    }

    public void update() {
        this.x -= 4;
    }

    public void paint(Graphics2D g2) {
        g2.drawImage(image, x, y, pipeWidth, pipeHeight, null);
    }

}
