package arkanoid.game.animation.menu;

/**
 * Class Name: Task.
 * <p>
 * This interface represents a menu option's task.
 *
 * @param <T> the return type of the menu options
 * @author Rony Utevsky - ronyut@gmail.com - 319384657 - utevskr
 * @version 1.0.0
 * @since 18 June 2019
 */
public interface Task<T> {
    /**
     * run: runs a task and returns T.
     *
     * @return T type
     */
    T run();
}