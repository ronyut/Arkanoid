package arkanoid.sprites;

import arkanoid.game.animation.GameLevel;
import arkanoid.geometry.Point;
import arkanoid.geometry.Rectangle;
import arkanoid.util.Velocity;
import biuoop.KeyboardSensor;
import biuoop.DrawSurface;

import java.awt.Color;
import java.util.List;

/**
 * Class Name: Paddle.
 * <p>
 * Paddle class represents a paddle in the game. It implements the collidable interface.
 * This class can be hit by other objects, be moved right and left on the screen, etc.
 *
 * @author Rony Utevsky - ronyut@gmail.com - 319384657 - utevskr
 * @version 1.0.2
 * @since 11 May 2019
 */
public class Paddle implements Sprite, Collidable {
    public static final int WINDOW_WIDTH = Ball.WINDOW_WIDTH;
    public static final int WINDOW_HEIGHT = Ball.WINDOW_HEIGHT;
    public static final int COMING_FROM_ABOVE = Rectangle.COMING_FROM_ABOVE;
    public static final int COMING_FROM_BELOW = Rectangle.COMING_FROM_BELOW;
    public static final int COMING_FROM_LEFT = Rectangle.COMING_FROM_LEFT;
    public static final int COMING_FROM_RIGHT = Rectangle.COMING_FROM_RIGHT;
    public static final int BORDER_THICKNESS = GameLevel.BORDER_THICKNESS;
    public static final Color OUTLINE_COLOR = Block.OUTLINE_COLOR;
    public static final int PADDLE_REGIONS = 5;
    public static final int PADDLE_REGION_1_ANGLE = 60;
    public static final int PADDLE_REGIONS_ANGLE_DIFF = 30;
    public static final int PADDLE_DEFAULT_WIDTH = 80;
    public static final int PADDLE_DEFAULT_HEIGHT = 20;

    private biuoop.KeyboardSensor keyboard;
    private Rectangle rect;
    private Velocity hitterVelocity;
    private int paddleSpeed;
    private Color color;
    private boolean verticalMovement = false;

    /**
     * Constructor.
     *
     * @param keyboard the keyboard sensor object
     * @param rect     the rectangle shape of the paddle
     * @param speed    the paddle's speed
     * @param color    the paddle's color
     */
    public Paddle(KeyboardSensor keyboard, Rectangle rect, int speed, Color color) {
        this.keyboard = keyboard;
        this.rect = rect;
        this.paddleSpeed = speed;
        this.color = color;
    }

    /**
     * moveLeft: move the paddle to the left.
     */
    public void moveLeft() {
        Point upperLeft = this.rect.getUpperLeft();

        // check if the paddle goes beyond the right border
        if (upperLeft.getX() - this.paddleSpeed < BORDER_THICKNESS) {
            upperLeft = new Point(BORDER_THICKNESS, upperLeft.getY());
        } else {
            upperLeft = new Point(upperLeft.getX() - this.paddleSpeed, upperLeft.getY());
        }

        this.rect.setUpperLeft(upperLeft);
    }

    /**
     * moveRight: move the paddle to the right.
     */
    public void moveRight() {
        Point upperLeft = this.rect.getUpperLeft();
        double width = this.rect.getWidth();

        // if the paddle goes beyond the right border, attach it to the border
        if (upperLeft.getX() + width + this.paddleSpeed > WINDOW_WIDTH - BORDER_THICKNESS) {
            upperLeft = new Point(WINDOW_WIDTH - BORDER_THICKNESS - width, upperLeft.getY());
        } else {
            upperLeft = new Point(upperLeft.getX() + this.paddleSpeed, upperLeft.getY());
        }

        this.rect.setUpperLeft(upperLeft);
    }

    /**
     * moveDown: move the paddle down.
     */
    public void moveDown() {
        Point upperLeft = this.rect.getUpperLeft();
        double height = this.rect.getHeight();

        // check if the paddle goes beyond the bottom border
        if (upperLeft.getY() + height + this.paddleSpeed > WINDOW_HEIGHT - BORDER_THICKNESS) {
            upperLeft = new Point(upperLeft.getX(), WINDOW_HEIGHT - BORDER_THICKNESS - height);
        } else {
            upperLeft = new Point(upperLeft.getX(), upperLeft.getY() + this.paddleSpeed);
        }

        this.rect.setUpperLeft(upperLeft);
    }

    /**
     * moveUp: move the paddle up.
     */
    public void moveUp() {
        Point upperLeft = this.rect.getUpperLeft();

        // check if the paddle goes beyond the upper border
        if (upperLeft.getY() - this.paddleSpeed < 2 * BORDER_THICKNESS) {
            upperLeft = new Point(upperLeft.getX(), 2 * BORDER_THICKNESS);
        } else {
            upperLeft = new Point(upperLeft.getX(), upperLeft.getY() - this.paddleSpeed);
        }

        this.rect.setUpperLeft(upperLeft);
    }

