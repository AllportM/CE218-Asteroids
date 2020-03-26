package View;

import Controller.Game;
import Model.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

import static Model.Constants.*;

public class MainUI extends JFrame
{
    public final static int BUTTON_WIDTH = 200, BUTTON_HEIGHT = 50;
    public JEasyPanel gamePanel;
    Game game;
    JButton newGame;

    public MainUI()
    {
        game = new Game();
        game.view = this;
        addKeyListener(game.ctrl);
        BasicView view = new BasicView(game);
        JEasyPanel panel = new JEasyPanel(view);
        gamePanel = panel;
        setTitle("Ma18522 - Asteroids - CE218 Assignment");
        pack();
        this.setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setRenderingOptions();
        setPreferredSize(FRAME_SIZE);
        resetView();
        pack();
        repaint();
        newGame = new JButton("New Game");
        newGame.setFocusable(false);
        newGame.setSize(BUTTON_WIDTH, BUTTON_HEIGHT );
        newGame.setLocation(FRAME_WIDTH / 2 - BUTTON_WIDTH / 2,FRAME_HEIGHT / 2 - BUTTON_HEIGHT / 2);
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == newGame)
                {
                    newGame();
                }
            }
        });
        add(newGame);
        newLevel();
    }

    public void newGame()
    {
        gamePanel.removeAll();
        gamePanel.add(gamePanel.comp);
        newGame.setVisible(false);
        gamePanel.comp.setVisible(true);
        game.initGame();
        requestFocus();
        newLevel();
    }

    public void addButton()
    {
        newGame.setVisible(true);
        gamePanel.comp.setVisible(false);
        gamePanel.removeAll();
        gamePanel.setLayout(null);
        gamePanel.add(newGame);
        revalidate();
        repaint();
        pack();
    }

    public void newLevel()
    {
        resetView();
        setPreferredSize(FRAME_SIZE);
        JPanel overlapPanel = PanelHelperFuncts.getOverlayPanel();
        JPanel levelBack = PanelHelperFuncts.getOverlayContainer();

        // initializes and sets the inner JPanel holding new level text
        JPanel levelFront = new JPanel();
        JLabel levelText = new JLabel("Level " + game.player.difficulty);
        levelText.setForeground(BasicView.FONT_COLOUR);
        Font font  = new Font("Bahnschrift Light", Font.BOLD, 20 + 50);
        levelText.setFont(font);
        Dimension textSize = levelText.getPreferredSize();
        levelFront.setPreferredSize(textSize);
        levelFront.setBounds(FRAME_SIZE.width/2  - (textSize.width / 2), FRAME_SIZE.height/2 - (textSize.height / 2)
                , textSize.width, textSize.height);
        levelFront.add(levelText);
        Color opacityCol = new Color(0, 0, 0, 200);
        levelBack.add(levelFront);
        levelFront.setBackground(opacityCol);

        // adds to overlay
        overlapPanel.add(levelBack);
        overlapPanel.add(gamePanel);
        add(overlapPanel);
        game.newLevel();
        setVisible(true);

        TimerTask tillreset = new TimerTask()
        {
            @Override
            public void run()
            {
                resetView();
                game.isEnd = false;
                game.gameLoop();
            }
        };

        Timer timer = new Timer(true);
        timer.schedule(tillreset, 2000);
    }

    public void resetView()
    {
        getContentPane().removeAll();
        revalidate();
        setLayout(null);
        gamePanel.setLocation(0,0);
        gamePanel.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        add(gamePanel);
        setPreferredSize(FRAME_SIZE);
    }

    public void setRenderingOptions()
    {
        //         below may improve game graphics at later date
        Graphics2D g = (Graphics2D) getGraphics();
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_DITHERING,
                RenderingHints.VALUE_DITHER_ENABLE);
        g.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_LCD_CONTRAST, 100);
        g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
                RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
                RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
                RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
                RenderingHints.VALUE_STROKE_PURE);
    }
}
