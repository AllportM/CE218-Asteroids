package Model;

import Controller.Action;
import Controller.Controller;
import Controller.Game;

import java.awt.*;

import static Model.Constants.DT;

/**
 * Ships purpose is to provide most of the basic functionality of a shit, and declare abstract methods
 * for player and enemy ships to directly implement
 */
public abstract class Ship extends GameObject {
    private double DRAG = 5; // constant speed loss factor
    private double thrust;
    private double bulletTime; // time bullet spawned used to know when to fire next
    public Bullet bullet; // bullet to fire used by Game to insert into gameobjs
    Sprite thrustSp; // the main sprite for a ship, main image to paint
    // controller for action
    private Controller ctrl;
    // constants used as a base for ships parameters
    public static final double MAX_SPEED = 300, STEER_RATE = 2, MAG_ACC = 600, FIRE_RATE = 1.5;
    public double mySpeed, mySteer, myAcc, myFire;

    /**
     * Standard constructor for a ship
     * @param position
     *      position vector
     * @param velocity
     *      velocity vector
     * @param RADIUS
     *      radius for a ship
     * @param ctrl
     *      controller for ta ship
     */
    public Ship(Vector2D position, Vector2D velocity, double RADIUS, Controller ctrl) {
        super(position, velocity, RADIUS);
        direction = new Vector2D(0, -20); // point forwards or up
        direction.normalise();
        this.ctrl = ctrl;
        bulletTime = System.currentTimeMillis();
        thrust = 0;
    }

    /**
     * abstract method given both ships have different bullets
     */
    public abstract void mkBullet();

    /**
     * update's purpose is to update the ships vectors, and as a result rotates shapes
     */
    @Override
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

        // updates bulletTime and if FIRE_RATE's time has passed, 1/rate seconds, then ship can
        // fire another bullet
        long now = System.currentTimeMillis();
        if (act.shoot && now - bulletTime >= 1000f/ myFire)
        {
            bulletTime = now;
            mkBullet();
        }

        // takes action for thrusting
        if (ctrl.action().thrust > 0 && thrust + DT <= 1)
        {
            thrust += DT;
        }
        else if (thrust - DT > 0)
        {
            thrust -= DT;
        }
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
