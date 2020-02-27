package Model;

import java.awt.*;

/**
 * ParallaxingObjects purpose is to provide default data members and behaviours for a parallexing object. That
 * bring the index values, the speed modifier, the comparable methods and the abstract draw methods to which
 * sub classes will be required to implement
 */
abstract class ParallaxingObject implements Comparable<ParallaxingObject>{
    private static final double z1 = 1/2, z2 = 1/3, z3 = 1/4;
    private double speedMult;
    private int zIndex;

    public ParallaxingObject(int index)
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
