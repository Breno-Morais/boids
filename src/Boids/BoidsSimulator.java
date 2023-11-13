package Boids;

import EventManager.EventManager;
import gui.GUISimulator;
import gui.Rectangle;
import gui.Simulable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BoidsSimulator implements Simulable {
    public GUISimulator gui;

    // World setting
    protected final int neighborDistance = 25;
    public int width;
    public int height;

    // Uniform grid for force detection
    public int nGrid;
    public int mGrid;
    public LinkedList<Boid>[][] grid;

    public List<Boids> listBoids;
    public EventManager eventManager;

    public BoidsSimulator(GUISimulator gui, int width, int height) {
        this.gui = gui;
        this.listBoids = new ArrayList<>();
        this.eventManager = new EventManager();

        this.width = width;
        this.height = height;

        int sizeOfGrid = (int) Math.ceil(neighborDistance / Math.sqrt(2));
        this.nGrid = (int) Math.floor((double) width / sizeOfGrid);
        this.mGrid = (int) Math.floor((double) height / sizeOfGrid);

        grid = new LinkedList[nGrid][mGrid];
        for(int i = 0; i < nGrid; i++)
        {
            for(int j = 0; j < mGrid; j++)
            {
                grid[i][j] = new LinkedList<>();
            }
        }
    }

    public void addBoids(Boids boids) {
        listBoids.add(boids);
    }

    public void activateBoid(Boid boid, Boids boids) {
        eventManager.addEvent(new BoidMoveEvent(eventManager.getCurrent(), boid, boids, eventManager));
    }

    @Override
    public void next() {
        eventManager.next();

        draw();
    }

    @Override
    public void restart() {
    }

    private void draw() {
        gui.reset();    // clear the window

        for(Boids boids: listBoids)
            for(Boid boid : boids.getBoids()) {
                int boidSize = (boid.type == Boid.BoidType.PREDATOR) ? 8 : 5;
                gui.addGraphicalElement(new Rectangle((int) boid.pos.x, (int) boid.pos.y, boid.color, boid.color, boidSize));
            }
    }
}
