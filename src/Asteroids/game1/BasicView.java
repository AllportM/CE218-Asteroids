package Asteroids.game1;

import Asteroids.utilities.Refresh;
import Asteroids.utilities.RotatableImage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * BasicView's purpose is to paint the main background view for the game
 */
public class BasicView extends JComponent {

    private final BufferedImage bg;
    private final RotatableImage bg2;
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
//        String path = "resources/BackgroundEdited.gif";
        String path = "resources/Background.gif";
        bg2 = new RotatableImage(path);
        try {
            bg = ImageIO.read(new File(path));
        }
        catch (IOException e)
        {
            throw new RuntimeException(String.format("Failed to load Image '%s'", path));
        }
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
//        g.drawImage(bg, 0, 0, null);
        bg2.setScale(0, 0);
        /*
         * to override rotatable images translate, set scale to 1/3 ad x/y args to width/6, height /64
         * so that 0,0 point is top right of image
         */
        bg2.paintIcon(this, g, bg2.getIconWidth()/6, bg2.getIconHeight()/6);
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
