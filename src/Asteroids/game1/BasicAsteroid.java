package Asteroids.game1;

import java.awt.*;

import static Asteroids.game1.Constants.*;

public class BasicAsteroid {
    public static final int RADIUS = 10; // size of the asteroid
    public static final double MAX_SPEED = 100; // maximum speed an asteroid can have

    private double x, y; // positional values of the asteroid
    private double vx, vy; // velocity values of the asteroid

    /**
     * Standard constructor for an asteroid
     *
     * @param x
     * @param y  Positional value to instantiate an asteroid with
     * @param vx
     * @param vy Velocity values to instantiate an asteroid with
     */
    public BasicAsteroid(double x, double y, double vx, double vy) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
    }

    /**
     * makeRandomAsteroid's main purpose it to create an asteroid with random positional
     * and velocity values utilizing Math.random
     *
     * @return
     */
    public static BasicAsteroid makeRandomAsteroid() {
        double x = Math.random() * FRAME_WIDTH;
        double y = Math.random() * FRAME_HEIGHT;
        double vx = (Math.random() * MAX_SPEED * 2) - MAX_SPEED;
        double vy = (Math.random() * MAX_SPEED * 2) - MAX_SPEED;
        System.out.printf("posx= %3.2f, posy= %3.2f\nspeedx= %3.2f, speedy = %3.2f",
                x, y, vx, vy);
        return new BasicAsteroid(x, y, vx, vy);
    }

    /**
     * update's purpose is to update the asteroids position given it's velocity
     * and change in time DT
     */
    public void update() {
        x += vx * DT;
        y += vy * DT;
        // below accounts for frame wrap
        x = (x + FRAME_WIDTH) % FRAME_WIDTH;
        y = (y + FRAME_HEIGHT) % FRAME_HEIGHT;
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.red);
        g.fillOval((int) x - RADIUS, (int) y - RADIUS, 2 * RADIUS,
                2 * RADIUS);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
