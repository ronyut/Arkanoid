package arkanoid.geometry;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Name: Line.
 * <p>
 * This class represents a line in 2D space - it must consist of two edge points.
 * This class can tell the middle point of a line; whether two lines intersect and in which point; whether a point is
 * located on a line; it can tell the start&end points, length, slope, intercept of a line, etc.
 *
 * @author Rony Utevsky - ronyut@gmail.com - 319384657 - utevskr
 * @version 1.0.3
 * @since 27 May 2019
 */
public class Line {

    public static final double MIN_PRECISION = Point.POINT_PRECISION;
    public static final double UNINITIALIZED_VALUE = -1.0;

    // the start and end points of the line
    private Point start;
    private Point end;

    /**
     * Constructor #1.
     *
     * @param start the start point of the line
     * @param end   the end point of the line
     */
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Constructor #2.
     *
     * @param x1 the X value of the start point of the line
     * @param y1 the Y value of the start point of the line
     * @param x2 the X value of the end point of the line
     * @param y2 the Y value of the end point of the line
     */
    public Line(double x1, double y1, double x2, double y2) {
        // create new points and send them to constructor #1
        this(new Point(x1, y1), new Point(x2, y2));
    }

    /**
     * Constructor #3.
     *
     * @param x1 the X value of the start point of the line
     * @param y1 the Y value of the start point of the line
     * @param x2 the X value of the end point of the line
     * @param y2 the Y value of the end point of the line
     */
    public Line(int x1, int y1, int x2, int y2) {
        // convert the values to double and send them to constructor #2
        this((double) x1, (double) y1, (double) x2, (double) y2);
    }


    /**
     * length: returns the length of the line.
     *
     * @return the length of the line
     */
    public double length() {
        return Math.abs(start.distance(end));
    }

    /**
     * middle: calculates and return the middle point of the line.
     *
     * @return the middle point of the line
     */
    public Point middle() {
        double midX = (this.start.getX() + this.end.getX()) / 2;
        double midY = (this.start.getY() + this.end.getY()) / 2;
        return new Point(midX, midY);
    }

    /**
     * start: returns the start point of the line.
     *
     * @return the start point of the line
     */
    public Point start() {
        return this.start;
    }

    /**
     * end: returns the start point of the line.
     *
     * @return the end point of the line
     */
    public Point end() {
        return this.end;
    }

    /**
     * isIntersecting: tells whether the self line intersects with another line.
     *
     * @param line2 the line which is checked for intersecting with the self line
     * @return true if the lines intersect; false otherwise
     */
    public boolean isIntersecting(Line line2) {
        Point intersection = this.intersectionWith(line2);
        if (intersection != null) {
            return true;
        }

        return false;
    }

    /**
     * intersectionWith: returns the intersection point of the self line with another line, if they indeed intersect.
     *
     * @param line2 the line which is checked for intersecting with the self line
     * @return the intersection point if the lines intersect; null otherwise
     */
    public Point intersectionWith(Line line2) {

        // get the slopes of both lines
        double slope1 = this.getSlope();
        double slope2 = line2.getSlope();

        // get the intercepts of both lines
        double intercept1 = this.getIntercept();
        double intercept2 = line2.getIntercept();

        double intersectionX, intersectionY = 0;

        // get the common edge both lines share, if exists
        Point commonEdge = this.getCommonEdge(line2);

        //  if both lines share a common edge, return it
        if (commonEdge != null) {
            return commonEdge;

        } else if ((slope1 == Double.POSITIVE_INFINITY && slope2 == Double.POSITIVE_INFINITY)
                || Math.abs(slope1 - slope2) < MIN_PRECISION) {
            // if both lines have the same slope or if both lines' slopes are infinity
            return this.getCommonPointOfCongruentLines(line2);

        } else if (slope1 == Double.POSITIVE_INFINITY) {
            // if only the self line's slope is infinite
            // get the X coordinate of the intersection point, as it has the same value along the line
            intersectionX = this.start.getX();

        } else if (slope2 == Double.POSITIVE_INFINITY) {
            // if only the other line's slope is infinite
            // get the X coordinate of the intersection point, as it has the same value along the line
            intersectionX = line2.start.getX();

        } else {
            // calculate the X coordinate of the intersection point
            intersectionX = (intercept2 - intercept1) / (slope1 - slope2);
        }


        /**
         * calculate the Y coordinate of the intersection point
         */
        if (slope1 != Double.POSITIVE_INFINITY) {
            // if our line is not vertical
            intersectionY = slope1 * intersectionX + intercept1;

        } else {
            intersectionY = slope2 * intersectionX + intercept2;
        }


        // the intersection point
        Point intersection = new Point(intersectionX, intersectionY);

        // check if the intersection point is indeed located on both lines
        if (this.isPointOnLine(intersection) && line2.isPointOnLine(intersection)) {
            return intersection;
        } else {
            return null;
        }
    }

