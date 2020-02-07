package Asteroids.utilities;

import java.awt.geom.Path2D;
import java.util.Arrays;

/**
 * Rotatable shape's purpose is to contain x/y coordinates for intended shapes whilst enabling the functionality
 * to scale and rotate the coordinates whilst also being able to return a Path2D awt shape for graphical drawing
 */
public class RotatableShape {
    double[] x; // x coordinate array
    double[] y; // y coordinate array
    int curs; // cursor for location in array
    boolean shapeComplete; // identifies when array is full
    double scale; // factor to scale cordinates by

    /**
     * Standard constructor, initializing arrays to the length given as argument
     * @param len
     *      Quantity of coordinates to be stored
     */
    public RotatableShape(int len)
    {
        x = new double[len];
        y = new double[len];
        curs = -1;
    }

    /**
     * Sets the scaling factor
     * @param scale
     *      Value
     */
    public void setScale(double scale)
    {
        this.scale = scale;
    }

    /**
     * Pushes coordinates onto arrays
     * @param xCoord
     *      x value
     * @param yCooord
     *      y value
     */
    public void pushCoords(double xCoord, double yCooord)
    {
        if (shapeComplete)
            throw new RotateableShapeException(String.format("Length of array '%d' reached when trying to insert element", x.length));
        curs++;
        x[curs] = xCoord;
        y[curs] = yCooord;
        if (curs == x.length -1) shapeComplete = true;
    }

    /**
     * Rotates the stored coordinates by a given angle
     * @param degree
     *      angle to be rotated by
     */
    public void rotateShape(double degree)
    {
        if (!(shapeComplete))
            throw new RotateableShapeException("Attempt to rotate incomplete shape, " +
                    String.format("array length '%d' array elements '%d'", x.length, curs));
        // loops over all coordinates and rotates
        for (int i = 0; i < x.length; i++)
        {
            double xtemp = x[i];
            double sin = Math.sin(degree);
            double cos = Math.cos(degree);
            x[i] = x[i] * cos - y[i] * sin;
            y[i] = xtemp * sin + y[i] * cos;
        }
    }

    /**
     * getPath's purpose is to return a Path2D object of this given shape
     * @param posx
     * @param posy
     * @return
     */
    public Path2D getPath(double posx, double posy)
    {
        if (!(shapeComplete))
            throw new RotateableShapeException("Attempt to attain path to incomplete shape");
        Path2D result = new Path2D.Double();
        result.moveTo(x[0] * scale + posx, y[0] * scale + posy);
        for (int i = 1; i < x.length; i++)
        {
            result.lineTo(x[i] * scale + posx, y[i] * scale + posy);
        }
        result.closePath();
        return result;
    }

    @Override
    public String toString()
    {
        return String.format("x= %s\ny=%s\n", Arrays.toString(x), Arrays.toString(y));
    }
}
