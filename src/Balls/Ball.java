package Balls;

import Boids.Vector2D;

public class Ball {
    public Vector2D pos;
    public Vector2D speed;
    public Vector2D initialPos;
    private final double speedConstant = 10;

    public Ball(Vector2D pos) {
        this.pos = pos;

        double r = Math.sqrt(Math.random());
        double theta = Math.random() * 2 * Math.PI;

        this.speed = new Vector2D(r * Math.cos(theta),r * Math.sin(theta)).getNormalized();

        this.initialPos = new Vector2D(pos);
    }

    public Ball(Vector2D pos, Vector2D speed) {
        this.pos = pos;
        this.speed = speed;
        this.initialPos = new Vector2D(pos);
    }

    public void translate() {
        pos.add(getSpeed());
    }

    public Vector2D getSpeed() {
        return speed.getMultiplied(speedConstant);
    }
}
