package EventManager;

public abstract class Event implements Comparable<Event> {
    protected long date;
    public Event(long date) {
        this.date = date;
    }

    public long getDate() {
        return date;
    }

    abstract public void execute();

    @Override
    public int compareTo(Event comp) {
        if(this.date == comp.date) return 0;
        if(this.date > comp.date) return 1;
        return -1;
    }

    @Override
    public String toString() {
        return date + "";
    }
}