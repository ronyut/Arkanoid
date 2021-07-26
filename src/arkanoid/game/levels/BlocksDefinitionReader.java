package arkanoid.game.levels;

import java.io.InputStream;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.TreeMap;

/**
 * Class Name: BlocksDefinitionReader
 * <p>
 * This class reads and parses the blocks from the levels and blocks files.
 *
 * @author Rony Utevsky - ronyut@gmail.com - 319384657 - utevskr
 * @version 1.0.1
 * @since 18 June 2019
 */
public class BlocksDefinitionReader {
    /**
     * getReader: create a reader to read the blocks file.
     *
     * @param filename the blocks file
     * @return the factory that creates the blocks
     */
    public BlocksFromSymbolsFactory getReader(String filename) {
        // raw byte-stream
        InputStream ins = null;
        // cooked reader
        Reader reader = null;

        try {
            // open file and read it using UTF-8 charset
            ins = ClassLoader.getSystemClassLoader().getResourceAsStream(filename);
            reader = new InputStreamReader(ins, StandardCharsets.UTF_8);
            // return the factory
            return BlocksDefinitionReader.fromReader(reader);

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
     * fromReader: get the factory that creates the blocks.
     *
     * @param reader the reader that reads the blocks file
     * @return the factory that creates the blocks
     */
    public static BlocksFromSymbolsFactory fromReader(Reader reader) {
        // buffered for readLine()
        BufferedReader br = null;
        Map<String, String> defaults = new TreeMap<>();
        BlocksFromSymbolsFactory factory = new BlocksFromSymbolsFactory();

        try {
            String row;
            br = new BufferedReader(reader);

            while ((row = br.readLine()) != null) {
                row = row.trim();
                parseLine(row, defaults, factory);
            }

            // get the factory
            return factory;

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
     * parseLine: parse the line with the blocks.
     *
     * @param row      the row with the blocks
     * @param defaults the default values of the blocks
     * @param factory  the factory that creates the blocks
     */
    public static void parseLine(String row, Map<String, String> defaults, BlocksFromSymbolsFactory factory) {

        // if the row's should be ignored
        if (row.startsWith("#") || row.isEmpty()) {
            // do nothing
            boolean ignore = true;

        } else if (row.startsWith("default")) {
            // remove the "default" from the beginning of the row
            row = row.replace("default", "").trim();
            // split the row into the pairs
            String[] pairs = row.split(" ");

            for (String pair : pairs) {
                // split each pair into key and value
                String[] couple = pair.split(":");
                String key = couple[0].trim();
                String value = couple[1].trim();
                // put them in the defaults
                defaults.put(key, value);
            }

        } else if (row.startsWith("bdef")) {
            // deal with blocks
            Map<String, String> props = new TreeMap<>();
            BlockCreator bc = new BlockTemplate(defaults);

            // remove the "bdef" from the beginning of the row
            row = row.replace("bdef", "").trim();

            // split the row into the pairs
            String[] pairs = row.split(" ");

            for (String pair : pairs) {
                // split each pair into key and value
                String[] couple = pair.split(":");
                String key = couple[0].trim();
                String value = couple[1].trim();
                // put them in a map
                props.put(key, value);
            }
            // update fields
            ((BlockTemplate) bc).parsePropsAndApply(props);
            // check integrity of class fields
            ((BlockTemplate) bc).isEmptyFields();
            factory.addBlockCreator(bc);

        } else if (row.startsWith("sdef")) {
            // deal with spacers
            Map<String, String> props = new TreeMap<>();

            // remove the "sdef" from the beginning of the row
            row = row.replace("sdef", "").trim();

            // split the row into the pairs
            String[] pairs = row.split(" ");

            // is there's a symbol tag?
            if (!row.contains("symbol")) {
                throw new RuntimeException("A spacer has no symbol");
            }

            String symbol = pairs[0].split(":")[1].trim();
            // check if there's a symbol value
            if (symbol.isEmpty()) {
                throw new RuntimeException("A spacer has no symbol");
            }

            // check if there's width value
            if (row.contains("width")) {
                String width = pairs[1].split(":")[1].trim();
                factory.addSpacer(symbol, Integer.parseInt(width));
            } else if (defaults.get("width") != null) {
                // if the spacer doesn't have width, give it the default width
                factory.addSpacer(symbol, Integer.parseInt(defaults.get("width")));
            } else {
                throw new RuntimeException("A spacer has no width value.");
            }

        } else {
            throw new RuntimeException("Invalid input in blocks file.");
        }

    }
}
