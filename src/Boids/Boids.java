package Boids;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/* Collection of boids and manager of their interactions */
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
    /* Number of rows of the grid */
    protected int nGrid;

    /* Number of columns of the grid */
    protected int mGrid;

    /* Uniform grid with boids of each cell */
    protected LinkedList<Boid>[][] grid;

    /* First caught boid, used for tracking during debug */
    protected Boid first;

    /* Simulator of boids */
    protected BoidsSimulator boidsSimulator;

    /* Base Constructor */
    public Boids(BoidsSimulator boidsSimulator) {
        this.width = boidsSimulator.width;
        this.height = boidsSimulator.height;

        this.nGrid = boidsSimulator.nGrid;
        this.mGrid = boidsSimulator.mGrid;
        this.grid = boidsSimulator.grid;

        this.boidsSimulator = boidsSimulator;

        this.boidsSimulator.addBoids(this);
    }

    /* Calculate the corresponding cell of a point in space */
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

    /* Add a boid to the grid and an correspoding event to the boid */
    public void addBoid(double posX, double posY, double dirX, double dirY) {
        Boid newBoid = new Boid(posX, posY, dirX, dirY, Boid.BoidType.BASIC);

        int[] coord = calculateMatrixCell(posX, posY);
        grid[coord[0]][coord[1]].add(newBoid);

        boidsSimulator.activateBoid(newBoid, this);

        if(first == null)
            first = newBoid;
    }

    /* Remove a boid from the grid */
    public void removeBoid(Boid boid) {
        for(int i= 0; i < nGrid; i++) {
            for(int j = 0; j < mGrid; j++) {
                grid[i][j].remove(boid);
            }
        }
    }

    /* Get all the neighboring cells of the cell n,m */
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

    /* Ge */
    protected List<Boid> neighborsOfBoid(Boid centerBoid) {
        List<Boid> neighbor = new ArrayList<>();

        // Get the cell of the boid and get all the boids in the neighoring cells
        int[] coord = calculateMatrixCell(centerBoid.pos.x, centerBoid.pos.y);
        LinkedList<Boid> cellNeighbors = getNeighboringCells(coord[0], coord[1]);

        // Check for each boid if it meets the requirements to be a neighbor 
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

    /* If the other boid is in distance */
    protected boolean inDistance(Boid centerBoid, Boid otherBoid) {
        return centerBoid.pos.distance(otherBoid.pos) <= neighborDistance;
    }

    /* Calculate the overall force over the boid */
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
        if(mediumPoint.x != 0 && mediumPoint.y != 0)
            force.add(cohesionForce(boid, mediumPoint));

        
        boid.force = force;
    }

    /* Calculate the force, move the boid and check for overstepping and cell chances */
    public void updatePos(Boid boid) {
        boid.dir.add(boid.force);
        boid.dir.normalize();

        int[] oldCoord = calculateMatrixCell(boid.pos.x, boid.pos.y);

        boid.pos.add(boid.dir.getMultiplied(speedConstant));

        // Check if the boid has stepped outside of the world and wrap it if it id;
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

    /* If necessary, change the boid from the cell */
    protected void recalculateGridCell(int[] oldCoord, Boid boid) {
        int[] newCoord = calculateMatrixCell(boid.pos.x, boid.pos.y);

        if((newCoord[0] != oldCoord[0]) || (newCoord[1] != oldCoord[1])) {

            // Add to the correct cell
            grid[newCoord[0]][newCoord[1]].add(boid);

            // Remove from the incorrect cell
            grid[oldCoord[0]][oldCoord[1]].remove(boid);
        }
    }

    /* Calculate the separation force between boids */
    protected Vector2D separationForce(Boid mainBoid, Boid otherBoid) {
        double distance = mainBoid.pos.distance(otherBoid.pos);
        double separationForce = smoothingAmplitude * Math.exp(smoothingRate * distance);
        Vector2D normalizedCenterToNeighbor = Vector2D.subtract(mainBoid.pos, otherBoid.pos).getNormalized();
        return normalizedCenterToNeighbor.getMultiplied(separationForce);
    }

    /* Calculate the alignment force between boids */
    protected Vector2D alignmentForce(Boid otherBoid, int numberNeighbors) {
        return otherBoid.dir.getMultiplied(1.0/numberNeighbors);
    }

    /* Calculate the cohesion force between boids */
    protected Vector2D cohesionForce(Boid mainBoid, Vector2D mediumPoint) {
        return Vector2D.subtract(mediumPoint, mainBoid.pos).getMultiplied(cohesionConstant);
    }

    /* Getter function of the boids */
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