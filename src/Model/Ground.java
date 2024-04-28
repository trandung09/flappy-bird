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
public class Ground {
    private Image ground;
    private int x1, y1, x2, y2;
    // cần có 2 tọa độ để 2 hình ảnh thay phiên nhau xuất hiện tạo hiệu ứng di
    // chuyển

    public Ground(Image groundImg) {
        this.ground = groundImg;
        x1 = 0;
        y1 = y2 = 550;
        x2 = x1 + 800; // 800 weight cua anh
    }

    public void update() {
        x1 -= 2;
        // x trừ hai để lùi về bên trái màn hình
        x2 -= 2;
        // điều kiện để 2 ảnh luôn phiên nhau xuất hiện
        if (x2 == 0)
            x1 = x2 + 800;
        if (x1 == 0)
            x2 = x1 + 800;
    }

    public void paint(Graphics2D g2) {
        g2.drawImage(ground, x1, y1, null);
        g2.drawImage(ground, x2, y1, null);
    }
}
