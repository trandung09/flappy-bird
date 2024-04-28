/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author Administrator
 */
public class Score {
    // max score
    private double maxScore = 0;
    // private int number = 0;
    private String filePath = "./Assert/Score.txt";
    FileInputStream inputStream;
    FileOutputStream outputStream;

    public Score() {
    }

    public double readScore() {
        try {
            // Tạo một đối tượng FileInputStream để đọc file
            inputStream = new FileInputStream(filePath);

            // Đọc byte từ file
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes);

            // Chuyển đổi byte thành chuỗi
            String content = new String(bytes);

            // Chuyển đổi chuỗi thành số nguyên
            maxScore = Integer.parseInt(content.trim());

            // Đóng luồng sau khi hoàn thành
            inputStream.close();
        } catch (IOException e) {
            // Xử lý nếu có lỗi xảy ra
            e.printStackTrace();
        }
        return maxScore;
    }

    public void writeScore(double score) {
        if (maxScore < (int) score) {
            try {

                // Tạo một đối tượng FileOutputStream để ghi vào file
                outputStream = new FileOutputStream(filePath);

                // Chuyển đổi số thành mảng byte
                byte[] bytes = String.valueOf((int) score).getBytes();

                // Ghi dữ liệu vào file
                outputStream.write(bytes);

                // Đóng luồng sau khi hoàn thành
                outputStream.close();
            } catch (IOException e) {
                // Xử lý nếu có lỗi xảy ra
                e.printStackTrace();
            }
        }
    }
}
