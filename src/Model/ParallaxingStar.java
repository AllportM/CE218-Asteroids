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
    double starSize; // used to scale star and for brightness during twinkle respectively
    boolean increm; // to indicate whether brightness increasing or decreasing in update
    double twinkleRate, twinkle; // for the rate of twinkle, and the opacity of the star


    /**
     * Standard constructor for a star, calls super to instantiate general class variables and instantiates
     * vairables specific to this class structure
     * @param index
     *      int, which z layer to use and in effect scale the scrolling speed
     * @param x
     *      int, x 2d positional value
     * @param y
     *      int, y 2d positional value
     */
    public ParallaxingStar(int index, int x, int y)
    {
        super(index, x, y);
        imBack = ImgManag.getImage("StarMain.png");
        imForeg = ImgManag.getImage(String.format("StarInner%d.png", (int) (Math.random() * 7)));
        starSize = (int) (Math.random() * 5) + 5; // random size between 5-15 pixels
        increm = true;
        twinkle = 0;
        twinkleRate = Math.random() + 2; // between 2-3 updates per second
    }

    /**
     * update's purpose is to adjust the stars brightness (twinkle variable) as a product of
     * the change in time and the twinkle rate generated in construction
     */
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
        int[] coords = getCoord();
        Composite initComp = g.getComposite();
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) (twinkle)));

        // sets image transform - scale and translating
        AffineTransform at = new AffineTransform();
        at.translate(coords[0], coords[1]);
        at.scale(starSize/imForeg.getWidth(), starSize/imForeg.getHeight());
        at.translate(imBack.getWidth()/2, imBack.getHeight()/2);

        // draws and resets transparency
        g.drawImage(imBack, at, null);
        g.drawImage(imForeg, at, null);
        g.setComposite(initComp);
    }

    @Override
    public String toString()
    {
        return String.format("Parallaxing star\nLayer: %s\n x: %d\ny: %d\n", zIndex, x, y);
    }
}
