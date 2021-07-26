package arkanoid.game.animation.menu;

import arkanoid.game.animation.Animation;

/**
 * Interface Name: Menu.
 * <p>
 * This interface represents a menu.
 *
 * @author Rony Utevsky - ronyut@gmail.com - 319384657 - utevskr
 * @version 1.0.0
 * @since 08 June 2019
 * @param <T> the return type of the menu options
 */
public interface Menu<T> extends Animation {
    /**
     * addSelection: adds a selection to the menu.
     *
     * @param key       the key which triggers the selection once pressed
     * @param message   the message to be displayed
     * @param returnVal the return value
     */
    void addSelection(String key, String message, T returnVal);

    /**
     * getStatus: gets the status.
     *
     * @return the status
     */
    T getStatus();

    /**
     * addSubMenu: adds a submenu.
     *
     * @param key     the key that once pressed, will invoke the submenu
     * @param message the message that describes the submenu
     * @param subMenu the submenu
     * @param returnVal the return value
     */
    void addSubMenu(String key, String message, Menu<T> subMenu, T returnVal);
}
