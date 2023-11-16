package Schelling;

import java.awt.*;
import java.util.*;

import JueVie.CellEvent;
import JueVie.Cells;
import gui.GUISimulator;
import java.awt.Color;

public class SchellingModels extends Cells {
    private LinkedList<Point> vacantHomes;
    private final int k = 3; // limit of neighbors of different colors
    private final int nbColors = 5; // 10 colors implemented

    private final double chanceForZero = 0.01;

    public SchellingModels(int rows, int cols, GUISimulator guiSim){
        super(rows,cols,guiSim);
        initializeVacantHomes();
    }

    public SchellingModels(int width, int heigth, int cellSize, GUISimulator guiSim) {
        super(width, heigth, cellSize, guiSim);
        initializeVacantHomes();
    }

    @Override
    public void initializeGrid() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = (Math.random() < chanceForZero) ? 0 : (int) (Math.random() * (nbColors)) + 1;; // Lower chance of having 0
                //eventManager.addEvent(new CellEvent((long) (Math.random() * ((long) rows * cols)), this, i, j));
                eventManager.addEvent(new CellEvent(0, this, i, j));
            }
        }
    }

    @Override
    public void evolve(int i, int j) {
        if(shouldMove(i,j)) {
            moveToVacantHome(i,j);
        }
    }

    private void moveToVacantHome(int i, int j) {
        if(vacantHomes.isEmpty())
            return;

        Point emptyHouse = vacantHomes.pop();

        grid[emptyHouse.x][emptyHouse.y] = grid[i][j];
        grid[i][j] = 0;

        vacantHomes.addLast(new Point(i,j));
    }

    private boolean shouldMove(int i, int j) {
        return (countNeighborsDifColor(i,j, grid[i][j]) > k);
    }

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
                //System.out.println("X: " + x + " Y: " + y + " neig: " + neighborX + ", " + neighborY + " values XY: " + color + " NEIG: " + grid[neighborX][neighborY]);
                if (notTheSame && isDifferent && isNotEmpty){
                    count++;
                }
            }
        }

        return count;
    }

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

    protected Color getColorForState(int state) {
        return switch (state) {
            case 10 -> Color.LIGHT_GRAY; // Color 10
            case 9 -> Color.YELLOW; // Color 9
            case 8 -> Color.PINK; // Color 8
            case 7 -> Color.ORANGE; // Color 7
            case 6 -> Color.MAGENTA; // Color 6
            case 5 -> Color.GRAY; // Color 5
            case 4 -> Color.GREEN; // Color 4
            case 3 -> Color.CYAN; // Color 3
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