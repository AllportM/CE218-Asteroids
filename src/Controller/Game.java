package Controller;

import Model.*;
import View.BasicView;
import View.ImgManag;
import View.JEasyFrame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeSet;

import static Model.Constants.*;

/**
 * Game's purpose is to instantiate the frame, view, and game containing main
 * method, and act as the main Game Engine
 */
public class Game
{
    public static final int N_INITIAL_ASTEROIDS = 50;
    public static long startOfGame;
    public boolean isEnd = false;
    public LinkedList<GameObject> gameObjects;
    public TreeSet<ParallaxingObject> pObjs;
    Keys ctrl;
    public static ViewPort vp;
    BasicView view;
    public static Player player;

    /**
     * No arg constructor, instantiates BasicAsteroids and adds to asteroids list
     */
    public Game()
    {
        ImgManag.init();
        ctrl = new Keys();
        gameObjects = new LinkedList<>();
        pObjs = new TreeSet<>();
        player = new Player();
        PlayerShip ps = new PlayerShip(ctrl);
        player.setPlayerShip(ps);
        gameObjects.add(ps);
        vp = new ViewPort(0,0, ps);
        generateSpawners();
        generateStars();
        generateAsteroids();
    }

    private void generateAsteroids()
    {
        for (int i = 0; i < N_INITIAL_ASTEROIDS; i++)
        {
            gameObjects.add(Asteroid.makeRandomAsteroid());
        }
    }

    private void generateSpawners()
    {
        ArrayList<MobSpawner> spawners = new ArrayList<>();
        int posx, posy;
        int awayFromEdge = (int) MobSpawner.SPAWN_RADIUS + 50;
        int spawnerdistance = 900;
        boolean xInRange, uppperBoundlowerBound;
        Vector2D spawnerPosition;
        int mobstospawn = 9;
//
//        ArrayList<double[]> points = new ArrayList<>();
//        double horiNodes = WORLD_WIDTH / (awayFromEdge * 3);
//        double vertiNodes = WORLD_HEIGHT / (awayFromEdge * 3);
//        System.out.println(horiNodes + ", " + vertiNodes);
//        for (double i = WORLD_HEIGHT / vertiNodes; i <= WORLD_HEIGHT; i += WORLD_HEIGHT / vertiNodes)
//        {
//            for (double j = WORLD_WIDTH / horiNodes; j <= WORLD_WIDTH; j += WORLD_WIDTH / horiNodes)
//            {
//                System.out.println(i + ", " + j);
//                if ((j  < WORLD_WIDTH / 2f - FRAME_WIDTH / 2f || j > WORLD_WIDTH / 2f + FRAME_WIDTH / 2f)
//                    && (i < WORLD_HEIGHT / 2f - FRAME_HEIGHT / 2 || i > WORLD_HEIGHT / 2f - FRAME_HEIGHT / 2f))
//                {
//                    double[] point = {i / 2, j / 2};
//                    points.add(point);
//                }
//            }
//        }
//        System.out.println(points.size());

        while (spawners.size() !=  mobstospawn)
        {
            xInRange = Math.random() < 0.5;
            uppperBoundlowerBound = Math.random() < 0.5;
            // generates x/y coordinates that are around the outskirts of the screen outside of view from
            // player viewport inset by awayFromEdge so that mobs shouldn't be visible on player spawn
            if (xInRange)
            {
                posx = (int) (Math.random() * (WORLD_WIDTH - awayFromEdge * 2) + awayFromEdge);
                int yRange = (int) (Math.random() * ((WORLD_HEIGHT / 2 - FRAME_HEIGHT / 2)
                        - awayFromEdge * 2)) + awayFromEdge;
                if (uppperBoundlowerBound)
                    posy = yRange;
                else
                    posy = yRange + (WORLD_HEIGHT / 2 + FRAME_HEIGHT / 2);
            }
            else
            {
                posy = (int) (Math.random() * (WORLD_HEIGHT - awayFromEdge * 2) + awayFromEdge);
                int xRange = (int) (Math.random() * ((WORLD_WIDTH / 2 - FRAME_WIDTH / 2)
                        - awayFromEdge * 2)) + awayFromEdge;
                if (uppperBoundlowerBound)
                    posx = xRange;
                else
                    posx = xRange + (WORLD_WIDTH / 2 + FRAME_WIDTH / 2);
            }

            // checks if any existing spawners are too close, if not adds to spawners arraylist
            spawnerPosition = new Vector2D(posx, posy);
            boolean anyInDistance = false;
            for (MobSpawner spawner: spawners)
            {
                if (spawner.position.distExcWW(spawnerPosition) < spawnerdistance)
                {
                    anyInDistance = true;
                }
            }
            if (!(anyInDistance))
            {
                spawners.add(new MobSpawner(spawnerPosition, this));
            }
        }

        // adds spawners to game objects
        for (MobSpawner spawner: spawners)
        {
            gameObjects.addAll(spawner.ships);
        }
    }
    /**
     * geenerateStars purpose is to populate the pObjs array with procedurally generated parallaxing
     * stars, and the background.
     */
    public void generateStars()
    {
        int i, j;
        pObjs.add(new ParallaxingImage(1, 0, 0, "BackgroundSmall3.png"));

        for (i = 0; i < WORLD_HEIGHT * 0.4; i+= 100)
        {
            for (j = 0; j < WORLD_WIDTH * 0.4; j+=100)
            {
                // draws layer 2 stars
                if (Math.random() > 0.7)
                {
                    pObjs.add(new ParallaxingStar(2, j + (int) (Math.random() * 101),
                            i + (int) (Math.random() * 101)));
                }
                // draws layer 3 stars
                if (Math.random() > 0.7)
                {
                    pObjs.add(new ParallaxingStar(3, j + (int) (Math.random() * 101),
                            i + (int) (Math.random() * 101)));
                }
//                 draws layer 4 stars
                if (Math.random() > 0.7)
                {
                    pObjs.add(new ParallaxingStar(4, j + (int) (Math.random() * 101),
                            i + (int) (Math.random() * 101)));
                }
            }
        }
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

        // Game loop
        Timer repaintTimer = new Timer(Constants.DELAY, e -> view.repaint());
        repaintTimer.start();
        game.startOfGame = System.currentTimeMillis();
        int missedFrames = 0;
        while (!game.isEnd) {
            long t0 = System.currentTimeMillis();
            game.update();
            long t1 = System.currentTimeMillis();
            long timeout = Constants.DELAY - (t1 - t0);
            if (timeout > 0)
                Thread.sleep(timeout);
            else missedFrames++;
        }
    }

