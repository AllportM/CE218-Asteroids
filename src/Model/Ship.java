package Model;

import Controller.Action;
import Controller.Controller;
import Controller.Game;

import java.awt.*;

import static Model.Constants.DT;

public abstract class Ship extends GameObject {
    private double DRAG = 5; // constant speed loss factor
    private double thrust;
    private double bulletTime;
    public Bullet bullet;
    Sprite thrustSp;
    // controller for action
    private Controller ctrl;
    private Player p;
    public static final double MAX_SPEED = 300, STEER_RATE = 2, MAG_ACC = 600, FIRE_RATE = 1.5;
    public double mySpeed, mySteer, myAcc, myFire;
    // vector and sprites


    public Ship(Vector2D position, Vector2D velocity, double RADIUS, Controller ctrl) {
        super(position, velocity, RADIUS);
        direction = new Vector2D(0, -20);
        direction.normalise();
        this.ctrl = ctrl;
        bulletTime = System.currentTimeMillis();
        thrust = 0;
        p = new Player();
    }

    public abstract void mkBullet();

    /**
     * update's purpose is to update the ships vectors, and as a result rotates shapes
     */
    public void update()
    {
        super.update();
        Action act = ctrl.action();

        // updates ship's velocity values as a product of user action, acceleration, and change in time
        // or deducts velocities magnitude by a scale of drag
        if (velocity.mag() < mySpeed && act.thrust > 0)
            velocity.add(Vector2D.polar(direction.angle(), (myAcc * act.thrust * DT)));
        // moves at half speed if thrust negative i.e backwards
        else if (velocity.mag() < mySpeed / 2 && act.thrust < 0)
            velocity.add(Vector2D.polar(direction.angle(), (myAcc * act.thrust * DT)));
        if (velocity.mag() >= DRAG)
            velocity.subtract(Vector2D.polar(velocity.angle(), DRAG));
        else velocity.set(0,0);

        // calcs if any rotation has been made
        // rotates ship upon change in angle from direction and ship Rot by calculating the
        // difference between updated direction and old shipRot value
        direction.rotate(act.turn * mySteer * DT);

/** depreciated **/
//        double angle = shipRot.angle(direction);
//        if (angle != 0)
//        {
//            shipRot.set(direction);
//            ship.setRotate(angle);
//            thrustImg.setRotate(angle);
//        }

        // updates bulletTime and if FIRE_RATE's time has passed, 1/rate seconds, then ship can
        // fire another bullet
        long now = System.currentTimeMillis();
        if (act.shoot && now - bulletTime >= 1000f/ myFire)
        {
            bulletTime = now;
            mkBullet();
        }

        if (ctrl.action().thrust > 0 && thrust + DT <= 1)
        {
            thrust += DT;
        }
        else if (thrust - DT > 0)
        {
            thrust -= DT;
        }

//        if (inv > 0 && now - invTime >= 1000f / 0.5f) {
//            System.out.println(now - invTime);
//            inv = 0;
//        }
    }

    /**
     * draw's purpose is to paint the ships shapes onto a give Graphics object
     * @param g
     *      Graphics2D object to be painted/drawn upon
     */
    @Override
    public void draw(Graphics2D g)
    {
        sp.paint(g);
        Composite init = g.getComposite();
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                (float) (thrust * (Math.random() * 0.3 + 0.7))));
        thrustSp.paint(g);
        g.setComposite(init);
    }
}
