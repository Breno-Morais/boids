package Balls;

import gui.GUISimulator;
import gui.Rectangle;
import java.awt.Color;


public class Jeu_Immigration extends Cells {
    private static final int NUM_STATES = 4;

    public Jeu_Immigration(int rows, int cols, GUISimulator guiSim,int nbStates) {
        super(rows, cols, guiSim, nbStates);
        initializeGrid(nbStates);
    }

    public void initializeGrid(int nbStates) {
        super.initializeGrid(4);
    }

    @Override
    public void evolve() {
        int[][] newGrid = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int nextState =  (grid[i][j] + 1) % nbStates;
                int neighbors = super.countAliveNeighbors(i, j, nextState);

                // Evolution rules specific to the cellular automaton
                newGrid[i][j] = nextState; // Assuming 4 states in this example
                if (neighbors < 3) {
                    newGrid[i][j] = grid[i][j];
                }
            }
        }

        grid = newGrid;
    }


    @Override
    public Color getColorForState(int state) {
        switch (state) {
            case 0:
                return Color.WHITE;
            case 1:
                return Color.BLUE;
            case 2:
                return Color.GREEN;
            case 3:
                return Color.RED;
            // Add more cases if needed for additional states
            default:
                return Color.BLACK;
        }
    }

    @Override
    protected void draw() {
        super.draw();
    }

}
































