package JueVie;

import EventManager.Event;

/* Event responsible for updating the grid */
public class BufferSwapEvent extends Event{
    /* Collection of all the cells */
    private Cells cells;

    /* Basic Constructor */
    public BufferSwapEvent(long date, Cells cells) {
        super(date);

        this.cells = cells;
    }

    @Override
    public void execute() {
        cells.bufferSwap();

        cells.eventManager.addEvent(new BufferSwapEvent(date + 2, cells));
    }
}
