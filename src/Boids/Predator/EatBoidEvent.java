package Boids.Predator;

import Boids.*;
import EventManager.Event;
import EventManager.EventManager;

import java.util.ArrayList;
import java.util.List;

public class EatBoidEvent extends Event {
    private final Boid prey;
    private final List<Boids> listBoids;
    private final EventManager eventManager;

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
