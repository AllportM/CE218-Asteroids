package Asteroids.game1;

import Asteroids.utilities.Refresh;
import Asteroids.utilities.RotatableShape;
import Asteroids.utilities.Vector2D;

import java.awt.*;

import static Asteroids.game1.Constants.*;

public abstract class GameObject implements Refresh {

    public Vector2D position;
    public Vector2D velocity;
    protected RotatableShape[] shapes;
    protected double RADIUS;
    public GameObject(Vector2D position, Vector2D velocity, double RADIUS)
    {
        this.position = position;
        this.velocity = velocity;
        this.RADIUS = RADIUS;
    }

    public abstract void hit();
    public void update()
    {
        position.addScaled(velocity, DT);
        position.wrap(FRAME_WIDTH, FRAME_HEIGHT);
    }
    public abstract void draw(Graphics2D g, Component c);
}
