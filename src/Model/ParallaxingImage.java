package Model;

import View.ImgManag;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * ParallaxingImage's purpose is to provide direct implementation of parallaxing objects draw method
 */
public class ParallaxingImage extends ParallaxingObject {
    BufferedImage im;

    public ParallaxingImage(int index, int x, int y, String imname)
    {
        super(index, x, y);
        im = ImgManag.getImage(imname);
    }

    @Override
    public void draw(Graphics2D g) {
        int[] coords = getCoord();
        g.drawImage(im, coords[0], coords[1], null);
    }
}
