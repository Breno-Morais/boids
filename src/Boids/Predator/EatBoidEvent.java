package Boids.Predator;

import Boids.*;
import EventManager.Event;
import EventManager.EventManager;

import java.util.ArrayList;
import java.util.List;

/* Event responsible for the removal of one boid*/
public class EatBoidEvent extends Event {
    /* Boid being destroyed */
    private final Boid prey;

    /* Collection of groups of Boids */
    private final List<Boids> listBoids;

    /* Event manager responsible for the creation of other events and their execution */
    private final EventManager eventManager;

    /* Base Constructor */
    public EatBoidEvent(long date, Boid prey, List<Boids> listBoids, EventManager eventManager) {
        super(date);
        this.prey = prey;
        this.listBoids = listBoids;
        this.eventManager = eventManager;
    }

    @Override
    public void execute() {
        listBoids.get(0).removeBoid(prey);

        killMoveEvent();
    }

    /* Remove all the events of said boid */
    private void killMoveEvent() {
        List<Event> eventsToRemove = new ArrayList<>();
        for(Event event : eventManager.events) {
            if(event instanceof BoidMoveEvent)
                if(((BoidMoveEvent) event).boid == prey) {
                    eventsToRemove.add(event);
                }
        }

        eventManager.events.removeAll(eventsToRemove);
    }
}
