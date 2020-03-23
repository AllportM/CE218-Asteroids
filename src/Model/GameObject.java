package Model;

import java.awt.*;

import static Model.Constants.*;

public abstract class GameObject implements Drawable{

    public Vector2D position;
    public Vector2D velocity;
    public Vector2D direction;
    public double RADIUS;
    public boolean alive;
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

            if (this.getClass() == PlayerShip.class && other.getClass() == Asteroid.class)
            {
                PlayerShip obj = (PlayerShip) this;
                this.hit();
                obj.hit();
            }
            else if (this.getClass() == Asteroid.class && other.getClass() == PlayerShip.class)
            {
                this.hit();
                other.hit();
            }
            else
            {
                this.hit();
                other.hit();
            }

            // sets killedByPlayer boolean on asteroid if either of the objects which destroyed asteroid were
            // bullets owned by the player so that score can be incremented
            if (this.getClass() == Asteroid.class && other.getClass() == Bullet.class)
            {
                Asteroid obj1 = (Asteroid) this;
                Bullet obj2 = (Bullet) other;
                if (obj2.firedByPlayer)
                    obj1.killedByPlayer = true;
            }
            else if (this.getClass() == Bullet.class && other.getClass() == Asteroid.class)
            {
                Asteroid obj1 = (Asteroid) other;
                Bullet obj2 = (Bullet) this;
                if (obj2.firedByPlayer)
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
    @Override
    public abstract void draw(Graphics2D g);
}
