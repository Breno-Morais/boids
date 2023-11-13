package Boids;

import java.awt.Color;

public class Boid {
    public Vector2D pos;
    public Vector2D dir;
    public Color color;
    public enum BoidType {
        BASIC, PREDATOR, PREY
    }
    public BoidType type;

    public Boid(double posX, double posY, double dirX, double dirY, BoidType type) {
        this.pos = new Vector2D(posX, posY);
        this.dir = new Vector2D(dirX, dirY).getNormalized();
        this.type = type;
        switch (type) {
            case BASIC:
                this.color = Color.BLACK;
                break;

            case PREDATOR:
                this.color = Color.RED;
                break;

            case PREY:
                this.color = Color.GREEN;
                break;
        }
    }

    @Override
    public String toString() {
        return "Boid (" + pos.x + ", " + pos.y + ")";
    }
}
