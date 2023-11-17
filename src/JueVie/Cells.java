package JueVie;

import EventManager.EventManager;
import gui.GUISimulator;
import gui.Rectangle;
import gui.Simulable;
import java.awt.Color;

/* Basic simulator for the game of life */
public class Cells implements Simulable {
    /* number of rows of the grid */
    protected int rows;

    /* number of columns of the grid */
    protected int cols;

    /* Graphical interface */
    protected GUISimulator guiSim;

    /* Grid of the current sworld tate */
    protected int[][] grid;

    /* Grid of the next world state */
    protected int[][] bufferGrid;

    /* Event manager responsible for the creation of other events and their execution */
    protected EventManager eventManager;

    /* Size of each cell, only the graphical representation */
    protected int cellSize;

    /* Width of the world */
    protected int width;

    /* Height of the world */
    protected int height;

    /* Base Constructor */
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

    /* Base Constructor */
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

    /* Initialize the grid with random number, add an event of update for each cell
     and an event for the update of the world state */
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

    public void addGlider(int xMeio, int yMeio) {
        grid[xMeio][yMeio] = 0;
        grid[xMeio][yMeio + 1] = 0;
        grid[xMeio - 1][yMeio + 1] = 0;
        grid[xMeio - 1][yMeio - 1] = 0;
        grid[xMeio - 1][yMeio] = 1;
        grid[xMeio][yMeio + 1] = 1;
        grid[xMeio + 1][yMeio - 1] = 1;
        grid[xMeio + 1][yMeio + 1] = 1;
        grid[xMeio + 1][yMeio] = 1;

        bufferGrid[xMeio][yMeio] = 0;
        bufferGrid[xMeio][yMeio + 1] = 0;
        bufferGrid[xMeio - 1][yMeio + 1] = 0;
        bufferGrid[xMeio - 1][yMeio - 1] = 0;
        bufferGrid[xMeio - 1][yMeio] = 1;
        bufferGrid[xMeio][yMeio + 1] = 1;
        bufferGrid[xMeio + 1][yMeio - 1] = 1;
        bufferGrid[xMeio + 1][yMeio + 1] = 1;
        bufferGrid[xMeio + 1][yMeio] = 1;
    }

    /* Get a random value for the grid */
    protected int gridValue() {
        return (Math.random() < 0.2) ? 1 : 0;
    }

    /* Store in the buffer grid the next state based on the rules of the game of life */
    public void evolve(int i, int j) {
        int state = 1;
        int neighbors = countAliveNeighbors(i, j,state);
        if ((grid[i][j] == 0) && (neighbors == 3)) {
            bufferGrid[i][j] = 1;
        } else if ((grid[i][j] == 1) && !(neighbors == 2 || neighbors == 3)) {
            bufferGrid[i][j] = 0;
        }
    }

    /* Count the number of cell in the vicinity that have the value of the variable state */
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

    /* Copy all the buffer grid to the grid, effectively taking the next step */
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

    /* Get color based on the state */
    protected Color getColorForState(int state) {
        return switch (state) {
            case 0 -> Color.WHITE;
            case 1 -> Color.BLACK;
            default -> Color.GRAY;
        };
    }

    /* Draw each cell */
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



























