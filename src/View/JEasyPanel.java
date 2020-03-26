package View;

import javax.swing.*;
import java.awt.*;

public class JEasyPanel extends JPanel {
    public Component comp;

    public JEasyPanel(Component comp)
    {
        this.comp = comp;
        add(BorderLayout.CENTER, comp);
    }
}

