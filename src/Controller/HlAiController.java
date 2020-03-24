package Controller;

import Model.GameObject;
import Model.MobSpawner;
import Model.PlayerShip;
import Model.Vector2D;

import static Model.Constants.*;

public class HlAiController implements Controller {
    private Game game;
    private GameObject owner;
    SeekAndShoot llcontrollerSS;
    Avoid llcontrolerAv;
    Patrol llcontrollerPat;
    private MobSpawner motherShip;
    private int action;
    private final static int SEEKSHOOT = 0, AVOID = 1, PATROL = 2;
    private final static int RETREAT_DIST = FRAME_WIDTH * 2;
    private final static int AVOID_DIST = 350;

    public HlAiController(Game game, MobSpawner ms)
    {
        this.game = game;
        this.motherShip = ms;
    }

    public void setOwner(GameObject owner)
    {
        llcontrollerSS = new SeekAndShoot(owner);
        llcontrolerAv = new Avoid(owner);
        llcontrollerPat = new Patrol(owner, motherShip);
        this.owner = owner;
    }

    public void makeDecision()
    {
        action = PATROL;
        GameObject player = Controllers.getNearestPlayer(owner, game);
        if (llcontrollerSS.target != null || (player != null && Controllers.targetInFov(owner, player)))
        {
            if (!(owner.position.distExcWW(motherShip.position) > RETREAT_DIST))
            {
                llcontrollerSS.setTarget(player);
                action = SEEKSHOOT;
            }
            else
            {
                action = PATROL;
                llcontrollerSS.target = null;
            }
        }

        if (owner.position.dist(motherShip.position) > RETREAT_DIST)
        {
            action = PATROL;
            llcontrollerSS.target = null;
        }

        GameObject nearestShip = Controllers.getNearestShip(owner, game);
        if (nearestShip != null && owner.position.distExcWW(nearestShip.position) <= AVOID_DIST)
        {
            if (nearestShip instanceof PlayerShip)
            {
                llcontrollerSS.setTarget(nearestShip);
            }
            llcontrolerAv.setTarget(nearestShip);
            action = AVOID;
        }
    }

    public Action action()
    {
        Action act;
        makeDecision();
        switch (action)
        {
            case SEEKSHOOT:
                llcontrollerSS.action();
                act = llcontrollerPat.action;
                break;
            case AVOID:
                act = llcontrolerAv.action();
                break;
            case PATROL:
            default:
                act = llcontrollerPat.action();
                break;
        }
        return act;
    }
}