    /**
     * timePassed: check if the paddle should move to the left/right, according to the key that has been pressed.
     */
    public void timePassed() {
        if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            moveRight();
        } else if (keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            moveLeft();
        } else if (keyboard.isPressed(KeyboardSensor.UP_KEY) && this.verticalMovement) {
            moveUp();
        } else if (keyboard.isPressed(KeyboardSensor.DOWN_KEY) && this.verticalMovement) {
            moveDown();
        } else if (keyboard.isPressed("v")) {
            // Easter egg: enable vertical movement of paddle!
            this.verticalMovement = true;
        }
    }

    /**
     * drawOn: draw the paddle on the screen.
     *
     * @param surface the surface to draw on
     */
    public void drawOn(DrawSurface surface) {

        int upperLeftX = (int) this.rect.getUpperLeft().getX();
        int upperLeftY = (int) this.rect.getUpperLeft().getY();
        int width = (int) this.rect.getWidth();
        int height = (int) this.rect.getHeight();

        // fill the rectangle with its color
        surface.setColor(this.color);
        surface.fillRectangle(upperLeftX, upperLeftY, width, height);
        // draw the outlines
        surface.setColor(OUTLINE_COLOR);
        surface.drawRectangle(upperLeftX, upperLeftY, width, height);
    }

    /**
     * getCollisionRectangle: return the rectangle shape of the paddle.
     *
     * @return the rectangle shape of the paddle
     */
    public Rectangle getCollisionRectangle() {
        return this.rect;
    }

    /**
     * hit: Notify the object that we collided with it with a given velocity at some point on it.
     * check which region of the paddle was hit, and return a velocity accordingly.
     *
     * @param collisionPoint  the collision point
     * @param currentVelocity the current velocity of the ball
     * @param hitter          the ball
     * @return the new velocity expected after the hit
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        Velocity newVelocity = currentVelocity;
        double speedX = currentVelocity.getDX();
        double speedY = currentVelocity.getDY();

        // if velocity of ball has already been assigned
        if (this.hitterVelocity != null) {
            newVelocity = hitterVelocity;
            this.hitterVelocity = null;
            return newVelocity;
        }

        List<Integer> collisionSide = this.rect.pointOnSide(collisionPoint);
        int collidesOn1 = collisionSide.get(0), collidesOn2;
        collidesOn2 = (collisionSide.size() > 1) ? collisionSide.get(1) : collisionSide.get(0);

        // if the ball is going to collide from below or above
        if ((collidesOn2 == COMING_FROM_RIGHT && speedX <= 0) || (collidesOn2 == COMING_FROM_LEFT && speedX >= 0)) {
            newVelocity = currentVelocity.inverseSpeedX();

            /**
             * Special case:
             * if the speed on X axis is zero, this means the ball is moving vertically.
             * Thus we subtract 2 from the collision side in order to convert "right" to "below",
             * or "left" to "above".
             * This will let us in the next if statement, that checks if the ball is going to collide from the left
             * or right.
             */
            if (currentVelocity.getDX() == 0) {
                collidesOn1 = collidesOn2 - 2;
            }
        }

        // if the ball is going to collide from the left or right
        if ((collidesOn1 == COMING_FROM_BELOW && speedY <= 0) || (collidesOn1 == COMING_FROM_ABOVE && speedY >= 0)) {
            double changeInAngle = this.getAngleByPointOnPaddle(collisionPoint);
            double newAngle = changeInAngle;
            newVelocity = Velocity.fromAngleAndSpeed(newAngle, currentVelocity.getAbsSpeed());
        }

        return newVelocity;
    }

    /**
     * addToGame: add this paddle to the game.
     *
     * @param g the game to add the paddle to
     */
    public void addToGame(GameLevel g) {
        g.addCollidable(this);
        g.addSprite(this);
    }

    /**
     * getAngleByPointOnPaddle: calculate the angle that should be assigned to the ball according to the region the
     * collision occurred on the paddle.
     * <p>
     * WARNING: The angles in this method will only work if the paddle is at the BOTTOM! (and not at the top e.g.)
     *
     * @param p the collision point
     * @return the new angle (-60 to +60)
     */
    public double getAngleByPointOnPaddle(Point p) {
        // X value of the point
        double x = p.getX();
        double width = this.rect.getWidth();
        double upperLeftX = this.rect.getUpperLeft().getX();
        double newAngle = PADDLE_REGION_1_ANGLE;

        for (double i = PADDLE_REGIONS - 1; i >= 0; i--) {
            // check if the collision point is on a specific region on the paddle
            if (x >= upperLeftX + width * (i / PADDLE_REGIONS)) {
                break;
            }
            newAngle -= PADDLE_REGIONS_ANGLE_DIFF;
        }

        return newAngle;
    }

    /**
     * isUnremovable: tell whether the paddle can be removed or not.
     *
     * @return whether the paddle can be removed or not
     */
    public boolean isUnremovable() {
        return true;
    }

    /**
     * setUnremovable: set the removability of the paddle.
     *
     * @param isUnremovable the removability of the block
     */
    public void setUnremovable(boolean isUnremovable) {
        // do nothing
    }

    @Override
    public void letsDance() {
        // nothing
    }

}