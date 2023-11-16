package Immigration;

import JueVie.CellEvent;
import JueVie.Cells;
import gui.GUISimulator;

import java.awt.Color;


public class JeuImmigration extends Cells {
    public static final int NUM_STATES = 4;

    public JeuImmigration(int rows, int cols, GUISimulator guiSim) {
        super(rows, cols, guiSim);
    }

    public JeuImmigration(int width, int height, int cellSize, GUISimulator guiSim) {
        super(width, height, cellSize, guiSim);
    }

    @Override
    protected int gridValue() {
        return (int) (Math.random() * NUM_STATES);
    }

    @Override
    public void evolve(int i, int j) {
        int nextState =  (grid[i][j] + 1) % NUM_STATES;
        int neighbors = super.countAliveNeighbors(i, j, nextState);

        // Evolution rules specific to the cellular automaton
        if (neighbors >= 3) {
            bufferGrid[i][j] = nextState;
        }
    }


    @Override
    public Color getColorForState(int state) {
        return switch (state) {
            case 0 -> Color.decode("#000000");
            case 1 -> Color.decode("#470000");
            case 2 -> Color.decode("#A60000");
            case 3 -> Color.decode("#ff0000");
            // Add more cases if needed for additional states
            default -> Color.WHITE;
        };
    }
}
































