package Balls;

import Boids.Vector2D;

public class Ball {
    /* Position of the ball */
    public Vector2D pos;

    /* Direction of motion, always normalized */
    public Vector2D speed;

    /* Initial position for the restart */
    public Vector2D initialPos;
    
    /* Magnitude of the speed */
    private final double speedConstant = 10;

    /* Base constructor for the class with random speed direction*/
    public Ball(Vector2D pos) {
        this.pos = pos;

        double r = Math.sqrt(Math.random());
        double theta = Math.random() * 2 * Math.PI;

        this.speed = new Vector2D(r * Math.cos(theta),r * Math.sin(theta)).getNormalized();

        this.initialPos = new Vector2D(pos);
    }

    /* Base constructor for the class */
    public Ball(Vector2D pos, Vector2D speed) {
        this.pos = pos;
        this.speed = speed;
        this.initialPos = new Vector2D(pos);
    }

    /* Translate the ball in the direction of the speed */
    public void translate() {
        pos.add(getSpeed());
    }

    /* Getter function of the final speed */
    public Vector2D getSpeed() {
        return speed.getMultiplied(speedConstant);
    }
}
