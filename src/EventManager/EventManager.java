package EventManager;

import java.util.PriorityQueue;

public class EventManager {
    private long currentDate;
    public PriorityQueue<Event> events;

    public EventManager() {
        events = new PriorityQueue<>();
        currentDate = 0;
    }

    public void addEvent(Event e) {
        events.add(e);
    }

    public void next() {
        currentDate++;

        while(events.peek() != null && events.peek().getDate() <= currentDate) {
            Event current = events.poll();
            assert current != null;
            current.execute();
        }
    }

    public boolean isFinished() {
        return (events.peek() == null || events.peek().getDate() > currentDate);
    }

    public void restart() {
        events.clear();
    }

    public long getCurrent() {
        return currentDate;
    }

    @Override
    public String toString() {
        return events.toString();
    }
}
