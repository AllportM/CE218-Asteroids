package Asteroids.utilities;

import com.sun.corba.se.impl.orbutil.graph.Graph;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Sprite {

    Vector2D position;
    Vector2D direction;
    BufferedImage img;
    double width;
    double height;
    private RotatableShape shape;

    public Sprite(Vector2D position, Vector2D direction, double width, double height, BufferedImage imname)
    {
        this.position = position;
        this.direction = direction;
        this.width = width;
        this.height = height;
        this.img = imname;
        scaleImage();
    }

    public Sprite(Vector2D position, Vector2D direction, double width, double height, BufferedImage imname
    , RotatableShape shape)
    {
        this.position = position;
        this.direction = direction;
        this.width = width;
        this.height = height;
        this.img = imname;
        this.shape = shape;
        scaleImage();
    }

    private void scaleImage()
    {
        double scalex = width / img.getWidth();
        double scaley = height / img.getHeight();
        BufferedImage scaled = new BufferedImage((int) (img.getWidth() * scalex),
                (int) (img.getHeight() * scaley), img.getType());
        Graphics2D g = (Graphics2D) scaled.getGraphics();
        g.scale(scalex, scaley);
        g.drawImage(img, 0, 0, null);
        img = scaled;
    }

    public void paint(Graphics2D g) {
        AffineTransform init = g.getTransform();
        AffineTransform at = new AffineTransform();
        at.rotate(direction.angle() + 0.5 * Math.PI, 0,0);
        at.translate(-img.getWidth()/2, -img.getHeight()/2);
        g.translate(position.x, position.y);
        g.drawImage(img, at, null);
        g.setTransform(init);
    }
}
