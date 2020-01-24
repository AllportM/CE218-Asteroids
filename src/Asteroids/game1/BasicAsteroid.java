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
        for (int x1 = -RADIUS, x2 = RADIUS; x1 < RADIUS * 2; x1++, x2--)
        {
            x[x1 + RADIUS] = x1 + this.x - RADIUS;
            y[x1 + RADIUS] = -Math.sqrt(Math.pow(RADIUS, 2) - Math.pow(x1, 2)) + this.y - RADIUS;
            x[x1 + (2 * RADIUS)] = x2 + this.x + RADIUS;
            y[x1 + (2 * RADIUS)] = +(Math.sqrt(Math.pow(RADIUS, 2) - Math.pow(x2, 2))) + this.y - RADIUS;
        }
//        for (int i = 0, j = RADIUS * 2; i < RADIUS * 2; i++, j--)
//        {
//            if (i > 1)
//            {
//                x[i] = Math.random() * 1 + i + this.x;
//                if (i < RADIUS / 2)
//                {
//                    y[i] = Math.random() * 1 + i + this.y;
//                }
//                else
//                {
//                    y[i] = RADIUS - Math.random() * 1 - (i % RADIUS) + this.y;
//                }
//            }
//            else
//            {
//                x[i] = i + this.x;
//                y[i] = i + this.y;
//            }
//            x[j + i * 2] = Math.random() * 1 + j + this.x;
//            if (j > RADIUS / 2)
//            {
//                y[j + i * 2] = (Math.random() * 1 + i) * -1 + this.y;
//            }
//            else
//            {
//                y[j + i * 2] = (RADIUS - Math.random() * 1 - (i % RADIUS)) * -1 + this.y;
//            }
//        }
        Path2D path = new Path2D.Double();
        path.moveTo(x[0], y[0]);
        for (int i = 1; i < x.length; i++)
        {
            path.lineTo(x[i], y[i]);
        }
        path.closePath();
        g.fill(path);
        //g.fillOval((int) this.x - RADIUS, (int) this.y - RADIUS, 2 * RADIUS,
        //        2 * RADIUS);
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
