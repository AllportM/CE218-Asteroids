package Model;

import View.ImgManag;
import View.Sprite;

import java.awt.*;

public class Bullet extends GameObject {
    private static Bullet bullet = null;
    private double ttl = 1;
    private int initSpd = 600;
    private Sprite sp;
    private Sprite sp2;
    private int width, height;
    private double bulletTime;
    private Vector2D direction;

    // bullets

    public static Bullet getInstance() {
        return bullet;
    }

    public Bullet(Ship ship, Vector2D direction) {
        //calls super constructor with new copied and scaled position and velocity vector as args
        super(ship.position.copy(), ship.velocity.copy(),
                2.5);
        this.width = 10;
        this.height = 80;
        this.direction = direction;
        // sets velocity to ships + init speed
        this.velocity = Vector2D.polar(ship.direction.angle(), (ship.velocity.mag() + initSpd));
        // sets bullets position to 5mm outside of ships radius
        this.position.add(Vector2D.polar(ship.direction.angle(), ship.RADIUS  + 45));
        sp = new Sprite(position, direction, width, height, ImgManag.getImage("LBullet1in.png"));
        sp2 = new Sprite(position, direction, width, height, ImgManag.getImage("Untitled-5.png"));
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
        sp.paint(g);
        sp2.paint(g);
    }
}
