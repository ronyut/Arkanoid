package arkanoid.sprites;

import arkanoid.game.CollisionInfo;
import arkanoid.game.animation.GameLevel;
import arkanoid.game.GameEnvironment;
import arkanoid.geometry.Line;
import arkanoid.geometry.Point;
import arkanoid.geometry.Rectangle;
import arkanoid.util.ColorEffects;
import arkanoid.util.Velocity;
import biuoop.DrawSurface;

import java.util.concurrent.ThreadLocalRandom;
import java.util.List;
import java.awt.Color;

/**
 * Class Name: Ball
 * <p>
 * Ball class represents a circle in 2D space - it has color, radius, center point and speed.
 * This class can make a ball move inside a board (or strictly speaking, it just changes its center point every
 * time), create random balls, etc.
 *
 * @author Rony Utevsky - ronyut@gmail.com - 319384657 - utevskr
 * @version 1.0.2
 * @since 11 May 2019
 */
public class Ball implements Sprite {

    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 600;
    public static final int MAX_ANGLE = 360;
    public static final int MAX_SPEED = 3;
    public static final int MIN_SPEED = 1;
    public static final int SLOWEST_BALL_MIN_SIZE = 50;
    public static final int DEFAULT_ANGLE = 45;
    public static final Color BALL_OUTLINE = Color.BLACK;

    private Point center;
    private int radius;
    private Color color;
    private Velocity speed;
    private Line frame;
    private GameEnvironment environment;
    private boolean hasOutline = false;

    /**
     * Ball: constructor #1.
     *
     * @param center      the initial center point of the ball
     * @param radius      the radius of the ball
     * @param color       the color of the ball
     * @param environment the game environment
     * @param hasOutline  whether the ball has outline or not
     */
    public Ball(Point center, int radius, Color color, GameEnvironment environment, boolean hasOutline) {
        this.center = center;
        this.radius = radius;
        this.color = color;
        this.speed = Velocity.fromAngleAndSpeed(0, 0);
        this.frame = new Line(new Point(0, 0), new Point(WINDOW_WIDTH, WINDOW_HEIGHT));
        this.environment = environment;
        this.hasOutline = hasOutline;
    }

    /**
     * Ball: constructor #2.
     *
     * @param x           the X value of the initial center point of the ball
     * @param y           the Y value of the initial center point of the ball
     * @param radius      the radius of the ball
     * @param color       the color of the ball
     * @param environment the game environment
     */
    public Ball(int x, int y, int radius, Color color, GameEnvironment environment) {
        // create a center point and send the parameters to constructor #1
        this(new Point((double) x, (double) y), radius, color, environment, false);
    }

    /**
     * Ball: constructor #3.
     *
     * @param x           the X value of the initial center point of the ball
     * @param y           the Y value of the initial center point of the ball
     * @param radius      the radius of the ball
     * @param color       the color of the ball
     * @param angle       the angle for the ball's movement
     * @param speed       the ball's speed
     * @param environment the game environment
     */
    public Ball(int x, int y, int radius, Color color, int angle, int speed, GameEnvironment environment) {
        this(x, y, radius, color, environment);
        this.speed = Velocity.fromAngleAndSpeed(angle, speed);
    }

    /**
     * Ball: constructor #4.
     *
     * @param center the initial center point of the ball
     * @param radius the radius of the ball
     * @param color  the color of the ball
     */
    public Ball(Point center, int radius, Color color) {
        this.center = center;
        this.radius = radius;
        this.color = color;
        this.frame = new Line(new Point(0, 0), new Point(WINDOW_WIDTH, WINDOW_HEIGHT));
    }

    /**
     * getCenter: get the center point of the ball.
     *
     * @return the center point of the ball.
     */
    public Point getCenter() {
        return this.center;
    }

    /**
     * getX: get the X value of the center point of self ball.
     *
     * @return the X value of the center point of self ball
     */
    public double getX() {
        return this.center.getX();
    }

    /**
     * getY: get the Y value of the center point of self ball.
     *
     * @return the Y value of the center point of self ball
     */
    public double getY() {
        return this.center.getY();
    }

    /**
     * getSize: get the radius of self ball.
     *
     * @return the radius of self ball
     */
    public int getSize() {
        return this.radius;
    }

