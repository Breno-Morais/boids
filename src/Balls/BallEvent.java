package Balls;

import EventManager.Event;
import EventManager.EventManager;

/* Event responsible for moving or creating one ball */
public class BallEvent extends Event {
    public enum EventType { 
        CREATION, TRANSLATION
    }

    /* Enumeration for identification of purpose: Creation or Translation */
    private EventType type;

    /* Number of frames of the animation */
    private int step;

    /* The ball that is being created or moved */
    private Ball ball;
    
    /* Collection of all the balls */
    private Balls balls;

    /* Event manager responsible for the creation of other events and their execution */
    private EventManager eventManager;

    /* Creation Constructor */
    public BallEvent(long date, EventManager eventManager, Ball ball, Balls balls) {
        super(date);
        this.type = EventType.CREATION;
        this.eventManager = eventManager;
        this.ball = ball;
        this.balls = balls;
    }

    /* Movement Constructor */
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

                // After creation, it start moving the ball
                eventManager.addEvent(new BallEvent(date + (int)(Math.random() * 100), eventManager, ball, 1000, balls));
                break;

            case TRANSLATION:
                balls.translate(ball);

                if(step > 0)
                    eventManager.addEvent(new BallEvent(date + 2, eventManager, ball, step-1, balls));
                break;
        }
    }
}
