package Asteroids.game1;

import Asteroids.utilities.JEasyFrame;
import org.junit.Before;
import org.junit.Test;

import static Asteroids.game1.Constants.*;
import static org.junit.Assert.*;

public class BasicAsteroidTest {
    public static BasicGame game;

    @Before
    public void BasicAsteroidTest()
    {
        game = new BasicGame();
    }

    @Test
    public void asteroidMovex() throws Exception
    {
        BasicAsteroid a = game.asteroids.get(0);
        double x = a.getX();
        double y = a.getY();
        a.getY();
        Thread.sleep(DELAY);
        double newx = ((x + (a.getVx() * DT)) + FRAME_WIDTH) % FRAME_WIDTH;
        double newy = ((y * DT) + FRAME_HEIGHT) % FRAME_HEIGHT;
        x = a.getX();
        y = a.getY();
        assertEquals(x, newx, 2);
    }

    @Test
    public void asteroidMovey() throws Exception
    {
        BasicAsteroid a = game.asteroids.get(0);
        double y = a.getY();
        a.getY();
        Thread.sleep(DELAY);
        double newy = ((y + (a.getVx() * DT)) + FRAME_HEIGHT) % FRAME_HEIGHT;
        y = a.getY();
        assertEquals(y, newy, 2);
    }
}