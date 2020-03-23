package Controller;

import Model.GameObject;
import Model.Ship;
import Model.Vector2D;

public class SeekAndShoot implements Controller {
    Action action = new Action();
    GameObject owner;
    GameObject target;
    private int safeDistance;
    private long safeDistReached = 0;
    private int chickenDist;
    private boolean retreating = false;
    private int retreatDir;

    public SeekAndShoot(GameObject owner)
    {
        this.owner = owner;
    }

    public void setTarget(GameObject target) {
        safeDistance = (int) target.RADIUS * 2 * 2;
        chickenDist = (int) (((Math.random() * 3 + 1) * owner.RADIUS * 2) + (int) owner.RADIUS * 4);
        this.target = target;
    }

    public void attackSeq()
    {
        // gets angle difference between direction and ships position
//            Vector2D between = new Vector2D(target.position.x - owner.position.x, target.position.y - owner.position.y);
//            between.normalise();
        Vector2D between2 = target.position.copy().subtract(owner.position);
        double direct = owner.direction.angle(between2);
        // turns towards target
        if (direct < 0)
            action.turn = -1;
        else if (direct > 0)
            action.turn = 1;
        else action.turn = 0;

        // if target in fov of +/- 5 degree ship shoots
//        if (direct > Math.toRadians(-5) && direct < Math.toRadians(5))
//            action.shoot = true;
//        else
//            action.shoot = false;

        // attempts to match players speed if facing away (same direction), else moves default speed of 150
        if (owner.direction.copy().dot(target.direction) > 0 && target.velocity.mag() > owner.velocity.mag())
            action.thrust = 1;
        else if (owner.velocity.mag() < 150)
            action.thrust = 1;
        else action.thrust = 0;
//        action.shoot = true;
    }

    public void comeAbout()
    {
        action.shoot = false;
        action.thrust = 1;
        if (owner.position.dist(target.position) > safeDistance)
        {
            retreatDir = (Math.random() > 0.5)? 1: -1;
            safeDistReached = System.currentTimeMillis();
        }
        else
        {
            retreatDir = 0;
            retreating = true;
            safeDistReached = 0;
        }
        action.turn = retreatDir;
    }

    @Override
    public Action action()
    {
        if (target.position.dist(owner.position) < chickenDist || retreating)
        {
            if (safeDistReached != 0 )
            {
                if (System.currentTimeMillis() - safeDistReached >= 1000) {
                    retreating = false;
                    System.out.println(action.thrust);
                }
            }
            else
            {
                comeAbout();
            }
        }
        else attackSeq();
        return action;
    }
}
