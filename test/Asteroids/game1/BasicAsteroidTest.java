package Asteroids.game1;

import Asteroids.utilities.JEasyFrame;
import org.junit.Before;
import org.junit.Test;

import static Asteroids.game1.Constants.DELAY;
import static org.junit.Assert.*;

public class BasicAsteroidTest {
    public static BasicGame game;
    @Before
    public void BasicAsteroidTest()
    {
        BasicGame game = new BasicGame();
    }

    @Test
    public void asteroidMove1() throws Exception
    {
        BasicAsteroid a = game.asteroids.get(0);
        double x = a.getX();
        double y = a.getY();
        a.getY();
        Thread.sleep(DELAY);

    }
}