    /**
     * equals: checks if the self line and another line are identical.
     *
     * @param line2 the line against which we'll compare the self line
     * @return true if the lines are identical; false otherwise
     */
    public boolean equals(Line line2) {
        boolean isEqual = (this.start.equals(line2.start()) && this.end.equals(line2.end()))
                || (this.end.equals(line2.start()) && this.start.equals(line2.end()));
        return isEqual;
    }

    /**
     * isPointOnLine: check if a point is located on the self line.
     *
     * @param point the point we want to see if it's located on the self line
     * @return true if it's located on the self line; false otherwise
     */
    public boolean isPointOnLine(Point point) {

        /**
         * get the slope of the line which consists of:
         * (1) the point we are checking
         * (2) the start point of self line
         */
        double slope2 = point.getSlope(this.start);
        // the slope of self line
        double slope1 = this.getSlope();

        // if our point is also the start point, then it is definitely located on the line
        if (point.equals(this.start)) {
            return true;
        }

        /**
         * 1. if the slopes are different, then the point is definitely not located on the self line.
         * 2. if the slopes are infinite, we'd like to check if our point and the line's start point have equal X value
         */
        double diff = Math.abs(slope1 - slope2);

        if (slope1 == Double.POSITIVE_INFINITY && slope2 == Double.POSITIVE_INFINITY
                && point.getX() != this.start.getX()) {
            return false;
        } else if (diff > MIN_PRECISION) {
            return false;
        }


        /**
         * check if the point is located on the line, by comparing:
         * (1) the distance of the point from the middle of the line
         * (2) half the length of the line
         */
        double distanceFromMiddle = point.distance(this.middle());
        boolean isOn = ((this.length() / 2) >= distanceFromMiddle);

        // tell if the point is on the line or not
        return isOn;

    }

    /**
     * getSlope: get the slope of the line, i.e. get the m value of the linear function: y = mx + n
     *
     * @return the slope of self line
     */
    public double getSlope() {
        return this.start.getSlope(this.end);
    }

    /**
     * getIntercept: get the intercept of the line, i.e. get the n value of the linear function: y = mx + n
     *
     * @return the intercept of self line
     */
    public double getIntercept() {
        return this.start.getY() - this.getSlope() * this.start.getX();
    }

    /**
     * getCommonEdge: check if the self line and another line share a common edge.
     *
     * @param line2 the line we want to check if share a common edge with the self line
     * @return the common edge; null if not found
     */
    public Point getCommonEdge(Line line2) {
        if (this.equals(line2)) {
            // we can return either the start ot the end; it doesn't matter at all
            return this.start;
        } else if (this.start.equals(line2.start) || this.start.equals(line2.end)) { // check 1st edge
            return this.start;
        } else if (this.end.equals(line2.end) || this.end.equals(line2.start)) { // check 2nd edge
            return this.end;
        } else {
            // in case there's more than one common edge, return null
            return null;
        }
    }

    /**
     * toString: prints the basic description of the line: start, end and middle points.
     *
     * @return line's description
     */
    public String toString() {
        String output = "";
        output += "Point from (" + this.start.getX() + ", " + this.start.getY() + ") to ";
        output += "(" + this.end.getX() + ", " + this.end.getY() + ") - ";
        output += "Middle: (" + this.middle().getX() + ", " + this.middle().getY() + ")";
        return output;
    }

    /**
     * closestIntersectionsToStartOfLine: If this line does not intersect with the rectangle, return null.
     * Otherwise, return an array of the closest intersection points to the start of the line.
     *
     * @param rect the rectangle which might have intersection with our line
     * @return the closest intersection point(s) of the rectangle's sides with our line
     */
    public List<Point> closestIntersectionsToStartOfLine(Rectangle rect) {

        // get all intersection points of the line with the rectangle's sides
        List<Point> intersections = rect.intersectionPoints(this);

        // return point of min distance to our point
        return this.start().minDistanceFromPointArray(intersections);
    }

