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
        BufferedImage scaled = new BufferedImage((int) (img.getWidth() * scalex) + 20,
                (int) (img.getHeight() * scaley) + 20, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) scaled.getGraphics();
        AffineTransform init = g.getTransform();
        Composite initComp = g.getComposite();
        // sets background to transparent
        g.setComposite(AlphaComposite.Clear);
        g.setColor(new Color(0,0,0));
        g.fill(new Rectangle(0,0, img.getWidth(), img.getHeight()));
        g.setComposite(initComp);
        // sets clip and fills background with textured pattern
        Shape initClip = g.getClip();
        g.setClip(shape);
        g.scale(scalex, scaley);
        g.drawImage(img, 0, 0, null);
        g.setClip(initClip);
        BasicStroke stroke = new BasicStroke(4f);
        g.setTransform(init);
        g.setColor(new Color(106, 23, 166));
        g.fill(new Area(stroke.createStrokedShape(shape)));
        img = scaled;
    }
}
