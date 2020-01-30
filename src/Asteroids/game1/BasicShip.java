package Asteroids.game1;

import Asteroids.utilities.*;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;

import static Asteroids.game1.Constants.*;

public class BasicShip implements Refresh {
    public static final int RADIUS = 8;

    public static final double MAX_SPEED = 10;
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
        img = new RotatableImage("resources/ship1.gif");
    }

    private void makeShape()
    {
        shapeBase = new RotatableShape(10);
        shapeBase.addCoords(-20, 20);
        shapeBase.addCoords(-20, 15);
        shapeBase.addCoords(-15, 5);
        shapeBase.addCoords(-5, 0);
        shapeBase.addCoords(5, 0);
        shapeBase.addCoords(15, 5);
        shapeBase.addCoords(20, 15);
        shapeBase.addCoords(20, 20);
        shapeBase.addCoords(0, 15);
        shapeBase.addCoords(-20, 20);
        oval = new RotatableShape(30);
        for (int i = -7; i < 8; i++)
        {
            double y = Math.sqrt(49 - i * i)*3;
            oval.addCoords(i, y);
        }
        for (int i = 7; i >-8; i--)
        {
            double y = -Math.sqrt(49 - i * i)*3;
            oval.addCoords(i, y);
        }
        System.out.println(oval);
//        oval = new Ellipse2D.Double(-5, -20, 10, 40);
    }

    public void update()
    {
        Action act = ctrl.action();
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
        }
        img.setRotate(angle);
    }

    public void draw(Graphics2D g, Component c)
    {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.translate(position.x, position.y);
        Shape clip = g.getClip();
        int x = - (img.getIconWidth()/2);
        int y = - (img.getIconHeight()/2);
        Path2D base = shapeBase.getPolygon();
//        g.setColor(new Color(23, 166, 116));
//        BasicStroke stroke = new BasicStroke(2f);
//        g.fill(new Area(stroke.createStrokedShape(base)));
        g.fill(base);
        g.setClip(base);
        img.paintIcon(c, g, x, y);
        g.setClip(clip);
        // draws oval
        Path2D ov = oval.getPolygon();
//        g.draw(new Area(stroke.createStrokedShape(ov)));
        g.setClip(ov);
        img.setScale(0.2, 0.9);
        img.paintIcon(c, g, x, y);

        g.setClip(clip);
//        g.fillOval((int) position.x - 10, (int) position.y - 10, 20, 20);
//        g.drawLine((int) position.x, (int) position.y, (int) direction.x + (int) position.x,
//                (int) direction.y + (int) position.y);
    }
}
