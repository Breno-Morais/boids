package Boids.Predator;

import Boids.*;
import Boids.Vector2D;
import EventManager.EventManager;

import java.util.List;

public class PredatorBoids extends Boids {
    // Predator settings
    protected Boid locked;
    protected double hunger = 10;
    protected final double eatingRadius = 7.5;
    protected int lastEaten;
    protected double hungerSpeedFactor = 0.0001;

    protected EventManager eventManager;
    protected List<Boids> listBoids;

    public PredatorBoids(BoidsSimulator boidsSimulator) {
        super(boidsSimulator);
        this.eventManager = boidsSimulator.eventManager;
        this.listBoids = boidsSimulator.listBoids;

        // Boid settings
        this.viewAngle = Math.toRadians(120);
        this.speedConstant = 1.5;
        this.neighborDistance = 50;
        this.smoothingRate = -0.05;
        this.smoothingAmplitude = 100;
        this.lastEaten = 0;
    }

    @Override
    public void addBoid(double posX, double posY, double dirX, double dirY) {
        Boid newBoid = new Boid(posX, posY, dirX, dirY, Boid.BoidType.PREDATOR);

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
        Boid lockedNow = null;

        for(Boid neighbor : neighbors) {
            if(neighbor.type == Boid.BoidType.PREDATOR) {
                // Separation
                force.add(separationForce(boid, neighbor));

            } else if(neighbor.type == Boid.BoidType.PREY) {
                if(neighbor.pos.distance(boid.pos) <= eatingRadius) {
                    lastEaten = -1;
                    eventManager.addEvent(new EatBoidEvent(0, neighbor, listBoids, eventManager));
                }

                if(neighbor == locked)
                    lockedNow = locked;

                if(lockedNow == null)
                    lockedNow = neighbor;
            }
        }

        if(lockedNow == null)
            locked = null;
        else
            // Hunt
            force.add(Vector2D.subtract(lockedNow.pos, boid.pos).getMultiplied(hunger));

        return force;
    }

    @Override
    public void updatePos(Boid boid) {
        Vector2D force = calcForce(boid);

        updateHunger();

        boid.dir.add(force);
        boid.dir.normalize();

        int[] oldCoord = calculateMatrixCell(boid.pos.x, boid.pos.y);

        // TODO: Avoid Border but rebound
        boid.pos.add(boid.dir.getMultiplied(speedConstant + (lastEaten * hungerSpeedFactor)));

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

    protected void updateHunger() {
        if(lastEaten < 0) {
            lastEaten = 0;
        } else {
            lastEaten++;
        }
    }
}
