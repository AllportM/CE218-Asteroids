package Asteroids.game1;

import Asteroids.utilities.RotatableImage;
import Asteroids.utilities.Vector2D;

import java.awt.*;
import java.util.LinkedList;

public class Bullet extends GameObject {
    private static Bullet bullet = null;
    private double ttl = 1;
    private int initSpd = 600;
    private RotatableImage sprite1;
    private int width, height;
    private double bulletTime;

    // bullets

    public static Bullet getInstance() {
        return bullet;
    }

    public Bullet(Ship ship) {
        //calls super constructor with new copied and scaled position and velocity vector as args
        super(ship.position.copy(), ship.velocity.copy(),
                2.5);
        this.width = 10;
        this.height = 80;
        // sets velocity to ships + init speed
        this.velocity = Vector2D.polar(ship.direction.angle(), (ship.velocity.mag() + initSpd));
        // sets bullets position to 5mm outside of ships radius
        this.position.add(Vector2D.polar(ship.direction.angle(), ship.RADIUS  + 45));
        sprite1 = RotatableImage.builder("resources/LBullet1in.png");
        sprite1.setRotate(ship.shipRot.angle() - Math.PI*1.5);
        bulletTime = System.currentTimeMillis();
    }

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
    public void hit()
    {
        alive = false;
    }

    @Override
    public void draw(Graphics2D g)
    {
        sprite1.paintIcon(g, (int) position.x, (int) position.y);
    }
}
