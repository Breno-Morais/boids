package Schelling;

import java.awt.*;
import java.util.*;

import JueVie.CellEvent;
import JueVie.Cells;
import gui.GUISimulator;
import java.awt.Color;

/* Simulator of the model of schelling, using the grid system of the game of life */
public class SchellingModels extends Cells {
    /* List of all the empty homes */
    private LinkedList<Point> vacantHomes;

    /* Limit of neighbors of different colors */
    private final int k = 3;

    /* Number Colors of the simulation, in the moment 10 colors are implemented */
    private final int nbColors = 4;

    /* Chance for a home to be empty */
    private final double chanceForZero = 0.06;

    /* Base Constructor */
    public SchellingModels(int rows, int cols, GUISimulator guiSim){
        super(rows,cols,guiSim);
        initializeVacantHomes();
    }

    /* Base Constructor */
    public SchellingModels(int width, int heigth, int cellSize, GUISimulator guiSim) {
        super(width, heigth, cellSize, guiSim);
        initializeVacantHomes();
    }

    @Override
    public void initializeGrid() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = gridValue(); // Initialize randomly
                bufferGrid[i][j] = grid[i][j];
                eventManager.addEvent(new CellEvent(0, this, i, j, 1));
            }
        }
    }

    @Override
    protected int gridValue() {
        return (Math.random() < chanceForZero) ? 0 : (int) (Math.random() * (nbColors)) + 1;
    }

    /* If a family at i,j has k neighbors of diferent colors, they move to another house that is empty */
    @Override
    public void evolve(int i, int j) {
        if(shouldMove(i,j, grid[i][j])) {
            moveToVacantHome(i,j);
        }
    }

    /* Move a family to another random home that is empty */
    private void moveToVacantHome(int i, int j) {
        if(vacantHomes.isEmpty())
            return;
        
        if(grid[i][j] == 0)
            return;

        Point emptyHouse = vacantHomes.poll();
        grid[emptyHouse.x][emptyHouse.y] = grid[i][j];
        grid[i][j] = 0;

        vacantHomes.addLast(new Point(i,j));
    }

    /* If a family has more neighbors of diffent colors that they can tolerate */
    private boolean shouldMove(int i, int j, int v) {
        return (countNeighborsDifColor(i,j, v) > k);
    }

    /* Count the number of neighbors of different colors */
    private int countNeighborsDifColor(int x, int y, int color) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int neighborX = x + i;
                int neighborY = y + j;

                if(neighborX < 0 || neighborX >= rows)
                    continue;
                if(neighborY < 0 || neighborY >= cols)
                    continue;

                boolean notTheSame = !(i == 0 && j == 0);
                boolean isDifferent = grid[neighborX][neighborY] != color;
                boolean isNotEmpty = grid[neighborX][neighborY] != 0;
                if (notTheSame && isDifferent && isNotEmpty){
                    count++;
                }
            }
        }

        return count;
    }

    /* Add all the empty houses to the linked list of vacant homes */
    private void initializeVacantHomes() {
        vacantHomes = new LinkedList<>(); // on stocke les places vacantes dans une liste
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if(grid[i][j] == 0)
                    vacantHomes.add(new Point(i, j));// on ajoute les nouvelles places vacantes dans notre liste
            }
        }
        Collections.shuffle(vacantHomes);// permet de melanger les places vacantes de facon aleatoire et est une propriete particuliere des collections
    }

    @Override
    protected Color getColorForState(int state) {
        return switch (state) {
            case 10 -> Color.LIGHT_GRAY; // Color 10
            case 9 -> Color.CYAN; // Color 9
            case 8 -> Color.PINK; // Color 8
            case 7 -> Color.ORANGE; // Color 7
            case 6 -> Color.MAGENTA; // Color 6
            case 5 -> Color.GRAY; // Color 5
            case 4 -> Color.GREEN; // Color 4
            case 3 -> Color.YELLOW; // Color 3
            case 2 -> Color.BLUE; // Color 2
            case 1 -> Color.RED; // Color 1
            case 0 -> Color.WHITE; // Vacant
            default -> Color.BLACK;
        };

    }

    @Override
    public void restart() {
        initializeGrid();
        vacantHomes.clear();
        initializeVacantHomes();
        draw();
    }
}