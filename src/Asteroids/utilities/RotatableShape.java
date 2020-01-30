package Asteroids.utilities;

import Asteroids.utilities.RotateableShapeException;

import java.awt.*;
import java.awt.geom.Path2D;
import java.util.Arrays;

public class RotatableShape {
    double[] x;
    double[] y;
    int curs;
    boolean shapeComplete;

    public RotatableShape(int len)
    {
        x = new double[len];
        y = new double[len];
        curs = -1;
    }

    public void addCoords(double xCoord, double yCooord)
    {
        if (shapeComplete)
            throw new RotateableShapeException(String.format("Length of array '%d' reached when trying to insert element", x.length));
        curs++;
        x[curs] = xCoord;
        y[curs] = yCooord;
        if (curs == x.length -1) shapeComplete = true;
    }

    public void rotateShape(double degree)
    {
        if (!(shapeComplete))
            throw new RotateableShapeException("Attempt to rotate incomplete shape, " +
                    String.format("array length '%d' array elements '%d'", x.length, curs));
        for (int i = 0; i < x.length; i++)
        {
            double xtemp = x[i];
            double sin = Math.sin(degree);
            double cos = Math.cos(degree);
            x[i] = x[i] * cos - y[i] * sin;
            y[i] = xtemp * sin + y[i] * cos;
        }
    }

    public Path2D getPolygon()
    {
        if (!(shapeComplete))
            throw new RotateableShapeException("Attempt to attain path to incomplete shape");
        Path2D result = new Path2D.Double();
        result.moveTo(x[0], y[0]);
        for (int i = 1; i < x.length; i++)
        {
            result.lineTo(x[i], y[i]);
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
