package Asteroids.game1;

import Asteroids.utilities.Refresh;
import Asteroids.utilities.RotatableShape;
import Asteroids.utilities.Vector2D;

import java.awt.*;

import static Asteroids.game1.Constants.*;

public abstract class GameObject implements Refresh {

    public Vector2D position;
    public Vector2D velocity;
    protected double RADIUS;
    public int[] grid;
    boolean alive;
    public GameObject(Vector2D position, Vector2D velocity, double RADIUS)
    {
        this.position = position;
        this.velocity = velocity;
        this.RADIUS = RADIUS;
        alive = true;
    }

    public boolean overlap(GameObject other)
    {
        //TODO: simple overlap method
        return false;
    }

    public void collisionHandling(GameObject other)
    {
        if (this.getClass() != other.getClass() && this.overlap(other))
        {
            this.hit();
            other.hit();
        }
    }

    public abstract void hit();
    public void update()
    {
        position.addScaled(velocity, DT);
        position.wrap(FRAME_WIDTH, FRAME_HEIGHT);
    }
    public abstract void draw(Graphics2D g);
}
