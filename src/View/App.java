/*
* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
* Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
*/
package View;

import java.io.IOException;
import javax.swing.*;

/**
 *
 * @author ADMIN
 */
public class App {
    public App() throws IOException {
        int boardWidth = 600;
        int boardHeight = 600;

        JFrame frame = new JFrame();
        frame.setTitle("Flappy Bird");
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        FlappyBird flappybird = new FlappyBird();
        frame.add(flappybird);
        frame.setResizable(false);

        // sử dụng pack để thiết lập kích thước của frame chỉ tính từ phần màn hình
        // mà không tính thanh title bar, tự động điều chỉnh với các phần tử bên trong
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) throws Exception {
        App app = new App();
    }
}
