package JueVie;

import EventManager.Event;

/* Event responsible for updating one cell */
public class CellEvent extends Event {
    /* Collection of all the cells */
    protected Cells cells;

    /* Row of the cell */
    protected int i;

    /* Column of the cell */
    protected int j;

    /* Frames skipped bewteen updates */
    protected int step;

    /* Base Constructor */
    public CellEvent(long date, Cells cells, int i, int j) {
        super(date);
        this.cells = cells;
        this.i = i;
        this.j = j;
        this.step = 2; // Usually the first is just to calculate, and the second is to update
    }

    /* Base Constructor */
    public CellEvent(long date, Cells cells, int i, int j, int step) {
        super(date);
        this.cells = cells;
        this.i = i;
        this.j = j;
        this.step = step; // Usually the first is just to calculate, and the second is to update
    }

    @Override
    public void execute() {
        cells.evolve(i, j);

        cells.eventManager.addEvent(new CellEvent(date + step, cells, i, j));
    }
}
