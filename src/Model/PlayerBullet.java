package Model;

import View.ImgManag;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;

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
        shape.append(new Rectangle(0,0,width, height), true);
        return shape;
    }

}
