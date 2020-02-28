package Model;

import Controller.Game;
import View.ImgManag;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ParallaxingImage extends ParallaxingObject {
    BufferedImage im;

    public ParallaxingImage(int index, int x, int y, String imname)
    {
        super(index, x, y);
        im = ImgManag.getImage(imname);
    }

    @Override
    public void draw(Graphics2D g) {
        System.out.println(speedMult);
        g.drawImage(im, (int) (posx + (Game.vp.getX() * speedMult)), (int) (posy + (Game.vp.getY() * speedMult)), null);
    }
}
