package Model;

import Controller.Game;
import Controller.HlAiController;

import java.awt.*;
import java.awt.geom.Path2D;
import java.util.ArrayList;

public class MobSpawner extends GameObject{
    private static final double RADIUS = 15;
    public static final double SPAWN_RADIUS = 250;
    private static final int ENEMIES_CAN_SPAWN = 3;

    public ArrayList<EnemyShip> ships;

    public MobSpawner(Vector2D position, Game game)
    {
        super(position, new Vector2D(0,0), RADIUS);
        ships = new ArrayList<>();
        double maximumAngle = Math.toRadians(360/ENEMIES_CAN_SPAWN);
        for (double i = 0; i < Math.toRadians(360); i+= maximumAngle)
        {
            double randomAngle = Math.random() * maximumAngle;
            Vector2D newPos = Vector2D.polar(i + randomAngle, SPAWN_RADIUS).add(position);
            ships.add(new EnemyShip(new HlAiController(game, this), newPos));
        }
    }

    @Override
    public boolean canHit(GameObject other) {
        return false;
    }

    @Override
    public void hit(GameObject other) {

    }

    @Override
    public Path2D genShape() {
        return new Path2D.Double();
    }

    @Override
    public void draw(Graphics2D g) {

    }
}
