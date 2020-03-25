package Model;

import Model.Sprite;
import Model.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;

public class AstSprite extends Sprite {

    public AstSprite(Vector2D position, Vector2D direction, double width, double height, BufferedImage imname
            , Path2D shape)
    {
        super(position, direction, width, height, imname, shape);
    }

    @Override
    void scaleImage()
    {
        double scalex = width / img.getWidth();
        double scaley = height / img.getHeight();
        BufferedImage scaled = new BufferedImage((int) (img.getWidth() * scalex),
                (int) (img.getHeight() * scaley), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) scaled.getGraphics();
        AffineTransform init = g.getTransform();
        Composite initComp = g.getComposite();
        Shape initClip = g.getClip();
        // sets background to transparent
        g.setComposite(AlphaComposite.Clear);
        g.setColor(new Color(0,0,0));
        g.fill(new Rectangle(0,0, img.getWidth(), img.getHeight()));
        g.setComposite(initComp);
        // sets clip and fills background with textured pattern


        // draws the asteroid onto new image utilizing shape, which requires translating given 0,0 is
        // centered
        AffineTransform shapeTransform = new AffineTransform();
        shapeTransform.scale(img.getWidth() / width, img.getHeight() / height);
        shapeTransform.translate(width/2, height/2);
        Shape transformed = shape.createTransformedShape(shapeTransform);
        g.setTransform(AffineTransform.getScaleInstance(scalex, scaley));
        g.setClip(transformed);
        g.drawImage(img, 0, 0, null);
        BasicStroke stroke = new BasicStroke(10f);
        g.setClip(initClip);
        g.setColor(new Color(106, 23, 166));
        g.fill(new Area(stroke.createStrokedShape(transformed)));
        img = scaled;
    }
}
