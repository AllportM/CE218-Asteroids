package Asteroids.game1;

import java.awt.*;
import java.awt.geom.Path2D;

import static Asteroids.game1.Constants.*;

public class BasicAsteroid {
    public final int RADIUS; // size of the asteroid
    public static final double MAX_SPEED = 200; // maximum speed an asteroid can have

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
    public BasicAsteroid(double x, double y, double vx, double vy, int rad) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        RADIUS = rad;
    }

    /**
     * makeRandomAsteroid's main purpose it to create an asteroid with random positional
     * and velocity values utilizing Math.random
     *
     * @return
     */
    public static BasicAsteroid makeRandomAsteroid() {
        int radius = (int) (Math.random() * 20) + 20; // random radius between 10-40
        double x = Math.random() * FRAME_WIDTH;
        double y = Math.random() * FRAME_HEIGHT;
        double vx = (Math.random() * MAX_SPEED * 2) - MAX_SPEED;
        double vy = (Math.random() * MAX_SPEED * 2) - MAX_SPEED;
        System.out.printf("posx= %3.2f, posy= %3.2f\nspeedx= %3.2f, speedy = %3.2f\n",
                x, y, vx, vy);
        return new BasicAsteroid(x, y, vx, vy, radius);
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
        double[] x = new double[RADIUS * 4];
        double[] y = new double[RADIUS * 4];
        for (int i = 0, j = RADIUS * 2; i < RADIUS * 2; i++, j--)
        {
            if (i > 1)
            {
                x[i] = Math.random() * 1 + i;
                if (i < RADIUS / 2)
                {
                    y[i] = Math.random() * 1 + i;
                }
                else
                {
                    y[i] = RADIUS - Math.random() * 1 - (i % RADIUS);
                }
            }
            else
            {
                x[i] = i;
                y[i] = i;
            }
            x[j + i * 2] = Math.random() * 1 + j;
            if (j > RADIUS / 2)
            {
                y[j + i * 2] = (Math.random() * 1 + i) * -1;
            }
            else
            {
                y[j + i * 2] = (RADIUS - Math.random() * 1 - (i % RADIUS)) * -1;
            }
        }
        g.fill(new Path2D.Double());
        g.fillOval((int) this.x - RADIUS, (int) this.y - RADIUS, 2 * RADIUS,
                2 * RADIUS);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getVx() {
        return vx;
    }

    public double getVy() {
        return vy;
    }
}
