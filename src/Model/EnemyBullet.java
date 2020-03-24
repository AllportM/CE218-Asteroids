package Model;

import View.ImgManag;

import java.awt.*;
import java.awt.geom.Path2D;

public class EnemyBullet extends Bullet {

    // bullets

    public EnemyBullet(Ship ship) {
        super(ship.position.copy(), ship.velocity.copy(),
                2.5, ship);
        sp = new Sprite(position, direction, width, height, ImgManag.getImage("LButtet2in.png"));
    }

    @Override
    public Path2D genShape() {
        Path2D shape = new Path2D.Double();
        shape.append(new Rectangle(0,0,sp.getWidth(), sp.getHeight()), true);
        return shape;
    }

}
