package Model;

import View.ImgManag;

import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;

/**
 * Playerbullets purpose is to directly implement Bulets abstract methods unique to a player bullet with
 * player bullets image
 */
public class PlayerBullet extends Bullet {

    // bullets

    public PlayerBullet(Ship ship) {
        super(ship.position.copy(), ship.velocity.copy(),
                2.5, ship);
        BufferedImage bul = ImgManag.getImage("LBullet1in.png");
        width = bul.getWidth();
        height = bul.getHeight();
        sp = new Sprite(position, direction, width, height, bul, genShape());
        RADIUS = sp.getHeight() / 2;
    }

    @Override
    public Path2D genShape() {
        Path2D shape = new Path2D.Double();
        shape.append(new Rectangle(-width/2,-height/2,width, height), true);
        return shape;
    }

}
