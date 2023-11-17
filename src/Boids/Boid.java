package Boids;

import java.awt.Color;

public class Boid {
    /* Position of the boid */
    public Vector2D pos;

    /* Direction of the movement*/
    public Vector2D dir;

    /* Current acceleration of the boid */
    public Vector2D force;

    /* Color of the boid */
    public Color color;
    public enum BoidType {
        BASIC, PREDATOR, PREY
    }

    /* Type of the boid */
    public BoidType type;

    /* Base Constructor */
    public Boid(double posX, double posY, double dirX, double dirY, BoidType type) {
        this.pos = new Vector2D(posX, posY);
        this.dir = new Vector2D(dirX, dirY).getNormalized();
        this.force = new Vector2D(0,0);
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
