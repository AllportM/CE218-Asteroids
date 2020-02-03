package Asteroids.game1;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BasicAsteroidTest {
    public static Game game;

    @Test
    public void testHyp()
    {
        assertEquals(Math.hypot(10,10), Math.sqrt(10*10 + 10*10), 0.1);
    }

    @Before
    public void BasicAsteroidTest()
    {
        game = new Game();
    }

//    @Test
//    public void asteroidMovex() throws Exception
//    {
//        BasicAsteroid a = game.asteroids.get(0);
//        double x = a.getX();
//        double y = a.getY();
//        a.getY();
//        Thread.sleep(DELAY);
//        double newx = ((x + (a.getVx() * DT)) + FRAME_WIDTH) % FRAME_WIDTH;
//        double newy = ((y * DT) + FRAME_HEIGHT) % FRAME_HEIGHT;
//        x = a.getX();
//        y = a.getY();
//        assertEquals(x, newx, 4);
//    }
//
//    @Test
//    public void asteroidMovey() throws Exception
//    {
//        BasicAsteroid a = game.asteroids.get(0);
//        double y = a.getY();
//        a.getY();
//        Thread.sleep(DELAY);
//        double newy = ((y + (a.getVx() * DT)) + FRAME_HEIGHT) % FRAME_HEIGHT;
//        y = a.getY();
//        assertEquals(y, newy, 4);
//    }
}