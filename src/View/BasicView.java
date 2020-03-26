package View;

import Controller.Game;
import Model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

import static Model.Constants.*;

/**
 * BasicView's purpose is to paint the main background view for the game
 */
public class BasicView extends JComponent {

    private Game game; // reference to the game instance
    public static final Font FONT = new Font("Bahnschrift Light", Font.BOLD, 20);
    public static final Color FONT_COLOUR = Color.cyan;

    /**
     * Standard instance constructor, initializes game reference
     * @param game
     *      instance of the game object
     */
    public BasicView(Game game)
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
        // whole drawing process synchronized so that all updates have been processed before
        // any rendering commences (reduces any gittering from painting old positional values)
        if (!(Game.isEnd))
        synchronized (Game.class)
        {
            Graphics2D g = (Graphics2D) g0;
            AffineTransform initG = g.getTransform();
            Shape initClip = g.getClip();
            Color initCol = g.getColor();

            // used to translate screen to top left of players position (
            AffineTransform viewPort = new AffineTransform();
            viewPort.translate((float) game.vp.getX(), (float) game.vp.getY());

            /*
             * Next block up until game object loop deals with minimap programming
             */

            //inits variables for positional values
            double minimapW = 300;
            double minimapH = 300;
            double radarRange = game.player.radarRange;
            AffineTransform miniAt;
            // sets minimaps size in relation to visible space after scaling
            BufferedImage miniMap = new BufferedImage(FRAME_WIDTH,
                    FRAME_HEIGHT, BufferedImage.TYPE_INT_ARGB);
            Graphics2D miniG = (Graphics2D) miniMap.getGraphics();

            // draws edge of map indicators (lines) onto minimap before any rendering done
            miniG.setColor(Color.black);
            miniG.fillRect(0,0,FRAME_WIDTH, FRAME_HEIGHT);
            miniG.setColor(Color.RED);
            for (int i = 0; i < FRAME_WIDTH; i += 20)
            {
                miniG.drawLine(0, i, i, 0);
                miniG.drawLine(i, 0, 0, i);
            }

            // sets transform up to edge of visible screen from ship using viewport
            miniAt = new AffineTransform();
            miniG.setTransform(miniAt);
            miniAt.translate(minimapW / 2, minimapH / 2);
            miniAt.scale(1 / radarRange, 1 / radarRange);
            miniAt.translate(game.vp.getX() - FRAME_WIDTH / 2, game.vp.getY()
                    - FRAME_HEIGHT / 2);
            miniG.setTransform(miniAt);

            // fills visible screen background onto minimap
            miniG.setColor(new Color(31, 0, 97));
            miniG.fill(new Rectangle(0, 0, WORLD_WIDTH,
                    WORLD_HEIGHT));


            for (ParallaxingObject obj : game.pObjs)
            {
                obj.draw(g);
            }
            // draws game objects by calling draw methods onto both current graphics object and
            // minimaps graphics objects
            for (GameObject obj : game.gameObjects)
            {
                g.setTransform(viewPort);
                obj.draw(g);
                g.setColor(Color.RED);
                obj.draw(miniG);
            }
            // resets transform on graphis
            g.setTransform(initG);

            // draws the minimap onto window
            g.setClip(new Ellipse2D.Double(20, 20, minimapW, minimapH));
            g.drawImage(miniMap, 20, 20, null);
            g.setClip(initClip);

            g.drawImage(ImgManag.getImage("hud.png"), 0, 0, null);

            // draws scores and lifes
            String score, destroyables, health;
            g.setColor(FONT_COLOUR);
            g.setFont(FONT);
            score = "Score: " + game.player.score;
            health = "Health: " + Player.health + " / " + Player.maxHealth;
            destroyables = "Ast/Mobs: " + Game.noOfDestroyables;
            int fontW = FRAME_WIDTH - g.getFontMetrics().stringWidth(score) - 120;
            g.drawString(score, fontW , 20);
            g.drawString(health, fontW , 45 );
            g.drawString(destroyables, fontW,  70);

            String timerString;
            //draws new level timer
            if (Game.newLevelTimer != 0)
            {
                timerString = "New level in " + (5 - ((System.currentTimeMillis() - Game.newLevelTimer) / 1000));
                fontW = FRAME_WIDTH/2 - g.getFontMetrics().stringWidth(timerString) / 2;
                g.drawString(timerString, fontW, 45);
            }

            // draws death time
            if (Game.playerDied != 0)
            {
                g.setColor(Color.red);
                String deathMessage = "Defeated";
                timerString = "Reset available in " + (5 - ((System.currentTimeMillis() - Game.playerDied) / 1000));
                fontW = FRAME_WIDTH/2 - g.getFontMetrics().stringWidth(deathMessage)/2;
                g.drawString(deathMessage, fontW, FRAME_HEIGHT/2);
                fontW = FRAME_WIDTH/2 - g.getFontMetrics().stringWidth(timerString)/2;
//                g.drawString(timerString, fontW, FRAME_HEIGHT/2 + 25);
            }
            g.setColor(initCol);
        }

    }

    @Override
    public Dimension getPreferredSize()
    {
        return Constants.FRAME_SIZE;
    }
}
