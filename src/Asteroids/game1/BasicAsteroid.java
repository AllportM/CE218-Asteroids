package Asteroids.game1;

import Asteroids.utilities.RotatableImage;
import Asteroids.utilities.RotatableShape;
import Asteroids.utilities.Vector2D;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Path2D;

import static Asteroids.game1.Constants.*;

public class BasicAsteroid extends GameObject {
    public static final double MAX_SPEED = 100; // maximum speed an asteroid can have
    private Vector2D rotationalVec;

    private RotatableImage img;

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
        super(new Vector2D(x, y), new Vector2D(vx, vy), rad);
        rotationalVec = (new Vector2D(vx, vy));
        switch (rad)
        {
            case 24:
                img = new RotatableImage(String.format("resources/Ast%d.png", (int) (Math.random() * 7) + 1));
                img.setScale((double) 40 / img.getWidth(), (double) 48 / img.getHeight());
                break;
            case 33:
                img = new RotatableImage(String.format("resources/Ast%d.png", (int) (Math.random() * 7) + 1));
                img.setScale((double) 66 / img.getWidth(), (double) 66 / img.getHeight());
                break;
            case 42:
            default:
                img = new RotatableImage(String.format("resources/Ast%d.png", (int) (Math.random() * 7) + 1));
                img.setScale((double) 84 / img.getWidth(), (double) 84 / img.getHeight());
                break;
        }
    }

    public void hit()
    {

    }

    /**
     * makeRandomAsteroid's main purpose it to create an asteroid with random positional
     * and velocity values utilizing Math.random
     *
     * @return
     */
    public static BasicAsteroid makeRandomAsteroid() {
        int[] radii = {20, 33, 42};
        int radius = radii[(int) (Math.random() * 3)]; // random radius between 12-24 in increments of 6 (3 different
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
        super.update();
        final double angle = rotationalVec.angle() * 0.02;
        // rotates the shape and image
        img.setRotate(angle);
    }

    /**
     * draw's purpose is to draw this asteroid on a give Graphics2D object utilizing populated
     * positional vectors in it's shape vector2D array
     * @param g
     *      Graphics2D, the jswing graphics object to draw unto
     */
    public void draw(Graphics2D g) {
        Graphics2D g1 = (Graphics2D) g.create();
        img.paintIcon(g1, (int) Math.round(position.x), (int) Math.round(position.y));
        g1.dispose();
    }

    @Override
    public String toString() {
        return String.format("I am an asteroid, my positional vector is\n%s", position);
    }
}
