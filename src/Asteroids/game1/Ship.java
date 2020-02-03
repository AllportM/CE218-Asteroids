package Asteroids.game1;

import Asteroids.utilities.*;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Path2D;

import static Asteroids.game1.Constants.*;

public class Ship extends GameObject {

    public static final double MAX_SPEED = 300;
    public static final double STEER_RATE = 2 * Math.PI; // rotational velocity in radians per second
    public static final double MAG_ACC = 600; // accelleration when thrust is applied
    public static final double DRAG = 5; // constant speed loss factor
    public static final Color COLOR = Color.cyan;
    private double fireRate = 2;
    private double bulletTime = 0;
    private final double scale = 1.5;
    private double thrust;

    // direction in which the nos of ship is pointing
    // direction thrust is applied
    // unit vefor representing angle by which ship rotated
    public Vector2D direction;
    public Vector2D shipRot;
    private RotatableImage ship; // texture for ship
    private RotatableImage thrustImg;

    // controller for action
    private Controller ctrl;

    public boolean thrusting;
    public Bullet bullet;

    public Ship(Controller ctrl)
    {
        super(new Vector2D(FRAME_WIDTH / 2.0, FRAME_HEIGHT / 2.0),
                new Vector2D(0, 0), 20*1.5);
        this.ctrl = ctrl;
        position = new Vector2D(FRAME_WIDTH / 2.0, FRAME_HEIGHT / 2.0);
        velocity = new Vector2D(0, 0);
        direction = new Vector2D(0, -20);
        shipRot = new Vector2D(direction);
        ship = new RotatableImage("resources/Ship.png");
        thrustImg = new RotatableImage("resources/ShipThrust.png");
        bulletTime = System.currentTimeMillis();
    }

    public void hit()
    {

    }

    public void mkBullet()
    {
        bullet = new Bullet(this);
    }

    /**
     * update's purpose is to update the ships vectors, and as a result rotates shapes
     */
    public void update()
    {
        super.update();
        Action act = ctrl.action();

        // updates ship's velocity values as a product of user action, acceleration, and change in time
        // or deducts velocities magnitude by a scale of drag
        if (velocity.mag() < MAX_SPEED)
            velocity.add(Vector2D.polar(direction.angle(), (MAG_ACC * act.thrust * DT)));
        if (velocity.mag() >= DRAG)
            velocity.subtract(Vector2D.polar(velocity.angle(), DRAG));
        else velocity.set(0,0);

        // calcs if any rotation has been made
        // rotates ship upon change in angle from direction and ship Rot by calculating the
        // difference between updated direction and old shipRot value
        direction.rotate(act.turn * STEER_RATE * DT);
        double angle = shipRot.angle(direction);
        if (angle != 0)
        {
            shipRot.set(direction);
            ship.setRotate(angle);
            thrustImg.setRotate(angle);
        }

        // updates thrust values so that opacity of thrust can be adjusted
        // checks thrust is applied in a forward direction, if so update values, if not decrease values
        if (act.thrust > 0 && thrust + DT <= 1)
        {
            thrust += DT;
        }
        else if (thrust - DT > 0)
        {
            thrust -= DT;
        }

        // updates bulletTime and if fireRate's time has passed, 1/rate seconds, then ship can
        // fire another bullet
        long now = System.currentTimeMillis();
        if (act.shoot && now - bulletTime >= 1000/fireRate)
        {
            bulletTime = now;
            mkBullet();
        }
    }

    /**
     * draw's purpose is to paint the ships shapes onto a give Graphics object
     * @param g
     *      Graphics2D object to be painted/drawn upon
     */
    public void draw(Graphics2D g)
    {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) thrust));
        thrustImg.paintIcon(g2, (int) Math.round(position.x), (int) Math.round(position.y));
        ship.paintIcon(g, (int) Math.round(position.x), (int) Math.round(position.y));
    }
}
