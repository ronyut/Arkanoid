package arkanoid.sprites.Fill;

/**
 * Interface Name: Fill.
 * <p>
 * This interface represents a filling, such as color or image.
 *
 * @author Rony Utevsky - ronyut@gmail.com - 319384657 - utevskr
 * @version 1.0.0
 * @since 17 June 2019
 */
public interface Fill extends Comparable<Fill> {
    /**
     * isColor: tells if this is a color.
     * @return if this is a color.
     */
    boolean isColor();

    /**
     * getIndex: get the index of the fill, which represents the filling's order.
     * @return the index of the fill, which represents the filling's order
     */
    int getIndex();

    /**
     * setIndex: set the fill's index.
     * @param i the fill's new index.
     */
    void setIndex(int i);
}
