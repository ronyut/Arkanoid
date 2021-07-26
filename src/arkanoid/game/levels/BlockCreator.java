package arkanoid.game.levels;

import arkanoid.sprites.Block;

/**
 * Interface Name: BlockCreator.
 * <p>
 * This interface represents a block creator.
 *
 * @author Rony Utevsky - ronyut@gmail.com - 319384657 - utevskr
 * @version 1.0.0
 * @since 18 June 2019
 */
public interface BlockCreator {
    /**
     * create: creates a block at the specified location.
     *
     * @param xpos x value of the block's top left point
     * @param ypos y value of the block's top left point
     * @return a block at the specified location
     */
    Block create(int xpos, int ypos);

    /**
     * getSymbol: get the block's symbol.
     *
     * @return the block's symbol
     */
    String getSymbol();
}
