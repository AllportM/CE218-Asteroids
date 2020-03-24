package Controller;

import Model.GameObject;
import Model.PlayerShip;
import Model.Ship;
import Model.Vector2D;

import static Model.Constants.WORLD_HEIGHT;
import static Model.Constants.WORLD_WIDTH;

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
        if (distance < 400)
            fovDeg = 90;
        else if (distance < 500)
            fovDeg = 45;
        else if (distance < 600)
            fovDeg = 15;
        Vector2D between = target.position.copy().subtract(ship.position).normalise();
        if (fovDeg > 0)
            return Math.acos(ship.direction.dot(between)) < Math.toRadians(fovDeg);
        else return false;
    }

    public static GameObject getNearestPlayer(GameObject ship, Game game)
    {
        double nearestDist = Double.MAX_VALUE;
        GameObject nearestShip = null;
        for (GameObject obj: game.gameObjects)
        {
            if (obj instanceof PlayerShip && obj.alive)
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

    /**
     * unwrappedPosTargets purpose is to calculate whether travelling towards the target within the worlds
     * bounds is the shortest distance or whether utilizing the world wrap would be the shortest distance
     * and return a new vector for the shortest means of travel for the owner.
     *
     * This is accomplished by first calculating the distance by subtracting the targets position vector from
     * the owners, and if the distance is greater than half the worlds width then using the world wrap function
     * would be the shortest means of travel
     * @param owner
     *      GameObject, the reference object to check
     * @param target
     *      GameObject, the target to check against
     * @return
     *      Vector2D, the new travel vector
     */
    public static Vector2D unwrappedPosTarg(GameObject owner, GameObject target)
    {
        Vector2D ownerPos = owner.position;
        Vector2D targetPos = target.position;
        Vector2D diff = targetPos.copy().subtract(ownerPos);
        double x2 = targetPos.x;
        double y2 = targetPos.y;
        if (diff.x > WORLD_WIDTH / 2 || diff.x < -(WORLD_WIDTH / 2))
        {
            if (targetPos.x < WORLD_WIDTH / 2)
                x2 = x2 + WORLD_WIDTH;
            else
                x2 = x2 - WORLD_WIDTH;
        }
        if (diff.y > WORLD_HEIGHT / 2 || diff.y < -(WORLD_HEIGHT / 2))
        {
            if (targetPos.y < WORLD_HEIGHT / 2)
                y2 = y2 + WORLD_HEIGHT;
            else
                y2 = y2 - WORLD_HEIGHT;
        }
        Vector2D result = new Vector2D(x2, y2);
        return result;
    }
}
