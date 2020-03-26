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
        velocity.set(vt).add(voc.mult(0.3));
        other.velocity.set(vot).add(vc.mult(0.3));
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
