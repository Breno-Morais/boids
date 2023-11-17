package EventManager;

import java.util.PriorityQueue;

/* Manager of event that stores and determine the execution order */
public class EventManager {
    private long currentDate;

    /* Collection of events storaded from the smallest date to the largest */
    public PriorityQueue<Event> events;

    /* Base constructor */
    public EventManager() {
        events = new PriorityQueue<>();
        currentDate = 0;
    }

    /* Add an event to the priority queue */
    public void addEvent(Event e) {
        events.add(e);
    }

    /* Executes all the events with and date less than or equal to the current date */
    public void next() {
        // Calculte the next state
        while(events.peek() != null && events.peek().getDate() <= currentDate) {
            Event current = events.poll();
            assert current != null;
            current.execute();
        }

        // Update all
        currentDate++;
        while(events.peek() != null && events.peek().getDate() <= currentDate) {
            Event current = events.poll();
            assert current != null;
            current.execute();
        }

        currentDate++;
    }

    /* Return if there isn't any event to be executed */
    public boolean isFinished() {
        return (events.peek() == null || events.peek().getDate() > currentDate);
    }

    /* Clears the collection */
    public void restart() {
        events.clear();
    }

    /* Getter function of the current date */
    public long getCurrent() {
        return currentDate;
    }

    @Override
    public String toString() {
        return events.toString();
    }
}