    /**
     * getColor: get the color of self ball.
     *
     * @return the color of self ball
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * drawOn: draw the ball on a given surface and fill it with its color.
     *
     * @param surface the surface to draw the ball on
     */
    public void drawOn(DrawSurface surface) {
        // fill the ball with its color
        surface.setColor(this.color);
        surface.fillCircle((int) this.getX(), (int) this.getY(), this.getSize());

        // draw a circle around the ball?
        if (this.hasOutline) {
            surface.setColor(BALL_OUTLINE);
            surface.drawCircle((int) this.getX(), (int) this.getY(), this.getSize());
        }
    }

    /**
     * setVelocity: Set new velocity for the ball.
     *
     * @param velocity the new speed
     */
    public void setVelocity(Velocity velocity) {
        this.speed = velocity;
    }


    /**
     * setVelocity: Set new velocity for the ball using delta x and delta y.
     *
     * @param dx delta x
     * @param dy delta y
     */
    public void setVelocity(double dx, double dy) {
        new Velocity(dx, dy);
    }

    /**
     * getVelocity: Get new velocity of the ball.
     *
     * @return the ball's speed
     */
    public Velocity getVelocity() {
        return this.speed;
    }

    /**
     * moveOneStep: Make the ball move one step forward.
     */
    public void moveOneStep() {

        // set default values to the X and Y coords (as if no collision is going to occur)
        double posX = this.getX();
        double posY = this.getY();

        double speedX = this.speed.getDX();
        double speedY = this.speed.getDY();

        // the potential destination point of the ball in the next move
        Point destination = new Point(posX + speedX, posY + speedY);
        // build the trajectory from the ball to the potential destination point
        Line trajectory = new Line(this.center, destination);
        // get the collision info
        CollisionInfo colInfo = this.environment.getClosestCollision(trajectory);
        // the collision point
        Point collisionPoint = colInfo.collisionPoint();
        // the new speed
        Velocity newSpeed = this.speed;

        // if there's a collision going to happen
        if (collisionPoint != null) {

            // get the rectangle shape
            Rectangle rect = colInfo.collisionObject().getCollisionRectangle();

            // determine from which direction the ball is going to hit the shape (i.e. on which side of the rectangle
            // the collision will take place)
            List<Integer> collidesOn = rect.pointOnSide(collisionPoint);

            // if it's a collision with a block's edge then inverse the speed in X&Y axes
            if (colInfo.isEdgeHit() && colInfo.collisionObject() instanceof Block) {
                newSpeed = newSpeed.inverseSpeedX();
                newSpeed = newSpeed.inverseSpeedY();
            } else {
                // otherwise, fetch the new velocity via "hit" method
                newSpeed = colInfo.collisionObject().hit(this, collisionPoint, this.speed);
            }

            // avoid IndexOutOfBoundsException error
            if (collidesOn.isEmpty()) {
                this.setVelocity(newSpeed);
                return;
            }

            // find the sides of the rectangle where the collision takes place
            int collidesOn1 = collidesOn.get(0), collidesOn2;
            collidesOn2 = (collidesOn.size() > 1) ? collidesOn.get(1) : collidesOn.get(0);

            // we will attach the ball to the point right before it collides
            Point attachedCenter;

            // if the ball is going to collide from the right or left
            if (collidesOn2 == Rectangle.COMING_FROM_RIGHT && speedX <= 0
                    || collidesOn2 == Rectangle.COMING_FROM_LEFT && speedX >= 0) {
                // calculate the new point to move to ball to
                posX = collisionPoint.getX() - this.getSize() * Math.signum(speedX);
                attachedCenter = trajectory.getPointByX(posX);
                posY = attachedCenter.getY() - speedY;
                posX = attachedCenter.getX() - speedX;

                /**
                 * Special case:
                 * if the speed on X axis is zero, this means the ball is moving vertically.
                 * Thus we subtract 2 from the collision side in order to convert "right" to "below",
                 * or "left" to "above".
                 * This will let us in the next if statement, that checks if the ball is going to collide from the left
                 * or right.
                 */
                if (speedX == 0) {
                    collidesOn1 = collidesOn2 - 2;
                }
            }

            // if the ball is going to collide from below or above
            if (collidesOn1 == Rectangle.COMING_FROM_BELOW && speedY < 0
                    || collidesOn1 == Rectangle.COMING_FROM_ABOVE && speedY > 0) {
                // calculate the new point to move to ball to
                posY = collisionPoint.getY() - this.getSize() * Math.signum(speedY);
                attachedCenter = trajectory.getPointByY(posY);
                posX = attachedCenter.getX() - speedX;
                posY = attachedCenter.getY() - speedY;
            }

        }

        // the point from which the ball will move one step
        Point normalizedCenter = new Point(posX, posY);

        // move the ball one step
        this.center = this.speed.applyToPoint(normalizedCenter);

        // set the new speed
        this.setVelocity(newSpeed);
    }

