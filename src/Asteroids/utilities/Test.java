package Asteroids.utilities;

import Asteroids.game1.BasicKeys;
import Asteroids.game1.BasicShip;
import Asteroids.game1.Constants;

import javax.swing.*;
import java.awt.*;

import static Asteroids.game1.Constants.DELAY;

public class Test extends JComponent {
    RotatableImage rt = new RotatableImage("resources/ship1.gif");
    BasicKeys k = new BasicKeys();
    BasicShip ship = new BasicShip(k);
    public Test()
    {
        addKeyListener(k);
    }

    @Override
    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, getWidth(), getHeight());
        ship.draw(g2, this);
    }

    public void update()
    {
        ship.update();
    }

    @Override
    public Dimension getPreferredSize()
    {
        return Constants.FRAME_SIZE;
    }

    public static void main(String[] args) throws Exception{
        Test test = new Test();
        JEasyFrame frame = new JEasyFrame(test, "test");
        while (true)
        {
            test.update();
            frame.repaint();
            Thread.sleep(DELAY);
        }
    }
}
