package View;

import javax.sound.sampled.*;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Paths;

// SoundManager for Asteroids
/*
 * Referencing information
 *
 * All sound tracks attained were covered under the CC (Creative Commons) Licensing whereby the owners
 * give permission for the re-use/distribution of their works, therefor, no referencing is required
 */

/**
 * SoundManager's purpose is to provide functionality to play sound streams
 */
public class SoundsManag {

    static int nMeBullet = 0, pBullet = 0, nSmallAst = 0, nMedAst = 0, nLargeAst = 0;

    static boolean thrusting = false;

    // this may need modifying
    final static String path = "resources/sounds/";

    // note: having too many clips open may cause
    // "LineUnavailableException: No Free Voices"
    public final static Clip[] enemyBullets = new Clip[15];
    public final static Clip[] playerBullets = new Clip[30];
    public final static Clip[] smallAstExplosion = new Clip[4];
    public final static Clip[] mediumAstExplosion = new Clip[4];
    public final static Clip[] largeAstExplosion = new Clip[4];

    public final static Clip thud = getClip("thud");
    public final static Clip shipExp = getClip("shipExp");
    public final static Clip thrust = getClip("thrust");
    public static Clip music = getClip("music" + (int) (Math.random()*4));
    private static Clip ding = getClip("itemPackage" + (int) (Math.random() * 5));

    public static void changeMusic()
    {
        music = getClip("music" + (int) Math.random()*4);
    }

    public static void playDing()
    {
        ding.setFramePosition(0);
        ding.start();
        ding = getClip("itemPackage" + (int) (Math.random() * 5));
    }

    public static void enemyFire()
    {
        Clip clip = enemyBullets[nMeBullet];
        clip.setFramePosition(0);
        clip.start();
        nMeBullet = (nMeBullet + 1) % 15;
    }

    public static void playerFire() {
        // fire the n-th bullet and increment the index
        Clip clip = playerBullets[pBullet];
        clip.setFramePosition(0);
        clip.start();
        pBullet = (pBullet + 1) % playerBullets.length;
    }

    public static void smallExp() {
        // fire the n-th bullet and increment the index
        Clip clip = smallAstExplosion[nSmallAst];
        clip.setFramePosition(0);
        clip.start();
        nSmallAst = (nSmallAst + 1) % smallAstExplosion.length;
    }

    public static void medExp() {
        // fire the n-th bullet and increment the index
        Clip clip = mediumAstExplosion[nMedAst];
        clip.setFramePosition(0);
        clip.start();
        nMedAst = (nMedAst + 1) % mediumAstExplosion.length;
    }

    public static void largeExp() {
        // fire the n-th bullet and increment the index
        Clip clip = largeAstExplosion[nLargeAst];
        clip.setFramePosition(0);
        clip.start();
        nLargeAst = (nLargeAst + 1) % largeAstExplosion.length;
    }

    public static void thud() {
        play(thud);
    }

    public static void shipExp()
    {
        play(shipExp);
    }

    static {
        for (int i = 0; i < enemyBullets.length; i++)
            enemyBullets[i] = getClip("fire2");
    }

    static
    {
        for (int i = 0; i < playerBullets.length; i++)
            playerBullets[i] = getClip("playerShort");
    }

    static
    {
        for (int i = 0; i < smallAstExplosion.length; i++)
        {
            smallAstExplosion[i] = getClip("smallExp");
            mediumAstExplosion[i] = getClip("medExp");
            largeAstExplosion[i] = getClip("largeEx");
        }
    }

    public static void startThrust() {
        if (!thrusting) {
            thrust.loop(-1);
            thrusting = true;
        }
    }

    public static void stopThrust() {
        thrust.loop(0);
        thrusting = false;
    }

    public static void startMusic()
    {
        music.loop(-1);
    }

    public static void stopMusic()
    {
        music.loop(0);
        music.stop();
        changeMusic();
    }

    public static void play(Clip clip) {
        clip.setFramePosition(0);
        clip.start();
    }

    private static Clip getClip(String filename) {
        Clip clip = null;
        try {
            clip = AudioSystem.getClip();
            URL dir = SoundsManag.class.getResource(path + filename + ".wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(dir);
//            clip.open(audioInputStream);

            DataLine.Info info = new DataLine.Info(Clip.class, audioInputStream.getFormat());  // you might want to specify your own format
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(audioInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clip;
    }
}
