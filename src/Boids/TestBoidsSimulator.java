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
            double dirX = Math.random() - 0.5;
            double dirY = Math.random() - 0.5;

            predatorBoids.addBoid(posX, posY, dirX, dirY);
        }

        for(int i = 0; i < 990; i++) {
            double posX = Math.random() * width;
            double posY = Math.random() * height;
            double dirX = Math.random() - 0.5;
            double dirY = Math.random() - 0.5;

            preyBoids.addBoid(posX, posY, dirX, dirY);
        }

        // for(int i = 0; i < 1000; i++) {
        //     double posX = Math.random() * width;
        //     double posY = Math.random() * height;
        //     double dirX = Math.random() - 0.5;
        //     double dirY = Math.random() - 0.5;

        //     normalBoids.addBoid(posX, posY, dirX, dirY);
        // }

        gui.setSimulable(boidsSimulator);
    }
}
