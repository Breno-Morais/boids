package Balls;
import java.awt.*;
import gui.GUISimulator;
import gui.Rectangle;
import gui.Simulable;

import static java.lang.Math.random;

public class Cells implements Simulable {
    private int rows;
    private int cols;
    private GUISimulator guiSim;
    private int[][] grid;

    public Cells(int rows, int cols, GUISimulator guiSim) {
        this.rows = rows;
        this.cols = cols;
        this.guiSim = guiSim;
        grid = new int[rows][cols];
        initializeGrid();
        draw();
    }

    private void initializeGrid() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = (Math.random() < 0.2) ? 1 : 0; // Initialize randomly with 20% alive cells
            }
        }
    }

    private void evolve() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int neighbors = countAliveNeighbors(i, j);
                if ((grid[i][j] == 0) && (neighbors == 3)) {
                    //System.out.println("VIVEU");
                    grid[i][j] = 1;
                } else if ((grid[i][j] == 1) && !(neighbors == 2 || neighbors == 3)) {
                    //System.out.println("MORREU");
                    grid[i][j] = 0;
                }
            }
        }


    }

    private int countAliveNeighbors(int x, int y) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int neighborX = (x + i + rows) % rows;
                int neighborY = (y + j + cols) % cols;
                if (!(i == 0 && j == 0) && (grid[neighborX][neighborY] == 1)) {
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    public void next() {
        evolve();
        draw();
    }

    @Override
    public void restart() {

    }

    private void draw() {
        guiSim.reset();    // clear the window

        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                if(grid[i][j] == 1)
                    guiSim.addGraphicalElement(new Rectangle(i*5, j*5, Color.BLUE, Color.BLUE, 5));
            }
        }
    }
}

























