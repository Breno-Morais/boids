package EventManager;

/* Representation of an event, an action in a timely manner */
public abstract class Event implements Comparable<Event> {
    /* Moment of expected execution */
    protected long date;

    /* Random seed responsable for tie breaking in the case of same date between events*/
    protected double seed;

    /* Base constructor */
    public Event(long date) {
        this.date = date;
        this.seed = Math.random();
    }

    /* Getter function of date */
    public long getDate() {
        return date;
    }

    abstract public void execute();

    /* Comparison function between events, in case of match, random seed is compared*/
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
        return "Event:\n    Date: " + date + " Seed: " + seed;
    }
}