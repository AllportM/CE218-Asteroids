package Model;

import Controller.Game;
import View.ImgManag;
import View.SoundsManag;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

import static Model.Constants.*;

/**
 * Asteroid's purpose is to provide functionality to create asteroids, generate a procedurally generated
 * shape for this asteroid, instantiate a sprite from that shape, contain the logic on what to do when hit,
 * contain the logic for deciding when to randomly explode, and how to update the object
 */
public class Asteroid extends GameObject {
    private static final double MAX_SPEED = 100; // maximum speed an asteroid can have
    private Vector2D rotationalVec; // direction
    public Asteroid[] child = new Asteroid[3]; // array to contain children asteroids when hit
    public boolean killedByPlayer = false; // flags whether to give player scores
    public int ttl; // how long before asteroid explodes, will be between 30-90 seconds as set in constructor
    public long spawnTime; // when this asteroid was spawned, used in timer for random explosion
    private static final int LARGE = 55, MEDIUM = 40, SMALL = 25; // constants for radii
    private static final double MIN_DISTANCE_FROM_PLAYER = 500;

    /**
     * Standard constructor for an asteroid instantiating member variables and procedurally generating
     * time to explode
     *
     * Asteroids texture image is procedurally generated
     *
     * @param x positional x value
     * @param y  Positional value to instantiate an asteroid with
     * @param vx velocity x value
     * @param vy Velocity values to instantiate an asteroid with
     */
    public Asteroid(double x, double y, double vx, double vy, int rad) {
        super(new Vector2D(x, y), new Vector2D(vx, vy), rad);
        rotationalVec = (new Vector2D(vx, vy));
        String text = String.format("asttext%d.png", (int) (Math.random() * 9));
        // sets sprite accordingly to radius of asteroid with random picture
        sp = new AstSprite(position, rotationalVec, rad*2, rad*2, ImgManag.getImage(text), genShape());
        ttl = ((int) (Math.random() * 61) + 30) * 1000;
        spawnTime = System.currentTimeMillis();
    }

    /**
     * Procedurally generates an asteroid that is
     * @return
     */
    public static Asteroid makeRandomAsteroid() {
        int[] radii = {SMALL, MEDIUM, LARGE};
        int radius = radii[(int) (Math.random() * 3)]; // random radius between 12-24 in increments of 6 (3 different
                                                        // sizes of asteroids)
        GameObject player = Game.player.playerShip;
        Vector2D playerPos = player.position;
        double x, y;
        Vector2D pos;
        // generates random set of x y coordinates and uses this vector if not too close to player
        do
        {
            x = Math.random() * WORLD_WIDTH;
            y = Math.random() * WORLD_HEIGHT;
            pos = new Vector2D(x, y);
        }
        while (playerPos.dist(pos) < MIN_DISTANCE_FROM_PLAYER);
        double vx = (Math.random() * MAX_SPEED * 2) - MAX_SPEED;
        double vy = (Math.random() * MAX_SPEED * 2) - MAX_SPEED;
        return new Asteroid(x, y, vx, vy, radius);
    }

    /**
     * creates 3 new child asteroids off 1 radius smaller then existing dead one
     * with random velocity scaled greater than parent's with random directional angle
     */
    private void spawnBabies()
    {

        for (int i = 0; i < 3; i++) {
            double randAngle = Math.toRadians(Math.random() * 360);
            Vector2D newV = Vector2D.polar(randAngle, velocity.mag() * (Math.random() + 1));
            switch ((int) RADIUS)
            {
                case MEDIUM:
                    child[i] = new Asteroid(this.position.x, this.position.y,
                            newV.x, newV.y, SMALL);
                    break;
                case LARGE:
                    child[i] = new Asteroid(this.position.x, this.position.y,
                            newV.x, newV.y, MEDIUM);
                    break;
                case SMALL:
                default:
                    break;
            }
        }
    }

    /**
     * specifies which objects this can collide with
     * @param other
     * @return
     */
    @Override
    public boolean canHit(GameObject other) {
        return other instanceof Bullet || other instanceof PlayerShip;
    }

    /**
     * Determines behaviour to take if this asteroid has been hit, will generate babies if medium or large
     * and sets killed by player flag
     * @param other
     */
    @Override
    public void hit(GameObject other) {
        super.hit(other);
        alive = false;
        spawnBabies();
        if (other instanceof PlayerBullet)
        {
            killedByPlayer = true;
        }
        // plays explosion sound
        if (position.dist(Game.player.playerShip.position) <= FRAME_WIDTH + 200)
        {
            switch ((int) RADIUS)
            {
                case MEDIUM:
                    SoundsManag.medExp();
                    break;
                case LARGE:
                    SoundsManag.largeExp();
                    break;
                case SMALL:
                default:
                    SoundsManag.smallExp();
                    break;
            }
        }
    }

    /**
     * getShapeCord's purpose is to randomly generate circlish shape
     * @return
     *      int[][], [0][..] and [1][..] being randomly generated x and y coordinates respectively
     */
    @Override
    public Path2D genShape() {
        Path2D shape = new Path2D.Double();
        int points = (int) RADIUS * 2  / 5;

        // a randomly generated polar quantity is produced with angles in range of 0-2*pi (0-360 deg)
        // in increments of RADIUS divided by 5, i.e how many points to draw. From this a randomly
        // generated magnitude or hypotenuse is generated, and converted into cartesian coordinates
        // to create shape
        int mag = 2 * (int) (RADIUS / 3) + (int) (Math.random() * (RADIUS / 3));
        double angle = (Math.random() * (Math.PI * 2 / points));
        // first coord
        shape.moveTo(Math.cos(angle) * mag, Math.sin(angle) * mag);
        // generates points-1 more coordinates
        for (int i = 1; i < points; i++)
        {
            mag =  2 * (int) (RADIUS / 3) + (int) (Math.random() * (RADIUS / 3));
            angle = (Math.random() * (Math.PI * 2/ points)) + i * (Math.PI * 2 / points);
            shape.lineTo(Math.cos(angle) * mag, Math.sin(angle) * mag);
        }
        shape.closePath();
        shape.createTransformedShape(AffineTransform.getTranslateInstance(RADIUS, RADIUS));
        return shape;
    }

    /**
     * update's purpose is to update the asteroids position given it's velocity
     * and change in time DT
     */
    @Override
    public void update() {
        super.update();
        rotationalVec.rotate(0.01);
        if (System.currentTimeMillis() - spawnTime >= ttl && RADIUS > SMALL)
        {
            alive = false;
            spawnBabies();
        }
    }

    /**
     * draw's purpose is to draw this asteroid on a give Graphics2D object utilizing populated
     * positional vectors in it's shape vector2D array
     * @param g
     *      Graphics2D, the jswing graphics object to draw unto
     */
    @Override
    public void draw(Graphics2D g)
    {

        sp.paint(g);
    }

    @Override
    public String toString() {
        return String.format("I am an asteroid, my positional vector is\n%s", position);
    }
}
