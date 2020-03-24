package Controller;

import Model.GameObject;
import Model.Vector2D;

public class Avoid implements Controller {
    Action action = new Action();
    GameObject owner;
    GameObject target;

    public Avoid(GameObject owner)
    {
        this.owner = owner;
    }

    public void setTarget(GameObject target) {
        this.target = target;
    }

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
