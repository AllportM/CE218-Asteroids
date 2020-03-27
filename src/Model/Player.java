package Model;

/**
 * Player's purpose is to hold player stat variables acessable throughout the game statically
 */
public class Player {
    // multipliers for attributes effecting player's stats
    public static int radarRange = 10, score = 0, difficulty = 1, health = 100, maxHealth = 100;
    public static double shipSpeed = 1.0, shipAcc = 1.0, fireRate = 1.0, turnResp = 1.0;
    public PlayerShip playerShip;
    public String name = "";
    public int timtaken = 0;

    public void setPlayerShip(PlayerShip playerShip) {
        this.playerShip = playerShip;
    }

    /**
     * resets stats back to default having been changed after item pickups in previous games
     */
    public void resetPlayer()
    {
        shipSpeed = shipAcc = fireRate = turnResp = 1.0;
        radarRange = 10;
        score = 0;
        difficulty = 1;
        health = maxHealth = 100;
    }
}