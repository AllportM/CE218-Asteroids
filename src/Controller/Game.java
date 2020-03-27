package Controller;

import Model.*;
import View.ImgManag;
import View.MainUI;
import View.SoundsManag;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeSet;

import static Model.Constants.*;

/**
 * Game's purpose is to instantiate and store all current GameObjects in a list of member variables, Asteroids,
 * Ships, Spawners, Parallaxing objects etc. It has all of the game logic required for updating these members
 * in the update method including collision detection, initiating ships firing bulets, adding new asteroids etc.
 * It also can reset required variables in order to start a new game and detect the end of game.
 */
public class Game
{
    private static final int N_INITIAL_ASTEROIDS = 30; // Initial asteroids to spawn
    private static final int MIN_MOBS = 3; // minimum amount of mob spawners to add at start level
    private static final int MAX_MOBS = 15; // maximum number of mob spawners for a game
    private static final int MOB_DIFF_FAC = 2; // how many difficulty levels required to add extra spawners
    private static final int MAX_ASTEROIDS = 102; // maximum number of asteroids to spawn
    private static final int AST_DIFF_MULT = 3; // how many asteroids to add for each difficulty level increase
         // max mobs reached in (15-3) * 2 levels, asteroids must reach 72 in 24 levels, 3 each new map
    public static long startOfGame; // start of game timer
    public static int noOfDestroyables = 0; // counter to decide when game level is finished
    public static boolean isEnd; // end of game boolean
    public LinkedList<GameObject> gameObjects; // linked list to iterate through in update, no random access required
    public TreeSet<ParallaxingObject> pObjs; // sorted list of parallaxing objects sorted dependent upon z-layering
    public Keys ctrl; // event listener for the ui to use
    public static ViewPort vp; // viewport to track the 0,0 location of the screen in relation to the players position
    public MainUI view; // the main ui required to initiate new level/end of game interfaces
    public static Player player; // for classes to access player states for this instance
    public static long newLevelTimer; // used as a timeout to control when to end the game (5 second delay from death)
    public static long playerDied; // the time that a player died, also used as a timer to comtrol ui screens

    /**
     * No arg constructor, instantiates BasicAsteroids and adds to asteroids lists
     */
    public Game()
    {
        ImgManag.init();
        ctrl = new Keys();
        initGame();
        gameObjects = new LinkedList<>();
        pObjs = new TreeSet<>();
    }

    /**
     * initGames purpose is to reset member variables to initial states, used initializing the game and when
     * a new game is started after death from ui
     */
    public void initGame()
    {
        gameObjects = new LinkedList<>();
        pObjs = new TreeSet<>();
        player = new Player();
        PlayerShip ps = new PlayerShip(ctrl, player);
        player.setPlayerShip(ps);
        gameObjects.add(ps);
        vp = new ViewPort(0,0, ps);
        isEnd = false;
        newLevelTimer = 0;
        player.resetPlayer();
        playerDied = 0;
    }

    /**
     * newLevel's purpose is to repopulate the world upon a new level, leaving the player intact
     */
    public void newLevel()
    {
        // synchronized to avoid concurrency from paint thread
        synchronized (Game.class)
        {
            gameObjects = new LinkedList<>();
            pObjs = new TreeSet<>();
            gameObjects.add(player.playerShip);
            generateStars();
//            generateSpawners();
//            generateAsteroids();

//             test code to make spawner
            Vector2D position = player.playerShip.position.copy();
            position.add(500, 0);
            MobSpawner mp = new MobSpawner(position, this);
            gameObjects.addAll(mp.ships);
            gameObjects.add(mp);
            newLevelTimer = 0;
        }
    }

    /**
     * Main method
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception
    {
        new MainUI();
    }

    /**
     * The more advanced game loop alllowing for game updates to be done independent of the repainting
     */
    public void gameLoop()
    {
        SoundsManag.startMusic();
        Timer repaintTimer = new Timer(Constants.DELAY, e ->
        {
            view.repaint();
        });
        repaintTimer.start();
        startOfGame = System.currentTimeMillis();
        int missedFrames = 0;
        while (!isEnd) {
            long t0 = System.currentTimeMillis();
            update();
            long t1 = System.currentTimeMillis();
            long timeout = Constants.DELAY - (t1 - t0);
            if (timeout > 0)
                try {
                    Thread.sleep(timeout);
                }
                catch (InterruptedException ignore){}
            else {
                missedFrames++;
                System.out.println(missedFrames);
            }
        }
        repaintTimer.stop();

        // end of game check for new level or player death
        if (player.playerShip.alive)
        {
            SoundsManag.stopMusic();
            Player.difficulty++;
            view.newLevel();
        }
        else {
            view.addButton();
        }
    }

    /**
     * populates gameObjs with asteroids
     */
    private void generateAsteroids()
    {
        int toSpawn = Math.min(N_INITIAL_ASTEROIDS + (AST_DIFF_MULT * (Player.difficulty - 1)), MAX_ASTEROIDS);
        for (int i = 0; i < toSpawn; i++)
        {
            gameObjects.add(Asteroid.makeRandomAsteroid());
        }
    }

    /**
     * populates gameObjs with enemy spawners procedurally
     */
    private void generateSpawners()
    {
        ArrayList<MobSpawner> spawners = new ArrayList<>();
        int posx, posy;
        int awayFromEdge = (int) MobSpawner.SPAWN_RADIUS + 50;
        int spawnerdistance = 900;
        boolean xInRange, uppperBoundlowerBound;
        Vector2D spawnerPosition;
        // every 3 levels mob spawners increase by 1
        int mobstospawn = Math.min(Player.difficulty /MOB_DIFF_FAC + MIN_MOBS, MAX_MOBS);
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
    private void generateStars()
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
                if (first.getClass() != second.getClass() ||
                        first instanceof EnemyShip && second instanceof EnemyShip)
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

        // synchronized to avoid concurrency
        synchronized (Game.class)
        {
            noOfDestroyables = 0;
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
                    if (obj instanceof EnemyShip || obj instanceof Asteroid)
                        noOfDestroyables += 1;
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
                else if (obj instanceof EnemyShip && ((EnemyShip)obj).killedByPlayer)
                {
                    Player.score += 300;
                    // 30% chance to drop an item
                    if (Math.random() > 0.5)
                        alive.add(new ItemPickup(obj));
                }
            }

            gameObjects.clear();
            gameObjects.addAll(alive);

            // handles end of level, setting timers counting down from 5 seconds
            if (noOfDestroyables == 0 && newLevelTimer == 0 && player.playerShip.alive)
                newLevelTimer = System.currentTimeMillis();
            if (newLevelTimer != 0 && System.currentTimeMillis() - newLevelTimer >= 5000)
                isEnd = true;

            if (!player.playerShip.alive && playerDied == 0)
            {
                playerDied = System.currentTimeMillis();
            }
            if (playerDied != 0 && System.currentTimeMillis() - playerDied >= 5000)
                isEnd = true;
        }
    }
}
