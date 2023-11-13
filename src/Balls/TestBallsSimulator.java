package Balls;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import gui.GUISimulator;
import gui.Rectangle;
import gui.Simulable;

public class TestBallsSimulator {
    public static void main(String[] args) {
        int width = 700;
        int height = 500;
        GUISimulator gui = new GUISimulator(width,height, Color.BLACK);
        BallsSimulator ballsSimulator = new BallsSimulator(gui, width, height);
        for(int i = 0; i < 10; i++) {
            double posX = Math.random() * width;
            double posY = Math.random() * height;

            ballsSimulator.createBall(posX, posY);
        }

        gui.setSimulable(ballsSimulator);
    }
}