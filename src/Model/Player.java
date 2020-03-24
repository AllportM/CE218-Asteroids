package Model;

public class Player {
    // multipliers for attributes effecting player's stats
    public static int radarRange = 10, score = 0, difficulty = 1;
    public static double health = 100, shipSpeed = 1.0, shipAcc = 1.0, fireRate = 1.0, turnResp = 1.0;
    public PlayerShip playerShip;

    public void setPlayerShip(PlayerShip playerShip) {
        this.playerShip = playerShip;
    }
}