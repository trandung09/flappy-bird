/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import View.FlappyBird;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JOptionPane;

/**
 *
 * @author ADMIN
 */
public class Controller implements KeyListener, ActionListener {

    FlappyBird panel;

    public Controller(FlappyBird panel) {
        this.panel = panel;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (panel.CURRENT_SCREEN == panel.BEGIN_SCREEN) {
            this.panel.play = true;
            if (panel.play == true) {
                this.panel.setVelocity(-5);
                // panel.wing();
            }
            panel.CURRENT_SCREEN = panel.PLAY_SCREEN;

        } else if (panel.CURRENT_SCREEN == panel.PLAY_SCREEN) {
            this.panel.play = true;
            if (panel.play == true) {
                this.panel.setVelocity(-5);
                // panel.wing();
            }
        } else if (panel.CURRENT_SCREEN == panel.GAMEOVER_SCREEN) {

        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(panel.restart())) {
            panel.CURRENT_SCREEN = panel.BEGIN_SCREEN;
        } else if (e.getSource().equals(panel.quit())) {
            int ret = JOptionPane.showConfirmDialog(panel, "Bạn muốn thoát khỏi Flappy Bird?", "quit",
                    JOptionPane.YES_NO_OPTION);
            if (ret == JOptionPane.YES_OPTION)
                System.exit(0);
        }
    }

}
