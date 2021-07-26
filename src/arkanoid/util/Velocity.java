package arkanoid.util;

import arkanoid.geometry.Line;
import arkanoid.geometry.Point;
import arkanoid.sprites.Ball;

/**
 * Class Name: Velocity
 * <p>
 * Velocity class represent velocity of object in 2D space.
 * Its attributes specify the change in position on the `x` and the `y` axes.
 *
 * @author Rony Utevsky - ronyut@gmail.com - 319384657 - utevskr
 * @version 1.0.2
 * @since 11 May 2019
 */
public class Velocity {

    public static final int ANGLE_OFFSET = 90;
    public static final int MAX_ANGLE = Ball.MAX_ANGLE;

    // the change in position on `x` and `y` axes
    private double dx;
    private double dy;

    /**
     * Velocity: constructor.
     *
     * @param dx the change in position on `x` axis
     * @param dy the change in position on `y` axis
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * applyToPoint: Take a point with position (x,y) and return a new point with position (x+dx, y+dy).
     *
     * @param p the coords of the center of the ball
     * @return a new point with position (x+dx, y+dy)
     */
    public Point applyToPoint(Point p) {
        return new Point(this.dx + p.getX(), this.dy + p.getY());
    }

    /**
     * fromAngleAndSpeed: Convert speed & angle (i.e. vector) to x&y components, and return the velocity
     * (note: it could have been a constructor, but there's already a constructor that accepts:
     * double & double as parameters)
     *
     * @param angle the angle of movement
     * @param speed the ball's speed size
     * @return the ball's speed
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        // calculate the change in position on x&y axes
        double dx = speed * Math.sin(Math.toRadians(angle));
        double dy = speed * Math.cos(Math.toRadians(angle));

        if (angle % MAX_ANGLE > 0 && angle % MAX_ANGLE < 90) {
            dx = Math.abs(dx);
            dy = -Math.abs(dy);
        } else if (angle % MAX_ANGLE > 90 && angle % MAX_ANGLE < 180) {
            dx = Math.abs(dx);
            dy = Math.abs(dy);
        } else if (angle % MAX_ANGLE > 180 && angle % MAX_ANGLE < 270) {
            dx = -Math.abs(dx);
            dy = Math.abs(dy);
        } else {
            dx = -Math.abs(dx);
            dy = -Math.abs(dy);
        }

        return new Velocity(dx, dy);
    }

    /**
     * getDX: return the delta x.
     *
     * @return delta x
     */
    public double getDX() {
        return this.dx;
    }

    /**
     * getDX: return the delta y.
     *
     * @return delta y
     */
    public double getDY() {
        return this.dy;
    }

    /**
     * setDX: set the delta x.
     *
     * @param deltaX the new delta x
     */
    public void setDX(double deltaX) {
        this.dx = deltaX;
    }

    /**
     * setDY: set the delta y.
     *
     * @param deltaY the new delta y
     */
    public void setDY(double deltaY) {
        this.dy = deltaY;
    }

    /**
     * toString: prints the basic description of the speed: delta of x and y.
     *
     * @return speed's description
     */
    public String toString() {
        String output = "";
        output += "dx: " + this.dx + " | ";
        output += "dy: " + this.dy;
        return output;
    }

    /**
     * trajectoryToVelocity: convert trajectory to velocity.
     *
     * @param trajectory the trajectory from the ball's center point to the next point it will land on in the
     *                   next move
     * @return the velocity
     */
    public static Velocity trajectoryToVelocity(Line trajectory) {
        Point p1, p2;
        p1 = trajectory.start();
        p2 = trajectory.end();

        double dx, dy;
        dx = p2.getX() - p1.getX();
        dy = p2.getY() - p1.getY();

        Velocity speed = new Velocity(dx, dy);

        return speed;
    }

    /**
     * inverseSpeedX: inverse the ball's speed on X axis and give it some random change.
     *
     * @return the velocity with inversion in the X axis
     */
    public Velocity inverseSpeedX() {
        return new Velocity(this.getDX() * -1, this.getDY());
    }

    /**
     * inverseSpeedY: inverse the ball's speed on Y axis and give it some random change.
     *
     * @return the velocity with inversion in the Y axis
     */
    public Velocity inverseSpeedY() {
        return new Velocity(this.getDX(), this.getDY() * -1);
    }

    /**
     * getAngle: get the angle of the velocity.
     *
     * @return the angle of the velocity
     */
    public double getAngle() {
        double angle;

        if (dy == 0) {
            if (dx >= 0) {
                angle = 90;
            } else {
                angle = 270;
            }
            return angle;
        }

        angle = Math.atan(dx / dy);
        angle = Math.toDegrees(angle) + ANGLE_OFFSET;

        if (dx >= 0 && dy > 0) {
            angle = 180 - angle;
        } else if (dx <= 0 && dy < 0) {
            angle = 180 + angle;
        } else if (dx <= 0 && dy > 0) {
            angle = -angle;
        }

        return angle;
    }

    /**
     * getAbsSpeed: get the absolute speed.
     *
     * @return the absolute speed
     */
    public double getAbsSpeed() {
        double speed = Math.sqrt(dx * dx + dy * dy);
        return speed;
    }
}
