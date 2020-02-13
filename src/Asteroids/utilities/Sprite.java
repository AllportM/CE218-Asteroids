package Asteroids.utilities;

import com.sun.corba.se.impl.orbutil.graph.Graph;

import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;

public class Sprite {

    Vector2D position;
    Vector2D direction;
    BufferedImage img;
    double width;
    double height;
    private Path2D shape;

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
    , Path2D shape)
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

    public void paintWithShape(Graphics2D g)
    {
        BufferedImage rotated = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        AffineTransform init = g.getTransform();
        Shape initClip = g.getClip();
        Graphics2D imgG = (Graphics2D) rotated.getGraphics();
        AffineTransform at = new AffineTransform();
        at.rotate(direction.angle() + 0.5 * Math.PI, img.getWidth()/2,img.getHeight()/2);
//        at.translate(-img.getWidth()/2, -img.getHeight()/2);
        imgG.setTransform(at);
//        BasicStroke stroke = new BasicStroke(2f);
        imgG.setClip(shape);
//        imgG.fill(new Area(stroke.createStrokedShape(shape)));
        imgG.drawImage(img, 0, 0, null);
        g.drawImage(rotated, (int) position.x - img.getWidth()/2, (int) position.y - img.getHeight()/2, null);
        g.setClip(initClip);
    }
}
