package JueVie;

import EventManager.EventManager;
import gui.GUISimulator;
import gui.Rectangle;
import gui.Simulable;
import java.awt.Color;

public class Cells implements Simulable {
    protected int rows;
    protected int cols;
    protected GUISimulator guiSim;
    protected int[][] grid;
    protected int[][] bufferGrid;
    protected EventManager eventManager;
    protected int cellSize;
    protected int width;
    protected int height;

    public Cells(int rows, int cols, GUISimulator guiSim) {
        this.rows = rows;
        this.cols = cols;
        this.guiSim = guiSim;
        this.grid = new int[rows][cols];
        this.bufferGrid = new int[rows][cols];
        this.eventManager = new EventManager();
        this.cellSize = 10;
        initializeGrid();
        draw();
    }

    public Cells(int width, int height, int cellSize, GUISimulator guiSim) {
        this.height = height;
        this.width = width;
        this.cellSize = cellSize;
        this.rows = width/cellSize;
        this.cols = height/cellSize;
        this.guiSim = guiSim;
        this.grid = new int[rows][cols];
        this.bufferGrid = new int[rows][cols];
        this.eventManager = new EventManager();
        initializeGrid();
        draw();
    }

    public void initializeGrid() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = gridValue(); // Initialize randomly
                bufferGrid[i][j] = grid[i][j];
                eventManager.addEvent(new CellEvent(0, this, i, j));
            }
        }

        eventManager.addEvent(new BufferSwapEvent(1, this));
    }

    protected int gridValue() {
        return (Math.random() < 0.2) ? 1 : 0;
    }

    public void evolve(int i, int j) {
        int state = 1;
        int neighbors = countAliveNeighbors(i, j,state);
        if ((grid[i][j] == 0) && (neighbors == 3)) {
            bufferGrid[i][j] = 1;
        } else if ((grid[i][j] == 1) && !(neighbors == 2 || neighbors == 3)) {
            bufferGrid[i][j] = 0;
        }
    }

    public int countAliveNeighbors(int x, int y,int state) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int neighborX = (x + i + rows) % rows;
                int neighborY = (y + j + cols) % cols;
                if (!(i == 0 && j == 0) && (grid[neighborX][neighborY] == state)){
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    public void next() {
        eventManager.next();

        draw();
    }

    public void bufferSwap() {
        for (int i = 0; i < rows; i++) {
            grid[i] = bufferGrid[i].clone();
        }
    }

    @Override
    public void restart() {
        initializeGrid();
        draw();
    }
    protected Color getColorForState(int state) {
        return switch (state) {
            case 0 -> Color.WHITE;
            case 1 -> Color.BLUE;
            default -> Color.BLACK;
        };
    }
    protected void draw() {
        guiSim.reset();    // clear the window

        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                // Color mapping based on the state
                Color color = getColorForState(grid[i][j]);
                guiSim.addGraphicalElement(new Rectangle(i*cellSize, j*cellSize, color, color, cellSize-1));
            }
        }
    }
}



























