package arkanoid.game.animation.menu;

/**
 * Class Name: MenuChoice.
 * <p>
 * This class represents a menu choice.
 *
 * @author Rony Utevsky - ronyut@gmail.com - 319384657 - utevskr
 * @version 1.0.0
 * @since 18 June 2019
 * @param <T> the return type of the menu options
 */
public class MenuChoice<T> {
    private String key;
    private String message;
    private T returnVal;

    /**
     * Constructor.
     *
     * @param key the key that once pressed will do something.
     * @param message the message that explains the action
     * @param returnVal the return value of the choice
     */
    public MenuChoice(String key, String message, T returnVal) {
        this.key = key;
        this.message = message;
        this.returnVal = returnVal;
    }

    /**
     * getKey: gets the key.
     *
     * @return the key
     */
    public String getKey() {
        return this.key;
    }

    /**
     * getMessage: gets the message.
     *
     * @return the message
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * getReturnVal: get the return value.
     *
     * @return the return value
     */
    public T getReturnVal() {
        return this.returnVal;
    }
}