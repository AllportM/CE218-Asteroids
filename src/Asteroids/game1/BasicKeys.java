package Asteroids.game1;

import Asteroids.utilities.Action;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class BasicKeys extends KeyAdapter implements BasicController {
    Action action;
    public BasicKeys()
    {
        action = new Action();
    }

    public Action action()
    {
        return action;
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        int key = e.getExtendedKeyCode();
        switch (key)
        {
            case KeyEvent.VK_UP:
                action.thrust = 1;
                break;
            case KeyEvent.VK_LEFT:
                action.turn = -1;
                break;
            case KeyEvent.VK_RIGHT:
                action.turn = 1;
                break;
            case KeyEvent.VK_DOWN:
                action.thrust = -1;
                break;
            case KeyEvent.VK_SPACE:
                action.shoot = true;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        int key = e.getExtendedKeyCode();
        switch (key)
        {
            case KeyEvent.VK_UP:
                action.thrust = 0;
                break;
            case KeyEvent.VK_LEFT:
                action.turn = 0;
                break;
            case KeyEvent.VK_RIGHT:
                action.turn = 0;
                break;
            case KeyEvent.VK_DOWN:
                action.thrust = 0;
                break;
            case KeyEvent.VK_SPACE:
                action.shoot = false;
                break;
        }
    }
}
