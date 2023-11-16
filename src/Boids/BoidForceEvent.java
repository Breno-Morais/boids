package Boids;

import EventManager.Event;
import EventManager.EventManager;

public class BoidForceEvent extends Event {
    private final EventManager eventManager;
    private final Boids boids;
    public Boid boid;

    public BoidForceEvent(long date, Boid boid, Boids boids, EventManager eventManager) {
        super(date);
        this.eventManager = eventManager;
        this.boids = boids;
        this.boid = boid;
    }

    public void execute() {
        boids.calcForce(boid);

        eventManager.addEvent(new BoidForceEvent(date + 2, boid, boids, eventManager));
    }
}