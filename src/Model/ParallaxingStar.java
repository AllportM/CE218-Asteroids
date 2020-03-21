package Model;

import Controller.Game;
import View.ImgManag;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import static Model.Constants.*;

public class ParallaxingStar extends ParallaxingObject{
    BufferedImage imBack; // background star
    BufferedImage imForeg; // foreground star
    double starSize, opacity, lastUpdated;
    boolean increm;
    double twinkleRate, twinkle;

    public ParallaxingStar(int index, int x, int y)
    {
        super(index, x, y);
        imBack = ImgManag.getImage("StarMain.png");
        imForeg = ImgManag.getImage(String.format("StarInner%d.png", (int) (Math.random() * 7)));
        starSize = (int) (Math.random() * 10) + 5;
        opacity = 0;
        increm = true;
        twinkle = 0;
        twinkleRate = Math.random() + 2;
    }

    public void update()
    {
        if (increm)
        {
            if (twinkle + DT * twinkleRate >= 1)
            {
                twinkle = 1;
                increm = false;
            }
            else
                twinkle += DT * twinkleRate;
            return;
        }
        if (twinkle - DT * twinkleRate <= 0)
        {
            twinkle = 0;
            increm = true;
        }
        else
            twinkle -= DT * twinkleRate;
    }

    @Override
    public void draw(Graphics2D g)
    {
        Graphics2D forg = (Graphics2D) imBack.getGraphics();
        Composite forgInitComp = forg.getComposite();
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) (twinkle)));
        int[] coords = getCoord();
        AffineTransform at = new AffineTransform();
        at.translate(coords[0], coords[1]);
        at.scale(starSize/imForeg.getWidth(), starSize/imForeg.getHeight());
        at.translate(imBack.getWidth()/2, imBack.getHeight()/2);
        g.drawImage(imBack, at, null);
        g.drawImage(imForeg, at, null);
        g.setComposite(forgInitComp);
    }

    @Override
    public String toString()
    {
        return String.format("Parallaxing star\nLayer: %s\n x: %d\ny: %d\n", zIndex, x, y);
    }
}
