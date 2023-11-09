package Balls;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import EventManager.EventManager;
import gui.GUISimulator;
import gui.Rectangle;
import gui.Simulable;

public class BallsSimulator implements Simulable {
    private GUISimulator gui;
    private Balls balls;
    private EventManager eventManager;

    public BallsSimulator(GUISimulator gui) {
        this.balls = new Balls();
        this.gui = gui;
        eventManager = new EventManager();

        eventManager.addEvent(new BallEvent(0, eventManager, new Point(200,200), balls));
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

        for(Point ball : balls.balls)
            gui.addGraphicalElement(new Rectangle(ball.x, ball.y, Color.WHITE, Color.WHITE, 10));
    }
}
