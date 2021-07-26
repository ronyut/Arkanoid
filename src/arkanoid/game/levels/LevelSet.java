package arkanoid.game.levels;

/**
 * Class Name: LevelSet.
 * <p>
 * This class holds all the properties of a level set that are needed for the menu.
 *
 * @author Rony Utevsky - ronyut@gmail.com - 319384657 - utevskr
 * @version 1.0.0
 * @since 18 June 2019
 */
public class LevelSet {
    private String key;
    private String name;
    private String path;

    /**
     * Constructor.
     *
     * @param key the key that will invoke the action once pressed
     * @param name the title of the action
     * @param path the file path of the level set's levels info
     */
    public LevelSet(String key, String name, String path) {
        this.key = key;
        this.name = name;
        this.path = path;
    }

    /**
     * getKey: gets the key.
     *
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * getName: gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * getPath: gets the file path.
     *
     * @return the file path
     */
    public String getPath() {
        return path;
    }

    /**
     * setKey: sets the key.
     * @param k the key
     */
    public void setKey(String k) {
        this.key = k;
    }

    /**
     * setName: sets the name.
     * @param n the name
     */
    public void setName(String n) {
        this.name = n;
    }

    /**
     * setPath: sets the path.
     * @param p the path
     */
    public void setPath(String p) {
        this.path = p;
    }
}
