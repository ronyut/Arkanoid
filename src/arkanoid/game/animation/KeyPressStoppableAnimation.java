package arkanoid.game.animation;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * Class Name: KeyPressStoppableAnimation.
 * <p>
 * This class represents animations that can be stopped when a certain key is pressed.
 *
 * @author Rony Utevsky - ronyut@gmail.com - 319384657 - utevskr
 * @version 1.0.0
 * @since 26 May 2019
 */
public class KeyPressStoppableAnimation implements Animation {
    private KeyboardSensor sensor;
    private String key;
    private Animation animation;
    private boolean isAlreadyPressed;

    /**
     * Constructor.
     *
     * @param sensor    the keyboard sensor
     * @param key       the key that will stop the animation once it's pressed
     * @param animation the animation
     */
    public KeyPressStoppableAnimation(KeyboardSensor sensor, String key, Animation animation) {
        this.sensor = sensor;
        this.key = key;
        this.animation = animation;
        this.isAlreadyPressed = true;
    }

    /**
     * doOneFrame: handle the logic of a single frame in the game.
     *
     * @param surface the surface to draw on.
     */
    public void doOneFrame(DrawSurface surface) {
        // stop the animation if the key is pressed
        if (this.sensor.isPressed(this.key)) {
            // stop animation only if this key is not already pressed
            if (!this.isAlreadyPressed) {
                this.animation.shouldStop(true);
            }
        } else {
            this.animation.doOneFrame(surface);
            this.isAlreadyPressed = false;
        }
    }

    /**
     * shouldStop: tells if the animation should stop.
     *
     * @return whether the animation should stop or not.
     */
    public boolean shouldStop() {
        return this.animation.shouldStop();
    }

    /**
     * shouldStop: tell the animation to stop.
     *
     * @param stop whether the animation should stop or not.
     */
    public void shouldStop(boolean stop) {
        // do nothing
    }

    /**
     * setAnimation: update the animation.
     *
     * @param a the animation
     */
    public void setAnimation(Animation a) {
        this.animation = a;
    }
}
