package Boids;

import EventManager.Event;
import EventManager.EventManager;

/* Event responsible for calculation of the force over one boid*/
public class BoidForceEvent extends Event {
    /* Event manager responsible for the creation of other events and their execution */
    private final EventManager eventManager;

    /* Collection of Boids */
    private final Boids boids;

    /* Boid being updated */
    public Boid boid;

    /* Base Constructor */
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