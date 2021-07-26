package arkanoid.sprites.Fill;

/**
 * Class Name: Image.
 * <p>
 * This class represents am image filling.
 *
 * @author Rony Utevsky - ronyut@gmail.com - 319384657 - utevskr
 * @version 1.0.0
 * @since 17 June 2019
 */
public class Image implements Fill {
    private String image;
    private int index;

    /**
     * Constructor #1.
     *
     * @param img the image
     */
    public Image(String img) {
        // remove "decorations" from the string
        if (img.contains("image(") && img.contains(")")) {
            img = img.replace("image(", "");
            img = img.replace(")", "");
        }
        this.image = img.trim();
    }

    /**
     * Constructor #2.
     *
     * @param img the image
     * @param index the index which represents the filling's order
     */
    public Image(String img, int index) {
        this(img);
        this.index = index;
    }

    @Override
    public boolean isColor() {
        return false;
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
     * getImage: get the image.
     *
     * @return the image
     */
    public String getImage() {
        return image;
    }
}
