package Controller;

import View.SoundsManag;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Keys extends KeyAdapter implements Controller {
    Action action;
    public Keys()
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
