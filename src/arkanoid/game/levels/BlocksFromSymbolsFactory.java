package arkanoid.game.levels;

import arkanoid.sprites.Block;

import java.util.Map;
import java.util.TreeMap;

/**
 * Class Name: BlocksFromSymbolsFactory.
 * <p>
 * This class represents a block factory.
 *
 * @author Rony Utevsky - ronyut@gmail.com - 319384657 - utevskr
 * @version 1.0.0
 * @since 18 June 2019
 */
public class BlocksFromSymbolsFactory {
    private Map<String, Integer> spacerWidths = new TreeMap<>();
    private Map<String, BlockCreator> blockCreators = new TreeMap<>();

    /**
     * isBlockSymbol: returns true if 's' is a valid space symbol.
     *
     * @param s a symbol that represents a space
     * @return true if 's' is a valid space symbol
     */
    public boolean isSpaceSymbol(String s) {
        return this.spacerWidths.get(s) != null;
    }

    /**
     * isBlockSymbol: returns true if 's' is a valid block symbol.
     *
     * @param s a symbol that represents a block
     * @return true if 's' is a valid block symbol
     */
    public boolean isBlockSymbol(String s) {
        return this.blockCreators.get(s) != null;
    }

    /**
     * getBlock: gets a block according to the definitions associated with symbol s. The block will be located at
     * position (xpos, ypos).
     *
     * @param s the symbol of the block
     * @param x the x position
     * @param y the y position
     * @return a block according to the definitions associated with symbol s
     */
    public Block getBlock(String s, int x, int y) {
        return this.blockCreators.get(s).create(x, y);
    }

    /**
     * getSpaceWidth: returns the width in pixels associated with the given spacer-symbol.
     *
     * @param s the symbol of the spacer
     * @return the width in pixels associated with the given spacer-symbol
     */
    public int getSpaceWidth(String s) {
        return this.spacerWidths.get(s);
    }

    /**
     * addBlockCreator: add block creator.
     *
     * @param bc a block creator
     */
    public void addBlockCreator(BlockCreator bc) {
        this.blockCreators.put(bc.getSymbol(), bc);
    }

    /**
     * addSpacer: adds a spacer.
     *
     * @param symbol the spacer's symbol
     * @param width  the spacer's width
     */
    public void addSpacer(String symbol, int width) {
        this.spacerWidths.put(symbol, width);
    }
}
