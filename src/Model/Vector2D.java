package Model;

import static Model.Constants.WORLD_HEIGHT;
import static Model.Constants.WORLD_WIDTH;

public class Vector2D {
    public double x, y;

    /**
     * Default no arg zero constructor
     */
    public Vector2D()
    {
        this.x = 0;
        this.y = 0;
    }

    /**
     * Double 2 arg constructor initializing x and y to args
     * @param x
     *      double, x value
     * @param y
     *      double, y value
     */
    public Vector2D(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     * Copy constructor
     * @param v
     *      Vector2D, object to copy instance variables from
     */
    public Vector2D(Vector2D v)
    {
        this.x = v.x;
        this.y = v.y;
    }

    /**
     * set's purpose is to update the x/y coordinates and return itself with them adjusted
     * @param x
     *      double, new x coordinat
     * @param y
     *      double, new y coordinate
     * @return
     *      this instance
     */
    public Vector2D set(double x, double y)
    {
        this.x = x;
        this.y = y;
        return this;
    }

    /**
     * Overloaded set method taking a Vector2D instance as argument to attain its x/y values
     * @param v
     *      Vector2D object to obtain x/y values
     * @return
     *      this instance
     */
    public Vector2D set(Vector2D v)
    {
        x = v.x;
        y = v.y;
        return this;
    }

    /**
     * Overrides the equals method returning true if argument object is equal to this instance object
     * @param o
     *      Object to be checked
     * @return
     *      boolean, true if equal false otherwise
     */
    @Override
    public boolean equals(Object o)
    {
        if (!(o instanceof Vector2D)) return false;
        Vector2D check = (Vector2D) o;
        return (check.x == this.x && check.y == this.y);
    }

    /**
     * Overrides the toString method
     * @return
     *      String, formatted string for this object
     */
    @Override
    public String toString()
    {
        return String.format("posX = %.2f, posY = %.2f", x, y);
    }

    /**
     * mag's purpose is to return the magnitude, or Hypotenuse, of this vector
     * @return
     *      double, hypotenuse/magnitude
     */
    public double mag()
    {
        return Math.hypot(x, y); // could also use sqrt(x*x + y*y)
    }

    /**
     * no arg angle returns the angle, or direction, of this vectors magnitude
     * @return
     *      double, angle in radians
     */
    public double angle()
    {
        return Math.atan2(y, x);
    }

    public double angle(Vector2D o)
    {
        double thisAngle = angle();
        double thatAngle = o.angle();
        double result = thatAngle - thisAngle;
        if (result < -Math.PI) result += 2 * Math.PI;
        if (result > Math.PI) result -= 2* Math.PI;
        return result;
    }

    /**
     * add's purpose is to add the horizontal and vertical components of o to this instances
     * and return this
     * @param o
     *      Vector2D, instance containing componenets to be added
     * @return
     *      Vector2D this
     */
    public Vector2D add(Vector2D o)
    {
        x += o.x;
        y += o.y;
        return this;
    }

    /**
     * Overloaded add's purpose is to add horizontal and vertical components from args to this
     * and return this
     * @param x
     *      double, x horizontal component value to be added
     * @param y
     *      double, y vertical component to be added
     * @return
     *      Vector2D this
     */
    public Vector2D add(double x, double y)
    {
        this.x += x;
        this.y += y;
        return this;
    }

    /**
     * addScaled's purpose add the product of a given vectors components by a given factor to itself
     * and return this
     * @param v
     *      Vector2D, initial vector
     * @param fac
     *      double, the factor to multiply the vectors components by
     * @return
     *      this, a copy with it's increased values
     */
    public Vector2D addScaled(Vector2D v, double fac)
    {
        x += v.x * fac;
        y += v.y * fac;
        return this;
    }

    /**
     * subtract's purpose is to deduct a given vectors linear components from itself
     * @param v
     *      Vector2D, vector with components to be deducted
     * @return
     *      Vector2D, this instance with deducted components
     */
    public Vector2D subtract(Vector2D v)
    {
        this.x -= v.x;
        this.y -= v.y;
        return this;
    }

    /**
     * Overloaded subtract's purpose is to deduct given horizonntal and vertical components from itself
     * and return this instance
     * @param x
     *      double, horizontal component to be deducted
     * @param y
     *      double, vertical component to be deducted
     * @return
     *      Vector2D, this instance with deducted components
     */
    public Vector2D subtract(double x, double y)
    {
        this.x -= x;
        this.y -= y;
        return this;
    }

    /**
     * mult's purpose is to multiply this instances horizontal and vertical components by given factor
     * @param fac
     *      double, factor for components to be multiplied by
     * @return
     *      Vector2D, this instance with multiplied components
     */
    public Vector2D mult(double fac)
    {
        x *= fac;
        y *= fac;
        return this;
    }

    /**
     * rotate's purpose is to update this vectors components given a rotation angle
     * @param angle
     *      double, angle to be rotated by in radians
     * @return
     *      Vector2D, this instance with updated components
     */
    public Vector2D rotate(double angle)
    {
        double tempX = x;
        x = x * Math.cos(angle) - y * Math.sin(angle);
        y = tempX * Math.sin(angle) + y * Math.cos(angle);
        return this;
    }

    /**
     * dot's purpose is to return the dot product of this and the given vector
     * @param v
     *      Vector2D, object containing components for dot multiplication
     * @return
     *      double, the product of the dot multiplication
     */
    public double dot(Vector2D v)
    {
        return x * v.x + y * v.y;
    }

    /**
     * dist's purpose is to calculate the length of the magnitude in different from a this vector from
     * a given vectors
     * @param v
     *      Vector2D, the vector for distance to be calculated
     * @return
     *      double, the distance between the two objects
     */
    public double dist(Vector2D v)
    {
        return Math.hypot(v.x - x, v.y - y);
    }

    public Vector2D proj(Vector2D d)
    {
        Vector2D result = new Vector2D(d);
        result.mult(this.dot(d));
        return result;
    }
    /**
     * dist's purpose is to calculate the length of the magnitude in different from a this vector from
     * a given vectors. In addition, calculates distance including factoring the world wrap
     * @param v
     *      Vector2D, the vector for distance to be calculated
     * @return
     *      double, the distance between the two objects
     */
    public double distExcWW(Vector2D v)
    {
        double dx = Math.abs(x - v.x);
        double dy = Math.abs(y - v.y);
        if (dx > WORLD_WIDTH / 2f)
        {
            dx = WORLD_WIDTH - dx;
        }
        if (dy > WORLD_HEIGHT / 2f)
        {
            dy = WORLD_HEIGHT - dy;
        }
        return Math.hypot(dx, dy);
    }

    /**
     * normalise's purpose is to normalise this vector such that the magnitude == 1
     * @return
     *      Vector2D, this instance with normalized components
     */
    public Vector2D normalise()
    {
        double angle = this.angle();
        x = Math.cos(angle) * 1;
        y = Math.sin(angle) * 1;
        return this;
    }

    // who knows this ones porpose
    public Vector2D wrap(double w, double h)
    {
        x = (x + w) % w;
        y = (y + h) % h;
        return this;
    }

    public Vector2D copy()
    {
        return new Vector2D(this);
    }

    public static Vector2D polar(double angle, double mag)
    {
        return new Vector2D(Math.cos(angle) * mag, Math.sin(angle) * mag);
    }
}
