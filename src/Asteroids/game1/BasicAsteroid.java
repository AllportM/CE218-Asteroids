package Asteroids.game1;

import Asteroids.utilities.Refresh;
import Asteroids.utilities.Vector2D;
import com.sun.imageio.plugins.common.ImageUtil;

import javax.imageio.ImageIO;
import javax.xml.crypto.dsig.Transform;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import static Asteroids.game1.Constants.*;

public class BasicAsteroid implements Refresh {
    public final int RADIUS; // size of the asteroid
    public static final double MAX_SPEED = 100; // maximum speed an asteroid can have

    private Vector2D velocityVec;
    private Vector2D positionalVec;
    private Vector2D rotationalVec;

    private BufferedImage surface;

    private Vector2D[] shape;
    private LinkedList<Vector2D> texture;

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
        try
        {
            surface = ImageIO.read(new File("resources/astSurface.gif"));
        }
        catch (IOException e)
        {
            throw new RuntimeException("Image '/resources/astSurface.gif' not found");
        }
        ColorModel cm = surface.getColorModel();
        boolean isAlp = cm.isAlphaPremultiplied();
        WritableRaster rast = surface.copyData(null);
        surface = new BufferedImage(cm, rast, isAlp, null);
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
        // init arays
        shape = new Vector2D[(RADIUS * 2) - (2* (RADIUS / 3))];
        texture = new LinkedList<>();
        int count = 0; // count for coordinates for 0-(2*radius)coordinates for x
        int count2 = RADIUS - (RADIUS / 3); // count for coordinates (2*radius) - 0 coordinates for x starting at the
                                            // end of above count

        // draws oval, slightly randomized y coord +/- 3pixels
        for (int xCoord = -RADIUS; xCoord < RADIUS; xCoord+=3)
        {
            double x = xCoord;
            double y = (Math.random()*8-4) + Math.sqrt(RADIUS * RADIUS - xCoord * xCoord);
            shape[count] = new Vector2D(x, y);
            shape[count2] = new Vector2D(-x, -y);
            count++;
            count2++;
        }

        for (int i = 0; i < 100; i++)
        {
            texture.add(new Vector2D(Math.random()*50 - RADIUS, Math.random()*50-RADIUS));
        }
    }

    /**
     * update's purpose is to update the asteroids position given it's velocity
     * and change in time DT
     */
    public void update() {
        positionalVec.add(velocityVec.x * DT, velocityVec.y * DT);
        positionalVec.wrap(FRAME_WIDTH, FRAME_HEIGHT);

        final double angle = rotationalVec.angle() * 0.02;
        // rotates the shape in a given direction related to its velocity and scaled
        for (Vector2D vec: shape)
        {
            vec.rotate(angle);
        }
        for (Vector2D vec: texture)
        {
            vec.rotate(-angle);
        }
    }

    /**
     * draw's purpose is to draw this asteroid on a give Graphics2D object utilizing populated
     * positional vectors in it's shape vector2D array
     * @param g
     *      Graphics2D, the jswing graphics object to draw unto
     */
    public void draw(Graphics2D g) {
        //sets fill image
        TexturePaint tp = new TexturePaint(surface, new Rectangle((int) positionalVec.x - RADIUS,
                (int) positionalVec.y - RADIUS,50, 48));
        g.setPaint(tp);

//        g.setColor(Color.BLUE);
        // creates polygon/path of this asteroid given it's shape
        Path2D path = new Path2D.Double();
        path.moveTo(shape[0].x + positionalVec.x,shape[0].y + positionalVec.y);
        for (int i = 1; i < shape.length; i++)
        {
            path.lineTo(shape[i].x + positionalVec.x,shape[i].y + positionalVec.y);
        }
        path.closePath();
        g.fill(path);
        // adds outline
        g.setColor(new Color(153, 43, 43));
        BasicStroke stroke = new BasicStroke(1f);
        g.draw(new Area(stroke.createStrokedShape(path)));

//        Shape s = g.getClip();
//        g.clip(path);
//        g.setColor(Color.red);
//        for (Vector2D vec: texture)
//        {
//            g.fillOval((int) (vec.x + positionalVec.x), (int) (vec.y + positionalVec.y),
//                    2, 2);
//        }
//        g.setClip(s);
    }
}
