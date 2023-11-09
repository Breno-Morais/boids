package Boids;

import gui.GUISimulator;

import java.awt.*;

public class TestBoidsSimulator {
    public static void main(String[] args) {
        Boids boids = new Boids(800,600);
        for(int i = 0; i < 100; i++) {
            double posX = Math.random() * (800 + 1);
            double posY = Math.random() * (600 + 1);
            double dirX = Math.random() * (2);
            double dirY = Math.random() * (2);

            boids.addBoid(new Boid(posX, posY, dirX, dirY));
        }

        GUISimulator gui = new GUISimulator(800,600, Color.BLACK);
        gui.setSimulable(new BoidsSimulator(gui, boids));
    }
}
