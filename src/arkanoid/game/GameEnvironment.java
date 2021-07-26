package arkanoid.game;

import arkanoid.geometry.Line;
import arkanoid.geometry.Point;
import arkanoid.geometry.Rectangle;
import arkanoid.sprites.Collidable;
import biuoop.DrawSurface;
import java.util.List;
import java.util.ArrayList;

/**
 * Class Name: GameEnvironment.
 * <p>
 * GameEnvironment class represents a game's environment that holds all the important information about the game, such
 * as the ball, the collidable parts, etc.
 * This class can add collidables to the game, calculate the next collision of the ball and so on.
 *
 * @author Rony Utevsky - ronyut@gmail.com - 319384657 - utevskr
 * @version 1.0.2
 * @since 11 May 2019
 */
public class GameEnvironment {
    // the list of collidable objects
    private List<Collidable> collidables;

    /**
     * Constructor.
     */
    public GameEnvironment() {
        this.collidables = new ArrayList<>();
    }

    /**
     * addCollidable: add a collidable object to the game environment.
     *
     * @param c the new collidable to be added
     */
    public void addCollidable(Collidable c) {
        this.collidables.add(c);
    }

    /**
     * removeCollidable: remove a collidable object from the game environment.
     *
     * @param c the new collidable to be added
     */
    public void removeCollidable(Collidable c) {
        this.collidables.remove(c);
    }

    /**
     * getClosestCollision: Assume an object is moving from line.start() to line.end().
     * If this object is going to collide with any of the collidables in this collection, return null.
     * Else, return the information about the closest collision that is going to occur.
     *
     * @param trajectory the line that goes from the center of the ball to the next point it potentially can land on
     *                   in the next move
     * @return the information about the collision
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        // a flag that indicates that a collision was found
        boolean flag = false;

        // set initial values for collision info
        CollisionInfo colInfo = new CollisionInfo(null, null,
                CollisionInfo.DEFAULT_NO_DISTANCE, CollisionInfo.DEFAULT_IS_EDGE_HIT);

        // iterate through all collidable objects to find out which the ball will collide with
        for (Collidable c : collidables) {

            Rectangle rect = c.getCollisionRectangle();
            // get the point of intersection (i.e. collision)
            Point intersection = trajectory.closestIntersectionToStartOfLine(rect);
            boolean isEdgeHit = rect.isEdgeHit(trajectory);

            // if there's no collision then move on to check the next collidable object
            if (intersection == null) {
                continue;
            }

            // calculate the distance from the trajectory's start point to the intersection
            double distance = intersection.distance(trajectory.start());

            // check if we found a closer collision
            if (colInfo.collisionDistance() == CollisionInfo.DEFAULT_NO_DISTANCE
                    || distance < colInfo.collisionDistance()) {
                // update the collision point, shape and distance
                colInfo.setCollisionPoint(intersection);
                colInfo.setCollisionObject(c);
                colInfo.setCollisionDistance(distance);
                colInfo.setIsEdgeHit(isEdgeHit);

                // if another collidable collides with the ball (i.e. 2 or more collidables has the same distance from
                // the ball), it obviously means that the ball hits the edge of two or more collidable
                if (flag) {
                    // for some reason it doesn't work when colliding within a terrace ...
                    // colInfo.setIsEdgeHit(true);
                    flag = true;
                } else {
                    flag = true;
                }
            }
        }

        return colInfo;
    }

    /**
     * getCollidables: get all the collidables.
     *
     * @return all the collidables
     */
    public List<Collidable> getCollidables() {
        return this.collidables;
    }

    /**
     * getUnremovableCollidables: get all the unremovable collidables.
     *
     * @return all the unremovable collidables
     */
    public List<Collidable> getUnremovableCollidables() {
        List<Collidable> unremovables = new ArrayList<>();

        for (int i = 0; i < this.collidables.size(); i++) {
            if (this.collidables.get(i).isUnremovable()) {
                unremovables.add(this.collidables.get(i));
            }
        }

        return unremovables;
    }

    /**
     * drawAllOn: call drawOn for all collidables.
     *
     * @param surface the surface
     */
    public void drawAllOn(DrawSurface surface) {
        for (Collidable c : this.collidables) {
            c.drawOn(surface);
        }
    }

}