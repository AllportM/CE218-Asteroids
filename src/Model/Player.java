package Model;

import Controller.Controller;

public class Player {
    // multipliers for attributes effecting player's stats
    public static int radarRange, score;
    public static double health, shipSpeed, shipAcc, fireRate, turnResp;
    public Ship playerShip;

    public Player()
    {
        health = shipSpeed = shipAcc = fireRate = turnResp= 1.0;
        radarRange = 1;
        score = 0;
        health = 100;
    }

    public void setPlayerShip(Ship playerShip) {
        this.playerShip = playerShip;
    }
}