package Boids;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Boids {

    // Settings Constants
    protected int neighborDistance = 25;
    protected double smoothingRate = -0.75;
    protected double smoothingAmplitude = 80;
    protected double speedConstant = 3;
    protected double cohesionConstant = 0.001;
    protected double viewAngle = Math.toRadians(160);

    // World values
    protected double width;
    protected double height;

    // Uniform grid for force detection
    protected int nGrid;
    protected int mGrid;
    protected LinkedList<Boid>[][] grid;

    // Debug
    protected Boid first;

    protected BoidsSimulator boidsSimulator;

    public Boids(BoidsSimulator boidsSimulator) {
        this.width = boidsSimulator.width;
        this.height = boidsSimulator.height;

        this.nGrid = boidsSimulator.nGrid;
        this.mGrid = boidsSimulator.mGrid;
        this.grid = boidsSimulator.grid;

        this.boidsSimulator = boidsSimulator;

        this.boidsSimulator.addBoids(this);
    }

    protected int[] calculateMatrixCell(double x, double y) {
        // Calculate the width and height of each cell
        double cellWidth = width / mGrid;
        double cellHeight = height / nGrid;

        // Calculate the row and column indices of the cell
        int row = (int) Math.floor(y / cellHeight);
        int col = (int) Math.floor(x / cellWidth);

        // Ensure that the indices are within the valid range
        row = Math.max(0, Math.min(row, nGrid - 1));
        col = Math.max(0, Math.min(col, mGrid - 1));

        // Calculate the cell index in a row-major order
        return new int[]{row, col};
    }

    public void addBoid(double posX, double posY, double dirX, double dirY) {
        Boid newBoid = new Boid(posX, posY, dirX, dirY, Boid.BoidType.BASIC);

        int[] coord = calculateMatrixCell(posX, posY);
        grid[coord[0]][coord[1]].add(newBoid);

        boidsSimulator.activateBoid(newBoid, this);

        if(first == null)
            first = newBoid;
    }

    public void removeBoid(Boid boid) {
        for(int i= 0; i < nGrid; i++) {
            for(int j = 0; j < mGrid; j++) {
                grid[i][j].remove(boid);
            }
        }
    }

    protected LinkedList<Boid> getNeighboringCells(int n, int m) {
        LinkedList<Boid> neighbors = new LinkedList<>();

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                boolean isXwithin = ((n + i) >= 0) && ((n + i) < nGrid);
                boolean isYwithin = ((m + j) >= 0) && ((m + j) < mGrid);
                if(isXwithin && isYwithin)
                    neighbors.addAll(grid[n + i][m + j]);
            }
        }

        return neighbors;
    }

    protected List<Boid> neighborsOfBoid(Boid centerBoid) {
        List<Boid> neighbor = new ArrayList<>();

        int[] coord = calculateMatrixCell(centerBoid.pos.x, centerBoid.pos.y);
        LinkedList<Boid> cellNeighbors = getNeighboringCells(coord[0], coord[1]);

        for(Boid otherBoid : cellNeighbors) {
            boolean notSame = centerBoid != otherBoid;

            Vector2D vecFromCenterToOther = Vector2D.subtract(otherBoid.pos, centerBoid.pos);
            double dot = centerBoid.dir.dot(vecFromCenterToOther);
            double angle = Math.acos(dot/vecFromCenterToOther.getLength());
            boolean inView = angle <= viewAngle; 
            boolean inDistance = inDistance(centerBoid, otherBoid);
            
            if(notSame && inDistance && inView)
                neighbor.add(otherBoid);
        }

        return neighbor;
    }

    protected boolean inDistance(Boid centerBoid, Boid otherBoid) {
        return centerBoid.pos.distance(otherBoid.pos) <= neighborDistance;
    }

    protected void calcForce(Boid boid) {
        List<Boid> neighbors = neighborsOfBoid(boid);
        Vector2D force = new Vector2D(0,0);
        Vector2D mediumPoint = new Vector2D(0,0);
        int numberOfNeighbors = neighbors.size();

        for(Boid neighbor : neighbors) {
            // Separation
            force.add(separationForce(boid, neighbor));

            // Alignment
            force.add(alignmentForce(neighbor, numberOfNeighbors));

            // Medium point for cohesion
            mediumPoint.add(neighbor.pos.getMultiplied(1.0/numberOfNeighbors));
        }
        // Cohesion
        force.add(cohesionForce(boid, mediumPoint));
        
        boid.force = force;
    }

    public void updatePos(Boid boid) {
        boid.dir.add(boid.force);
        boid.dir.normalize();

        int[] oldCoord = calculateMatrixCell(boid.pos.x, boid.pos.y);

        // TODO: Avoid Border but rebound
        boid.pos.add(boid.dir.getMultiplied(speedConstant));

        if(boid.pos.x <= 0)
            boid.pos.x = width;
        else if(boid.pos.x >= width)
            boid.pos.x = 0;

        if(boid.pos.y <= 0)
            boid.pos.y = height;
        else if(boid.pos.y >= height)
            boid.pos.y = 0;

        recalculateGridCell(oldCoord, boid);
    }

    protected void recalculateGridCell(int[] oldCoord, Boid boid) {
        int[] newCoord = calculateMatrixCell(boid.pos.x, boid.pos.y);

        if((newCoord[0] != oldCoord[0]) || (newCoord[1] != oldCoord[1])) {
            //System.out.println("OLD: (" + oldCoord[0] + ", " + oldCoord[1] + ") NEW: (" + newCoord[0] + ", " + newCoord[1] + ")");

            // Add to the correct cell
            grid[newCoord[0]][newCoord[1]].add(boid);

            // Remove from the incorrect cell
            grid[oldCoord[0]][oldCoord[1]].remove(boid);

            //System.out.println("    OLD LIST: " + grid[oldCoord[0]][oldCoord[1]] + " NEW LIST: " + grid[newCoord[0]][newCoord[1]]);
        }
    }

    protected Vector2D separationForce(Boid mainBoid, Boid otherBoid) {
        double distance = mainBoid.pos.distance(otherBoid.pos);
        double separationForce = smoothingAmplitude * Math.exp(smoothingRate * distance);
        Vector2D normalizedCenterToNeighbor = Vector2D.subtract(mainBoid.pos, otherBoid.pos).getNormalized();
        return normalizedCenterToNeighbor.getMultiplied(separationForce);
    }

    protected Vector2D alignmentForce(Boid otherBoid, int numberNeighbors) {
        return otherBoid.dir.getMultiplied(1.0/numberNeighbors);
    }

    protected Vector2D cohesionForce(Boid mainBoid, Vector2D mediumPoint) {
        return Vector2D.subtract(mediumPoint, mainBoid.pos).getMultiplied(cohesionConstant);
    }

    public List<Boid> getBoids() {
        LinkedList<Boid> list = new LinkedList<>();

        for(int i= 0; i < nGrid; i++) {
            for(int j = 0; j < mGrid; j++) {
                list.addAll(grid[i][j]);
            }
        }

        return list;
    }

    @Override
    public String toString() {
        StringBuilder strBd = new StringBuilder();

        strBd.append("Boids info: \n");
        for(Boid boid : getBoids())
            strBd.append(String.format("    (%.2f;%.2f) | (%.2f;%.2f)\n", boid.pos.x, boid.pos.y, boid.dir.x, boid.dir.y));

        return strBd.toString();
    }
}