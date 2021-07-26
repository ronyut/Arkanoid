package arkanoid.game;

import arkanoid.sprites.Sprite;
import java.util.List;
import java.util.ArrayList;
import biuoop.DrawSurface;

/**
 * Class Name: SpriteCollection.
 * <p>
 * SpriteCollection class represents a collection of sprite objects
 * This class can tell all the sprite objects to draw themselves on the screen, it can notify them that time has
 * passed so they should do something, it can add more sprites, etc.
 *
 * @author Rony Utevsky - ronyut@gmail.com - 319384657 - utevskr
 * @version 1.0.2
 * @since 11 May 2019
 */
public class SpriteCollection {

    private List<Sprite> spritesList;

    /**
     * Constructor.
     */
    public SpriteCollection() {
        this.spritesList = new ArrayList<>();
    }

    /**
     * addSprite: adds a sprite object to the sprites list.
     *
     * @param s a sprite to add
     */
    public void addSprite(Sprite s) {
        this.spritesList.add(s);
    }

    /**
     * addSprite: removes a sprite object from the sprites list.
     *
     * @param s a sprite to remove
     */
    public void removeSprite(Sprite s) {
        this.spritesList.remove(s);
    }

    /**
     * notifyAllTimePassed: call timePassed() on all sprites in the sprites list.
     */
    public void notifyAllTimePassed() {
        for (int i = 0; i < this.spritesList.size(); i++) {
            this.spritesList.get(i).timePassed();
        }
    }

    /**
     * notifyAllTimePassed: call drawOn(d) on all sprites in the sprites list, in order to draw them all on the
     * screen.
     *
     * @param surface the surface to draw on
     */
    public void drawAllOn(DrawSurface surface) {
        for (Sprite sp : this.spritesList) {
            sp.drawOn(surface);
        }
    }

}