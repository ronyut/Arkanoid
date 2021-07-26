package arkanoid.sprites;

import arkanoid.Ass7Game;
import arkanoid.game.animation.GameLevel;
import arkanoid.geometry.Point;
import arkanoid.geometry.Rectangle;
import arkanoid.listeners.HitListener;
import arkanoid.listeners.HitNotifier;
import arkanoid.sprites.Fill.Fill;
import arkanoid.util.ColorEffects;
import arkanoid.util.Velocity;
import biuoop.DrawSurface;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;

/**
 * Class Name: Block.
 * <p>
 * Block class represents a collidable object. It is essentially consists of a rectangle.
 * This class can generate random blocks, calculate an object's new velocity after it has collided with it, etc.
 *
 * @author Rony Utevsky - ronyut@gmail.com - 319384657 - utevskr
 * @version 1.0.3
 * @since 11 June 2019
 */
public class Block implements Collidable, Sprite, HitNotifier {

    public static final int WINDOW_WIDTH = Ball.WINDOW_WIDTH;
    public static final int WINDOW_HEIGHT = Ball.WINDOW_HEIGHT;
    public static final Color OUTLINE_COLOR = Color.BLACK;
    public static final int COMING_FROM_ABOVE = Rectangle.COMING_FROM_ABOVE;
    public static final int COMING_FROM_BELOW = Rectangle.COMING_FROM_BELOW;
    public static final int COMING_FROM_LEFT = Rectangle.COMING_FROM_LEFT;
    public static final int COMING_FROM_RIGHT = Rectangle.COMING_FROM_RIGHT;
    public static final int BLOCK_LIVES = 1;
    public static final Color TEXT_COLOR = Color.BLACK;
    public static final int TEXT_OFFSET = 5;
    public static final String DEAD_BLOCK = "X";
    public static final boolean SHOULD_PRINT_LIVES = false;
    public static final String PATH = Ass7Game.PATH;
    public static final int BORDER_THICKNESS = GameLevel.BORDER_THICKNESS;

    private Rectangle rect;
    private boolean unremovable = false;
    private boolean isDancer = false;
    private int lives;
    private List<HitListener> hitListeners = new ArrayList<>();
    private boolean isBallKiller = false;
    private Color stroke;
    private List<Fill> fill = new ArrayList<>();
    private Image loadedImage;
    private int loadedImageIndex;

    /**
     * Constructor 1.
     *
     * @param upperLeft the upper-left point of the block
     * @param width     the width of the block
     * @param height    the height of the block
     * @param color     the color of the block
     * @param lives     life counter (how many times it can still be hit)
     */
    public Block(Point upperLeft, double width, double height, Color color, int lives) {
        this.rect = new Rectangle(upperLeft, width, height);
        this.lives = lives;
    }

    /**
     * Constructor 2.
     *
     * @param rect  the rectangle shape of the block
     * @param lives life counter (how many times it can still be hit)
     */
    public Block(Rectangle rect, int lives) {
        this.rect = rect;
        this.lives = lives;
    }

    /**
     * Constructor 3.
     *
     * @param upperLeft the upper-left point of the block
     * @param width     the width of the block
     * @param height    the height of the block
     * @param color     the color of the block
     */
    public Block(Point upperLeft, double width, double height, Color color) {
        this.rect = new Rectangle(upperLeft, width, height);
        this.unremovable = true;
        this.fill.add(new arkanoid.sprites.Fill.Color(color));
    }

    /**
     * Constructor 1.
     *
     * @param upperLeft the upper-left point of the block
     * @param width     the width of the block
     * @param height    the height of the block
     * @param fills     the fillings of the block
     * @param lives     life counter (how many times it can still be hit)
     */
    public Block(Point upperLeft, double width, double height, List<Fill> fills, int lives) {
        this.rect = new Rectangle(upperLeft, width, height);
        this.fill = fills;
        this.lives = lives;
    }

