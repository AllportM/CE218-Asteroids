package Asteroids.game1;

import Asteroids.utilities.Action;
import Asteroids.utilities.Vector2D;

import java.awt.*;

import static Asteroids.game1.Constants.*;

public class BasicShip {
    public static final int RADIUS = 8;

    // rotational velocity in radians per second
    public static final double STEER_RATE = 2 * Math.PI;

    // accelleration when thrust is applied
    public static final double MAG_ACC = 200;

    // constant speed loss factor
    public static final double DRAG = 0.1;

    public static final Color COLOR = Color.cyan;

    public Vector2D position; // on frame
    public Vector2D velocity; // per second
    // direction in which the nos of ship is pointing
    // direction thrust is applied
    // unit vefor representing angle by which ship rotated
    public Vector2D direction;

    // controller for action
    private BasicController ctrl;

    public BasicShip(BasicController ctrl)
    {
        this.ctrl = ctrl;
        position = new Vector2D(FRAME_WIDTH / 2, FRAME_HEIGHT / 2);
        velocity = new Vector2D(0, 0);
        direction = new Vector2D(0, 1);
    }

    public void update()
    {
        Action act = ctrl.action();
    }
}
