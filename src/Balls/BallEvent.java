import EventManager.Event;

public class BallEvent extends Event {
    public enum EventType { 
        CREATION, TRANSLATION
    }

    private EventType type;

    private int step;
    private int lim;
    private Point pos;
    private Balls balls;

    // Creation
    public BallEvent(long date, EventType type, Point pos, Balls balls) {
        super(date);
        this.type = type;
        this.pos = pos;
        this.balls = balls;
    }

    // Movement
    public BallEvent(long date, EventType type) {
        super(date);
    }

    public void execute() {
        switch (type) {
            case EventType.CREATION:
                balls.addBall(pos);
                
                break;
        
            default:
                break;
        }
    }
}
