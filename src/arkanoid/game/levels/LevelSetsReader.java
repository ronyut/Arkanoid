package arkanoid.game.levels;

import java.io.InputStream;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Class Name: LevelSetsReader
 * <p>
 * This class reads and parses the level sets from the level sets file.
 *
 * @author Rony Utevsky - ronyut@gmail.com - 319384657 - utevskr
 * @version 1.0.0
 * @since 18 June 2019
 */
public class LevelSetsReader {

    /**
     * getReader: create a reader to read the level sets file.
     *
     * @param filename the level sets file
     * @return list of level sets.
     */
    public static List<LevelSet> getReader(String filename) {
        // raw byte-stream
        InputStream ins = null;
        // cooked reader
        Reader reader = null;

        try {
            // open file and read it using UTF-8 charset
            ins = ClassLoader.getSystemClassLoader().getResourceAsStream(filename);
            reader = new InputStreamReader(ins, StandardCharsets.UTF_8);
            // return the level sets
            return new LevelSetsReader().fromReader(reader);

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
    public List<LevelSet> fromReader(Reader reader) {
        BufferedReader br = null;
        List<LevelSet> levelSets = new ArrayList<>();
        int rowNum = 0;

        try {
            String row;
            br = new BufferedReader(reader);

            // loop through the lines
            while ((row = br.readLine()) != null) {
                rowNum++;

                // odd rows
                if (rowNum % 2 != 0) {
                    String key = row.split(":")[0];
                    String name = row.split(":")[1];
                    LevelSet levelSet = new LevelSet(key, name, "");
                    levelSets.add(levelSet);
                } else {
                    // update the path of the last levelSet
                    levelSets.get(levelSets.size() - 1).setPath(row);
                }
            }

            // get the levels!
            return levelSets;

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
}