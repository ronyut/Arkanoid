package arkanoid.game.levels;

import arkanoid.Ass7Game;
import arkanoid.geometry.Point;
import arkanoid.sprites.Block;
import arkanoid.util.ColorEffects;
import arkanoid.util.Velocity;

import java.awt.Color;
import java.io.InputStream;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Class Name: LevelSpecificationReader
 * <p>
 * This class reads and parses the levels from the levels file.
 *
 * @author Rony Utevsky - ronyut@gmail.com - 319384657 - utevskr
 * @version 1.0.0
 * @since 17 June 2019
 */
public class LevelSpecificationReader {
    private List<LevelInformation> levels = new ArrayList<>();

    public static final String LEVEL_NAME = "level_name";
    public static final String BALL_VELOCITIES = "ball_velocities";
    public static final String BACKGROUND = "background";
    public static final String PADDLE_SPEED = "paddle_speed";
    public static final String PADDLE_WIDTH = "paddle_width";
    public static final String BLOCK_DEFINITIONS = "block_definitions";
    public static final String BLOCKS_START_X = "blocks_start_x";
    public static final String BLOCKS_START_Y = "blocks_start_y";
    public static final String ROW_HEIGHT = "row_height";
    public static final String NUM_BLOCKS = "num_blocks";
    public static final String START_BLOCKS = "START_BLOCKS";
    public static final String END_BLOCKS = "END_BLOCKS";
    public static final String START_LEVEL = "START_LEVEL";
    public static final String END_LEVEL = "END_LEVEL";
    public static final int LEVEL_PARAMS = 14;
    public static final String PATH = Ass7Game.PATH;

    /**
     * getReader: create a reader to read the levels file.
     *
     * @param filename the levels file
     * @return list of levels to be run.
     */
    public static List<LevelInformation> getReader(String filename) {
        // raw byte-stream
        InputStream ins = null;
        // cooked reader
        Reader reader = null;

        try {
            // open file and read it using UTF-8 charset
            ins = ClassLoader.getSystemClassLoader().getResourceAsStream(filename);
            reader = new InputStreamReader(ins, StandardCharsets.UTF_8);
            // return the game levels
            return new LevelSpecificationReader().fromReader(reader);

        } catch (Exception e) {
            // handle exception
            System.err.println(e.getMessage());
            System.exit(-1);

        } finally {
            // check if the reader is ok
            if (reader != null) {
                try {
                    reader.close();
                } catch (Throwable t) {
                    // print error if couldn't close the reader
                    System.err.println(t.getMessage());
                    System.exit(-1);
                }
            }

            // check if the File Input Stream is ok
            if (ins != null) {
                try {
                    ins.close();
                } catch (Throwable t) {
                    // print error if couldn't close the stream
                    System.err.println(t.getMessage());
                    System.exit(-1);
                }
            }
        }

        // return null if an error occurred
        return null;
    }

    /**
     * fromReader: get the list of levels.
     *
     * @param reader the reader that reads the levels file
     * @return list of levels to be run.
     */
    public List<LevelInformation> fromReader(Reader reader) {
        BufferedReader br = null;
        List<List<String>> levelsRaw = new ArrayList<>();

        try {
            List<String> levelRaw = new ArrayList<>();
            String row;
            br = new BufferedReader(reader);

            // loop through the lines
            while ((row = br.readLine()) != null) {
                // add row to level
                levelRaw.add(row);

                // if it's the end of the level's definitions
                if (row.contains(END_LEVEL)) {
                    // add this level to the levels
                    levelsRaw.add(levelRaw);
                    // reset level
                    levelRaw = new ArrayList<>();
                }
            }

            // parse each level
            for (List<String> lvlRaw : levelsRaw) {
                this.levels.add(this.parseRawLevel(lvlRaw));
            }

            // get the levels!
            return this.levels;

        } catch (Exception e) {
            // handle exception
            System.err.println(e.getMessage());
            System.exit(-1);

        } finally {
            // check if the Buffer Reader is ok
            if (br != null) {
                try {
                    br.close();
                } catch (Throwable t) {
                    // print error if couldn't close the buffer
                    System.err.println(t.getMessage());
                    System.exit(-1);
                }
            }
        }

        // if an error occurred
        return null;
    }

