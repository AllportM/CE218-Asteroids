package Asteroids.game1;

import Asteroids.utilities.JEasyFrame;

import java.util.ArrayList;
import java.util.List;

import static Asteroids.game1.Constants.DELAY;

/**
 * BasicGame's purpose is to instantiate the frame, view, and game containing main
 * method, and act as the main Game Engine
 */
public class BasicGame
{
    public static final int N_INITIAL_ASTEROIDS = 10;
    public List<BasicAsteroid> asteroids;

    /**
     * No arg constructor, instantiates BasicAsteroids and adds to asteroids list
     */
    public BasicGame()
    {
        asteroids = new ArrayList<>();
        for (int i = 0; i < N_INITIAL_ASTEROIDS; i++)
        {
            asteroids.add(BasicAsteroid.makeRandomAsteroid());
        }
    }

    /**
     * Main method
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception
    {
        BasicGame game = new BasicGame();
        BasicView view = new BasicView(game);
        new JEasyFrame(view, "Basic Game");
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
        for (BasicAsteroid ast: asteroids)
        {
            ast.update();
        }
    }
}
