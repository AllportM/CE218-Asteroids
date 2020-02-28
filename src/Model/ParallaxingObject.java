package Model;

import Controller.Game;

import java.awt.*;

/**
 * ParallaxingObjects purpose is to provide default data members and behaviours for a parallexing object. That
 * bring the index values, the speed modifier, the comparable methods and the abstract draw methods to which
 * sub classes will be required to implement
 */
abstract class ParallaxingObject implements Comparable<ParallaxingObject>{
    private static final double z1 = 1f/2f, z2 = 1f/3f, z3 = 1f/4f;
    public double speedMult;
    private int zIndex, x, y;
    double posx, posy;


    public ParallaxingObject(int index, int x, int y)
    {
        zIndex = index;
        switch (index)
        {
            case 1:
                speedMult = z1;
                break;
            case 2:
                speedMult = z2;
                break;
            case 3:
                speedMult = z3;
                break;
            default:
                speedMult = 1;
        }
        this.x = x;
        this.y = y;
    }

    /**
     * draw's purpose will differ between parallaxing objects i.e there will be either an image or an animation
     * drawable. In both instances the purpose will be to draw the objects image/animation upon the graphics object
     * @param g
     */
    public abstract void draw(Graphics2D g);

    @Override
    public int compareTo(ParallaxingObject o)
    {
        return o.zIndex - this.zIndex;
    }
}
