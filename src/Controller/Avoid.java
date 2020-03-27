package Controller;

import Model.GameObject;
import Model.Vector2D;

/**
 * Avoid's purpose is to allow an AI ship to take action and steer the ship away from a given target
 */
public class Avoid implements Controller {
    Action action = new Action(); // action controller to maneuver ship upon actions behaviour
    GameObject owner; // the ship this instance belongs to
    GameObject target; // the target to steer away from

    /**
     * Default constructor, initializing the ship this belongs to
     * @param owner
     *      GameObject, the ship this belongs to
     */
    public Avoid(GameObject owner)
    {
        this.owner = owner;
    }

    /**
     * setTargets purpose is to initialize the target from a given GameObject
     * @param target
     *      GameObject, the target to steer away from
     */
    public void setTarget(GameObject target) {
        this.target = target;
    }

    /**
     * action's purp;ose is to decide which direction to steer the ship
     * @return
     */
    @Override
    public Action action()
    {
        Vector2D between2 = target.position.copy().subtract(owner.position);
        double direct = owner.direction.angle(between2);
        // turns towards target
        if (direct < 0)
            action.turn = 1;
        else if (direct > 0)
            action.turn = -1;
        if (owner.velocity.mag() < 150)
            action.thrust = 1;
        else
            action.thrust = 0;
        return action;
    }
}
