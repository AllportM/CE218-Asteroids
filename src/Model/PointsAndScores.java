package Model;

import java.util.Objects;

/* This class has been copied and modified from an assignment submitted by myself earlier thisyear
 * for CE203
 *
 * Reference for this is therefor myself
 */

/**
 * PlayerScore's purpose is to contain an object of players name, score, and time spent
 * This is the format in which files are stores, and objects are created from, and can also be stored in
 * TreeSets given comparable overriding
 */
public class PointsAndScores implements Comparable<PointsAndScores>{
    private int score; // score int
    private String name; // name of player
    private int time; // time in milliseconds spent making moves

    // default constructor
    PointsAndScores(String[] entity)
    {
        name = entity[0];
        score = Integer.parseInt(entity[1]);
        time = Integer.parseInt(entity[2]);
    }

    // constructor taking p1 as argument, calculates their score and creates object for it
    public PointsAndScores(Player pl)
    {
        name = pl.name;
        time = pl.timtaken;
        name = pl.name;
    }

    @Override
    public int compareTo(PointsAndScores o)
    {
        return (this.score - o.score > 0)? 1: (this.score - o.score) < 0? -1: this.name.compareTo(o.name);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(name, score, time);
    }

    @Override
    public boolean equals(Object o)
    {
        return (o instanceof PointsAndScores && ((PointsAndScores) o).name.equals(name) && ((PointsAndScores) o).score == score
                && ((PointsAndScores) o).time==time);
    }
}
