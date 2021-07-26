package arkanoid.game.animation;

import arkanoid.geometry.Point;
import arkanoid.sprites.Block;
import biuoop.DrawSurface;

import java.awt.Color;

/**
 * Class Name: PauseScreen.
 * <p>
 * This class represents a paused game screen.
 *
 * @author Rony Utevsky - ronyut@gmail.com - 319384657 - utevskr
 * @version 1.0.0
 * @since 24 May 2019
 */
public class PauseScreen implements Animation {
    public static final int WINDOW_WIDTH = GameLevel.WINDOW_WIDTH;
    public static final int WINDOW_HEIGHT = GameLevel.WINDOW_HEIGHT;
    public static final String TEXT = "Game paused. Press space to resume.";
    public static final int TEXT_X_POS = 125;
    public static final int TEXT_SIZE = 32;

    private boolean stop;

    /**
     * Constructor.
     */
    public PauseScreen() {
        this.stop = false;
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

        d.setColor(Color.BLUE);
        d.drawText(TEXT_X_POS, d.getHeight() / 2, TEXT, TEXT_SIZE);
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