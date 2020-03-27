package Controller;

import Model.GameObject;
import Model.MobSpawner;
import Model.PlayerShip;

import static Model.Constants.*;

/**
 * HiAiController is a higher level controller for AI which controls the perception and behaviour
 * parts of the ai logic changing states to patrol, avoid, or attack by invoking lower level controllers
 */
public class HlAiController implements Controller {
    private Game game; // current game instance used for sensing
    private GameObject owner; // the ship this controller belongs to
    SeekAndShoot llcontrollerSS; // attack lower level controller
    Avoid llcontrolerAv; // collision avoidence lower level controller
    Patrol llcontrollerPat; // patrol controller
    private MobSpawner motherShip; // the spawner to which a mob belongs to, used in patrolling distances etc
    private int action; // the action to pass through to the ship
    private final static int SEEKSHOOT = 0, AVOID = 1, PATROL = 2; // constants to dictate state
    private final static int RETREAT_DIST = FRAME_WIDTH * 2; // constants used in decision making distances
    private final static int AVOID_DIST = 250; // same as above
    protected final static int MAX_SHIP_SPEED = 150; // maximum speed a ship should travel if not chasing player;

    /**
     * default constructor which initialises class members
     * @param game
     *      Game, the current instance of the game
     * @param ms
     *      GameObject, the mothership/spawner to which the owner ship belongs to
     */
    public HlAiController(Game game, MobSpawner ms)
    {
        this.game = game;
        this.motherShip = ms;
    }

    /**
     * Sets the ships owner, given a ship cannot be created without a controller, one must have a set method
     * @param owner
     *      GameObject, the ship this belongs to
     */
    public void setOwner(GameObject owner)
    {
        llcontrollerSS = new SeekAndShoot(owner);
        llcontrolerAv = new Avoid(owner);
        llcontrollerPat = new Patrol(owner, motherShip);
        this.owner = owner;
    }

    /**
     * Logic/behaviour behind the controller on how to decide whether to patrol, attack, or avoid. This is done
     * in a last in first out (LIFO) mode where patrol is lowest precedence, attack middle, and avoid highest
     */
    public void makeDecision()
    {
        action = PATROL;

        // decides whether to attack player is within set distance
        GameObject player = ControllerHelperFuncts.getNearestPlayer(owner, game);
        if (llcontrollerSS.target != null || (player != null && ControllerHelperFuncts.targetInFov(owner, player)))
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

        // decides whether to stop chasing attacking tarket
        if (owner.position.dist(motherShip.position) > RETREAT_DIST)
        {
            action = PATROL;
            llcontrollerSS.target = null;
        }

        // decides whether to avoid
        GameObject nearestShip = ControllerHelperFuncts.getNearestShip(owner, game);
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

    /**
     * actions purpose is to provide implementation of the Controller interface and direct a given lowerlevelcontrollers
     * action dependant upon the current state
     * @return
     */
    @Override
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
