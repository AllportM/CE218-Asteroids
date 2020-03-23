package Model;

import Controller.Game;
import Model.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;

import static Model.Constants.WORLD_HEIGHT;
import static Model.Constants.WORLD_WIDTH;

public class Sprite {

    Vector2D position;
    Vector2D direction;
    BufferedImage img;
    double width;
    double height;
    Path2D shape;

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

    void scaleImage()
    {
        double scalex = width / img.getWidth();
        double scaley = height / img.getHeight();
        BufferedImage scaled = new BufferedImage((int) (img.getWidth() * scalex),
                (int) (img.getHeight() * scaley), img.getType());
        Graphics2D g = (Graphics2D) scaled.getGraphics();
        AffineTransform initG = g.getTransform();
        g.setTransform(AffineTransform.getScaleInstance(scalex, scaley));
//        g.scale(scalex, scaley);
        g.drawImage(img, 0, 0, null);
        g.setTransform(initG);
        img = scaled;
    }

    public void paint(Graphics2D g) {
        AffineTransform init = g.getTransform();
        AffineTransform imgRotatedAT = new AffineTransform();
        // sets images affine transform such that resultant image will be rotated about center point
        imgRotatedAT.rotate(direction.angle() + 0.5 * Math.PI, 0,0);
        imgRotatedAT.translate((float) -img.getWidth()/2, (float) -img.getHeight()/2);

        // uses translate directly on graphics given affine transforms are not commutative and
        // BasicView already applies a transform to fix to ship
        AffineTransform gAt = g.getTransform();
        gAt.translate((float) (position.x), (float) (position.y));
        g.setTransform(gAt);
        g.drawImage(img, imgRotatedAT, null);
        g.setTransform(init);
    }
}