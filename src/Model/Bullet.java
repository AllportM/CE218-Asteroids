package Model;

import View.ImgManag;

import java.awt.*;
import java.awt.geom.Path2D;

public abstract class Bullet extends GameObject {
    protected int initSpd = 600;
    protected int width;
    protected int height;
    protected double bulletTime;
    private double ttl = 1;

    public Bullet(Vector2D position, Vector2D velocity, double RADIUS, Ship ship) {
        super(position, velocity, RADIUS);
        bulletTime = System.currentTimeMillis();
        this.direction = ship.direction.copy();
        // sets velocity to ships + init speed
        //TODO: change to
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
        g.fill(sp.getTransformedShape());
        System.out.println("Bullet pos" + position);
        System.out.println("sp pos" + sp.position);
    }
}
