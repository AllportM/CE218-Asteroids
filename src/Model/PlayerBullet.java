package Model;

import View.ImgManag;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;

public class PlayerBullet extends Bullet {

    // bullets

    public PlayerBullet(Ship ship) {
        super(ship.position.copy(), ship.velocity.copy(),
                2.5, ship);
        sp = new Sprite(position, direction, width, height, ImgManag.getImage("LBullet1in.png"));
    }

    @Override
    public Path2D genShape() {
        Path2D shape = new Path2D.Double();
        shape.append(new Rectangle(0,0,sp.getWidth(), sp.getHeight()), true);
        return shape;
    }

}
