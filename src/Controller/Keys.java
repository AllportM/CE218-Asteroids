package Controller;

import View.SoundsManag;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Keys purpose is to provide an interface to the user and the ship by reacting to key presses in turn
 * changing the state of the associated Action object which controls the playership
 */
public class Keys extends KeyAdapter implements Controller {
    Action action; // sends actions to the playership

    /**
     * no arg constructor instantiates the action member
     */
    public Keys()
    {
        action = new Action();
    }

    @Override
    public Action action()
    {
        return action;
    }

    /**
     * detects what key has been pressed and changes the state of the action
     * @param e
     *     KeyEvent, the event triggering the update
     */
    @Override
    public void keyPressed(KeyEvent e)
    {
        int key = e.getExtendedKeyCode();
        switch (key)
        {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                SoundsManag.startThrust();
                action.thrust = 1;
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                action.turn = -1;
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                action.turn = 1;
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                SoundsManag.startThrust();
                action.thrust = -1;
                break;
            case KeyEvent.VK_SPACE:
            case KeyEvent.VK_ENTER:
                action.shoot = true;
                break;
        }
    }

    /**
     * detects which key has been released changing the state of the action
     * @param e
     */
    @Override
    public void keyReleased(KeyEvent e)
    {
        int key = e.getExtendedKeyCode();
        switch (key)
        {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                SoundsManag.stopThrust();
                action.thrust = 0;
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                action.turn = 0;
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                action.turn = 0;
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                SoundsManag.stopThrust();
                action.thrust = 0;
                break;
            case KeyEvent.VK_SPACE:
            case KeyEvent.VK_ENTER:
                action.shoot = false;
                break;
        }
    }
}
