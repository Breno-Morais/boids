package Balls;

import java.util.ArrayList;
import java.util.List;

import Boids.Vector2D;

public class Balls {

    public List<Ball> balls;
    public int width;
    public int height;

    public Balls(List<Ball> inArr) {
        balls = new ArrayList<Ball>(inArr);
    }

    public Balls(int width, int height) {
        balls = new ArrayList<Ball>();
        this.width = width;
        this.height = height;
    }

    public void addBall(Ball ball) {
        balls.add(ball);
    }

    public void translate(Ball ball) {

        Vector2D nextPos = ball.pos.getAdded(ball.getSpeed());
        if((nextPos.x <= 0) || (nextPos.x >= width))
            ball.speed.set(-ball.speed.x, ball.speed.y);

        if((nextPos.y <= 0) || (nextPos.y >= height))
            ball.speed.set(ball.speed.x, -ball.speed.y);

        ball.translate();
    }

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

