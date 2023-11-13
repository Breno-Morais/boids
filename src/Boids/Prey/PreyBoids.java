package Boids.Prey;

import Boids.*;

import java.util.List;

public class PreyBoids extends Boids {
    protected final double fear = 10;
    protected final double smoothingRatePredator = -0.5;
    protected final double smoothingAmplitudePredator = 60;

    public PreyBoids(BoidsSimulator boidsSimulator) {
        super(boidsSimulator);
        //this.neighborDistance = 50;
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
    protected Vector2D calcForce(Boid boid) {
        // Show one
//        boid.color = Color.BLACK;
//        followFirst();

        List<Boid> neighbors = neighborsOfBoid(boid);
        Vector2D force = new Vector2D(0,0);
        Vector2D mediumPoint = new Vector2D(0,0);
        int numberOfNeighbors = neighbors.size();
        double inverseOfNbNeighbors = 1.0/numberOfNeighbors;

        for(Boid neighbor : neighbors) {
            if(neighbor.type == Boid.BoidType.PREY) {
                // Separation
                force.add(separationForce(boid, neighbor));

                // Alignment
                force.add(alignmentForce(neighbor, numberOfNeighbors));

                // Medium point for cohesion
                mediumPoint.add(neighbor.pos.getMultiplied(inverseOfNbNeighbors));
            } else if(neighbor.type == Boid.BoidType.PREDATOR) {
                // Fear
                force.add(fearForce(boid.pos, neighbor.pos));
            }
        }
        // Cohesion
        force.add(cohesionForce(boid, mediumPoint));
        return force;
    }

    private Vector2D fearForce(Vector2D preyPos, Vector2D predatorPos) {
        double distance = preyPos.distance(predatorPos);
        double separationForce = smoothingAmplitudePredator * Math.exp(smoothingRatePredator * distance);
        return Vector2D.subtract(preyPos, predatorPos).getMultiplied(fear * separationForce);
    }
}
