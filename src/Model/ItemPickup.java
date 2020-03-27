package Model;

import Controller.Game;
import View.BasicView;
import View.ImgManag;
import View.SoundsManag;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

/**
 * ItemPickups purpose is to provide functionality for an item drop, adjusting Players static member variables
 * in order to increase its stats. Item type is randomly generated
 */
public class ItemPickup extends GameObject{
    // constants identifiying which type of pickup
    public static final String SHIP_SPEED = "Speed Up", SHIP_ACC = "Acc Up", FIRE_RATE = "Fire Rate Up",
    HEAL = "Heal++", RADAR_RANGE = "Radar Up", HEALTH_UP = "Health Up", STEER_UP = "Steering Up";
    private String id; // this specific item pickup
    private static final int TEXT_TTL = 1000; // time to live for the text after the item has been picked up
    public boolean pickedup = false; // whether the item has been picked up, so collision isnt made more than once
    public long pickedUpTime; // the time picked up so that this object can be flagged as dead

    /**
     * Standard constructor
     * @param droppedBy
     *      GameObject, the object that dropped the item, used for the positional value
     */
    public ItemPickup(GameObject droppedBy)
    {
        super(droppedBy.position, new Vector2D(0,0), 20);
        direction = new Vector2D(0, -20).normalise();
        sp = new Sprite(position, direction, RADIUS * 2, RADIUS * 2, ImgManag.getImage("pickup.png"), genShape());
    }

    @Override
    public boolean canHit(GameObject other) {
        return other instanceof PlayerShip;
    }

    /**
     * used to set whether the object is dead
     */
    @Override
    public void update()
    {
        if (pickedup && System.currentTimeMillis() - pickedUpTime > TEXT_TTL)
            this.alive = false;
    }

    /**
     * Randomly decides what item this instance will be, and given only ships can hit this we know
     * the other object is a player ship
     * @param other
     */
    @Override
    public void hit(GameObject other)
    {
        if (!pickedup)
        {
            pickedup = true;
            pickedUpTime = System.currentTimeMillis();
            switch((int) (Math.random() * 6))
            {
                case 0:
                    id = SHIP_SPEED;
                    Player.shipSpeed += 0.1;
                    break;
                case 1:
                    id = SHIP_ACC;
                    Player.shipAcc += 0.2;
                    break;
                case 2:
                    id = FIRE_RATE;
                    Player.fireRate += 0.3;
                    break;
                case 3:
                    id = HEAL;
                    if (Game.player.health < 100)
                        Player.health += 20;
                    break;
                case 5:
                    id = HEALTH_UP;
                    Player.maxHealth += 20;
                    break;
                case 6:
                    id = STEER_UP;
                    Player.turnResp += 0.3;
                case 4:
                default:
                    id = RADAR_RANGE;
                    Player.radarRange += 1;
                    break;
            }
            ((PlayerShip) other).updateStats();
            SoundsManag.playDing();
        }
    }

    @Override
    public Path2D genShape()
    {
        Path2D result = new Path2D.Double();
        result.moveTo(0, 28);
        result.lineTo(0,8);
        result.lineTo(21, 0);
        result.lineTo(40, 8);
        result.lineTo(40, 28);
        result.lineTo(21, 40);
        result.lineTo(0, 28);
        result.closePath();
        result.transform(AffineTransform.getTranslateInstance(-RADIUS, -RADIUS));
        return result;
    }

    /**
     * draws either the sprite for this object or text
     * @param g
     */
    @Override
    public void draw(Graphics2D g) {
        if (!pickedup)
        {
            sp.paint(g);
        }
        else
        {
            g.setFont(BasicView.FONT);
            g.setColor(BasicView.FONT_COLOUR);
            int fontW = g.getFontMetrics().stringWidth(id);
            g.drawString(id, (int) (position.x - fontW / 2), (int) position.y);
        }
    }
}
