package Boids;

import EventManager.Event;
import EventManager.EventManager;

public class BoidMoveEvent extends Event {
    private final EventManager eventManager;
    private final Boids boids;
    public Boid boid;

    public BoidMoveEvent(long date, Boid boid, Boids boids, EventManager eventManager) {
        super(date);
        this.eventManager = eventManager;
        this.boids = boids;
        this.boid = boid;
    }

    public void execute() {
        boids.updatePos(boid);

        eventManager.addEvent(new BoidMoveEvent(date + 1, boid, boids, eventManager));
    }
}
