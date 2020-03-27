package Model;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;

/**
 * Sprites purpose is to store a gameobjects image, position, and direction, and provide functionality
 * to paint an image onto a Graphics2D object
 */
public class Sprite {

    Vector2D position; // position relative to world
    Vector2D direction; // direction facing
    BufferedImage img; // the image of this sprite
    double width; // width of image
    double height; // height of image
    Path2D shape; // the shape, to be translated and rotated for collision detection based upon position and direction

    /**
     * Standard constructor for a sprite
     * @param position
     * @param direction
     * @param width
     * @param height
     * @param imname
     * @param shape
     */
    public Sprite(Vector2D position, Vector2D direction, double width, double height, BufferedImage imname
    , Path2D shape)
    {
        this.position = position;
        this.direction = direction;
        this.width = width;
        this.height = height;
        this.img = imname;
        this.shape = shape;
        transformImage();
    }

    /**
     * transform image's purpose is to transform the image upon its construction to the new scale
     */
    void transformImage()
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

    /**
     * provides functionality to translate/rotate and draw this image upon the position and vector
     * @param g
     */
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

    public int getWidth()
    {
        return img.getWidth();
    }

    public int getHeight()
    {
        return img.getHeight();
    }

    /**
     * transorms the shape upon the position and direction
     * @return
     *      transformed shape
     */
    public Shape getTransformedShape()
    {
        AffineTransform at = new AffineTransform();
        at.translate(position.x, position.y);
        at.rotate(direction.angle() + 0.5 * Math.PI, 0,0);
        return shape.createTransformedShape(at);
    }
}