    /**
     * getCollisionRectangle: get the rectangle shape of the block.
     *
     * @return the rectangle shape of the block
     */
    public Rectangle getCollisionRectangle() {
        return this.rect;
    }

    /**
     * hit: Notify the object that we collided with it with a given velocity at some point on it.
     * Check which side of the object was hit, and return the new velocity expected after the hit (inversion of the
     * X or the Y axis' velocity).
     *
     * @param collisionPoint  the collision point
     * @param currentVelocity the current velocity of the ball
     * @param hitter          the ball
     * @return the new velocity expected after the hit (inversion of the X or the Y axis' velocity)
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        Velocity newVelocity = currentVelocity;
        double speedX = currentVelocity.getDX();
        double speedY = currentVelocity.getDY();

        // notify the listener about the hit
        this.notifyHit(hitter);

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
            newVelocity = currentVelocity.inverseSpeedY();
        }

        return newVelocity;
    }

    /**
     * drawOn: draw the block on a given surface and fill it with its color.
     *
     * @param surface the surface to draw the ball on
     */
    public void drawOn(DrawSurface surface) {

        int upperLeftX = (int) this.rect.getUpperLeft().getX();
        int upperLeftY = (int) this.rect.getUpperLeft().getY();
        int width = (int) this.rect.getWidth();
        int height = (int) this.rect.getHeight();

        // get the last fill
        Fill filling = this.fill.get(this.fill.size() - 1);

        // if it's color, fill the rectangle with color
        if (filling.isColor()) {
            surface.setColor(((arkanoid.sprites.Fill.Color) filling).getColor());
            surface.fillRectangle(upperLeftX, upperLeftY, width, height);

        } else {
            // otherwise, it's an image
            Image img = null;

            // if the image hasn't been loaded yet
            if (this.loadedImage == null || this.loadedImageIndex != this.lives) {
                try {
                    String filename = ((arkanoid.sprites.Fill.Image) filling).getImage();
                    img = ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream(PATH + filename));
                    this.loadedImage = img;
                    this.loadedImageIndex = this.lives;
                } catch (IOException e) {
                    // error while loading image
                    System.err.println(e.getMessage());
                    System.exit(-1);
                }
            } else {
                // use the already loaded image
                img = this.loadedImage;
            }

            // draw the image on a DrawSurface
            surface.drawImage(upperLeftX, upperLeftY, img);
        }

        // draw the outlines of the block
        if (this.stroke != null) {
            surface.setColor(this.stroke);
            surface.drawRectangle(upperLeftX, upperLeftY, width, height);
        }

