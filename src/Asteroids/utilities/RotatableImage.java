package Asteroids.utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class RotatableImage implements Icon {

    private ImageIcon img;
    private double degrees;
    private double scaleX;
    private double scaleY;

    public RotatableImage(String fname)
    {
       img = new ImageIcon(fname);
       degrees = 0;
       this.scaleX = this.scaleY = 1;
    }

    public void setRotate(double degrees)
    {
        this.degrees = this.degrees + degrees;
    }

    public void setScale(double x, double y)
    {
        this.scaleX = x;
        this.scaleY = y;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int width = img.getIconWidth()/2;
        int height = img.getIconHeight()/2;
        g2.translate((getIconWidth() - img.getIconWidth()) / 2, (getIconHeight() - img.getIconHeight()) / 2);
        g2.rotate(degrees, x + width, y + height);
//        g2.scale(scaleX, scaleY);
        img.paintIcon(c, g2, x, y);
        g2.dispose();
        scaleX = scaleY = 1;
    }

    @Override
    public int getIconWidth() {
        double sin = Math.sin(degrees);
        double cos = Math.sin(degrees);
        return (int) Math.round(img.getIconWidth() * cos - img.getIconHeight() * sin);
    }

    @Override
    public int getIconHeight() {
        double sin = Math.sin(degrees);
        double cos = Math.sin(degrees);
        return (int) Math.round(img.getIconWidth() * sin + img.getIconHeight() * cos);
    }
}
