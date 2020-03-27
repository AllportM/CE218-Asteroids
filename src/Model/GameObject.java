package Model;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Path2D;

import static Model.Constants.*;

/**
 * GameObjects purpose is to provide standard functionality for all intractable game objects for the game
 * which contain velocity, position, direction etc and provide abstract methods for direct implementation
 * for its sub classes
 */
public abstract class GameObject implements Drawable{
    public Vector2D position; // the position the world
    public Vector2D velocity; // the velocity vector
    public Vector2D direction; // the direction the object is facing
    public double RADIUS; // the raadius used in collision detections first pass
    public boolean alive; // whether object is alive
    protected Sprite sp; // the sprite/image for this object

    /**
     * Standard constructor for a game object associating member variables
     * @param position
     *      Vector2D, the objects position
     * @param velocity
     *      Vector2D, the objects speed
     * @param RADIUS
     *      double, the objects radii
     */
    public GameObject(Vector2D position, Vector2D velocity, double RADIUS)
    {
        this.position = position;
        this.velocity = velocity;
        this.RADIUS = RADIUS;
        alive = true;
    }

    /**
     * Double pass through for overlap detection, firstly checked whether the ships radii has overlapped,
     * if so it generates a translated and rotated shap in accordance to ships position and direction from
     * the sprites shape method and detects if the shapes intersects
     * @param other
     *      GameObject, the other object to check against
     * @return
     */
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

    /**
     * collisionHandling purpose is to call hit upon this object and the other if they can hit one another
     * and the overlap
     * @param other
     */
    public void collisionHandling(GameObject other)
    {
        if (this.canHit(other) && overlap(other))
        {
            this.hit(other);
            other.hit(this);
        }
    }

    /**
     * abstract method for direct implementation, to identify if the object can be hit by the other
     * @param other
     * @return
     */
    public abstract boolean canHit(GameObject other);

    /**
     * Hit method to be overridden by all sub classes and be called by all classes, this implementation
     * adjusts their velocities such that they push one another away in the right direction
     * @param other
     *      GameObject, the other shit to ammend
     */
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

    /**
     * Abstract method for direct implementation, generates a Path2D shape for the given object
     * @return
     */
    public abstract Path2D genShape();

    /**
     * standard update that all objects will do, individual objects will provide further implementation
     * and override through polymorphism
     */
    public void update()
    {
        position.addScaled(velocity, DT);
        position.wrap(WORLD_WIDTH, WORLD_HEIGHT);
    }

    /**
     * abstract method for direct implementation on how to draw the objects
     * @param g
     */
    @Override
    public abstract void draw(Graphics2D g);
}
