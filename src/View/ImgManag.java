package View;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

/**
 * ImgManag's purpose is to have open image files for each game resource image and to provide functionality
 * to retrive references to those images via getimage
 */
public class ImgManag {
    final static String path = "resources/images/"; // uri to the resource path
    // list of all the images used, did have a more elegeant way of achieving this but it did not work
    // with built .jar files, so had to manually type in CSV string instead
    final static String files = "Ast1.png,Ast2.png,Ast3.png,Ast4.png,Ast5.png,Ast6.png,Ast7.png,AstMed.png" +
            ",AstSmall.png,asttext0.png,asttext1.png,asttext2.png,asttext3.png,asttext4.png,asttext5.png" +
            ",asttext6.png,asttext7.png,asttext8.png,BackgroundSmall3.png,EnemyrShip.png" +
            ",EnemyShipThrust.png,LaserBullet.png,LBullet1in.png,LButtet2in.png,pickup.png,PlayerShip.png" +
            ",ShipThrust.png,StarInner0.png,StarInner1.png,StarInner2.png,StarInner3.png,StarInner4.png,StarInner5.png" +
            ",StarInner6.png,StarMain.png,hud.png";
    static HashMap<String, BufferedImage> images = new HashMap<>(); // stored images

    // puts image objects of the loaded strings into list
    public static void init()
    {
        String[] filenames = files.split(",");
        for (String file : filenames)
        {
            try
            {
                images.put(file, ImageIO.read(SoundsManag.class.getResource(path + file)));
            } catch (IOException ignore){}

        }
    }

    /**
     * retrives image for a given filename
     * @param name
     * @return
     */
    public static BufferedImage getImage(String name)
    {
        return images.get(name);
    }
}
