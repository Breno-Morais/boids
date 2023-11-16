package EventManager;

public abstract class Event implements Comparable<Event> {
    protected long date;
    protected double seed;

    public Event(long date) {
        this.date = date;
        this.seed = Math.random();
    }

    public long getDate() {
        return date;
    }

    abstract public void execute();

    @Override
    public int compareTo(Event comp) {
        if(this.date == comp.date) 
            if(this.seed > comp.seed) 
                return 1;
            else
                return -1;
        if(this.date > comp.date) return 1;
        return -1;
    }

    @Override
    public String toString() {
        return date + "";
    }
}