package Model;

import Controller.Controller;
import View.ImgManag;

import java.awt.geom.Path2D;
import static Model.Constants.*;
import Controller.Game;
import View.SoundsManag;

public class PlayerShip extends Ship {


    public PlayerShip(Controller ctrl, Player p)
    {
        super(new Vector2D(WORLD_WIDTH / 2.0, WORLD_HEIGHT / 2.0),
                new Vector2D(0, 0), 34, ctrl);
//        MAX_SPEED = 300 * p.shipSpeed;
//        STEER_RATE = 2 * Math.PI * p.turnResp; // rotational velocity in radians per second
//        MAG_ACC = 600 * p.shipAcc; // accelleration when thrust is applied
        sp = new Sprite(position, direction, RADIUS * 2, RADIUS * 2, ImgManag.getImage("PlayerShip.png")
        , genShape());
        thrustSp = new Sprite(position, direction, RADIUS * 2+ 20, RADIUS * 2 + 120, ImgManag.getImage("ShipThrust.png")
        , genShape());
        myAcc = MAG_ACC;
        myFire = FIRE_RATE;
        mySteer = STEER_RATE + 1;
        mySpeed = MAX_SPEED;
    }

    private void setDeath()
    {
        alive = false;
    }

    public void updateStats()
    {
        mySpeed = MAX_SPEED * Player.shipSpeed;
        mySteer = STEER_RATE * Player.turnResp;
        myFire = FIRE_RATE * Player.fireRate;
        myAcc = MAG_ACC * Player.shipAcc;
    }

    @Override
    public boolean canHit(GameObject other)
    {
        return other instanceof Asteroid || other instanceof Bullet || other instanceof Ship || other instanceof ItemPickup;
    }

    @Override
    public void update()
    {
        super.update();
        Game.vp.update();
        if (bullet != null)
        {

        }
    }

    @Override
    public void hit(GameObject other) {
        if (!(other instanceof ItemPickup))
        {
            super.hit(other);
            if (other instanceof EnemyBullet)
            {
                Player.health -= 10;
            }
            else
                setDeath();
            if (Player.health <= 0)
            {
                setDeath();
            }
            if (Player.health > 0)
            {
                SoundsManag.thud();
            }
            else
                SoundsManag.shipExp();
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
    public void mkBullet() {
        bullet = new PlayerBullet(this);
        SoundsManag.playerFire();
    }
}
