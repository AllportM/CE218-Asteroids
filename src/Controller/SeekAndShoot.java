package Controller;

import Model.GameObject;
import Model.Player;
import Model.Vector2D;

/**
 * SeekAndShoots purpose to to steer towards a ship and fire if within a set distance
 */
public class SeekAndShoot implements Controller {
    Action action = new Action(); // action controller to maneuver ship upon actions behaviour
    GameObject owner; // the ship this instance belongs to
    GameObject target; // the target ship to chase and shoot
    private static final double MIN_FIRE_DISTANCE = 400 + (Player.difficulty / 12) * 100;
                /// the minimum fire distance scaled by difficulty

    /**
     * standard constructor associated member variable
     * @param owner
     *      GameObject, the ship this instance belongs to
     */
    public SeekAndShoot(GameObject owner)
    {
        this.owner = owner;
    }

    /**
     * sets the target to chase and shoot
     * @param target
     */
    public void setTarget(GameObject target) {
        this.target = target;
    }

    /**
     * The logic to make the decision in which direction to turn, whether to shoot, and what speed to go
     * @return
     */
    @Override
    public Action action()
    {
        // if null, do nothing
        if (target == null)
            return new Action();

        // gets unwrapped world vector from owner to target
        Vector2D toTravel = ControllerHelperFuncts.unwrappedPosTarg(owner, target);

        // gets angle difference between direction and ships position
        Vector2D between2 = toTravel.copy().subtract(owner.position);
        double direct = owner.direction.angle(between2);
        // turns towards target
        if (direct < 0)
            action.turn = -1;
        else if (direct > 0)
            action.turn = 1;
        else action.turn = 0;

        // if target in fov of +/- 5 degree ship shoots by a distance of 400-600 dependant upon difficulty
        if (direct > Math.toRadians(Math.random()*1) && direct < Math.toRadians(5)
                && owner.position.dist(target.position) < MIN_FIRE_DISTANCE)
            action.shoot = true;
        else
            action.shoot = false;

        // attempts to match players speed if facing away (same direction), else moves default speed of 150
        if (owner.direction.copy().dot(target.direction) > 0 && target.velocity.mag() > owner.velocity.mag())
            action.thrust = 1;
        else if (owner.velocity.mag() < HlAiController.MAX_SHIP_SPEED)
            action.thrust = 1;
        else action.thrust = 0;
        return action;
    }
}
