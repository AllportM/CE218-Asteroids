package Asteroids.game1;

import Asteroids.utilities.Refresh;

import javax.swing.*;
import java.awt.*;

/**
 * BasicView's purpose is to paint the main background view for the game
 */
public class BasicView extends JComponent {

    public static final Color BG_COLOR = Color.black; // colour for the background

    private BasicGame game; // reference to the game instance

    /**
     * Standard instance constructore, initializes game reference
     * @param game
     *      instance of the game object
     */
    public BasicView(BasicGame game)
    {
        this.game = game;
    }

    /**
     * paintComponent's purpose is to draw the background utilizing the Graphics object
     * @param g0
     *      Graphics component
     */
    @Override
    public void paintComponent(Graphics g0)
    {
        Graphics2D g = (Graphics2D) g0;
        // paint the background
        g.setColor(BG_COLOR);
        g.fillRect(0, 0, getWidth(), getHeight());
        for (Refresh obj: game.gameObjects)
        {
            obj.draw(g, this);
        }
    }

    @Override
    public Dimension getPreferredSize()
    {
        return Constants.FRAME_SIZE;
    }
}
