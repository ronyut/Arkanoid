package arkanoid.game.animation;

import arkanoid.geometry.Point;
import arkanoid.sprites.Block;
import arkanoid.util.Counter;
import biuoop.DrawSurface;
import java.awt.Color;

/**
 * Class Name: EndScreen.
 * <p>
 * This class represents the screen at end game.
 *
 * @author Rony Utevsky - ronyut@gmail.com - 319384657 - utevskr
 * @version 1.0.0
 * @since 26 May 2019
 */
public class EndScreen implements Animation {
    public static final int WINDOW_WIDTH = GameLevel.WINDOW_WIDTH;
    public static final int WINDOW_HEIGHT = GameLevel.WINDOW_HEIGHT;
    public static final String WINNER_TEXT = "You Win! Your score is ";
    public static final String LOSER_TEXT = "Game Over. Your score is ";
    public static final String WTF_TEXT = "Did you even play? Your score is ";
    public static final int TEXT_SIZE = 32;

    private Counter lives;
    private Counter score;
    private boolean stop;

    /**
     * Constructor.
     *
     * @param lives the lives counter
     * @param score the score counter
     */
    public EndScreen(Counter lives, Counter score) {
        this.stop = false;
        this.lives = lives;
        this.score = score;
    }

    /**
     * doOneFrame: handle the logic of a single frame in the game.
     *
     * @param d the surface to draw on
     */
    public void doOneFrame(DrawSurface d) {
        Block bg = new Block(new Point(0, 0), WINDOW_WIDTH, WINDOW_HEIGHT, Color.MAGENTA);
        bg.addFill(new arkanoid.sprites.Fill.Image("background_images/xp.jpg"));
        bg.drawOn(d);

        int xPos = d.getWidth() / 2 - 200;
        String text = WINNER_TEXT;
        // if the player lost the game
        if (this.lives.getValue() == 0) {
            text = LOSER_TEXT;
        }

        // if the score is zero
        if (this.score.getValue() == 0) {
            text = WTF_TEXT;
            xPos = d.getWidth() / 5;
        }

        d.drawText(xPos, d.getHeight() / 2, text + this.score.getValue(), TEXT_SIZE);
    }

    /**
     * shouldStop: tells if the animation should stop.
     *
     * @return whether the animation should stop or not.
     */
    public boolean shouldStop() {
        return this.stop;
    }

    /**
     * shouldStop: tell the animation to stop.
     *
     * @param shouldStop whether the animation should stop or not.
     */
    public void shouldStop(boolean shouldStop) {
        this.stop = shouldStop;
    }
}