    /**
     * createRandomBallWithSpeed: create a random moving ball, assuming the borders are the window borders.
     *
     * @param ballSize    the ball's radius
     * @param environment the game environment
     * @return a random moving ball, assuming the borders are the window borders
     */
    public static Ball createRandomBallWithSpeed(int ballSize, GameEnvironment environment) {
        return createRandomBallWithSpeed(new Point(0, 0), new Point(WINDOW_WIDTH, WINDOW_HEIGHT),
                ballSize, environment);
    }

    /**
     * createRandomBallWithSpeed: create a random moving ball inside a frame.
     *
     * @param topLeftCorner     the frame's top left corner
     * @param rightBottomCorner the frame's right bottom corner
     * @param ballSize          the ball's radius
     * @param environment       the game environment
     * @return a random moving ball inside the given frame
     */
    public static Ball createRandomBallWithSpeed(Point topLeftCorner, Point rightBottomCorner, int ballSize,
                                                 GameEnvironment environment) {
        // create a random-number generator
        ThreadLocalRandom rand = ThreadLocalRandom.current();

        // get random coordinates for the ball's starting point
        int randomPosX = rand.nextInt((int) Math.round(topLeftCorner.getX()) + ballSize,
                (int) Math.round(rightBottomCorner.getX()) - ballSize);
        int randomPosY = rand.nextInt((int) Math.round(topLeftCorner.getY()) + ballSize,
                (int) Math.round(rightBottomCorner.getY()) - ballSize);

        // get random color for the ball
        Color randomColor = ColorEffects.getRandomColor();
        // create the ball
        Ball newBall = new Ball(randomPosX, randomPosY, ballSize, randomColor, environment);

        // calculate the ball's speed via its mass
        int speed = getSpeedByMass(ballSize);
        // get a random direction (angle)
        int randomAngle = rand.nextInt(MAX_ANGLE);

        // set the velocity
        Velocity v = Velocity.fromAngleAndSpeed(randomAngle, speed);
        newBall.setVelocity(v);
        // set the boundaries for the ball
        newBall.setFrame(topLeftCorner, rightBottomCorner);

        return newBall;
    }

    /**
     * getSpeedByMass: given a ball's mass, calculate its speed.
     * The larger the ball, the slower it will be.
     *
     * @param mass the ball's radius (mass)
     * @return the speed
     */
    public static int getSpeedByMass(int mass) {
        double speed;

        if (mass > SLOWEST_BALL_MIN_SIZE) {
            speed = (double) MIN_SPEED;
        } else {
            double subtract = mass * ((double) MAX_SPEED / (double) SLOWEST_BALL_MIN_SIZE);
            speed = MAX_SPEED - subtract + 2;
        }

        return (int) speed;
    }

    /**
     * setFrame: set boundaries for the ball.
     *
     * @param topLeft     the frame's top left corner
     * @param rightBottom the frame's right bottom corner
     */
    public void setFrame(Point topLeft, Point rightBottom) {
        this.frame = new Line(topLeft, rightBottom);
    }

    /**
     * timePassed: tell the ball that it should move now.
     */
    public void timePassed() {
        this.moveOneStep();
    }

    /**
     * addToGame: add the ball to a gameLevel's sprites collection.
     *
     * @param gameLevel the gameLevel to add the ball to
     */
    public void addToGame(GameLevel gameLevel) {
        gameLevel.addSprite(this);
    }

    /**
     * setColor: update the ball's color.
     *
     * @param newColor the new color
     */
    public void setColor(Color newColor) {
        this.color = newColor;
    }

    /**
     * removeFromGame: remove the ball from a gameLevel's sprites collection.
     *
     * @param gameLevel the gameLevel to remove the ball from
     */
    public void removeFromGame(GameLevel gameLevel) {
        gameLevel.removeSprite(this);
    }
}
