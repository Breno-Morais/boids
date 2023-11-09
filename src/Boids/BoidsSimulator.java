package Boids;

import gui.GUISimulator;
import gui.Rectangle;
import gui.Simulable;

import java.awt.*;

public class BoidsSimulator implements Simulable {
    public GUISimulator gui;
    public Boids boids;

    public BoidsSimulator(GUISimulator gui, Boids boids) {
        this.gui = gui;
        this.boids = boids;
    }

    @Override
    public void next() {
        //System.out.println(boids.toString());
        boids.step();
        draw();
    }

    @Override
    public void restart() {

    }

    private void draw() {
        gui.reset();    // clear the window

        for(Boid boid : boids.getBoids())
            gui.addGraphicalElement(new Rectangle((int) boid.pos.x, (int) boid.pos.y, Color.WHITE, Color.WHITE, 5));
    }
}
