package Asteroids.game1;

import java.awt.*;

/**
 * Constant's purpose is to contain constant variables throughout the Asteroids packages
 */
public enum Constants
{
    F;
    public static final int FRAME_HEIGHT = 480;
    public static final int FRAME_WIDTH = 640;
    public static final Dimension FRAME_SIZE = new Dimension(FRAME_WIDTH, FRAME_HEIGHT);
    // sleep time between two frames
    public static final int DELAY = 20; // milliseconds
    public static final double DT = DELAY / 1000.0; // seconds
}