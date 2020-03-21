package Model;

import Controller.Game;

import java.awt.*;

import static Model.Constants.*;

/**
 * ParallaxingObjects purpose is to provide default data members and behaviours for a parallexing object. That
 * bring the index values, the speed modifier, the comparable methods and the abstract draw methods to which
 * sub classes will be required to implement
 */
public abstract class ParallaxingObject implements Comparable<ParallaxingObject>, Drawable{
    private static final double z1 = 2560, z2 = z1 + 860, z3 = z2 + 860, z4 = z3 + 860;
    public double speedMult;
    public int zIndex, x, y;


    ParallaxingObject(int index, int x, int y)
    {
        zIndex = index;
        switch (index)
        {
            case 1:
                speedMult = (z1 - FRAME_WIDTH)  / (2 * (WORLD_WIDTH - FRAME_WIDTH));
                break;
            case 2:
                speedMult = (z2 - FRAME_WIDTH)  / (2 * (WORLD_WIDTH - FRAME_WIDTH));
                break;
            case 3:
                speedMult = (z3 - FRAME_WIDTH)  / (2 * (WORLD_WIDTH - FRAME_WIDTH));
                break;
            default:
                speedMult = (z4 - FRAME_WIDTH)  / (2 * (WORLD_WIDTH - FRAME_WIDTH));
        }
        this.x = x;
        this.y = y;
    }

    int[] getCoord()
    {
        int[] coords = {(int) (x + (Game.vp.getX() * speedMult)), (int) (y + (Game.vp.getY() * speedMult))};
        return coords;
    }

    /**
     * draw's purpose will differ between parallaxing objects i.e there will be either an image or an animation
     * drawable. In both instances the purpose will be to draw the objects image/animation upon the graphics object
     * @param g
     */
    @Override
    public abstract void draw(Graphics2D g);

    @Override
    public int compareTo(ParallaxingObject o)
    {
        return (speedMult - o.speedMult < 0)? -1: 1;
    }
}
