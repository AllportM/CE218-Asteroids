package View;

import Controller.Game;
import Model.Player;
import Model.PointsAndScores;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import static Model.Constants.*;

public class MainUI extends JFrame
{
    public final static int BUTTON_WIDTH = 200, BUTTON_HEIGHT = 50;
    public JEasyPanel gamePanel;
    Game game;
    JButton newGame;
    JLayeredPane lp;
    JPanel newGamePanel;
    JLayeredPane newLevelLP;
    JLayeredPane newGameLP;
    JLayeredPane scoreLP;
    JLabel spaceBgLabel;

    public MainUI()
    {
        game = new Game();
        game.view = this;
        addKeyListener(game.ctrl);
        BasicView view = new BasicView(game);
        JEasyPanel panel = new JEasyPanel(view);
        gamePanel = panel;
        initLayout();
        newGame();
    }

    public void initLayout()
    {
        setTitle("Ma18522 - Asteroids - CE218 Assignment");
        pack();
        this.setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setRenderingOptions();
        setPreferredSize(FRAME_SIZE);
        setBGImage();
        setLayout(null);
    }

    public void setBGImage()
    {
        // creates background image as ImageIcon
        ImageIcon icon = new ImageIcon(ImgManag.getImage("bgingame.png"));
        spaceBgLabel = new JLabel() {
            @Override
            public void paintComponent(Graphics g) {
                g.drawImage(icon.getImage(), 0, 0, null);
                super.paintComponent(g);
            }
        };
        spaceBgLabel.setLocation(0,0);
        spaceBgLabel.setSize(FRAME_SIZE);
    }

