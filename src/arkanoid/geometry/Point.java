package arkanoid.geometry;

import biuoop.DrawSurface;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Class Name: Point
 * <p>
 * Point class represents a point in 2D space - it has X and Y coordinates.
 * This class can help measure distance between two points, calculate the slope of a line using two points, tell if two
 * points are identical, etc.
 *
 * @author Rony Utevsky - ronyut@gmail.com - 319384657 - utevskr
 * @version 1.0.3
 * @since 12 June 2019
 */
public class Point {
    public static final double POINT_PRECISION = 0.0000001;
    public static final double MIN_DEFAULT_DISTANCE = -1.0;
    public static final Color POINT_COLOR = Color.YELLOW;
    public static final int POINT_SIZE = 3;

    // the x and y values of the point
    private double x;
    private double y;

    /**
     * Point: constructor #1.
     *
     * @param x the X coordinate of the point to be constructed
     * @param y the Y coordinate of the point to be constructed
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Point: constructor #2.
     *
     * @param x the X coordinate of the point to be constructed
     * @param y the Y coordinate of the point to be constructed
     */
    public Point(int x, int y) {
        // convert to double and send the values to constructor #1
        this((double) x, (double) y);
    }

    /**
     * distance: measures the distance between some point and self point.
     *
     * @param p the point to which we will measure the distance from self point
     * @return the distance
     */
    public double distance(Point p) {
        double distance;

        // calculate the distance: (x1 - x2)^2 - (y1 - y2)^2
        distance = Math.pow(this.x - p.getX(), 2) + Math.pow(this.y - p.getY(), 2);
        distance = Math.sqrt(distance); // perform square root

        return distance;
    }

    /**
     * equals: tell whether this point and another point are identical.
     *
     * @param p the point against which we'll compare the self point
     * @return true if they are identical; false if not
     */
    public boolean equals(Point p) {
        boolean isEqual = Math.abs(this.x - p.getX()) < POINT_PRECISION
                && Math.abs(this.y - p.getY()) < POINT_PRECISION;
        return isEqual;
    }

    /**
     * getX: get the X value of the self point.
     * WARNING: Do NOT round the return value, as it will cause unexpected behaviour
     *
     * @return the X value of the self point
     */
    public double getX() {
        return this.x;
    }

    /**
     * getY: get the Y value of the self point.
     * WARNING: Do NOT round the return value, as it will cause unexpected behaviour
     *
     * @return the Y value of the self point
     */
    public double getY() {
        return this.y;
    }

    /**
     * getSlope: calculate the slope of the line through which the self point and another one pass.
     *
     * @param point2 the other point on the line whose slope we desire to calculate
     * @return the slope of the line through which both points pass
     */
    public double getSlope(Point point2) {
        double slope;

        // if both points' X values are equal, then it's an edge case and the slope is infinity (we check it as we
        // mustn't divide by 0)
        if (Math.abs(point2.getX() - this.x) < POINT_PRECISION) {
            slope = Double.POSITIVE_INFINITY;
        } else {
            slope = (point2.getY() - this.y) / (point2.getX() - this.x);
        }

        return slope;
    }

    /**
     * toString: prints the basic description of the point: X and Y coordinates.
     *
     * @return point's description
     */
    public String toString() {
        String output;
        output = "Point: (" + this.getX() + ", " + this.getY() + ")";
        return output;
    }

    /**
     * minDistanceFromPointArray: calculates the minimal distance between our point and the points in the array, and
     * return the point(s) in the array that is/are the closest to our point.
     *
     * @param pointArr an array of points
     * @return the point(s) in the array that is/are the closest to our point
     */
    public List<Point> minDistanceFromPointArray(List<Point> pointArr) {
        // assign initial values
        double minDistance = MIN_DEFAULT_DISTANCE;
        List<Point> closestPoint = new ArrayList<>();

        for (Point p : pointArr) {
            // skip null points
            if (p == null) {
                continue;
            }

            // calculate the distance between our point and the current point in array
            double distance = this.distance(p);
            // if the current point is closer than the current closest point or if this is the first point we are
            // checking then consider this point as the closest
            if (distance < minDistance || closestPoint.size() == 0) {
                minDistance = distance;

                // update if exists
                if (closestPoint.size() > 0) {
                    closestPoint.set(0, p);
                } else {
                    // create new 0 index
                    closestPoint.add(p);
                }

            } else if (Math.abs(distance - minDistance) < POINT_PRECISION) {
                // if another point has the same distance as the current closest point, add it too
                closestPoint.add(p);
            }
        }

        return closestPoint;
    }

    /**
     * drawOn: draw the point on a given surface and fill it with color.
     *
     * @param surface the surface to draw the point on
     */
    public void drawOn(DrawSurface surface) {
        // fill the point with color
        surface.setColor(POINT_COLOR);
        surface.fillCircle((int) this.getX(), (int) this.getY(), POINT_SIZE);
    }

    /**
     * setX: set the X value of the point.
     *
     * @param xVal the X value of the point
     */
    public void setX(double xVal) {
        this.x = xVal;
    }

    /**
     * setY: set the Y value of the point.
     *
     * @param yVal the Y value of the point
     */
    public void setY(double yVal) {
        this.y = yVal;
    }

}

