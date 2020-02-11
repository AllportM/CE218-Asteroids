package Asteroids.game1;

import Asteroids.utilities.Refresh;
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
        return (this.position.dist(other.position) < this.RADIUS + other.RADIUS);
    }

    public void collisionHandling(GameObject other)
    {
        // ensures two objects are of differenct classes
        if (this.getClass() != other.getClass() && this.overlap(other))
        {

            if (this.getClass() == Ship.class && other.getClass() == BasicAsteroid.class)
            {
                Ship obj = (Ship) this;
                if (!(obj.inv > 0))
                {
                    this.hit();
                    obj.hit();
                }
            }
            else if (this.getClass() == BasicAsteroid.class && other.getClass() == Ship.class)
            {
                Ship obj = (Ship) other;
                if (!(obj.inv > 0))
                {
                    this.hit();
                    other.hit();
                }
            }
            else
            {
                this.hit();
                other.hit();
            }

            // sets killedByPlayer boolean on asteroid if either of the objects which destroyed asteroid were
            // bullets owned by the player so that score can be incremented
            if (this.getClass() == BasicAsteroid.class && other.getClass() == Bullet.class)
            {
                BasicAsteroid obj1 = (BasicAsteroid) this;
                obj1.killedByPlayer = true;
            }
            else if (this.getClass() == Bullet.class && other.getClass() == BasicAsteroid.class)
            {
                BasicAsteroid obj1 = (BasicAsteroid) other;
                obj1.killedByPlayer = true;
            }
        }
    }
    public abstract void hit();
    public void update()
    {
        position.addScaled(velocity, DT);
        position.wrap(WORLD_WIDTH, WORLD_HEIGHT);
    }
    public abstract void draw(Graphics2D g);
}
