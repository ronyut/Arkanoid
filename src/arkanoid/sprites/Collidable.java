package arkanoid.sprites;

import arkanoid.geometry.Point;
import arkanoid.geometry.Rectangle;
import arkanoid.util.Velocity;
import biuoop.DrawSurface;

/**
 * Interface Name: Collidable.
 * <p>
 * Collidable interface represents all objects that can be collided with.
 *
 * @author Rony Utevsky - ronyut@gmail.com - 319384657 - utevskr
 * @version 1.0.2
 * @since 11 May 2019
 */
public interface Collidable {
    /**
     * getCollisionRectangle: get the rectangle shape of the block.
     *
     * @return the rectangle shape of the block
     */
    Rectangle getCollisionRectangle();

    /**
     * Notifies the collidable object that we collided with it with a given velocity at some point on it.
     * Check which side of the collidable was hit, and return the new velocity expected after the hit (inversion of the
     * X or the Y axis' velocity).
     *
     * @param collisionPoint  the collision point
     * @param currentVelocity the current velocity of the ball
     * @param hitter          the ball that hits
     * @return the new velocity expected after the hit (inversion of the X or the Y axis' velocity)
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);

    /**
     * drawOn: draw the collidable on a given surface.
     *
     * @param surface the surface to draw the collidable on
     */
    void drawOn(DrawSurface surface);

    /**
     * isUnremovable: tell whether the block can be removed or not.
     *
     * @return whether the block can be removed or not
     */
    boolean isUnremovable();

    /**
     * setUnremovable: set the removability of the block.
     *
     * @param isUnremovable the removability of the block
     */
    void setUnremovable(boolean isUnremovable);

    /**
     * makes it dance.
     */
    void letsDance();
}
