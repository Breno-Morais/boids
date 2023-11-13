package Boids;

import Boids.Predator.PredatorBoids;
import Boids.Prey.PreyBoids;
import gui.GUISimulator;

import java.awt.*;

public class TestBoidsSimulator {
    public static void main(String[] args) {
        int width = 1275;
        int height = 955;

        GUISimulator gui = new GUISimulator(width,height, Color.WHITE);
        BoidsSimulator boidsSimulator = new BoidsSimulator(gui, width, height);

        Boids predatorBoids = new PredatorBoids(boidsSimulator);
        Boids preyBoids = new PreyBoids(boidsSimulator);

        for(int i = 0; i < 10; i++) {
            double posX = Math.random() * width;
            double posY = Math.random() * height;
            double dirX = Math.random() - 0.5;
            double dirY = Math.random() - 0.5;

            /*
            float red = (float) Math.random();
            float green = (float) Math.random();
            float blue = (float) Math.random();
            Color randomColor = Color.WHITE;

            if((posX > width/2.0) && (posY < height/2.0))
                randomColor = Color.RED;
            else if((posX < width/2.0) && (posY < height/2.0))
                randomColor = Color.GREEN;
            else if((posX < width/2.0) && (posY > height/2.0))
                randomColor = Color.BLUE;
            else if((posX > width/2.0) && (posY > height/2.0))
                randomColor = Color.YELLOW;
            */

            predatorBoids.addBoid(posX, posY, dirX, dirY);
        }

        for(int i = 0; i < 990; i++) {
            double posX = Math.random() * width;
            double posY = Math.random() * height;
            double dirX = Math.random() - 0.5;
            double dirY = Math.random() - 0.5;

            /*
            float red = (float) Math.random();
            float green = (float) Math.random();
            float blue = (float) Math.random();
            Color randomColor = Color.WHITE;

            if((posX > width/2.0) && (posY < height/2.0))
                randomColor = Color.RED;
            else if((posX < width/2.0) && (posY < height/2.0))
                randomColor = Color.GREEN;
            else if((posX < width/2.0) && (posY > height/2.0))
                randomColor = Color.BLUE;
            else if((posX > width/2.0) && (posY > height/2.0))
                randomColor = Color.YELLOW;
            */

            preyBoids.addBoid(posX, posY, dirX, dirY);
        }

        gui.setSimulable(boidsSimulator);
    }
}
