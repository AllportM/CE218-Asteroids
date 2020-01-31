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
        String path = "resources/background.jpg";
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
        bg2.setScale(0.7, 0.7);
        bg2.paintIcon(this, g, 0, 0);
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
