package Boids;

import gui.GUISimulator;

import java.awt.*;

public class TestBoidsSimulator {
    public static void main(String[] args) {
        int widht = 1840;
        int height = 920; 

        Boids boids = new Boids(widht,height);

        for(int i = 0; i < 500; i++) {
            double posX = Math.random() * widht;  
            double posY = Math.random() * height;
            double dirX = Math.random() * 2 - 1;
            double dirY = Math.random() * 2 - 1;

            // float red = (float) Math.random();
            // float green = (float) Math.random();
            // float blue = (float) Math.random();
            Color randomColor = Color.WHITE;

            if((posX > widht/2) && (posY < height/2))
                randomColor = Color.RED;
            else if((posX < widht/2) && (posY < height/2))
                randomColor = Color.GREEN;
            else if((posX < widht/2) && (posY > height/2))
                randomColor = Color.BLUE;
            else if((posX > widht/2) && (posY > height/2))
                randomColor = Color.YELLOW;


            boids.addBoid(new Boid(posX, posY, dirX, dirY, randomColor));
        }

        GUISimulator gui = new GUISimulator(widht,height, Color.BLACK);
        gui.setSimulable(new BoidsSimulator(gui, boids));
    }
}
