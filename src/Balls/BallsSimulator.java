package Balls;

import java.awt.*;
import Boids.Vector2D;
import EventManager.EventManager;
import gui.GUISimulator;
import gui.Rectangle;
import gui.Simulable;

public class BallsSimulator implements Simulable {
    private GUISimulator gui;
    private Balls balls;
    private EventManager eventManager;

    public BallsSimulator(GUISimulator gui, int width, int height) {
        this.balls = new Balls(width, height);
        this.gui = gui;
        eventManager = new EventManager();
    }

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

    private void draw() {
        gui.reset();    // clear the window

        for(Ball ball : balls.balls)
            gui.addGraphicalElement(new Rectangle((int) ball.pos.x, (int) ball.pos.y, Color.WHITE, Color.WHITE, 10));
    }
}
