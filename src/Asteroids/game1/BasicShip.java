package Asteroids.game1;

import Asteroids.utilities.*;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;

import static Asteroids.game1.Constants.*;

public class BasicShip implements Refresh {
    public static final int RADIUS = 8;

    public static final double MAX_SPEED = 5;
    public static final double STEER_RATE = 2 * Math.PI; // rotational velocity in radians per second
    public static final double MAG_ACC = 20; // accelleration when thrust is applied
    public static final double DRAG = 0.1; // constant speed loss factor
    public static final Color COLOR = Color.cyan;
    private RotatableImage img;
    BufferedImage img2;

    public Vector2D position; // on frame
    public Vector2D velocity; // per second
    // direction in which the nos of ship is pointing
    // direction thrust is applied
    // unit vefor representing angle by which ship rotated
    public Vector2D direction;

    private RotatableShape shapeBase;
    private RotatableShape oval;
    private Vector2D shipRot;

    // controller for action
    private BasicController ctrl;

    public BasicShip(BasicController ctrl)
    {
        this.ctrl = ctrl;
        position = new Vector2D(FRAME_WIDTH / 2, FRAME_HEIGHT / 2);
        velocity = new Vector2D(0, 0);
        direction = new Vector2D(0, -20);
        shipRot = new Vector2D(direction);
        makeShape();
        shapeBase.setScale(1.5);
        oval.setScale(1.5);
        img = new RotatableImage("resources/ship2.gif");
    }

    /**
     * makeShape's purpose is to initialize ships shapes by storing specified drawing coordinates
     */
    private void makeShape()
    {
        // initializes and creates shape for base of ship
        shapeBase = new RotatableShape(10);
        shapeBase.pushCoords(-20, 20);
        shapeBase.pushCoords(-20, 15);
        shapeBase.pushCoords(-15, 5);
        shapeBase.pushCoords(-5, 0);
        shapeBase.pushCoords(5, 0);
        shapeBase.pushCoords(15, 5);
        shapeBase.pushCoords(20, 15);
        shapeBase.pushCoords(20, 20);
        shapeBase.pushCoords(0, 15);
        shapeBase.pushCoords(-20, 20);
        oval = new RotatableShape(30);
        for (int i = -7; i < 8; i++)
        {
            double y = Math.sqrt(49 - i * i)*3;
            oval.pushCoords(i, y);
        }
        for (int i = 7; i >-8; i--)
        {
            double y = -Math.sqrt(49 - i * i)*3;
            oval.pushCoords(i, y);
        }
        System.out.println(oval);
//        oval = new Ellipse2D.Double(-5, -20, 10, 40);
    }

    /**
     * update's purpose is to update the ships vectors, and as a result rotates shapes
     */
    public void update()
    {
        Action act = ctrl.action();
        // updates vectors
        direction.rotate(act.turn * STEER_RATE * DT);
        if (velocity.mag() < MAX_SPEED)
            velocity.add(Vector2D.polar(direction.angle(), (MAG_ACC * act.thrust * DT)));
        if (velocity.mag() >= DRAG)
            velocity.subtract(Vector2D.polar(velocity.angle(), DRAG));
        else velocity.set(0,0);
        position.add(velocity.x, velocity.y);
        position.wrap(FRAME_WIDTH, FRAME_HEIGHT);
        double angle = shipRot.angle(direction); // calcs if any rotation has been made
        // rotates ship upon change in angle from direction and ship Rot
        if (angle != 0)
        {
            shapeBase.rotateShape(angle);
            oval.rotateShape(angle);
            // updates shipRot
            shipRot.set(direction);
            img.setRotate(angle);
        }
    }

    /**
     * draw's purpose is to paint the ships shapes onto a give Graphics object
     * @param g
     *      Graphics2D object to be painted/drawn upon
     * @param c
     *      Component, JComponenyt to be passed as reference to image icon
     */
    public void draw(Graphics2D g, Component c)
    {
        // creates a mirror graphics object, so changes doesn't carry on to other paints, and init shapes
        Graphics2D g1 = (Graphics2D) g.create();
        Shape clip = g1.getClip();
        Path2D base = shapeBase.getPath(position.x, position.y);
        Path2D ov = oval.getPath(position.x, position.y);
        g1.setColor(new Color(0,0,0));

        // draws shapes and paints icons
        // adds outline
        BasicStroke stroke = new BasicStroke(4f);
        g1.fill(new Area(stroke.createStrokedShape(base)));
        g1.fill(new Area(stroke.createStrokedShape(ov)));
        g1.draw(base);
        g1.draw(ov);
        g1.setClip(base);
        img.setScale(0.8, 0.8);
        img.paintIcon(c, g1, (int) Math.round(position.x), (int) Math.round(position.y));
        g1.setClip(clip);
        g1.setClip(ov);
        img.setScale(0.2, 0.8);
        img.paintIcon(c, g1, (int) Math.round(position.x), (int) Math.round(position.y));
        g1.dispose();
    }
}
