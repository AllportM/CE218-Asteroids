package Controller;

import Model.GameObject;
import Model.MobSpawner;
import Model.Vector2D;

/**
 * Patrols purpose is to take action and steer the ship towards the mob spawner it belongs to if it is
 * outside of a given distance
 */
public class Patrol implements Controller {
    Action action; // action controller to maneuver ship upon actions behaviour
    GameObject owner; // the ship this instance belongs to
    GameObject mothership; // the mob spawner to which the ship belongs to
    static final double DISTANCE = MobSpawner.SPAWN_RADIUS; // the distance to which the ship should be within

    /**
     * Default constructor associating member variables to args
     * @param owner
     *      GameObject, the ship this instance belongs to
     * @param ms
     *      GameObject, the mob spawner the ship belongs to
     */
    public Patrol(GameObject owner, GameObject ms)
    {
        this.owner = owner;
        this.mothership = ms;
        action = new Action();
    }

    /**
     * actions purpose is to decide whether the ship is within the set distance from a mob spawner, if not
     * steer the ship towards it, if so steer straight. This also dictates whether to accelerate from a default speed
     * of 150
     * @return
     */
    @Override
    public Action action() {
        if (owner.velocity.mag() < HlAiController.MAX_SHIP_SPEED)
        {
            action.thrust = 1;
        }
        else
            action.thrust = 0;
        if (owner.position.dist(mothership.position) > DISTANCE)
        {
            Vector2D toTravel = ControllerHelperFuncts.unwrappedPosTarg(owner, mothership);
            Vector2D between2 = toTravel.copy().subtract(owner.position);
            double direct = owner.direction.angle(between2);
            // turns towards target
            if (direct < 0)
                action.turn = -1;
            else if (direct > 0)
                action.turn = 1;
            else action.turn = 0;
        }
        else action.turn = 0;
        return action;
    }
}
