package View;

import javax.swing.*;

import java.awt.*;

import static Model.Constants.FRAME_SIZE;

public class PanelHelperFuncts {
    public static JPanel getOverlayPanel()
    {
        JPanel overlapPanel = new JPanel();
        overlapPanel.setSize(FRAME_SIZE.width, FRAME_SIZE.height);
        overlapPanel.setLayout(new OverlayLayout(overlapPanel));
        return overlapPanel;
    }

    public static JPanel getOverlayContainer()
    {
        JPanel levelBack = new JPanel();
        levelBack.setSize(FRAME_SIZE.width, FRAME_SIZE.height);
        levelBack.setLayout(null);
        Color opacityCol = new Color(0, 0, 0, 200);
        levelBack.setBackground(opacityCol);
        return levelBack;
    }


}
