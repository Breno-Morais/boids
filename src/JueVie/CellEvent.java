package JueVie;

import EventManager.Event;

public class CellEvent extends Event {
    protected Cells cells;
    protected int i;
    protected int j;
    protected int step;
    public CellEvent(long date, Cells cells, int i, int j) {
        super(date);
        this.cells = cells;
        this.i = i;
        this.j = j;
        this.step = 2; // Usually the first is just to calculate, and the second is to update
    }

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
