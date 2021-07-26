package arkanoid.sprites;

import arkanoid.game.animation.GameLevel;
import biuoop.DrawSurface;

import java.awt.Color;

/**
 * Class Name: LevelIndicator.
 * <p>
 * This class indicates the level name and shows it on the screen.
 *
 * @author Rony Utevsky - ronyut@gmail.com - 319384657 - utevskr
 * @version 1.0.0
 * @since 25 May 2019
 */
public class LevelIndicator implements Sprite {
    public static final Color BG_COLOR = Color.WHITE;
    public static final Color TEXT_COLOR = Color.BLACK;
    public static final int THICKNESS = GameLevel.BORDER_THICKNESS;

    private String levelName;

    /**
     * Constructor.
     *
     * @param levelName the level's
     */
    public LevelIndicator(String levelName) {
        this.levelName = levelName;
    }

    /**
     * drawOn: draw the sprite on a given surface.
     *
     * @param d the surface to draw the sprite on
     */
    public void drawOn(DrawSurface d) {
        double fontSize = THICKNESS * 0.6;
        d.setColor(TEXT_COLOR);
        double textX = GameLevel.WINDOW_WIDTH * 0.65;
        d.drawText((int) textX, THICKNESS / 2 + 5, "Level: " + this.levelName, (int) fontSize);
    }

    /**
     * timePassed: notify the sprite that time has passed.
     */
    public void timePassed() {
        // do nothing
    }

    /**
     * addToGame: add a sprite to the game.
     *
     * @param g the game to add the sprite to
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }
}
