package Model;

import View.ImgManag;

import java.awt.*;

public class Bullet extends GameObject {
    private double ttl = 1;
    private int initSpd = 600;
    private Sprite sp;
    private Sprite sp2;
    private int width, height;
    private double bulletTime;
    public boolean firedByPlayer = false;

    // bullets

    public Bullet(Ship ship, Vector2D direction) {
        super(ship.position.copy(), ship.velocity.copy(),
                2.5);
        if (ship instanceof PlayerShip)
        {
            firedByPlayer = true;
        }
        this.width = 10;
        this.height = 80;
        this.direction = direction;
        // sets velocity to ships + init speed
        this.velocity = Vector2D.polar(ship.direction.angle(), (ship.velocity.mag() + initSpd));
        // sets bullets position to 5mm outside of ships radius
        this.position.add(Vector2D.polar(ship.direction.angle(), ship.RADIUS  + 45));
        sp = new Sprite(position, direction, width, height, ImgManag.getImage("LBullet1in.png"));
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
    }
}