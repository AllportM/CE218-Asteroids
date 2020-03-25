package Model;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;

import static Model.Constants.*;

public abstract class GameObject implements Drawable{

    public Vector2D position;
    public Vector2D velocity;
    public Vector2D direction;
    public double RADIUS;
    public boolean alive;
    protected Sprite sp;
    public GameObject(Vector2D position, Vector2D velocity, double RADIUS)
    {
        this.position = position;
        this.velocity = velocity;
        this.RADIUS = RADIUS;
        alive = true;
    }

    public boolean overlap(GameObject other)
    {
        if (this.position.dist(other.position) < this.RADIUS + other.RADIUS)
        {
            Shape myShape = sp.getTransformedShape();
            Shape theirShape = other.sp.getTransformedShape();
            Area area = new Area(myShape);
            area.intersect(new Area(theirShape));
            return !area.isEmpty();
        }
        else return false;
    }


    public void collisionHandling(GameObject other)
    {
        if (this.canHit(other) && overlap(other))
        {
            this.hit(other);
            other.hit(this);
        }
//        // ensures two objects are of differenct classes
//        if (this.getClass() != other.getClass() && this.overlap(other))
//        {
//
//            if (this.getClass() == PlayerShip.class && other.getClass() == Asteroid.class)
//            {
//                PlayerShip obj = (PlayerShip) this;
//                this.hit();
//                obj.hit();
//            }
//            else if (this.getClass() == Asteroid.class && other.getClass() == PlayerShip.class)
//            {
//                this.hit();
//                other.hit();
//            }
//            else if (this.getClass() != MobSpawner.class && other.getClass() != MobSpawner.class)
//            {
//                this.hit();
//                other.hit();
//            }
//
//            // sets killedByPlayer boolean on asteroid if either of the objects which destroyed asteroid were
//            // bullets owned by the player so that score can be incremented
//            if (this.getClass() == Asteroid.class && other.getClass() == Bullet.class)
//            {
//                Asteroid obj1 = (Asteroid) this;
//                Bullet obj2 = (Bullet) other;
//                if (obj2.firedByPlayer)
//                    obj1.killedByPlayer = true;
//            }
//            else if (this.getClass() == Bullet.class && other.getClass() == Asteroid.class)
//            {
//                Asteroid obj1 = (Asteroid) other;
//                Bullet obj2 = (Bullet) this;
//                if (obj2.firedByPlayer)
//                    obj1.killedByPlayer = true;
//            }
//        }
    }
    public abstract boolean canHit(GameObject other);

    public void hit(GameObject other)
    {
        Vector2D coll = new Vector2D(other.position);
        coll.subtract(position).normalise();
        Vector2D tang = new Vector2D(-coll.y, coll.x);
        Vector2D vc = velocity.proj(coll);
        Vector2D vt = velocity.proj(tang);
        Vector2D voc = other.velocity.proj(coll);
        Vector2D vot = other.velocity.proj(tang);
        velocity.set(vt).add(voc);
        other.velocity.set(vot).add(vc);
    }

    public abstract Path2D genShape();
    public void update()
    {
        position.addScaled(velocity, DT);
        position.wrap(WORLD_WIDTH, WORLD_HEIGHT);
    }
    @Override
    public abstract void draw(Graphics2D g);
}
