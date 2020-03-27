package Model;

import static Model.Constants.*;

/**
 * Viewports purpose is to provide a new 0,0 offset relative to the players position
 */
public class ViewPort {
    private double x, y; // the 0,0 position relative to players position
    PlayerShip ps; // the playership to attain position vectore, could have used position vector but hey ho

    public ViewPort( double x, double y, PlayerShip ps)
    {
        this.x = x;
        this.y = y;
        this.ps = ps;
    }

    /**
     * sets the new 0,0 position
     */
    public void update()
    {
        double x =  ps.position.x;
        double y =  ps.position.y;
        this.x = (x > WORLD_WIDTH - FRAME_WIDTH/2f)? -WORLD_WIDTH + FRAME_WIDTH:
                (x < FRAME_WIDTH/2f)? 0: -x + FRAME_WIDTH / 2f;
        this.y = (y > WORLD_HEIGHT - FRAME_HEIGHT/2)? -WORLD_HEIGHT + FRAME_HEIGHT:
                (y < FRAME_HEIGHT/2f)? 0: -y  + FRAME_HEIGHT / 2f;
    }

    public void setShip(PlayerShip ps)
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
