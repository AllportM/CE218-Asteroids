package Asteroids.game1;

import Asteroids.utilities.Refresh;
import Asteroids.utilities.RotatableImage;
import Asteroids.utilities.RotatableShape;
import Asteroids.utilities.Vector2D;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.util.LinkedList;

import static Asteroids.game1.Constants.*;

public class BasicAsteroid implements Refresh {
    public final int RADIUS; // size of the asteroid
    public static final double MAX_SPEED = 100; // maximum speed an asteroid can have

    private Vector2D velocityVec;
    private Vector2D positionalVec;
    private Vector2D rotationalVec;

    private RotatableImage img;

    private RotatableShape shape;
//    private LinkedList<Vector2D> texture; // old rotating texture

    /**
     * Standard constructor for an asteroid
     *
     * @param x
     * @param y  Positional value to instantiate an asteroid with
     * @param vx
     * @param vy Velocity values to instantiate an asteroid with
     */
    public BasicAsteroid(double x, double y, double vx, double vy, int rad) {
        positionalVec = new Vector2D(x, y);
        velocityVec = new Vector2D(vx, vy);
        RADIUS = rad;
        rotationalVec = (new Vector2D(velocityVec));
        makeShapeCoords();
        shape.setScale(1.5);
        img = new RotatableImage("resources/astSurface2.gif");
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
     * getShapeCord's purpose is to generate x/y coordinates for a slightly random shape given it's radius
     * @return
     *      int[][], [0][..] and [1][..] being randomly generated x and y coordinates respectively
     */
    public void makeShapeCoords()
    {
        // init shape
        shape = new RotatableShape((RADIUS * 2) - (2* (RADIUS / 3)));
//        texture = new LinkedList<>();

        // Adds coordinates for top half of circle, slightly randomized y coord +/- 3pixels
        for (int xCoord = -RADIUS; xCoord < RADIUS; xCoord+=3)
        {
            double x = xCoord;
            double y = (Math.random()*8-4) + Math.sqrt(RADIUS * RADIUS - xCoord * xCoord);
            shape.pushCoords(x, y);
        }

        // Adds coordinates for bottom half of circle
        for (int xCoord = -RADIUS; xCoord < RADIUS; xCoord+=3)
        {
            double x = xCoord;
            double y = (Math.random()*8-4) + Math.sqrt(RADIUS * RADIUS - xCoord * xCoord);
            shape.pushCoords(-x, -y);
        }

//        for (int i = 0; i < 100; i++)
//        {
//            texture.add(new Vector2D(Math.random()*50 - RADIUS, Math.random()*50-RADIUS));
//        }
    }

    /**
     * update's purpose is to update the asteroids position given it's velocity
     * and change in time DT
     */
    public void update() {
        positionalVec.add(velocityVec.x * DT, velocityVec.y * DT);
        positionalVec.wrap(FRAME_WIDTH, FRAME_HEIGHT);

        final double angle = rotationalVec.angle() * 0.02;
        // rotates the shape and image
        shape.rotateShape(angle);
        img.setRotate(angle);
//        for (Vector2D vec: texture)
//        {
//            vec.rotate(-angle);
//        }
    }

    /**
     * draw's purpose is to draw this asteroid on a give Graphics2D object utilizing populated
     * positional vectors in it's shape vector2D array
     * @param g
     *      Graphics2D, the jswing graphics object to draw unto
     */
    public void draw(Graphics2D g, Component c) {
        Graphics2D g1 = (Graphics2D) g.create();
        g1.setColor(new Color(106, 23, 166));
        Path2D path = shape.getPath(positionalVec.x, positionalVec.y);

        // adds outline
        BasicStroke stroke = new BasicStroke(7f);
        g1.fill(new Area(stroke.createStrokedShape(path)));
        g1.fill(path);

        Shape clip = g1.getClip();
        g1.setClip(path);
        img.setScale(2, 2);
        img.paintIcon(c, g1, (int) Math.round(positionalVec.x), (int) Math.round(positionalVec.y));
        g1.setClip(clip);
        g1.dispose();
    }
}
