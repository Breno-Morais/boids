import java.awt.Color;

public class Boid {
    public Vector2D pos;
    public Vector2D dir;
    public Color color;

    public Boid(Vector2D pos, Vector2D speed) {
        this.pos = new Vector2D(pos);
        this.dir = new Vector2D(speed);
        this.color = Color.WHITE;
    }

    public Boid(double posX, double posY, double dirX, double dirY) {
        this.pos = new Vector2D(posX, posY);
        this.dir = new Vector2D(dirX, dirY).getNormalized();
        this.color = Color.WHITE;
    }

    public Boid(Vector2D pos, Vector2D speed, Color color) {
        this.pos = new Vector2D(pos);
        this.dir = new Vector2D(speed);
        this.color = color;
    }

    public Boid(double posX, double posY, double dirX, double dirY, Color color) {
        this.pos = new Vector2D(posX, posY);
        this.dir = new Vector2D(dirX, dirY).getNormalized();
        this.color = color;
    }
}
