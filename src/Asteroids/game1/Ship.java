package Asteroids.game1;

import Asteroids.utilities.*;

import java.awt.*;
import java.awt.geom.AffineTransform;
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
    private double bulletTime;
    private double invTime = 0;
    private final double scale = 1.5;
    private double thrust;
    public int inv;

    // direction in which the nos of ship is pointing
    // direction thrust is applied
    // unit vefor representing angle by which ship rotated
    public Vector2D direction;
    public Vector2D shipRot;
    private Sprite mainShip;
    private Sprite thrustSp;
    private RotatableImage ship; // texture for ship
    private RotatableImage thrustImg;

    // controller for action
    private Controller ctrl;
    public Bullet bullet;

    public Ship(Controller ctrl)
    {
        super(new Vector2D(WORLD_WIDTH / 2.0, WORLD_HEIGHT / 2.0),
                new Vector2D(0, 0), 34);
        this.ctrl = ctrl;
        direction = new Vector2D(0, -20);
        shipRot = new Vector2D(direction);
        ship = new RotatableImage("resources/Ship.png");
        thrustImg = new RotatableImage("resources/ShipThrust.png");
        bulletTime = System.currentTimeMillis();
        applyInv(3);
        mainShip = new Sprite(position, direction, RADIUS * 2, RADIUS * 2, ImgManag.getImage("Ship.png")
        , genShape());
        thrustSp = new Sprite(position, direction, RADIUS * 2+ 20, RADIUS * 2 + 60, ImgManag.getImage("ShipThrust.png")
        , genShape());
    }

    public RotatableShape genShape()
    {
        RotatableShape shape = new RotatableShape(20);
        shape.pushCoords(4, 64);
        shape.pushCoords(4, 54);
        shape.pushCoords(11, 40);
        shape.pushCoords(21, 35);
        shape.pushCoords(24, 35);
        shape.pushCoords(24, 23);
        shape.pushCoords(26, 11);
        shape.pushCoords(32, 2);
        shape.pushCoords(36, 2);
        shape.pushCoords(41, 11);
        shape.pushCoords(44, 21);
        shape.pushCoords(44, 35);
        shape.pushCoords(55, 40);
        shape.pushCoords(64, 54);
        shape.pushCoords(64, 64);
        shape.pushCoords(41, 58);
        shape.pushCoords(37, 64);
        shape.pushCoords(31, 64);
        shape.pushCoords(27, 58);
        shape.pushCoords(4, 64);
        return shape;
    }

    public void applyInv(int duration)
    {
        invTime = System.currentTimeMillis();
        inv = duration;
    }

    public void hit()
    {
        if (inv <= 0)
        this.alive = false;
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

/** depreciated **/
//        double angle = shipRot.angle(direction);
//        if (angle != 0)
//        {
//            shipRot.set(direction);
//            ship.setRotate(angle);
//            thrustImg.setRotate(angle);
//        }

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

        if (inv > 0 && now - invTime >= 1000 / 0.5) {
            System.out.println(now - invTime);
            inv = 0;
        }
    }

    /**
     * draw's purpose is to paint the ships shapes onto a give Graphics object
     * @param g
     *      Graphics2D object to be painted/drawn upon
     */
    public void draw(Graphics2D g)
    {
        Composite init = g.getComposite();
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) thrust));
        thrustSp.paint(g);
        g.setComposite(init);
//        ship.paintIcon(g, (int) Math.round(position.x), (int) Math.round(position.y));
        mainShip.paint(g);
//        if (inv > 0) {
//            AffineTransform initat = g.getTransform();
//            AffineTransform newat = new AffineTransform();
//            newat.translate(-position.x, -position.y);
//            newat.rotate(direction.angle());
//            g.setTransform(newat);
//            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
//            g.setColor(Color.cyan);
//            g.fillOval((int) (0), (int) (0), (int) (RADIUS * 2), (int) (RADIUS * 2));
//            g.setComposite(init);
//            g.setTransform(initat);
//        }


    }
}
