package Asteroids.utilities;

import javax.swing.*;
import java.awt.*;

/**
 * RotatableImage's purpose is to extend awt Icon class, overriding functionality to paint the image associated
 * with to an instance, and adding functionality to rotate and scale the image
 */
public class RotatableImage implements Icon {

    private ImageIcon img; // core image to be painted
    private double degrees; // angle to be rotated by
    private double scaleX; // scaling factor on x component
    private double scaleY; // scaling factor on y component

    /**
     * Standard constructor associating image icon to filename argument
     * @param fname
     *      String, file and path for the image
     */
    public RotatableImage(String fname)
    {
       img = new ImageIcon(fname);
       degrees = 0;
       this.scaleX = this.scaleY = 1;
    }

    /**
     * setRotate's purpose is to increment/decrement angle of rotation by given degrees
     * @param degrees
     *      double, value of angle to be changed by
     */
    public void setRotate(double degrees)
    {
        this.degrees = this.degrees + degrees;
    }

    /**
     * setScale's purpose is to set the scaling factor
     * @param x
     *      double, x factor
     * @param y
     *      double, y factor
     */
    public void setScale(double x, double y)
    {
        this.scaleX = x;
        this.scaleY = y;
    }

    /**
     * paintIcon's purpose is to overrid the superclasses paint method, painting a scaled and rotated
     * image onto given graphics component
     * @param c
     *      JComponent, reference to the component to be painted on
     * @param g
     *      Graphics object, to be painted on
     * @param x
     *      int, x positional value for 0,0
     * @param y
     *      int, y positional value for 0,0
     */
    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2 = (Graphics2D) g.create();
        int width = img.getIconWidth()/2;
        int height = img.getIconHeight()/2;
        g2.translate(-width * scaleX + x, -height * scaleY + y); // sets 0,0 point to center of image
                        // scaled by given scale factor, then adjusts adds center of screen
        g2.rotate(degrees, width * scaleX, height * scaleY);
        g2.scale(scaleX, scaleY);
        img.paintIcon(c, g2, 0, 0);
        g2.dispose();
        scaleX = scaleY = 1;
    }

    @Override
    public int getIconWidth() {
        return img.getIconWidth();
    }

    @Override
    public int getIconHeight() {
        return img.getIconHeight();
    }

    public double getRotatedWidth()
    {
        double sin = Math.sin(degrees);
        double cos = Math.cos(degrees);
        return Math.round(img.getIconWidth() * cos - img.getIconHeight() * sin);
    }

    public double getRotatedHeight()
    {
        double sin = Math.sin(degrees);
        double cos = Math.cos(degrees);
        return (int) Math.round(img.getIconWidth() * sin + img.getIconHeight() * cos);
    }
}
