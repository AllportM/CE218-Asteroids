package Model;

import View.ImgManag;

import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;

/**
 * EnemyBullets purpose is to implement Bullets abstract methods
 */
public class EnemyBullet extends Bullet {

    /**
     * Standard constructor, instantiates member variables unique to this bullet i.e sprite image
     * @param ship
     */
    public EnemyBullet(Ship ship) {
        super(ship.position.copy(), ship.velocity.copy(),
                2.5, ship);
        BufferedImage bul = ImgManag.getImage("LButtet2in.png");
        width = bul.getWidth();
        height = bul.getHeight();
        sp = new Sprite(position, direction, width, height, bul, genShape());
        genShape();
    }

    /**
     * Generates the shape for this bullet
     * @return
     */
    @Override
    public Path2D genShape() {
        Path2D shape = new Path2D.Double();
        shape.append(new Rectangle(-width/2,-height/2,width, height), true);
        return shape;
    }

    @Override
    public void hit(GameObject other) {
        alive = false;
    }

}
