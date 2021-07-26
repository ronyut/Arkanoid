package arkanoid.game.levels;

import arkanoid.geometry.Point;
import arkanoid.sprites.Block;
import arkanoid.sprites.Fill.Fill;

import java.awt.Color;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Class Name: BlockCreator.
 * <p>
 * This class represents a block template.
 *
 * @author Rony Utevsky - ronyut@gmail.com - 319384657 - utevskr
 * @version 1.0.1
 * @since 18 June 2019
 */
public class BlockTemplate implements BlockCreator {
    private String symbol;
    private int width;
    private int height;
    private int hitPoints;
    private List<Fill> fill = new ArrayList<>();
    private Color stroke;

    /**
     * Constructor.
     *
     * @param defaults the default properties of the block
     */
    public BlockTemplate(Map<String, String> defaults) {
        parsePropsAndApply(defaults);
    }

    /**
     * parsePropsAndApply: parses the properties and applies them to the template.
     *
     * @param props the properties to apply
     */
    public void parsePropsAndApply(Map<String, String> props) {
        for (Map.Entry<String, String> prop : props.entrySet()) {
            String key = prop.getKey().trim();
            String value = prop.getValue().trim();

            if (key.equals("symbol")) {
                this.symbol = value;

            } else if (key.equals("height")) {
                this.height = Integer.parseInt(value);

            } else if (key.equals("width")) {
                this.width = Integer.parseInt(value);

            } else if (key.equals("hit_points")) {
                this.hitPoints = Integer.parseInt(value);

            } else if (key.equals("stroke")) {
                this.stroke = LevelSpecificationReader.parseColor(value);

            } else if (key.contains("fill")) {
                Fill newFill;

                // if it's a color
                if (LevelSpecificationReader.parseColor(value) != null) {
                    newFill = new arkanoid.sprites.Fill.Color(LevelSpecificationReader.parseColor(value));
                } else {
                    // it's an image
                    newFill = new arkanoid.sprites.Fill.Image(value);
                }

                // assign index 1 for 1 hit point
                if (key.equals("fill") || key.equals("fill-1")) {
                    newFill.setIndex(1);
                } else {
                    String index = key.replace("fill-", "");
                    newFill.setIndex(Integer.parseInt(index));
                }

                // add to list
                this.fill.add(newFill);
            }
        }

        // sort the fills list by index
        Collections.sort(this.fill);
    }

    /**
     * isEmptyFields: checks if there are any empty class members.
     */
    public void isEmptyFields() {
        if (this.symbol.isEmpty() || this.fill.size() == 0 || this.hitPoints == 0 || this.height == 0
                || this.width == 0) {
            throw new RuntimeException("Insufficient properties were found in blocks file");
        }
    }

    @Override
    public Block create(int xpos, int ypos) {
        Point p = new Point(xpos, ypos);
        Block block = new Block(p, this.width, this.height, new ArrayList<>(fill), this.hitPoints);
        block.setStroke(this.stroke);
        return block;
    }

    /**
     * setSymbol: sets the symbol.
     *
     * @param s the symbol
     */
    public void setSymbol(String s) {
        this.symbol = s;
    }

    /**
     * setWidth: sets the width.
     *
     * @param w the width
     */
    public void setWidth(int w) {
        this.width = w;
    }

    /**
     * setHeight: sets the height.
     *
     * @param h the height
     */
    public void setHeight(int h) {
        this.height = h;
    }

    /**
     * setHitPoints: sets the hit points.
     *
     * @param hp the hit points
     */
    public void setHitPoints(int hp) {
        this.hitPoints = hp;
    }

    /**
     * setFill: sets the fillings.
     *
     * @param f the fillings
     */
    public void setFill(List<Fill> f) {
        this.fill = f;
    }

    /**
     * setStroke: sets the stroke.
     *
     * @param s the stroke's color
     */
    public void setStroke(Color s) {
        this.stroke = s;
    }

    @Override
    public String getSymbol() {
        return this.symbol;
    }
}
