package Model;

import View.ImgManag;

import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;

public class EnemyBullet extends Bullet {

    // bullets

    public EnemyBullet(Ship ship) {
        super(ship.position.copy(), ship.velocity.copy(),
                2.5, ship);
        BufferedImage bul = ImgManag.getImage("LButtet2in.png");
        width = bul.getWidth();
        height = bul.getHeight();
        sp = new Sprite(position, direction, width, height, bul, genShape());
        genShape();
    }

    @Override
    public Path2D genShape() {
        Path2D shape = new Path2D.Double();
        shape.append(new Rectangle(0,0,width, height), true);
        return shape;
    }

}
