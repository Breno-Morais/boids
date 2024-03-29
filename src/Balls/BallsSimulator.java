package Balls;

import java.awt.*;
import Boids.Vector2D;
import EventManager.EventManager;
import gui.GUISimulator;
import gui.Oval;
import gui.Simulable;

/* Simulator responsable for the storage of the balls and handling ther events */
public class BallsSimulator implements Simulable {
    /* Graphical interface */
    private GUISimulator gui;

    /* Collection of balls */
    private Balls balls;

    /* Event manager responsible for the creation of other events and their execution */
    private EventManager eventManager;

    /* Base Constructor */
    public BallsSimulator(GUISimulator gui, int width, int height) {
        this.balls = new Balls(width, height);
        this.gui = gui;
        eventManager = new EventManager();
    }

    /* Add an event to create a ball */
    public void createBall(double x, double y) {
        eventManager.addEvent(new BallEvent(eventManager.getCurrent(), eventManager, new Ball(new Vector2D(x,y)), balls));
    }

    @Override
    public void next() {
        eventManager.next();

        draw();
    }

    @Override
    public void restart() {
        balls.clear();
    }

    /* Draw all the balls on the screen */
    private void draw() {
        gui.reset();    // clear the window

        for(Ball ball : balls.balls)
            gui.addGraphicalElement(new Oval((int) ball.pos.x, (int) ball.pos.y, Color.WHITE, Color.WHITE, 10));
    }
}