    /**
     * update's purpose is to set the positional and velocity values of game objects
     * given the increase in time between delays
     */
    public void update()
    {
        // collision detection handling
        for (int i = 0; i < gameObjects.size() -1; i++)
        {
            for (int j = i+1; j < gameObjects.size(); j++)
            {
                GameObject first = gameObjects.get(i);
                GameObject second = gameObjects.get(j);
                if (first.getClass() != second.getClass())
                {
                    first.collisionHandling(second);
                }
            }
        }

        // updates stars
        for (ParallaxingObject obj : pObjs)
        {
            if (obj instanceof ParallaxingStar)
                ((ParallaxingStar) obj).update();
        }

        synchronized (Game.class)
        {
            LinkedList<GameObject> alive = new LinkedList<>();
            for (Iterator<GameObject> it = gameObjects.iterator(); it.hasNext();)
            {
                GameObject obj = it.next();
                // spawns bullet from ship if fires
                if (obj instanceof Ship)
                {
                    Ship shipObj = (Ship) obj;
                    if (shipObj.bullet != null)
                    {
                        alive.add(shipObj.bullet);
                        shipObj.bullet = null;
                    }
                }
                obj.update();

                // adds live objects to alive list in order to remove dead ones
                if (obj.alive)
                {
                    alive.add(obj);
                }

                /*** following clauses are for if object is dead***/
                // sets data if item is an asteroid i.e score and children asteroids on death
                else if (obj.getClass() == Asteroid.class)
                {
                    Asteroid obj1 = (Asteroid) obj;
                    // spawns children asteroids
                    if (obj1.child[0] != null)
                    {
                        for (int i = 0; i < 3; i++)
                        {
                            alive.add(obj1.child[i]);
                        }
                    }
                    if (obj1.killedByPlayer)
                    {
                        Player.score += 100;
                    }
                }
    //            };
    //            thread = new Thread(task);
    //            thread.start();
            }
            gameObjects.clear();
            gameObjects.addAll(alive);
        }
    }
}