    public void setNewGame()
    {
        newGameLP = new JLayeredPane();
        newGameLP.setSize(FRAME_SIZE);
        newGameLP.setLocation(0,0);
        // creates background image as ImageIcon
        ImageIcon icon = new ImageIcon(ImgManag.getImage("bgingame.png"));
        spaceBgLabel = new JLabel() {
            @Override
            public void paintComponent(Graphics g) {
                g.drawImage(icon.getImage(), 0, 0, null);
                super.paintComponent(g);
            }
        };
        newGameLP.setLayout(null); // relative position
        spaceBgLabel.setLocation(0,0);
        spaceBgLabel.setSize(FRAME_SIZE);

        //
        JTextField pname = new JTextField();
        pname.setForeground(Color.BLACK);
        pname.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        pname.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        pname.setLocation(FRAME_WIDTH / 2 - BUTTON_WIDTH / 2,FRAME_HEIGHT / 2 - BUTTON_HEIGHT + 10);

        // sets up newgame button
        newGame = new JButton("New Game");
        newGame.setFocusable(false);
        newGame.setSize(BUTTON_WIDTH, BUTTON_HEIGHT );
        newGame.setLocation(FRAME_WIDTH / 2 - BUTTON_WIDTH / 2 ,FRAME_HEIGHT / 2 + BUTTON_HEIGHT - 10);
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == newGame)
                {
                    if (pname.getText() != "")
                    {
                        Player.name = pname.getText();
                    }
                    game.initGame();
                    startNewLevel();
                }
            }
        });

        JLabel name = new JLabel("Enter your name");
        name.setSize(100,40);
        name.setForeground(BasicView.FONT_COLOUR);
        name.setBackground(new Color(0,0,0,0));
        name.setLocation(FRAME_WIDTH / 2 - BUTTON_WIDTH / 2 + 50,FRAME_HEIGHT / 2 - BUTTON_HEIGHT - 40);

        newGameLP.add(name, 0);

        newGameLP.add(spaceBgLabel, 1);
        newGameLP.add(newGame, 0);
        newGameLP.add(pname, 0);
        pname.requestFocus();
        newGame.requestFocus();
    }

    public void setNewLevelLP()
    {
        newLevelLP = new JLayeredPane();
        newLevelLP.setSize(FRAME_SIZE);
        newLevelLP.setLocation(0,0);

        JPanel textWrap = new JPanel();
        textWrap.setVisible(true);
        textWrap.setBackground(new Color(0,0,0,0));
        textWrap.setBounds(0,0,FRAME_WIDTH, FRAME_HEIGHT);


        // sets text to display
        JLabel levelText = new JLabel("Level " + game.player.difficulty);
        levelText.setForeground(BasicView.FONT_COLOUR);
        levelText.setBackground(new Color(0,0,0,0));
        Font font  = new Font("Bahnschrift Light", Font.BOLD, 20 + 50);
        levelText.setFont(font);
        Dimension textSize = levelText.getPreferredSize();
        levelText.setSize(textSize);
        levelText.setLocation(FRAME_WIDTH / 2 - textSize.width / 2, FRAME_HEIGHT / 2 - textSize.height / 2);
        newLevelLP.add(spaceBgLabel, 1);
        newLevelLP.add(levelText, 0);
    }

    /**
     * replaces the old score layered pane with new up having updated scores
     */
    public void endGameScreen()
    {
        getContentPane().removeAll();
        // inits score layer panel
        scoreLP = new JLayeredPane();
        scoreLP.setSize(FRAME_SIZE);
        scoreLP.setLocation(0,0);

        // inits new score wrapper JPanel
        JPanel scorespanel = new JPanel();
        scorespanel.setVisible(true);
        scorespanel.setBackground(new Color(0,0,0,0));
        scorespanel.setLayout(new BorderLayout());
        //populated score wrapper
        JLabel scores = new JLabel("High Scores", SwingConstants.CENTER);
        scores.setFont(BasicView.FONT);
        scores.setBackground(new Color(0,0,0,0));
        scores.setForeground(BasicView.FONT_COLOUR);

        //sets top 5 scores panel
        JPanel topScorePanel = new JPanel();
        GridBagLayout gbl = new GridBagLayout();
        topScorePanel.setLayout(gbl);
        topScorePanel.setBackground(new Color(0,0,0,0));
        ScoreHandler scoreHandler = new ScoreHandler("scores.txt");
        PointsAndScores playerScore = new PointsAndScores(game.player);
        scoreHandler.addScore(playerScore);
        scoreHandler.writeScores();
        JLabel name, time, score;
        int itCount = 0;
        class GBLAdder
        {
            private int y = 0;
            protected void addTo(JPanel parent, JLabel label1, JLabel label2, JLabel label3) {

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets =
                        new Insets(10,10,10,10);
                gbc.anchor = GridBagConstraints.WEST;
                gbc.gridx = 0;
                gbc.gridy = y;
                parent.add(label1, gbc);

                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.gridx++;
                gbc.weightx = 1;
                parent.add(label2, gbc);

                gbc.gridx++;
                gbc.weightx = 0;
                parent.add(label3, gbc);
                y++;
            }
        }
        GBLAdder gblAdd = new GBLAdder();
        if (scoreHandler.getScores().size() > 0)
        {
            int topScoresCount = Math.min(scoreHandler.getScores().size(), 5);
            for(Iterator<PointsAndScores> scoreIt = scoreHandler.getScores().descendingIterator(); scoreIt.hasNext() && itCount < topScoresCount;)
            {
                PointsAndScores nextScore = scoreIt.next();
                name = new JLabel(nextScore.getName());
                time = new JLabel(getClockFormat(nextScore.getTime()));
                score = new JLabel(String.valueOf(nextScore.getScore()));
                name.setFont(BasicView.FONT);
                name.setForeground(BasicView.FONT_COLOUR);
                Color transparent = new Color(0,0,0,0);
                name.setBackground(transparent);
                time.setForeground(BasicView.FONT_COLOUR);
                time.setFont(BasicView.FONT);
                time.setBackground(transparent);
                score.setForeground(BasicView.FONT_COLOUR);
                score.setFont(BasicView.FONT);
                score.setBackground(transparent);
                if (nextScore.equals(playerScore))
                {
                    Color topScoreCol = new Color(127, 67, 196);
                    name.setForeground(topScoreCol);
                    time.setForeground(topScoreCol);
                    score.setForeground(topScoreCol);
                }
                gblAdd.addTo(topScorePanel, name, time, score);
                itCount += 1;
            }
        }
        if (itCount < 5*3)
        {
            for (int i = 0; i < 5 - itCount; i++)
            {
                gblAdd.addTo(topScorePanel, new JLabel(), new JLabel(), new JLabel());
            }
        }
        scorespanel.add(scores, BorderLayout.NORTH);
        scorespanel.add(topScorePanel, BorderLayout.CENTER);
        Dimension gblSize = gbl.minimumLayoutSize(scorespanel);
        scorespanel.setBounds(FRAME_WIDTH / 2 - Math.max(gblSize.width/2, 250),FRAME_HEIGHT/2 - 150,
                Math.max(gblSize.width, 500),300);
        Border outer = BorderFactory.createMatteBorder(2, 2, 2, 2,
            Color.CYAN);
        Border padding = BorderFactory.createEmptyBorder(10,10,10,10);
        scorespanel.setBorder(new CompoundBorder(outer, padding));
        add(scorespanel);

        // adds to score lp
        scoreLP.add(scorespanel, 0);
        scoreLP.add(spaceBgLabel, 2);
        newGame.setLocation(FRAME_WIDTH/2 - newGame.getWidth() /2, FRAME_HEIGHT/2 + 200 + newGame.getHeight()/2);
        scoreLP.add(newGame, 1);
        add(scoreLP);
        pack();
        repaint();
    }

    /*
     * getClockForm's purpose is to format an integer of milliseconds to a readable time format
     */
    private String getClockFormat(int milliseconds)
    {
        int totSeconds = milliseconds / 1000;
        int hours = totSeconds / 3600;
        int minutes = (totSeconds % 3600) / 60;
        int seconds = (totSeconds % 3600) % 60;
        return ((hours > 0)? hours + " hrs, ": "0 hrs, ") + ((minutes > 0)? minutes + " mins, ": "0 mins, ")
                + seconds + " secs.";
    }

    public void startNewLevel()
    {
        getContentPane().removeAll();
        revalidate();
        setLayout(null);
        gamePanel.setLocation(0,0);
        gamePanel.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setPreferredSize(FRAME_SIZE);
        setNewLevelLP();
        lp = new JLayeredPane();
        setNewLevelLP();
        lp.add(newLevelLP, 0);
        lp.setSize(FRAME_SIZE);
        lp.setLocation(0,0);
        setPreferredSize(FRAME_SIZE);
        add(lp);
        pack();
        repaint();
        game.newLevel();
        requestFocus();


        TimerTask tillreset = new TimerTask()
        {
            @Override
            public void run()
            {
                getContentPane().removeAll();
                add(gamePanel);
                setPreferredSize(FRAME_SIZE);
                pack();
                repaint();
                game.isEnd = false;
                game.gameLoop();
            }
        };

        Timer timer = new Timer(true);
        timer.schedule(tillreset, 2000);
    }

    public void newGame()
    {
        game.initGame();
        setNewGame();
        add(newGameLP);
        pack();
        repaint();
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
