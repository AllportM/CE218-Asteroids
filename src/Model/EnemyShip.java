package Model;

import View.ImgManag;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import Controller.Controller;
import Controller.HlAiController;

public class EnemyShip extends Ship {

    public EnemyShip(Controller ctrl, Vector2D pos)
    {
        super(pos, new Vector2D(0, 0), 25, ctrl);
        MAX_SPEED = 300;
        STEER_RATE = 2; // rotational velocity in radians per second
        MAG_ACC = 600; // accelleration when thrust is applied
        mainShip = new Sprite(position, direction, RADIUS * 2, RADIUS * 2, ImgManag.getImage("EnemyrShip.png")
                , genShape());
        thrustSp = new Sprite(position, direction, RADIUS * 2, RADIUS * 2 + 30, ImgManag.getImage("EnemyShipThrust.png")
                , genShape());
        ((HlAiController) ctrl).setOwner(this);
        fireRate = 1;
    }

    public boolean canHit(GameObject other)
    {
        return  other instanceof Bullet || other instanceof PlayerShip;
    }

    @Override
    public void hit(GameObject other) {

    }

    @Override
    public Path2D genShape()
    {
        Path2D shape = new Path2D.Double();
        shape.append(new Ellipse2D.Double(0,0,RADIUS * 2, RADIUS * 2), true);
        return shape;
    }

}