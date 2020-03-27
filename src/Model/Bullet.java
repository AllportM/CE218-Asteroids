package Model;

import java.awt.*;
import java.awt.geom.Path2D;

/**
 * Bullets purpose is to provide standard functionality to be inheritted by both player and enemy bullets
 * given both have different appearences
 */
public abstract class Bullet extends GameObject {
    protected int initSpd = 600; // standard speed of a bullet
    protected int width; // width of this bullet
    protected int height; // height of this bullet
    protected double bulletTime; // the time this bullet was spawned
    private double ttl = 1; // time to live in seconds

    /**
     * Standard constructor
     * @param position
     * @param velocity
     * @param RADIUS
     * @param ship
     */
    public Bullet(Vector2D position, Vector2D velocity, double RADIUS, Ship ship) {
        super(position, velocity, RADIUS);
        bulletTime = System.currentTimeMillis();
        this.direction = ship.direction.copy();
        // sets velocity to ships + init speed
        this.velocity = Vector2D.polar(ship.direction.angle(), (ship.velocity.mag() + initSpd));
        // sets bullets position to 5mm outside of ships radius
        this.position.add(Vector2D.polar(ship.direction.angle(), ship.RADIUS  + height/2 + 50));
    }

    @Override
    public boolean canHit(GameObject other) {
        return other instanceof Asteroid || other instanceof Ship;
    }

    @Override
    public void hit(GameObject other) {
        super.hit(other);
        alive = false;
    }

    @Override
    public abstract Path2D genShape();

    @Override
    public void update() {
        super.update();
        // sets alive status if creation time - this time (seconds) > ttl
        double time = System.currentTimeMillis();
        if (time - bulletTime > ttl * 1000)
        {
            alive = false;
        }
    }

    @Override
    public void draw(Graphics2D g)
    {
        sp.paint(g);
    }
}
