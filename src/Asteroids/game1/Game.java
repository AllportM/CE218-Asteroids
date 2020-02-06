package Asteroids.game1;

import Asteroids.utilities.JEasyFrame;

import java.awt.*;
import java.util.Iterator;
import java.util.LinkedList;

import static Asteroids.game1.Constants.DELAY;

/**
 * Game's purpose is to instantiate the frame, view, and game containing main
 * method, and act as the main Game Engine
 */
public class Game
{
    public static final int N_INITIAL_ASTEROIDS = 20;
    public LinkedList<GameObject> gameObjects;
    Keys ctrl;

    /**
     * No arg constructor, instantiates BasicAsteroids and adds to asteroids list
     */
    public Game()
    {
        gameObjects = new LinkedList<>();
        ctrl = new Keys();
        for (int i = 0; i < N_INITIAL_ASTEROIDS; i++)
        {
            gameObjects.add(BasicAsteroid.makeRandomAsteroid());
        }
        gameObjects.add(new Ship(ctrl));
    }

    /**
     * Main method incorporating game loop
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception
    {
        Game game = new Game();
        BasicView view = new BasicView(game);
        new JEasyFrame(view, "Basic Game").addKeyListener(game.ctrl);
//         below may improve game graphics at later date
        Graphics2D g = (Graphics2D) view.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_DITHERING,
                RenderingHints.VALUE_DITHER_ENABLE);
        g.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_LCD_CONTRAST, 100);
        g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
                RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
                RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
                RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
                RenderingHints.VALUE_STROKE_PURE);
        while (true)
        {
            game.update();
            view.repaint();
	    // makes games run smoothly on linux due to inefficient graphics scheduling?
	    if (System.getProperty("os.name").equals("Linux"))
	    {
		Toolkit.getDefaultToolkit().sync();
	    }
            Thread.sleep(DELAY);
        }
    }

    /**
     * update's purpose is to set the positional and velocity values of game objects
     * given the increase in time between delays
     */
    public void update()
    {
//        Thread thread = null;
        LinkedList<GameObject> alive = new LinkedList<>();
        for (Iterator<GameObject> it = gameObjects.iterator(); it.hasNext();)
        {
            GameObject obj = it.next();
            if (obj instanceof Ship)
            {
                Ship shipObj = (Ship) obj;
                if (shipObj.bullet != null)
                {
                    alive.add(shipObj.bullet);
                    shipObj.bullet = null;
                }
            }
//            Runnable task = () ->
//            {
            obj.update();
            if (obj.alive)
            {
                alive.add(obj);
            }
//            };
//            thread = new Thread(task);
//            thread.start();
        }
        synchronized (Game.class)
        {
            gameObjects.clear();
            gameObjects.addAll(alive);
        }
    }
}
