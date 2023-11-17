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
        Boids normalBoids = new Boids(boidsSimulator);

        for(int i = 0; i < 10; i++) {
            double posX = Math.random() * width;
            double posY = Math.random() * height;

            double r = Math.sqrt(Math.random());
            double theta = Math.random() * 2 * Math.PI;
    
            double dirX = r * Math.cos(theta);
            double dirY = r * Math.sin(theta);

            predatorBoids.addBoid(posX, posY, dirX, dirY);
        }

        for(int i = 0; i < 500; i++) {
            double posX = Math.random() * width;
            double posY = Math.random() * height;

            double r = Math.sqrt(Math.random());
            double theta = Math.random() * 2 * Math.PI;
    
            double dirX = r * Math.cos(theta);
            double dirY = r * Math.sin(theta);


            preyBoids.addBoid(posX, posY, dirX, dirY);
        }

        for(int i = 0; i < 490; i++) {
            double posX = Math.random() * width;
            double posY = Math.random() * height;

            double r = Math.sqrt(Math.random());
            double theta = Math.random() * 2 * Math.PI;
    
            double dirX = r * Math.cos(theta);
            double dirY = r * Math.sin(theta);


            normalBoids.addBoid(posX, posY, dirX, dirY);
        }

        gui.setSimulable(boidsSimulator);
    }
}
