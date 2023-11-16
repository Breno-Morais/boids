package Balls;

import EventManager.Event;
import EventManager.EventManager;

public class BallEvent extends Event {
    public enum EventType { 
        CREATION, TRANSLATION
    }

    private EventType type;

    private int step;
    private Ball ball;
    private Balls balls;
    private EventManager eventManager;

    // Creation
    public BallEvent(long date, EventManager eventManager, Ball ball, Balls balls) {
        super(date);
        this.type = EventType.CREATION;
        this.eventManager = eventManager;
        this.ball = ball;
        this.balls = balls;
    }

    // Movement
    public BallEvent(long date, EventManager eventManager, Ball ball, int duration, Balls balls) {
        super(date);
        this.type = EventType.TRANSLATION;
        this.eventManager = eventManager;
        this.ball = ball;
        this.step = duration;
        this.balls = balls;
    }

    public void execute() {
        switch (type) {
            case CREATION:
                balls.addBall(ball);

                eventManager.addEvent(new BallEvent(date, eventManager, ball, 100, balls));
                break;

            case TRANSLATION:
                balls.translate(ball);

                //if(step > 0)
                    eventManager.addEvent(new BallEvent(date + 2, eventManager, ball, step-1, balls));
                break;
        }
    }
}
