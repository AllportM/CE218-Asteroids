package Asteroids.game1;

import Asteroids.utilities.Action;
import Asteroids.utilities.Refresh;
import Asteroids.utilities.Vector2D;
import javafx.scene.shape.Ellipse;
import sun.print.ProxyGraphics2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;

import static Asteroids.game1.Constants.*;

public class BasicShip implements Refresh {
    public static final int RADIUS = 8;

    public static final double MAX_SPEED = 10;
    public static final double STEER_RATE = 2 * Math.PI; // rotational velocity in radians per second
    public static final double MAG_ACC = 20; // accelleration when thrust is applied
    public static final double DRAG = 0.1; // constant speed loss factor
    public static final Color COLOR = Color.cyan;

    public Vector2D position; // on frame
    public Vector2D velocity; // per second
    // direction in which the nos of ship is pointing
    // direction thrust is applied
    // unit vefor representing angle by which ship rotated
    public Vector2D direction;

    private Vector2D[] shapeBase;
    private Shape oval;
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
    }

    private void makeShape()
    {
        shapeBase = new Vector2D[10];
        shapeBase[0] = new Vector2D(-20, 20);
        shapeBase[1] = new Vector2D(-20, 15);
        shapeBase[2] = new Vector2D(-15, 5);
        shapeBase[3] = new Vector2D(-5, 0);
        shapeBase[4] = new Vector2D(5, 0);
        shapeBase[5] = new Vector2D(15, 5);
        shapeBase[6] = new Vector2D(20, 15);
        shapeBase[7] = new Vector2D(20, 20);
        shapeBase[8] = new Vector2D(0, 15);
        shapeBase[9] = new Vector2D(-20, 20);
        oval = new Ellipse2D.Double(-5, -20, 10, 40);
    }

    public void update()
    {
        Action act = ctrl.action();
        direction.rotate(act.turn * STEER_RATE * DT);
        if (velocity.mag() < MAX_SPEED)
            velocity.add(Vector2D.polar(direction.angle(), (MAG_ACC * act.thrust * DT)));
        if (velocity.mag() > 0)
            velocity.subtract(Vector2D.polar(velocity.angle(), DRAG));
        position.add(velocity.x, velocity.y);
        position.wrap(FRAME_WIDTH, FRAME_HEIGHT);
        double angle = shipRot.angle(direction); // calcs if any rotation has been made
        // rotates ship upon change in angle from direction and ship Rot
        if (angle != 0)
        {
            // rotates base
            for (Vector2D vec: shapeBase)
            {
                vec.rotate(angle);
            }
            // rotates oval
            oval = AffineTransform.getRotateInstance(angle).createTransformedShape(oval);
            // updates shipRot
            shipRot.set(direction);
        }
    }

    public void draw(Graphics2D g)
    {
        Path2D path = new Path2D.Double();
        path.moveTo(shapeBase[0].x + position.x, shapeBase[0].y +position.y);
        for (int i = 1; i < shapeBase.length; i++)
        {
            path.lineTo(shapeBase[i].x + position.x, shapeBase[i].y + position.y);
        }
        path.closePath();
        g.setColor(Color.gray);
        g.fill(path);
        g.setColor(COLOR);
        g.draw(path);
        // draws oval
        Graphics2D g2 = (Graphics2D) g.create();
        g2.translate(position.x, position.y);
        g2.setColor(Color.gray);
        g2.fill(oval);
        g2.setColor(COLOR);
        g2.draw(oval);
//        g.fillOval((int) position.x - 10, (int) position.y - 10, 20, 20);
//        g.drawLine((int) position.x, (int) position.y, (int) direction.x + (int) position.x,
//                (int) direction.y + (int) position.y);
    }
}
