package arkanoid.game;

import arkanoid.geometry.Point;
import arkanoid.sprites.Collidable;

/**
 * Class Name: CollisionInfo
 * <p>
 * CollisionInfo class represents an information block that hold the information of a collision.
 * This class can update and return its fields.
 *
 * @author Rony Utevsky - ronyut@gmail.com - 319384657 - utevskr
 * @version 1.0.2
 * @since 11 May 2019
 */
public class CollisionInfo {
    public static final double DEFAULT_NO_DISTANCE = -1.0;
    public static final boolean DEFAULT_IS_EDGE_HIT = false;

    private Point collisionPoint;
    private Collidable collisionObject;
    private double collisionDistance;
    private boolean isEdgeHit;

    /**
     * Constructor.
     *
     * @param collisionPoint    the collision point
     * @param collisionObject   the collidable object that had the collision
     * @param collisionDistance the distance from the center of the hitting object and the collidable object
     * @param isEdgeHit         whether or not the collision point is on an edge
     */
    public CollisionInfo(Point collisionPoint, Collidable collisionObject, double collisionDistance,
                         boolean isEdgeHit) {
        this.collisionPoint = collisionPoint;
        this.collisionObject = collisionObject;
        this.collisionDistance = collisionDistance;
        this.isEdgeHit = isEdgeHit;
    }

    /**
     * collisionPoint: get the point at which the collision occurs.
     *
     * @return the point at which the collision occurs.
     */
    public Point collisionPoint() {
        return this.collisionPoint;
    }

    /**
     * setCollisionPoint: update the point at which the collision occurs.
     *
     * @param p update the point at which the collision occurs.
     */
    public void setCollisionPoint(Point p) {
        this.collisionPoint = p;
    }

    /**
     * collisionObject: get the collidable object involved in the collision.
     *
     * @return the collidable object involved in the collision
     */
    public Collidable collisionObject() {
        return this.collisionObject;
    }

    /**
     * setCollisionObject: update the collidable object involved in the collision.
     *
     * @param c the collidable object involved in the collision.
     */
    public void setCollisionObject(Collidable c) {
        this.collisionObject = c;
    }

    /**
     * collisionDistance: get the distance between the collision point and the ball.
     *
     * @return the distance between the collision point and the ball
     */
    public double collisionDistance() {
        return this.collisionDistance;
    }

    /**
     * setCollisionDistance: update the distance between the collision point and the ball.
     *
     * @param distance the distance between the collision point and the ball
     */
    public void setCollisionDistance(double distance) {
        this.collisionDistance = distance;
    }

    /**
     * isEdgeHit: tell if the collision point is on an edge.
     *
     * @return whether or not the collision point is on an edge.
     */
    public boolean isEdgeHit() {
        return this.isEdgeHit;
    }

    /**
     * setIsEdgeHit: update whether or not the collision point is on an edge.
     *
     * @param is whether or not the collision point is on an edge.
     */
    public void setIsEdgeHit(boolean is) {
        this.isEdgeHit = is;
    }
}
