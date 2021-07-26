package arkanoid.sprites.Fill;

/**
 * Class Name: Color
 * <p>
 * This class represents a color filling.
 *
 * @author Rony Utevsky - ronyut@gmail.com - 319384657 - utevskr
 * @version 1.0.0
 * @since 17 June 2019
 */
public class Color implements Fill {
    private java.awt.Color color;
    private int index;

    /**
     * Constructor.
     *
     * @param color the color
     */
    public Color(java.awt.Color color) {
        this.color = color;
    }

    @Override
    public boolean isColor() {
        return true;
    }

    @Override
    public int compareTo(Fill f) {
        return this.index - f.getIndex();
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public void setIndex(int i) {
        this.index = i;
    }

    /**
     * getColor: get the color.
     * @return the color
     */
    public java.awt.Color getColor() {
        return this.color;
    }
}
