package Balls;

import java.util.ArrayList;
import java.util.List;

import Boids.Vector2D;

/* Collection of all the balls and their enviroment */
public class Balls {
    /* Collection of balls */
    public List<Ball> balls;

    /* Width of the world */
    public int width;

    /* Height of the world */
    public int height;

    /* Base Constructor */
    public Balls(List<Ball> inArr) {
        balls = new ArrayList<Ball>(inArr);
    }

    /* Base Constructor */
    public Balls(int width, int height) {
        balls = new ArrayList<Ball>();
        this.width = width;
        this.height = height;
    }

    /* Add a ball to the collection */
    public void addBall(Ball ball) {
        balls.add(ball);
    }

    /* Move one ball, considering the limitations of the world */
    public void translate(Ball ball) {
        Vector2D nextPos = ball.pos.getAdded(ball.getSpeed());
        if((nextPos.x <= 0) || (nextPos.x >= width))
            ball.speed.set(-ball.speed.x, ball.speed.y);

        if((nextPos.y <= 0) || (nextPos.y >= height))
            ball.speed.set(ball.speed.x, -ball.speed.y);

        ball.translate();
    }

    /* Empty the collection of balls */
    public void clear() {
        balls.clear();
    }

    @Override
    public String toString() {
        StringBuilder strBd = new StringBuilder();

        strBd.append("Balls positions: \n");
        for(Ball ball : balls)
            strBd.append(String.format("    (%.2f,%.2f)\n", ball.pos.x, ball.pos.y));

        return strBd.toString();
    }
}

