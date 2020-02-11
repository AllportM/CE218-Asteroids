package Asteroids.utilities;

import Asteroids.game1.GameObject;

import static Asteroids.game1.Constants.*;

public class ViewPort {
    private double x, y;

    public ViewPort( double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public void update(GameObject ship)
    {
        double x =  ship.position.x;
        double y =  ship.position.y;
        this.x = (x > WORLD_WIDTH - FRAME_WIDTH/2)? -WORLD_WIDTH + FRAME_WIDTH:
                (x < FRAME_WIDTH/2)? 0: -x + FRAME_WIDTH / 2;
        this.y = (y > WORLD_HEIGHT - FRAME_HEIGHT/2)? -WORLD_HEIGHT + FRAME_HEIGHT:
                (y < FRAME_HEIGHT/2)? 0: -y  + FRAME_HEIGHT / 2;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
