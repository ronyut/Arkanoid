package arkanoid.util;

import java.util.Random;
import java.awt.Color;

/**
 * Class Name: ColorEffects
 * <p>
 * ColorEffects is a class that is responsible for color effects.
 * For example, it can generate a random color from a list.
 *
 * @author Rony Utevsky - ronyut@gmail.com - 319384657 - utevskr
 * @version 1.0.2
 * @since 11 May 2019
 */
public class ColorEffects {

    // array of colors
    public static final java.awt.Color[] COLORS = {java.awt.Color.RED,
            java.awt.Color.GREEN,
            java.awt.Color.BLUE,
            java.awt.Color.YELLOW,
            java.awt.Color.CYAN,
            java.awt.Color.GRAY,
            java.awt.Color.MAGENTA,
            java.awt.Color.ORANGE,
            java.awt.Color.PINK,
            java.awt.Color.WHITE,
            java.awt.Color.BLACK};

    /**
     * getRandomColor: get a random color from the list.
     *
     * @return a random color
     */
    public static Color getRandomColor() {
        Random rand = new Random();
        return COLORS[rand.nextInt(COLORS.length)];
    }

    /**
     * getRandomColor: get a random color from the list, but with an exception.
     *
     * @param exclude a color to be excluded
     * @return a random color
     */
    public static Color getRandomColor(Color exclude) {
        Random rand = new Random();
        Color randColor = COLORS[rand.nextInt(COLORS.length)];

        // if this color is excluded, fetch another one
        if (randColor == exclude) {
            return getRandomColor(exclude);
        } else {
            return randColor;
        }
    }

    /**
     * colorFromString: parse color definition and return the specified color.
     *
     * @param s the color's name
     * @return the specified color
     */
    public static Color colorFromString(String s) {
        switch (s) {
            case "black":
                return Color.BLACK;
            case "blue":
                return Color.BLUE;
            case "cyan":
                return Color.CYAN;
            case "gray":
                return Color.GRAY;
            case "grey":
                return Color.GRAY;
            case "lightGray":
                return Color.LIGHT_GRAY;
            case "darkGray":
                return Color.DARK_GRAY;
            case "green":
                return Color.GREEN;
            case "orange":
                return Color.ORANGE;
            case "pink":
                return Color.PINK;
            case "red":
                return Color.RED;
            case "white":
                return Color.WHITE;
            case "yellow":
                return Color.YELLOW;
            case "magenta":
                return Color.MAGENTA;
            case "brown":
                return new Color(171, 71, 0);
            default:
                throw new RuntimeException("Error: color not found!");
        }
    }
}
