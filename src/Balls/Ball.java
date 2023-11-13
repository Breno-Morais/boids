package Balls;

import Boids.Vector2D;

import java.awt.*;

public class Ball {
    public Vector2D pos;
    public Vector2D speed;
    public Vector2D initialPos;
    private final double speedConstant = 10;

    public Ball(Vector2D pos) {
        this.pos = pos;

        double r = Math.sqrt(Math.random());
        double theta = Math.random() * 2 * Math.PI;
        System.out.println(r);
        System.out.println(theta);

        this.speed = new Vector2D(r * Math.cos(theta),r * Math.sin(theta)).getNormalized();
        System.out.println(speed);

        this.initialPos = new Vector2D(pos);
    }

    public Ball(Vector2D pos, Vector2D speed) {
        this.pos = pos;
        this.speed = speed;
        this.initialPos = new Vector2D(pos);
    }

    public void translate() {
        pos.add(getSpeed());
        System.out.println(speed);
    }

    public Vector2D getSpeed() {
        return speed.getMultiplied(speedConstant);
    }
}