        // print number of block lives only if it's removable
        if (!this.isUnremovable() && SHOULD_PRINT_LIVES) {
            // the font size of lives number
            double fontSize = (width < height) ? width * 0.75 : height * 0.75;
            // the number of lives to be printed
            String livesToPrint = (lives == 0) ? DEAD_BLOCK : Integer.toString(lives);
            // set the color of the text
            surface.setColor(TEXT_COLOR);
            // draw the text
            surface.drawText(upperLeftX + width / 2 - TEXT_OFFSET, upperLeftY + height / 2 + TEXT_OFFSET,
                    livesToPrint, (int) fontSize);
        }
    }

    /**
     * generateRandomBlock: generate a random block.
     *
     * @return a random block
     */
    public static Block generateRandomBlock() {
        Random rand = new Random();

        int upperLeftX = rand.nextInt(WINDOW_WIDTH);
        int upperLeftY = rand.nextInt(WINDOW_HEIGHT);
        int width = rand.nextInt(WINDOW_WIDTH - upperLeftX);
        int height = rand.nextInt(WINDOW_HEIGHT - upperLeftY);
        Color randColor = ColorEffects.getRandomColor();

        Block block = new Block(new Point(upperLeftX, upperLeftY), width, height, randColor, BLOCK_LIVES);
        return block;
    }

    /**
     * timePassed: tell the block that it should do something.
     */
    public void timePassed() {
        // make the blocks dance!
        if (!this.unremovable && this.isDancer) {
            double x = this.rect.getUpperLeft().getX();
            double y = this.rect.getUpperLeft().getY();
            double width = this.rect.getWidth();
            double height = this.rect.getHeight();

            Random rand = new Random();
            double change = rand.nextInt(3) - rand.nextInt(3);
            double newX = x + change;
            double newY = y + change;

            if (newX + width > WINDOW_WIDTH - BORDER_THICKNESS || newX < BORDER_THICKNESS) {
                newX = x - change;
            }

            if (newY + height > WINDOW_HEIGHT - BORDER_THICKNESS || newY < 2 * BORDER_THICKNESS) {
                newY = y - change;
            }

            this.rect.setUpperLeft(new Point(newX, newY));
        }
    }

    /**
     * addToGame: add the block to a gameLevel's sprites collection and to the collidables.
     *
     * @param gameLevel the gameLevel to add the block to
     */
    public void addToGame(GameLevel gameLevel) {
        gameLevel.addSprite(this);
        gameLevel.addCollidable(this);
    }

    /**
     * removeFromGame: remove the block from a gameLevel's sprites collection and collidables lists.
     *
     * @param gameLevel the gameLevel to remove the block from
     */
    public void removeFromGame(GameLevel gameLevel) {
        gameLevel.removeSprite(this);
        gameLevel.removeCollidable(this);
    }

    /**
     * addHitListener: Add hl as a listener to hit events.
     *
     * @param hl the HitListener that will receive notifications from the HitNotifier
     */
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    /**
     * addHitListener: Remove hl from the list of listeners to hit events.
     *
     * @param hl the HitListener that will stop receiving notifications from the HitNotifier
     */
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    /**
     * notifyHit: notify all listeners about a hit event.
     *
     * @param hitter the ball that hit
     */
    private void notifyHit(Ball hitter) {
        // make a copy of the hitListeners before iterating over them
        List<HitListener> listeners = new ArrayList<>(this.hitListeners);
        // notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

    /**
     * getHitPoints: get the number of lives left for the block.
     *
     * @return the number of lives left for the block
     */
    public int getHitPoints() {
        return this.lives;
    }

    /**
     * setHitPoints: set the lives number of the block.
     *
     * @param livesNum the new lives number of the block
     */
    public void setHitPoints(int livesNum) {
        this.lives = livesNum;
    }

    /**
     * isUnremovable: tell whether the block can be removed or not.
     *
     * @return whether the block can be removed or not
     */
    public boolean isUnremovable() {
        return this.unremovable;
    }

    /**
     * setUnremovable: set the removability of the block.
     *
     * @param isUn the removability of the block
     */
    public void setUnremovable(boolean isUn) {
        this.unremovable = isUn;
    }

    /**
     * isBallKiller: tell whether the block kills balls or not.
     *
     * @return whether the block kills balls or not
     */
    public boolean isBallKiller() {
        return this.isBallKiller;
    }

    /**
     * setBallKiller: set the lethality of the block.
     *
     * @param isKiller the lethality of the block
     */
    public void setBallKiller(boolean isKiller) {
        this.isBallKiller = isKiller;
    }

    /**
     * setStroke: sets the block's stroke color.
     *
     * @param color the stroke's color
     */
    public void setStroke(Color color) {
        this.stroke = color;
    }

    /**
     * addFill: adds a filling to the fill array.
     *
     * @param filling a new filling
     */
    public void addFill(Fill filling) {
        this.fill.add(filling);
    }

    /**
     * removeFill: removes the last filling from the fill array.
     */
    public void removeFill() {
        this.fill.remove(this.fill.size() - 1);
    }

    @Override
    public void letsDance() {
        this.isDancer = !this.isDancer;
    }

}
