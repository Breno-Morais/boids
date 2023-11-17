package Boids.Prey;

import Boids.*;

import java.util.List;

/* Group of preys, subtype of boids */
public class PreyBoids extends Boids {
    /* Constant of repulsion of predators */
    protected final double fear = 10;

// Separation force settings
    protected final double smoothingRatePredator = -0.1;
    protected final double smoothingAmplitudePredator = 30;
    protected final double predatorDistance = neighborDistance * 2;

    /* Base Constructor */
    public PreyBoids(BoidsSimulator boidsSimulator) {
        super(boidsSimulator);

        this.viewAngle = Math.toRadians(200);
    }

    @Override
    public void addBoid(double posX, double posY, double dirX, double dirY) {
        Boid newBoid = new Boid(posX, posY, dirX, dirY, Boid.BoidType.PREY);

        int[] coord = calculateMatrixCell(posX, posY);
        grid[coord[0]][coord[1]].add(newBoid);

        boidsSimulator.activateBoid(newBoid, this);

        if(first == null)
            first = newBoid;
    }

    @Override
    protected void calcForce(Boid boid) {
        List<Boid> neighbors = neighborsOfBoid(boid);
        Vector2D force = new Vector2D(0,0);
        Vector2D mediumPoint = new Vector2D(0,0);
        int numberOfNeighbors = neighbors.size();
        double inverseOfNbNeighbors = 1.0/numberOfNeighbors;

        for(Boid neighbor : neighbors) {
            if(neighbor.type == Boid.BoidType.PREDATOR) {
                // Fear
                force.add(fearForce(boid.pos, neighbor.pos));
            } else {
                // Separation
                force.add(separationForce(boid, neighbor));

                // Alignment
                force.add(alignmentForce(neighbor, numberOfNeighbors));

                // Medium point for cohesion
                mediumPoint.add(neighbor.pos.getMultiplied(inverseOfNbNeighbors));
            }
        }
        // Cohesion
        if(mediumPoint.x != 0 && mediumPoint.y != 0)
            force.add(cohesionForce(boid, mediumPoint));
        
        boid.force = force;
    }


    @Override
    protected boolean inDistance(Boid centerBoid, Boid otherBoid) {
        return centerBoid.pos.distance(otherBoid.pos) <= ((otherBoid.type == Boid.BoidType.PREDATOR) ? predatorDistance : neighborDistance);
    }

    private Vector2D fearForce(Vector2D preyPos, Vector2D predatorPos) {
        double distance = preyPos.distance(predatorPos);
        double separationForce = smoothingAmplitudePredator * Math.exp(smoothingRatePredator * distance);
        return Vector2D.subtract(preyPos, predatorPos).getMultiplied(fear * separationForce);
    }
}