    /**
     * parseRawLevel: parses a level.
     *
     * @param rawLevel a level text to be parsed into level info.
     * @return level info parsed from the level's text.
     */
    public LevelInformation parseRawLevel(List<String> rawLevel) {
        GenericLevel levelInfo = new GenericLevel();
        BlocksFromSymbolsFactory blocksSettings = null;

        // indicate if we are currently reading the blocks' plan
        boolean readBlock = false;

        Point currentBlockPos = new Point(0, 0);
        int counter = 0;

        for (String row : rawLevel) {
            row = row.trim();

            // if the row's should be ignored
            if (row.startsWith("#") || row.isEmpty()) {
                continue;

            } else if (row.contains(START_LEVEL) || row.contains(END_LEVEL)) {
                // start or end of level
                counter++;

            } else if (row.contains(LEVEL_NAME)) {
                // set the level name
                levelInfo.setLevelName(removeFieldName(row, LEVEL_NAME));
                counter++;

            } else if (row.contains(BALL_VELOCITIES)) {
                // set balls velocities
                List<Velocity> velocities = parseVelocities(removeFieldName(row, BALL_VELOCITIES));
                levelInfo.addVelocities(velocities);
                counter++;

            } else if (row.contains(BACKGROUND)) {
                // set the background
                Color bgColor = parseColor(removeFieldName(row, BACKGROUND));

                // if it's a color
                if (bgColor != null) {
                    levelInfo.setBg(bgColor);
                    counter++;
                } else {
                    // if it's an image
                    levelInfo.setBg(extractImageName(row));
                    counter++;
                }

            } else if (row.contains(PADDLE_SPEED)) {
                // set the paddle speed
                levelInfo.setPaddleSpeed((int) Double.parseDouble(removeFieldName(row, PADDLE_SPEED)));
                counter++;

            } else if (row.contains(PADDLE_WIDTH)) {
                // set the paddle width
                levelInfo.setPaddleWidth(Integer.parseInt(removeFieldName(row, PADDLE_WIDTH)));
                counter++;

            } else if (row.contains(BLOCK_DEFINITIONS)) {
                String filename = removeFieldName(row, BLOCK_DEFINITIONS);
                blocksSettings = new BlocksDefinitionReader().getReader(PATH + filename);
                counter++;

            } else if (row.contains(BLOCKS_START_X)) {
                // set the x value of the start point of the blocks
                double x = Integer.parseInt(removeFieldName(row, BLOCKS_START_X));
                levelInfo.setBlockStartX(x);
                currentBlockPos.setX(x);
                counter++;

            } else if (row.contains(BLOCKS_START_Y)) {
                // set the y value of the start point of the blocks
                double y = Integer.parseInt(removeFieldName(row, BLOCKS_START_Y));
                levelInfo.setBlockStartY(y);
                currentBlockPos.setY(y);
                counter++;

            } else if (row.contains(ROW_HEIGHT)) {
                // set the row height of the blocks
                levelInfo.setRowHeight(Integer.parseInt(removeFieldName(row, ROW_HEIGHT)));
                counter++;

            } else if (row.contains(NUM_BLOCKS)) {
                levelInfo.setNumberOfBlocksToRemove(Integer.parseInt(removeFieldName(row, NUM_BLOCKS)));
                counter++;

            } else if (row.contains(START_BLOCKS)) {
                readBlock = true;
                counter++;

            } else if (row.contains(END_BLOCKS)) {
                readBlock = false;
                counter++;

            } else if (readBlock) {
                // we are reading the blocks' plan
                List<Block> rowBlocks = parseBlocksPlan(row, currentBlockPos, blocksSettings, levelInfo);
                levelInfo.addBlocks(rowBlocks);
            } else {
                throw new RuntimeException("Invalid input in levels file.");
            }
        }

        // check parameters number integrity
        if (counter != LEVEL_PARAMS) {
            throw new RuntimeException("Insufficient number of parameters were passed in levels file.");
        }

        return levelInfo;
    }

