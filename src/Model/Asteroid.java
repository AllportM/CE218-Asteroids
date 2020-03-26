package Model;

import Controller.Game;
import View.ImgManag;
import View.SoundsManag;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

import static Model.Constants.*;

public class Asteroid extends GameObject {
    public static final double MAX_SPEED = 100; // maximum speed an asteroid can have
    private Vector2D rotationalVec;
    public Asteroid[] child = new Asteroid[3];
    public boolean killedByPlayer = false;
    public int ttl; // how long before asteroid explodes, will be between 30-60 seconds as set in constructor
    public long spawnTime;
    private static final int LARGE = 55, MEDIUM = 40, SMALL = 25;

//    private LinkedList<Vector2D> texture; // old rotating texture

    /**
     * Standard constructor for an asteroid
     *
     * @param x
     * @param y  Positional value to instantiate an asteroid with
     * @param vx
     * @param vy Velocity values to instantiate an asteroid with
     */
    public Asteroid(double x, double y, double vx, double vy, int rad) {
        super(new Vector2D(x, y), new Vector2D(vx, vy), rad);
        rotationalVec = (new Vector2D(vx, vy));
        String text = String.format("asttext%d.png", (int) (Math.random() * 9));
        // sets sprite accordingly to radius of asteroid with random picture
        sp = new AstSprite(position, rotationalVec, rad*2, rad*2, ImgManag.getImage(text), genShape());
        ttl = ((int) (Math.random() * 30) + 30) * 1000;
        spawnTime = System.currentTimeMillis();
    }

    /**
     * makeRandomAsteroid's main purpose it to create an asteroid with random positional
     * and velocity values utilizing Math.random should positions not be within 10*playership radius
     *
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
        do
        {
            x = Math.random() * WORLD_WIDTH;
            y = Math.random() * WORLD_HEIGHT;
            pos = new Vector2D(x, y);
        }
        while (playerPos.dist(pos) < 500);
        double vx = (Math.random() * MAX_SPEED * 2) - MAX_SPEED;
        double vy = (Math.random() * MAX_SPEED * 2) - MAX_SPEED;
//        System.out.printf("posx= %3.2f, posy= %3.2f\nspeedx= %3.2f, speedy = %3.2f\n",
//                x, y, vx, vy);
        return new Asteroid(x, y, vx, vy, radius);
    }

    private void spawnBabies()
    {
        // creates 3 new child asteroids off 1 radius smaller then existing dead one
        // with random velocity scaled greater than parent's with random directional angle
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


    @Override
    public boolean canHit(GameObject other) {
        return other instanceof Bullet || other instanceof PlayerShip;
    }

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
