package Balls;

import EventManager.Event;
import EventManager.EventManager;

import java.awt.*;

public class BallEvent extends Event {
    public enum EventType { 
        CREATION, TRANSLATION
    }

    private EventType type;

    private int step;
    private Point pos;
    private Balls balls;
    private EventManager eventManager;
    private int ballId;

    // Creation
    public BallEvent(long date, EventManager eventManager, Point pos, Balls balls) {
        super(date);
        this.type = EventType.CREATION;
        this.eventManager = eventManager;
        this.pos = pos;
        this.balls = balls;
    }

    // Movement
    public BallEvent(long date, EventManager eventManager, Point t, int duration, int ballId, Balls balls) {
        super(date);
        this.type = EventType.TRANSLATION;
        this.eventManager = eventManager;
        this.pos = t;
        this.step = duration;
        this.ballId = ballId;
        this.balls = balls;
    }

    public void execute() {
        switch (type) {
            case CREATION:
                balls.addBall(pos);

                eventManager.addEvent(new BallEvent(date + 5, eventManager, new Point(0,5), 10, ballId, balls));
                break;

            case TRANSLATION:
                balls.translate(ballId, pos.x, pos.y);

                if(step > 0)
                    eventManager.addEvent(new BallEvent(date + 2, eventManager, pos, step-1, ballId, balls));
                break;
        }
    }
}
