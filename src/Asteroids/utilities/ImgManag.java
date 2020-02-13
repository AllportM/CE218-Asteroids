package Asteroids.utilities;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ImgManag {
    final static String path = "resources/";
    final static String[] ext = {".jpg", ".png"};
    static HashMap<String, BufferedImage> images = new HashMap<>();


    public static void init()
    {
        File dir = new File(path);
        loadImages(dir.list());
    }

    public static void loadImages(String[] filenames)
    {
        for (String file : filenames)
        {
            if (file.contains(ext[0]) || file.contains(ext[1]))
            {
                try
                {
                    images.put(file, ImageIO.read(new File(path + file)));
                } catch (IOException ignore){}
            }
        }
    }

    public static BufferedImage getImage(String name)
    {
        return images.get(name);
    }
}
