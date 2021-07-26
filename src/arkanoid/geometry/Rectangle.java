package arkanoid.geometry;

import java.util.List;
import java.util.ArrayList;

/**
 * Class Name: Rectangle
 * <p>
 * Rectangle class represents a rectangle in 2D space that consists of 4 lines.
 * This class can find intersections points of a line with the rectangle, get a rectangle's sides, and tell on
 * which side of a rectangle a point is located.
 *
 * @author Rony Utevsky - ronyut@gmail.com - 319384657 - utevskr
 * @version 1.0.3
 * @since 16 June 2019
 */
public class Rectangle {
    public static final int RECT_SIDES_NUM = 4;
    public static final int COMING_FROM_ABOVE = 0;
    public static final int COMING_FROM_BELOW = 1;
    public static final int COMING_FROM_LEFT = 2;
    public static final int COMING_FROM_RIGHT = 3;

    private Point upperLeft;
    private double width;
    private double height;

    /**
     * Constructor.
     *
     * @param upperLeft the upper-left point at which the rectangle starts
     * @param width     the rectangle's width
     * @param height    the rectangle's height
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
    }

    /**
     * intersectionPoints: get the intersection points with a specified line.
     *
     * @param line a line that we want to check if it intersects with any of the sides of the rectangle
     * @return a (possibly empty) list of intersection points with a specified line.
     */
    public List<Point> intersectionPoints(Line line) {
        // initialize the intersections' list
        List<Point> intersections = new ArrayList<>();
        // get an array of the rectangle's sides
        Line[] rectSides = this.getRectSides();

        // check for each side if it has an intersection point with our line
        for (Line side : rectSides) {
            Point intersection = line.intersectionWith(side);
            // add the intersection point to the list, if it exists
            if (intersection != null) {
                intersections.add(intersection);
            }
        }

        // return the list
        return intersections;
    }

    /**
     * getRectSides: get an array of a rectangle's 4 sides.
     *
     * @return an array of the rectangle's sides
     */
    public Line[] getRectSides() {
        // initialize the array that will contain the rectangle's sides
        Line[] rectSides = new Line[RECT_SIDES_NUM];

        // simplify the expressions
        double rectX = this.getUpperLeft().getX();
        double rectY = this.getUpperLeft().getY();
        double rectH = this.getHeight();
        double rectW = this.getWidth();

        // create the lines of the rectangle
        Line rectSideTop = new Line(rectX, rectY, rectX + rectW, rectY);
        Line rectSideBottom = new Line(rectX, rectY + rectH, rectX + rectW, rectY + rectH);
        Line rectSideLeft = new Line(rectX, rectY, rectX, rectY + rectH);
        Line rectSideRight = new Line(rectX + rectW, rectY, rectX + rectW, rectY + rectH);

        // populate the array with the rectangle's sides
        rectSides[0] = rectSideTop;
        rectSides[1] = rectSideBottom;
        rectSides[2] = rectSideLeft;
        rectSides[3] = rectSideRight;

        return rectSides;
    }

    /**
     * getWidth: get the width of the rectangle.
     *
     * @return the width of the rectangle
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * getHeight: get the height of the rectangle.
     *
     * @return the height of the rectangle
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * getUpperLeft: get the upper-left point of the rectangle.
     *
     * @return the upper-left point of the rectangle
     */
    public Point getUpperLeft() {
        return this.upperLeft;
    }

    /**
     * pointOnSide: determine on which side of the rectangle the collision takes place.
     *
     * @param p the collision point
     * @return int the side that collides with the ball
     */
    public List<Integer> pointOnSide(Point p) {

        Line[] rectSides = this.getRectSides();
        List<Integer> pointOnSide = new ArrayList<>();

        // check for each side if the point is located on it
        for (int i = 0; i < rectSides.length; i++) {
            if (rectSides[i].isPointOnLine(p)) {
                pointOnSide.add(i);
            }
        }
        return pointOnSide;
    }

    /**
     * getSideName: get a side's name of the rectangle.
     *
     * @param sideNumber the number of the side
     * @return a string with the side's name
     */
    public static String getSideName(int sideNumber) {
        String output;

        if (sideNumber == COMING_FROM_ABOVE) {
            output = "top";
        } else if (sideNumber == COMING_FROM_BELOW) {
            output = "bottom";
        } else if (sideNumber == COMING_FROM_LEFT) {
            output = "left";
        } else {
            output = "right";
        }

        return output;
    }

    /**
     * setUpperLeft: update the rectangle's upper-left point.
     *
     * @param ul the rectangle's new upper-left point
     */
    public void setUpperLeft(Point ul) {
        this.upperLeft = ul;
    }

    /**
     * isEdgeHit: tell whether a line touches any of the rectangle's edges.
     *
     * @param line a line that may be touching any of the rectangle's edges
     * @return true if it does; false if not
     */
    public boolean isEdgeHit(Line line) {
        List<Point> intersections = line.closestIntersectionsToStartOfLine(this);

        if (intersections.size() > 1) {
            return true;
        }
        return false;
    }

}
