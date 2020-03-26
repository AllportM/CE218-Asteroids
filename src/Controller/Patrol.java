package Controller;

import Model.GameObject;
import Model.MobSpawner;
import Model.Vector2D;

public class Patrol implements Controller {
    Action action;
    GameObject owner;
    GameObject mothership;
    static final double DISTANCE = MobSpawner.SPAWN_RADIUS;

    public Patrol(GameObject owner, GameObject ms)
    {
        this.owner = owner;
        this.mothership = ms;
        action = new Action();
    }

    @Override
    public Action action() {
        if (owner.velocity.mag() < 150)
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
