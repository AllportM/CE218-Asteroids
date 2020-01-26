package Asteroids.game1;

import javax.imageio.ImageIO;
import javax.xml.crypto.dsig.Transform;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static Asteroids.game1.Constants.*;

public class BasicAsteroid {
    public final int RADIUS; // size of the asteroid
    public static final double MAX_SPEED = 100; // maximum speed an asteroid can have

    private double x, y; // positional values of the asteroid
    private double vx, vy; // velocity values of the asteroid

    private BufferedImage surface;

    private int[][] astShape;

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
        this.astShape = getShapeCoords();
        try
        {
            surface = ImageIO.read(new File("resources/astSurface.gif"));
        }
        catch (IOException e)
        {
            throw new RuntimeException("Image '/resources/astSurface.gif' not found");
        }
    }

    /**
     * makeRandomAsteroid's main purpose it to create an asteroid with random positional
     * and velocity values utilizing Math.random
     *
     * @return
     */
    public static BasicAsteroid makeRandomAsteroid() {
        int radius = (int) (Math.random() * 3 + 2) * 6; // random radius between 12-24 in increments of 6 (3 different
                                                        // sizes of asteroids)
        double x = Math.random() * FRAME_WIDTH;
        double y = Math.random() * FRAME_HEIGHT;
        double vx = (Math.random() * MAX_SPEED * 2) - MAX_SPEED;
        double vy = (Math.random() * MAX_SPEED * 2) - MAX_SPEED;
//        System.out.printf("posx= %3.2f, posy= %3.2f\nspeedx= %3.2f, speedy = %3.2f\n",
//                x, y, vx, vy);
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

    /**
     * getShapeCord's purpose is to generate x/y coordinates for a slightly random shape given it's radius
     * @return
     *      int[][], [0][..] and [1][..] being randomly generated x and y coordinates respectively
     */
    public int[][] getShapeCoords()
    {
        // init arays
        int[][] result = new int[2][];
        int[] x = new int[(RADIUS * 2) - (2* (RADIUS / 3))];
        int[] y = new int[(RADIUS * 2) - (2* (RADIUS / 3))];
        int count = 0; // count for coordinates for 0-(2*radius)coordinates for x
        int count2 = RADIUS - (RADIUS / 3); // count for coordinates (2*radius) - 0 coordinates for x starting at the
                                            // end of above count
        for (int xCoord = 1; xCoord <= RADIUS * 2; xCoord+=3)
        {
            // positive x coords
            x[count] = (int) (Math.random()*5) + xCoord - RADIUS -1;
            y[count] = (xCoord <= RADIUS)? (int) (Math.random() * 5) + xCoord - 1:
                    (int) (Math.random()*5) + (RADIUS*2) - xCoord - 1;
            // negative x coords starting at the end count
            x[count2] = -((int) (Math.random()*5) + xCoord - RADIUS - 1);
            y[count2] = (xCoord <= RADIUS)? -((int) (Math.random() * 5) + xCoord - 1):
                    -((int) (Math.random()*5) + (RADIUS*2) - xCoord - 1);
            count++;
            count2++;
        }
        result[0] = x;
        result[1] = y;
        return result;
    }

    /**
     * draw's purpose is to draw this asteroid on a give Graphics2D object
     * @param g
     *      Graphics2D, the jswing graphics object to draw unto
     */
    public void draw(Graphics2D g) {
        // sets fill image
        TexturePaint tp = new TexturePaint(surface, new Rectangle((int) this.x - RADIUS,(int) this.y - RADIUS,
                50, 48));
        g.setPaint(tp);
        // creates polygon/path of this asteroid given it's shape
        Path2D path = new Path2D.Double();
        path.moveTo(astShape[0][0] + this.x, astShape[1][0] + this.y);
        for (int i = 1; i < astShape[0].length; i++)
        {
            path.lineTo(astShape[0][i] + this.x, astShape[1][i] + this.y);
        }
        path.closePath();
        g.fill(path);
        // adds outline
        g.setColor(new Color(153, 43, 43));
        BasicStroke stroke = new BasicStroke(1f);
        g.draw(new Area(stroke.createStrokedShape(path)));
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
