package Controller;

import Model.GameObject;
import Model.Vector2D;

public class SeekAndShoot implements Controller {
    Action action = new Action();
    GameObject owner;
    GameObject target;

    public SeekAndShoot(GameObject owner)
    {
        this.owner = owner;
    }

    public void setTarget(GameObject target) {
        this.target = target;
    }

    @Override
    public Action action()
    {
        if (target == null)
            return action;
        Vector2D toTravel = Controllers.unwrappedPosTarg(owner, target);

        // gets angle difference between direction and ships position
        Vector2D between2 = toTravel.copy().subtract(owner.position);
        double direct = owner.direction.angle(between2);
        // turns towards target
        if (direct < 0)
            action.turn = -1;
        else if (direct > 0)
            action.turn = 1;
        else action.turn = 0;

        // if target in fov of +/- 5 degree ship shoots
        if (direct > Math.toRadians(-5) && direct < Math.toRadians(5) && owner.position.dist(target.position) < 400)
            action.shoot = true;
        else
            action.shoot = false;

        // attempts to match players speed if facing away (same direction), else moves default speed of 150
        if (owner.direction.copy().dot(target.direction) > 0 && target.velocity.mag() > owner.velocity.mag())
            action.thrust = 1;
        else if (owner.velocity.mag() < 150)
            action.thrust = 1;
        else action.thrust = 0;
        return action;
    }
}
