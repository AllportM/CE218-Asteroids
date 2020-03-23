package Controller;

import Model.GameObject;
import Model.Vector2D;

import static Model.Constants.*;

public class HlAiController implements Controller {
    private Game game;
    private GameObject owner;
    SeekAndShoot llcontrollerSS;
    Avoid llcontrolerAv;
    Vector2D startLocation;
    private int action;
    private final static int SEEKSHOOT = 0, AVOID = 1, PATROL = 2;
    private final static int RETREAT_DIST = (int) Math.sqrt(Math.pow(FRAME_WIDTH * 2, 2) + Math.pow(FRAME_HEIGHT * 2, 2));

    public HlAiController(Game game, Vector2D startLocation)
    {
        this.game = game;
        this.startLocation = startLocation;
    }

    public void setOwner(GameObject owner)
    {
        llcontrollerSS = new SeekAndShoot(owner);
        this.owner = owner;
    }

    public void makeDecision()
    {
        action = PATROL;
        GameObject player = Controllers.getNearestPlayer(owner, game);
        if (llcontrollerSS.target != null || Controllers.targetInFov(owner, player))
        {
            if (!(owner.position.dist(startLocation) > RETREAT_DIST))
            {
                llcontrollerSS.setTarget(player);
                action = SEEKSHOOT;
            }
            else
            {
                action = PATROL;
            }
        }

        GameObject nearestShip = Controllers.getNearestShip(owner, game);
        double distCanTravel = owner.velocity.mag() * DT;
        if (owner.position.dist(nearestShip.position) <= distCanTravel)
        {
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
                act = llcontrollerSS.action;
                break;
            case AVOID:
                act = new Action();
                break;
            case PATROL:
            default:
                act = new Action();
                break;
        }
        return act;
    }
}
