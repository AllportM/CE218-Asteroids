package Asteroids.game1;

import Asteroids.utilities.RotatableImage;
import Asteroids.utilities.Vector2D;

import java.awt.*;

import static Asteroids.game1.Constants.DT;

public class Bullet extends GameObject {
    private static Bullet bullet = null;
    private double ttl = 1;
    private int initSpd = 50;
    private RotatableImage sprite;
    private Ship ship;

    // bullets

    public static Bullet getInstance() {
        return bullet;
    }

    private Bullet(Ship ship) {
        //calls super constructor with new copied and scaled position and velocity vector as args
        super(ship.position.copy().addScaled(ship.position, 1.55), ship.velocity.copy().mult(50),
                3);
        sprite = RotatableImage.builder("resources/ship2.gif").setRotate(velocity.angle());
    }

    @Override
    public void update()
    {
        super.update();
        ttl -= DT;
        if (ttl <= 0)
        {
            alive = false;
            ship.emptyBullet();
        }
    }

    @Override
    public void hit()
    {

    }

    @Override
    public void draw(Graphics2D g, Component c)
    {
        sprite.paintIcon(c, g, (int) position.x, (int) position.y);
    }
}
