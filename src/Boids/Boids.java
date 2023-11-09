package Boids;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

// TODO: Optimize it
public class Boids {
    private List<Boid> boids;

    // Settings Constants
    private final int neighborDistance = 50;
    private final double smoothingRate = -2;
    private final double smoothingAmplitude = 50;
    private final double speedConstant = 2;
    private final double cohesionConstant = 0.002;
    private final double viewAngle = Math.toRadians(120);

    // World values
    private double width;
    private double height;

    public Boids(double widht, double height) {
        boids = new ArrayList<Boid>();
        this.width = widht;
        this.height = height;
    }

    public void addBoid(Boid bd) {
        boids.add(bd);
    }

    // Highlight the first boid and shows in grey all the neighbors visible
    public void followFirst() {
        boids.get(0).color = Color.RED;
        for(Boid boid : neighborsOfBoid(boids.get(0)))
            boid.color = Color.GRAY;
    }

    public void step() {
        for(Boid boid : boids) {
            calcForce(boid);
        }
        
        //followFirst();
    }

    private List<Boid> neighborsOfBoid(Boid centerBoid) {
        List<Boid> neighbor = new ArrayList<Boid>();

        for(Boid otherBoid : boids) {
            boolean notSame = centerBoid != otherBoid;

            Vector2D vecFromCenterToOther = Vector2D.subtract(otherBoid.pos, centerBoid.pos);
            double dot = centerBoid.dir.dot(vecFromCenterToOther);
            double angle = Math.acos(dot/vecFromCenterToOther.getLength());
            boolean inView = angle <= viewAngle; 

            boolean inDistance = centerBoid.pos.distance(otherBoid.pos) <= neighborDistance;
            if(notSame && inDistance && inView)
                neighbor.add(otherBoid);
        }

        return neighbor;
    }

    private void calcForce(Boid boid) {
        List<Boid> neighbors = neighborsOfBoid(boid);
        Vector2D force = new Vector2D(0,0);
        Vector2D mediumPoint = new Vector2D(0,0);
        int numberOfNeighbors = neighbors.size();

        for(Boid neighbor : neighbors) {
            // Separation
            force.add(separationForce(boid, neighbor));

            // Alignment
            force.add(alignmentForce(boid, neighbor, numberOfNeighbors));

            // Medium point for cohesion
            mediumPoint.add(neighbor.pos.getMultiplied(1.0/numberOfNeighbors));
        }
        // Cohesion
        force.add(cohesionForce(boid, mediumPoint));

        boid.dir.add(force);
        boid.dir.normalize();

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

    }

    private Vector2D separationForce(Boid mainBoid, Boid otherBoid) {
        double distance = mainBoid.pos.distance(otherBoid.pos);
        double separationForce = smoothingAmplitude * Math.exp(smoothingRate * distance);
        Vector2D normalizedCenterToNeighbor = Vector2D.subtract(mainBoid.pos, otherBoid.pos).getNormalized();
        return normalizedCenterToNeighbor.getMultiplied(separationForce);
    }

    private Vector2D alignmentForce(Boid mainBoid, Boid otherBoid, int numberNeighbors) {
        return otherBoid.dir.getMultiplied(1.0/numberNeighbors);
    }

    private Vector2D cohesionForce(Boid mainBoid, Vector2D mediumPoint) {
        return Vector2D.subtract(mediumPoint, mainBoid.pos).getMultiplied(cohesionConstant);
    }

    public List<Boid> getBoids() {
        return boids;
    }

    @Override
    public String toString() {
        StringBuilder strBd = new StringBuilder();

        strBd.append("Boids info: \n");
        for(Boid boid : boids)
            strBd.append(String.format("    (%.2f;%.2f) | (%.2f;%.2f)\n", boid.pos.x, boid.pos.y, boid.dir.x, boid.dir.y));

        return strBd.toString();
    }
}
