package Model;

import View.BasicView;
import View.ImgManag;
import View.SoundsManag;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

public class ItemPickup extends GameObject{
    public static final String SHIP_SPEED = "Speed Up", SHIP_ACC = "Acc Up", FIRE_RATE = "Fire Rate Up",
    HEAL = "Heal++", RADAR_RANGE = "Radar Up", HEALTH_UP = "Health Up", STEER_UP = "Steering Up";
    private String id;
    private static final int TEXT_TTL = 1000;
    public boolean pickedup = false;
    public long pickedUpTime;

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

    @Override
    public void update()
    {
        if (pickedup && System.currentTimeMillis() - pickedUpTime > TEXT_TTL)
            this.alive = false;
    }

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
