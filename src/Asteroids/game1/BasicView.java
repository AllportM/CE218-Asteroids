package Asteroids.game1;

import Asteroids.utilities.ImgManag;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

import static Asteroids.game1.Constants.FRAME_HEIGHT;
import static Asteroids.game1.Constants.FRAME_WIDTH;

/**
 * BasicView's purpose is to paint the main background view for the game
 */
public class BasicView extends JComponent {

    private final BufferedImage bg;
    private Game game; // reference to the game instance

    /**
     * Standard instance constructore, initializes game reference
     * @param game
     *      instance of the game object
     */
    public BasicView(Game game)
    {
        bg = ImgManag.getImage("Background.png");
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
        AffineTransform initG = g.getTransform();
        Shape initClip = g.getClip();
        Color initCol = g.getColor();
        g.drawImage(bg, (int) game.vp.getX(), (int) game.vp.getY(), null);

        // used to translate screen to top left of players position (
        AffineTransform viewPort = new AffineTransform();
        viewPort.translate((float) game.vp.getX(), (float) game.vp.getY());

        /* following relates to minimap. This is achieved by creating a new image, attaining its
         graphics object, and drawing objects directly onto that, and then after all is drawn
         the 'image' is drawn onto the actual game graphics.
         */
        // Creates affine transform for minimap and inits minimap variables
        float minimapW = 300;
        float minimapH = 300;
        float radarRange = 8;
        AffineTransform miniAt = new AffineTransform();
        miniAt.translate(minimapW/2, minimapH/2);
        miniAt.scale(1/radarRange, 1/radarRange);
        miniAt.translate(game.vp.getX() - FRAME_WIDTH/2, game.vp.getY()
                - FRAME_HEIGHT/2);
        // paint onto minimap here (doodey circles)

        // creates a new image and uses its graphics to draw objects onto this image

        synchronized (Game.class) {
            for (GameObject obj : game.gameObjects) {
                g.setTransform(viewPort);
                obj.draw(g);
                g.setColor(Color.RED);
            }
            g.setTransform(initG);
            g.setColor(Color.BLACK);
            g.fill(new Ellipse2D.Double(0, 0, minimapW, minimapH));
            g.setClip(new Ellipse2D.Double(0, 0, minimapW, minimapH));
            g.setTransform(miniAt);
            for (GameObject obj : game.gameObjects) {
                obj.draw(g);
            }

            g.setClip(initClip);
            g.setTransform(initG);
        }


//        AffineTransform minimapToView = new AffineTransform();
//        minimapToView.translate(FRAME_WIDTH/2 , FRAME_HEIGHT / 2);
//        g.setTransform(minimapToView);
//        g.drawImage(miniMap, 0, 0, null);
//        g.setClip(initClip);
//        g.setTransform(initG);

        // draws scores and lifes
        String score, lifes;
        g.setColor(Color.cyan);
        g.setFont(new Font("Bahnschrift Light", Font.BOLD, 20));
        score = "Score: " + game.playerScore;
        lifes = "Lifes: " + game.lifes;
        int fontW = g.getFontMetrics().stringWidth(score);
        g.drawString(score, FRAME_WIDTH - fontW - 50, 20);
        g.drawString(lifes, 0, 20);
        g.drawString("SUPSUP", 0 , 0);
        g.setColor(initCol);

    }

    @Override
    public Dimension getPreferredSize()
    {
        return Constants.FRAME_SIZE;
    }
}
