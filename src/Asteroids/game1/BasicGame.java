package Asteroids.game1;

import Asteroids.utilities.JEasyFrame;
import Asteroids.utilities.Refresh;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static Asteroids.game1.Constants.DELAY;

/**
 * BasicGame's purpose is to instantiate the frame, view, and game containing main
 * method, and act as the main Game Engine
 */
public class BasicGame
{
    public static final int N_INITIAL_ASTEROIDS = 20;
    public LinkedList<Refresh> gameObjects;
    BasicKeys ctrl;

    /**
     * No arg constructor, instantiates BasicAsteroids and adds to asteroids list
     */
    public BasicGame()
    {
        gameObjects = new LinkedList<>();
        ctrl = new BasicKeys();
        for (int i = 0; i < N_INITIAL_ASTEROIDS; i++)
        {
            gameObjects.add(BasicAsteroid.makeRandomAsteroid());
        }
        gameObjects.add(new BasicShip(ctrl));
    }

    /**
     * Main method incorporating game loop
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception
    {
        BasicGame game = new BasicGame();
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
            Thread.sleep(DELAY);
        }
    }

    /**
     * update's purpose is to set the positional and velocity values of game objects
     * given the increase in time between delays
     */
    public void update()
    {
        Thread thread = null;
        for (Refresh obj: gameObjects)
        {
            Runnable task = () ->
            {
                obj.update();
            };
            thread = new Thread(task);
            thread.start();
        }
    }
}
