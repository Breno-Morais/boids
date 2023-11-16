package JueVie;

import EventManager.Event;

public class CellEvent extends Event {
    protected Cells cells;
    protected int i;
    protected int j;
    public CellEvent(long date, Cells cells, int i, int j) {
        super(date);
        this.cells = cells;
        this.i = i;
        this.j = j;
    }

    @Override
    public void execute() {
        cells.evolve(i, j);

        cells.eventManager.addEvent(new CellEvent(date + 1, cells, i, j));
    }
}
