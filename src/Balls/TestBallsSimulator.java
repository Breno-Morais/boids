package Balls;

import java.awt.*;
import gui.GUISimulator;

public class TestBallsSimulator {
    public static void main(String[] args) {
        int width = 1350;
        int height = 690;

        GUISimulator gui = new GUISimulator(width,height, Color.BLACK);
        BallsSimulator ballsSimulator = new BallsSimulator(gui, width, height);

        for(int i = 0; i < 100; i++) {
            double posX = Math.random() * width;
            double posY = Math.random() * height;

            ballsSimulator.createBall(posX, posY);
        }

        gui.setSimulable(ballsSimulator);
    }
}