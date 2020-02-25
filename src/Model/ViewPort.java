package Model;

import Model.GameObject;
import Model.Ship;

import static Model.Constants.*;

public class ViewPort {
    private double x, y;
    Ship ps;

    public ViewPort( double x, double y, Ship ps)
    {
        this.x = x;
        this.y = y;
        this.ps = ps;
    }

    public void update()
    {
        double x =  ps.position.x;
        double y =  ps.position.y;
        this.x = (x > WORLD_WIDTH - FRAME_WIDTH/2)? -WORLD_WIDTH + FRAME_WIDTH:
                (x < FRAME_WIDTH/2)? 0: -x + FRAME_WIDTH / 2;
        this.y = (y > WORLD_HEIGHT - FRAME_HEIGHT/2)? -WORLD_HEIGHT + FRAME_HEIGHT:
                (y < FRAME_HEIGHT/2)? 0: -y  + FRAME_HEIGHT / 2;
    }

    public void setShip(Ship ps)
    {
        this.ps = ps;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
