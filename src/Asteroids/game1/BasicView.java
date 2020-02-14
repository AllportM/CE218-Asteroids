package Asteroids.game1;

import Asteroids.utilities.RotatableImage;
import com.sun.corba.se.impl.orbutil.closure.Constant;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static Asteroids.game1.Constants.FRAME_HEIGHT;
import static Asteroids.game1.Constants.FRAME_WIDTH;

/**
 * BasicView's purpose is to paint the main background view for the game
 */
public class BasicView extends JComponent {

    private final BufferedImage bg;
    private final RotatableImage bg2;
    public static final Color BG_COLOR = Color.black; // colour for the background

    private Game game; // reference to the game instance

    /**
     * Standard instance constructore, initializes game reference
     * @param game
     *      instance of the game object
     */
    public BasicView(Game game)
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
//        bg2.setScale(0.333333, 0.333333);
        /*
         * to override rotatable images translate, set scale to 1/3 ad x/y args to width/6, height /64
         * so that 0,0 point is top right of image
         */

//        BufferedImage miniMap = new BufferedImage(FRAME_WIDTH, FRAME_WIDTH, bg.getType());
//        Graphics2D miniG = (Graphics2D) miniMap.getGraphics();
//        AffineTransform miniAt = new AffineTransform();
//        miniAt.translate(game.vp.getX(), game.vp.getY());
//        miniG.setTransform(miniAt);
        g.translate(game.vp.getX(), game.vp.getY());
        g.drawImage(bg, 0,0, null);
        synchronized (Game.class)
        {
            for (GameObject obj : game.gameObjects)
            {
                    obj.draw(g);
//                    miniG.setColor(Color.RED);
//                    miniG.draw(new Rectangle((int) obj.position.x - 5, (int) obj.position.y - 5, 10, 10));
            }
        }
        g.translate(-game.vp.getX(), -game.vp.getY());
//        Shape initClip = g.getClip();
//        g.setClip(new Ellipse2D.Double(0,0,300,300));
//        g.scale((float)300/FRAME_WIDTH, (float) 300 / FRAME_HEIGHT);
//        g.drawImage(miniMap, 0, 0, null);
//        g.setClip(initClip);
//        g.setColor(Color.cyan);
        g.setFont(new Font("Bahnschrift Light", Font.BOLD, 20));
        String score = "Score: " + game.playerScore;
        int fontW = g.getFontMetrics().stringWidth(score);
        g.drawString(score, FRAME_WIDTH - fontW - 50, 20);
        String lifes = "Lifes: " + game.lifes;
        g.drawString(lifes, 0, 20);
    }

    @Override
    public Dimension getPreferredSize()
    {
        return Constants.FRAME_SIZE;
    }
}
