import gui.GUISimulator;
import gui.Rectangle;
import gui.Simulable;

public class BoidsSimulator implements Simulable {
    public GUISimulator gui;
    public Boids boids;

    public BoidsSimulator(GUISimulator gui, Boids boids) {
        this.gui = gui;
        this.boids = boids;
    }

    @Override
    public void next() {
        boids.step();
        draw();
    }

    @Override
    public void restart() {

    }

    private void draw() {
        gui.reset();    // clear the window

        for(Boid boid : boids.getBoids())
            gui.addGraphicalElement(new Rectangle((int) boid.pos.x, (int) boid.pos.y, boid.color, boid.color, 5));
    }
}
