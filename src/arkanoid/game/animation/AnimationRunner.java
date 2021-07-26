package arkanoid.game.animation;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

/**
 * Class: AnimationRunner.
 * This class runs animations.
 *
 * @author Rony Utevsky - ronyut@gmail.com - 319384657 - utevskr
 * @version 1.0.0
 * @since 24 May 2019
 */
public class AnimationRunner {
    private GUI gui;
    private Sleeper sleeper;
    private int framesPerSecond;

    /**
     * Constructor.
     *
     * @param animationGui     the gui
     * @param animationSleeper the sleeper
     */
    public AnimationRunner(GUI animationGui, Sleeper animationSleeper) {
        this.gui = animationGui;
        this.sleeper = animationSleeper;
        this.framesPerSecond = GameLevel.FRAMES_PER_SECOND;
    }

    /**
     * run: runs the animation loop.
     *
     * @param animation the animation (game)
     */
    public void run(Animation animation) {
        int millisecondsPerFrame = 1000 / this.framesPerSecond;

        while (!animation.shouldStop()) {
            long startTime = System.currentTimeMillis();
            DrawSurface d = this.gui.getDrawSurface();

            // show one frame
            animation.doOneFrame(d);

            this.gui.show(d);
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                this.sleeper.sleepFor(milliSecondLeftToSleep);
            }

        }
    }

    /**
     * gui: return the gui.
     *
     * @return the gui
     */
    public GUI gui() {
        return this.gui;
    }
}