    /**
     * closestIntersectionToStartOfLine: If this line does not intersect with the rectangle, return null.
     * Otherwise, return the closest intersection point to the start of the line.
     *
     * @param rect the rectangle which might have intersection with our line
     * @return the closest intersection point of the rectangle's sides with our line
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {

        List<Point> closest = this.closestIntersectionsToStartOfLine(rect);

        if (closest.size() > 0) {
            return closest.get(0);
        }
        return null;
    }

    /**
     * getPointByX: retrieve the point on the line by X value.
     *
     * @param x the X value
     * @return the point on the line
     */
    public Point getPointByX(double x) {
        double y;
        double n = this.getIntercept();
        double m = this.getSlope();

        // if the line is vertical
        if (m == Double.POSITIVE_INFINITY) {
            y = this.start.getY();
        } else {
            y = m * x + n;
        }

        return new Point(x, y);
    }

    /**
     * getPointByX: retrieve the point on the line by Y value.
     *
     * @param y the Y value
     * @return the point on the line
     */
    public Point getPointByY(double y) {
        double x;
        double n;
        double m = this.getSlope();

        // if the line is horizontal
        if (m == 0) {
            // we can take any X value, so I chose the start point
            x = this.start.getX();
        } else if (m == Double.POSITIVE_INFINITY) {
            // if the line is vertical
            x = this.start.getX();
        } else {
            n = this.getIntercept();
            x = (y - n) / m;
        }

        return new Point(x, y);
    }

    /**
     * getCommonPointOfCongruentLines: When two lines are parallel and are located on the same line, we have this
     * method to check if these lines have common sections, and if they do, it returns a common point they share.
     *
     * @param line2 another line that might be congruent to our line
     * @return a common point both lines share (returns null if there's none)
     */
    public Point getCommonPointOfCongruentLines(Line line2) {
        // if these lines are vertical, make sure they're not parallel, OR:
        // if they're not vertical, make sure their intercepts are identical
        if (Math.abs(this.start.getX() - line2.start().getX()) <= MIN_PRECISION
                || Math.abs(this.getIntercept() - line2.getIntercept()) <= MIN_PRECISION) {
            // create a point array
            List<Point> points = new ArrayList<>();
            // populate the array with the edge points of both lines
            points.add(this.start);
            points.add(this.end);
            points.add(line2.start());
            points.add(line2.end());

            // find the highest and lowest points (i.e. relatively to the Y axis)
            Point highest = getHighestPoint(points);
            Point lowest = getLowestPoint(points);
            // calculate the distance between the highest and lowest points
            double maxDistance = highest.distance(lowest);
            // calculate the length of both lines
            double length1 = this.length();
            double length2 = line2.length();

            // if the lines have a gap between them
            if (maxDistance > length1 + length2) {
                return null;

            } else if (maxDistance == length1 + length2) {
                // if the lines overlap
                return this.getCommonEdge(line2);

            } else {
                // check which line has the lowest point
                if (this.isPointOnLine(lowest)) {
                    // get the other edge
                    if (lowest.equals(this.start)) {
                        return this.end;
                    } else {
                        return this.start;
                    }
                } else {
                    // get the other edge
                    if (lowest.equals(line2.start())) {
                        return line2.end();
                    } else {
                        return line2.start();
                    }
                }
            }

        }
        // otherwise, the lines obviously have no common points
        return null;
    }

    /**
     * getHighestPoint: get the highest point (relatively to the screen, i.e. with the lowest Y value) among some
     * points.
     *
     * @param points a list of points
     * @return the highest point (the highest point relatively to the screen, i.e. with the lowest Y value)
     */
    public Point getHighestPoint(List<Point> points) {
        // set initial values
        Point highest = null;
        double highestY = UNINITIALIZED_VALUE;

        for (Point p : points) {
            // check if the current point is higher
            if (highest == null || p.getY() < highestY) {
                highest = p;
                highestY = p.getY();
            }
        }

        return highest;
    }

    /**
     * getLowestPoint: get the lowest point (relatively to the screen, i.e. with the highest Y value) among some
     * points.
     *
     * @param points a list of points
     * @return the lowest point (relatively to the screen, i.e. with the highest Y value)
     */
    public Point getLowestPoint(List<Point> points) {
        // set initial values
        Point lowest = null;
        double lowestY = UNINITIALIZED_VALUE;

        for (Point p : points) {
            // check if the current point is lower
            if (lowest == null || p.getY() > lowestY) {
                lowest = p;
                lowestY = p.getY();
            }
        }

        return lowest;
    }

    /**
     * lineFromPntSlopeAndLen: generates a line using a start point, slope and length.
     *
     * @param p         start point
     * @param m         the slope
     * @param length    the length of the line
     * @return the end point
     */
    public static Line lineFromPntSlopeAndLen(Point p, double m, double length) {
        double x = p.getX() + length * (1 / Math.sqrt(1 + m * m));
        double y = p.getY() + length * (m / Math.sqrt(1 + m * m));
        return new Line(p, new Point(x, y));
    }
}
