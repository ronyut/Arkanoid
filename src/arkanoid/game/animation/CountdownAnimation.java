package arkanoid.game.animation;

import arkanoid.game.SpriteCollection;
import biuoop.DrawSurface;
import biuoop.Sleeper;

import java.awt.Color;

/**
 * Class Name: CountdownAnimation.
 * <p>
 * This class represents a countdown before a round starts.
 *
 * @author Rony Utevsky - ronyut@gmail.com - 319384657 - utevskr
 * @version 1.0.0
 * @since 24 May 2019
 */
public class CountdownAnimation implements Animation {
    public static final int TEXT_X_OFFSET = -30;
    public static final int TEXT_SIZE = 100;

    private double numOfSeconds;
    private int countFrom;
    private int countFromOriginal;
    private SpriteCollection gameScreen;
    private Sleeper sleeper;
    private boolean stop;

    /**
     * Constructor.
     *
     * @param seconds   number of seconds to show the countdown before the round starts
     * @param startFrom the number to start the countdown from
     * @param sprites   the game sprites
     */
    public CountdownAnimation(double seconds, int startFrom, SpriteCollection sprites) {
        this.numOfSeconds = seconds;
        this.countFrom = startFrom;
        this.countFromOriginal = startFrom;
        this.gameScreen = sprites;
        this.stop = false;
        this.sleeper = new Sleeper();
    }

    /**
     * doOneFrame: handle the logic of a single frame in the game.
     *
     * @param d the surface to draw on
     */
    public void doOneFrame(DrawSurface d) {
        this.gameScreen.drawAllOn(d);

        String text = Integer.toString(this.countFrom);
        if (this.countFrom <= 0) {
            text = "Go!";
        }

        d.setColor(Color.BLACK);
        d.drawText(d.getWidth() / 2 + TEXT_X_OFFSET - 4, d.getHeight() / 2 - 1, text, TEXT_SIZE + 2);
        d.setColor(Color.YELLOW);
        d.drawText(d.getWidth() / 2 + TEXT_X_OFFSET, d.getHeight() / 2, text, TEXT_SIZE);

        this.sleeper.sleepFor(((long) (this.numOfSeconds * 1000) / (countFromOriginal + 1)));
        this.countFrom -= 1;

        // countdown is over
        if (this.countFrom < -1) {
            this.stop = true;
        }
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