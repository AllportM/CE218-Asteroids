package Model;

import Controller.Controller;
import View.ImgManag;

import java.awt.*;
import java.awt.geom.Path2D;
import static Model.Constants.*;

public class PlayerShip extends Ship {
    private double thrust;

    public PlayerShip(Controller ctrl)
    {
        super(new Vector2D(WORLD_WIDTH / 2.0, WORLD_HEIGHT / 2.0),
                new Vector2D(0, 0), 34, ctrl);
        MAX_SPEED = 300 * Player.shipSpeed;
        STEER_RATE = 2 * Math.PI * Player.turnResp; // rotational velocity in radians per second
        MAG_ACC = 600 * Player.shipAcc; // accelleration when thrust is applied
        mainShip = new Sprite(position, direction, RADIUS * 2, RADIUS * 2, ImgManag.getImage("PlayerShip.png")
        , genShape());
        thrustSp = new Sprite(position, direction, RADIUS * 2+ 20, RADIUS * 2 + 120, ImgManag.getImage("ShipThrust.png")
        , genShape());
        thrust = 0;
        fireRate = 2 * Player.fireRate;
    }

    public boolean canHit(GameObject other)
    {
        return other instanceof Asteroid || other instanceof Bullet || other instanceof Ship;
    }

    @Override
    public void update()
    {
        super.update();

        // updates thrust values so that opacity of thrust can be adjusted
        // checks thrust is applied in a forward direction, if so update values, if not decrease values
        if (ctrl.action().thrust > 0 && thrust + DT <= 1)
        {
            thrust += DT;
        }
        else if (thrust - DT > 0)
        {
            thrust -= DT;
        }
    }

    @Override
    public Path2D genShape()
    {
        Path2D shape = new Path2D.Double();
        shape.moveTo(4 - RADIUS, 64 - RADIUS);
        shape.lineTo(4- RADIUS, 54- RADIUS);
        shape.lineTo(11 - RADIUS, 40 - RADIUS);
        shape.lineTo(21 - RADIUS, 35 - RADIUS);
        shape.lineTo(24 - RADIUS, 35 - RADIUS);
        shape.lineTo(24 - RADIUS, 23 - RADIUS);
        shape.lineTo(26 - RADIUS, 11 - RADIUS);
        shape.lineTo(32 - RADIUS, 2 - RADIUS);
        shape.lineTo(36 - RADIUS, 2 - RADIUS);
        shape.lineTo(41 - RADIUS, 11 - RADIUS);
        shape.lineTo(44 - RADIUS, 21 - RADIUS);
        shape.lineTo(44 - RADIUS, 35 - RADIUS);
        shape.lineTo(55 - RADIUS, 40 - RADIUS);
        shape.lineTo(64 - RADIUS, 54 - RADIUS);
        shape.lineTo(64 - RADIUS, 64 - RADIUS);
        shape.lineTo(41 - RADIUS, 58 - RADIUS);
        shape.lineTo(37 - RADIUS, 64 - RADIUS);
        shape.lineTo(31 - RADIUS, 64 - RADIUS);
        shape.lineTo(27 - RADIUS, 58 - RADIUS);
        shape.lineTo(4 - RADIUS, 64 - RADIUS);
        return shape;
    }

    @Override
    public void draw(Graphics2D g)
    {
        super.draw(g);
        Composite init = g.getComposite();
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) thrust));
        thrustSp.paint(g);
        g.setComposite(init);
    }

//    public void applyInv(int duration)
//    {
//        invTime = System.currentTimeMillis();
//        inv = duration;
//    }

}
