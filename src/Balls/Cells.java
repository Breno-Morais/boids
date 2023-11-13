package Balls;
import java.awt.*;
import gui.GUISimulator;
import gui.Rectangle;
import gui.Simulable;
import java.awt.Color;

import static java.lang.Math.random;

public class Cells implements Simulable {
    protected int rows;
    protected int cols;
    protected GUISimulator guiSim;
    protected int[][] grid;
    protected int nbStates;

    public Cells(int rows, int cols, GUISimulator guiSim, int nbStates) {
        this.rows = rows;
        this.cols = cols;
        this.guiSim = guiSim;
        this.nbStates = nbStates;
        grid = new int[rows][cols];
        initializeGrid(nbStates);
        draw();
    }



    public void initializeGrid(int nbStates) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = (int) (Math.random() * (nbStates+2));// Initialize randomly 
            }
        }
    }
    public void evolve() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int state =0;
                int neighbors = countAliveNeighbors(i, j,state);
                if ((grid[i][j] == 0) && (neighbors == 3)) {
                    grid[i][j] = 1;
                } else if ((grid[i][j] == 1) && !(neighbors == 2 || neighbors == 3)) {
                    grid[i][j] = 0;
                }
            }
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
        evolve();
        draw();
    }

    @Override
    public void restart() {

    }
    protected Color getColorForState(int state) {
        switch (state) {
            case 0:
                return Color.WHITE;
            case 1:
                return Color.BLUE;

        }
        return null;
    }
    protected void draw() {
        guiSim.reset();    // clear the window

        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                // Color mapping based on the state
                Color color = getColorForState(grid[i][j]);
                guiSim.addGraphicalElement(new Rectangle(i*10, j*10, color, color, 10));

            }
        }
    }
}



























