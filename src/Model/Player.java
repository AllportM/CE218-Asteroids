package Model;

public class Player {
    // multipliers for attributes effecting player's stats
    public static int radarRange, score;
    public static double health, shipSpeed, shipAcc, fireRate, turnResp;
    public PlayerShip playerShip;

    public Player()
    {
        health = shipSpeed = shipAcc = fireRate = turnResp= 1.0;
        radarRange = 10;
        score = 0;
        health = 100;
    }

    public void setPlayerShip(PlayerShip playerShip) {
        this.playerShip = playerShip;
    }
}