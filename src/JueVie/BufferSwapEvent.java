package JueVie;

import EventManager.Event;

public class BufferSwapEvent extends Event{
    private Cells cells;

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
