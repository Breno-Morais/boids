package Boids;

import EventManager.Event;
import EventManager.EventManager;

/* Event responsible for movement of one boid*/
public class BoidMoveEvent extends Event {
    /* Event manager responsible for the creation of other events and their execution */
    private final EventManager eventManager;

    /* Collection of Boids */
    private final Boids boids;

    /* Boid begin updated */
    public Boid boid;

    /* Base Constructor */
    public BoidMoveEvent(long date, Boid boid, Boids boids, EventManager eventManager) {
        super(date);
        this.eventManager = eventManager;
        this.boids = boids;
        this.boid = boid;
    }

    public void execute() {
        boids.updatePos(boid);

        eventManager.addEvent(new BoidMoveEvent(date + 2, boid, boids, eventManager));
    }
}
