package Asteroids.utilities;

import java.awt.*;

/**
 * Refresh's purpose is to provide abstract generic methods to classes to implement draw and update methods
 */
public interface Refresh {
    void draw(Graphics2D g);
    void update();
}
