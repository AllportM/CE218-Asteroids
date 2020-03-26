package View;

import javax.swing.*;
import java.awt.*;

public class Card extends JComponent {

    int barHeight, barWidth, innerHeight;

    public Card(int barHeight, int barWidh, int innerHeight)
    {
        setSize(new Dimension(400, 400));
        this.barHeight = barHeight;
        this.barWidth = barWidh;
        this.innerHeight = innerHeight;
    }

    @Override
    public void paintComponent(Graphics g)
    {
        Graphics2D g1 = (Graphics2D) g;
        // sets colour to blank and draws a rectangle of the size of this panel to ensure it gets
        // filled
        g.setColor(new Color(0,0,0, 0));
        g.fillRect(0,0, getWidth(),getHeight());

        g1.setColor(Color.gray);
        g1.fillRect(50, 350 - barHeight, barWidth, barHeight);

        // draws inner rect using difference of barheight and inner height to get starting point
        g1.setColor(Color.RED);
        g1.fillRect(50, 350 - barHeight +(barHeight- innerHeight), barWidth, innerHeight);

        // draws some text, using getwidth/2 to specify central point, and font width/2 for center of font
        g1.setColor(Color.blue);
        g.setFont(new Font("Bahnschrift Light", Font.BOLD, 20));
        String title = "This title sucks";
        int fontW = g.getFontMetrics().stringWidth(title);
        g.drawString(title, getWidth()/2 - fontW/2, 20);
    }
}

class Outer extends JFrame
{
    public Outer()
    {
        JPanel board = new JPanel();
        board.setLayout(new GridLayout(3, 3));
        for (int i = 0; i < 8; i++)
        {
            board.add(new Card(300, 40, (int) (Math.random() * 200 + 50)));
        }
        add(board);
        setSize(new Dimension(1200, 1200));
        setVisible(true);
        repaint();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new Outer();
    }
}
