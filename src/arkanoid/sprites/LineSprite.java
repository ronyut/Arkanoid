package arkanoid.sprites;

import arkanoid.game.animation.GameLevel;
import arkanoid.geometry.Line;
import biuoop.DrawSurface;

import java.awt.Color;

/**
 * Class Name: LineSprite.
 * <p>
 * This class represents a drawable line on the screen.
 *
 * @author Rony Utevsky - ronyut@gmail.com - 319384657 - utevskr
 * @version 1.0.0
 * @since 25 May 2019
 */
public class LineSprite implements Sprite {
    private Line line;
    private Color color;

    /**
     * Constructor.
     *
     * @param l the line
     * @param c the color
     */
    public LineSprite(Line l, Color c) {
        this.line = l;
        this.color = c;
    }

    /**
     * drawOn: draw the sprite on a given surface.
     *
     * @param d the surface to draw the sprite on
     */
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        d.drawLine((int) this.line.start().getX(), (int) this.line.start().getY(), (int) this.line.end().getX(),
                (int) this.line.end().getY());
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
     * @param gameLevel the game to add the sprite to
     */
    public void addToGame(GameLevel gameLevel) {
        gameLevel.addSprite(this);
    }
}
