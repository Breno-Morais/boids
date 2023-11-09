package Boids;

public class Boid {
    public Vector2D pos;
    public Vector2D dir;

    public Boid(Vector2D pos, Vector2D speed) {
        this.pos = new Vector2D(pos);
        this.dir = new Vector2D(speed);
    }

    public Boid(double posX, double posY, double dirX, double dirY) {
        this.pos = new Vector2D(posX, posY);
        this.dir = new Vector2D(dirX, dirY).getNormalized();
    }
}