    /**
     * parseBlocksPlan: parses the blocks' plan.
     *
     * @param row       the row of blocks to parse
     * @param pos       the current point where the next block should be created
     * @param factory   the factory that builds the blocks
     * @param levelInfo the level information
     * @return list of newly created blocks
     */
    public List<Block> parseBlocksPlan(String row, Point pos, BlocksFromSymbolsFactory factory,
                                       GenericLevel levelInfo) {
        List<Block> blocks = new ArrayList<>();
        String[] chars = row.split("");
        int xPos = (int) pos.getX();
        int yPos = (int) pos.getY();

        for (int i = 0; i < chars.length; i++) {
            // if it's a block
            if (factory.isBlockSymbol(chars[i])) {
                // generate the block
                Block block = factory.getBlock(chars[i], xPos, yPos);
                blocks.add(block);

                // update the x position
                xPos += block.getCollisionRectangle().getWidth();
                pos.setX(xPos);

            } else if (factory.isSpaceSymbol(chars[i])) {
                // if it's a spacer
                xPos += factory.getSpaceWidth(chars[i]);
                pos.setX(xPos);

            } else {
                throw new RuntimeException("Unknown char representing a block or spacer: " + chars[i]);
            }

            // add to yPos when the row is done, and reset xPos
            if (i == chars.length - 1) {
                pos.setY(yPos + levelInfo.getRowHeight());
                // reset x pos
                pos.setX(levelInfo.getBlockStart().getX());
            }
        }

        return blocks;
    }

    /**
     * removeFieldName: removes a field's name.
     *
     * @param row   the row to remove the field's name from.
     * @param field the field's name to remove
     * @return the string without the field's name
     */
    public String removeFieldName(String row, String field) {
        return row.replace(field + ":", "").trim();
    }

    /**
     * parseVelocities: parse the balls' velocities.
     *
     * @param row the row with the velocities
     * @return list of the balls' velocities
     */
    public List<Velocity> parseVelocities(String row) {
        List<Velocity> velocities = new ArrayList<>();

        // split velocities by space
        String[] onlyVels = row.trim().split(" ");

        for (String vel : onlyVels) {
            // split by comma
            String[] split = vel.split(",");
            // generate the velocity
            Velocity velocity = Velocity.fromAngleAndSpeed(Double.parseDouble(split[0]), Double.parseDouble(split[1]));
            velocities.add(velocity);
        }

        return velocities;
    }

    /**
     * parseColor: parses the color from the string.
     *
     * @param row the row with the color to parse.
     * @return the parsed color
     */
    public static Color parseColor(String row) {
        /*** first let's check if it's an RGB color ***/

        // get the RGB colors
        String rgb = row.replace("color(RGB(", "");

        if (!row.equals(rgb)) {
            rgb = rgb.replace(")", "");

            String[] colors = rgb.split(",");
            return new Color(Integer.parseInt(colors[0]), Integer.parseInt(colors[1]), Integer.parseInt(colors[2]));
        }

        /*** if the above didn't work, maybe it's the color name ***/

        // get the color's name
        String color = row.replace("color(", "");

        if (!row.equals(color)) {
            color = color.replace(")", "");
            return ColorEffects.colorFromString(color.trim());
        }

        // if it's not a color at all, return null
        return null;

    }

    /**
     * extractImageName: extracts the image's name from the string.
     *
     * @param row the row to extract the image name from
     * @return the extracted image's name
     */
    public String extractImageName(String row) {
        return row.replace("background:image(", "").replace(")", "");
    }
}
