package Controller;

import Model.GameObject;
import Model.PlayerShip;
import Model.Ship;
import Model.Vector2D;

public class Controllers {

    /**
     * Calculates whether target object is visible to the ship in relation to their distance and the ships
     * fov parameters. It does so by setting parameters and getting the dot product of the difference of positional
     * vectors.
     * @param ship
     *      GameObject, the ship looking for a target
     * @param target
     *      GameObject, the target ship
     * @return
     *      boolean, true if the target is in the field of view, false if the target is not
     */
    public static boolean targetInFov(GameObject ship, GameObject target)
    {
        double distance = ship.position.dist(target.position);
        int fovDeg =0;
        if (distance < 250)
            fovDeg = 90;
        else if (distance < 350)
            fovDeg = 45;
        else if (distance < 500)
            fovDeg = 15;
        Vector2D between = target.position.copy().subtract(ship.position).normalise();
        if (fovDeg > 0)
            return Math.acos(ship.direction.dot(between)) < Math.toRadians(fovDeg);
        else return false;
//        Vector2D between = target.position.copy().subtract(ship.position);
//        if (fovDeg > 0)
//            return ship.direction.angle(between) < Math.toRadians(fovDeg);
//        else return false;
//            System.out.println(Math.toDegrees(ship.direction.angle(between)));
    }

    public static GameObject getNearestPlayer(GameObject ship, Game game)
    {
        double nearestDist = Double.MAX_VALUE;
        GameObject nearestShip = null;
        for (GameObject obj: game.gameObjects)
        {
            if (obj instanceof PlayerShip)
            {
                double dist = ship.position.dist(obj.position);
                if (dist < nearestDist)
                {
                    nearestShip = obj;
                    nearestDist = dist;
                }
            }
        }
        return nearestShip;
    }

    public static GameObject getNearestShip(GameObject ship, Game game)
    {
        double nearestDist = Double.MAX_VALUE;
        GameObject nearestShip = null;
        for (GameObject obj: game.gameObjects)
        {
            if (obj instanceof Ship && obj != ship)
            {
                double dist = ship.position.dist(obj.position);
                if (dist < nearestDist)
                {
                    nearestShip = obj;
                    nearestDist = dist;
                }
            }
        }
        return nearestShip;
    }
}
