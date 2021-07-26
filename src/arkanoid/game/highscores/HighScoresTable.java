package arkanoid.game.highscores;

import java.io.File;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class Name: HighScoresTable.
 * <p>
 * This class is responsible for a managing a high score table, such as loading and saving the high score data from a
 * file.
 *
 * @author Rony Utevsky - ronyut@gmail.com - 319384657 - utevskr
 * @version 1.0.0
 * @since 06 June 2019
 */
public class HighScoresTable {
    public static final String CSV_DELIMITER = ",";
    public static final int DEFAULT_MAX_SIZE = 7;

    private List<ScoreInfo> table;
    private File originalFile;
    private int maxSize;

    /**
     * Constructor: creates an empty high-scores table with the specified size.
     *
     * @param size the table holds up to this number of top scores
     */
    public HighScoresTable(int size) {
        this.table = new ArrayList<>(size);
        this.maxSize = size;
    }

    /**
     * add: adds a high-score.
     *
     * @param score the score to add to the table
     */
    public void add(ScoreInfo score) {
        this.table.add(score);
    }

    /**
     * size: return the table's max size.
     *
     * @return the table's max size
     */
    public int maxSize() {
        return this.maxSize;
    }

    /**
     * size: return the table's size.
     *
     * @return the table's size
     */
    public int size() {
        return this.table.size();
    }

    /**
     * getHighScores: gets the current high scores.
     * The list is sorted such that the highest scores come first.
     *
     * @return a sorted table
     */
    public List<ScoreInfo> getHighScores() {
        List<ScoreInfo> sortedTable = new ArrayList<>(this.table);
        Collections.sort(sortedTable, Collections.reverseOrder());
        return sortedTable;
    }

    /**
     * getRank: return the rank of the current score: where will it be on the list if added?
     * 1. Rank 1 means the score will be highest on the list.
     * 2. Rank i means the score will be in the i-th place.
     * 3. Rank `size` means the score will be lowest.
     * 4. Rank > `size` means the score is too low and will not be added to the list.
     *
     * @param score the user's score
     * @return the rank of the current score: where will it be on the list if added?
     */
    public int getRank(int score) {
        List<ScoreInfo> highscores = this.getHighScores();
        for (int i = 1; i < this.getHighScores().size(); i++) {
            if (score > highscores.get(i).getScore()) {
                return i;
            }
        }
        // the score is too low and won't be added to the list
        return this.getHighScores().size() + 1;
    }

    /**
     * isHighScore: tells if a certain score will be in the highscore table.
     *
     * @param score the player's score
     * @return if a certain score will be in the highscore table
     */
    public boolean isHighScore(int score) {
        return (this.getRank(score) <= this.maxSize);
    }

    /**
     * clear: clears the table.
     */
    public void clear() {
        this.table = new ArrayList<>();
    }

    /**
     * load: loads the table data from file, while current table data is cleared.
     *
     * @param filename a CSV file to load the data from
     * @throws IOException exception to be thrown if anything goes wrong with the file loading.
     */
    public void load(File filename) throws IOException {
        // clear current table
        this.clear();
        // set the file name
        this.originalFile = filename;

        // if highscores file doesn't exist
        if (!filename.exists()) {
            // create it, and if its fails
            if (!filename.createNewFile()) {
                throw new RuntimeException("Could not create highscores file.");
            }
        }

        List<ScoreInfo> data = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] block = line.split(CSV_DELIMITER);
            // get player's name and score
            ScoreInfo info = new ScoreInfo(block[0], Integer.parseInt(block[1]));
            data.add(info);
        }
        // close the file
        reader.close();
        // update the table
        this.table = data;
    }

    /**
     * save: Save table data to the specified file.
     *
     * @param filename a CSV file to save the data to
     * @throws IOException exception to be thrown if anything goes wrong with the file saving.
     */
    public void save(File filename) throws IOException {
        PrintStream ps = new PrintStream(filename);
        for (ScoreInfo row : this.getHighScores()) {
            ps.println(row.getName() + CSV_DELIMITER + row.getScore());
        }
        // close the streamer
        ps.close();
    }

    /**
     * save: Save table data to the specified file.
     *
     * @throws IOException exception to be thrown if anything goes wrong with the file saving.
     */
    public void save() throws IOException {
        save(this.originalFile);
    }

    /**
     * loadFromFile: reads a table from file and return it. If the file does not exist, or there is a problem
     * with reading it, an empty table is returned.
     *
     * @param filename a CSV file to load the data from
     * @return the highscore table
     */
    public static HighScoresTable loadFromFile(File filename) {
        HighScoresTable h = new HighScoresTable(DEFAULT_MAX_SIZE);

        try {
            // load the data to table from file
            h.load(filename);
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
            System.exit(-1);
        }

        return h;
    }

    /**
     * countRecordsInFile: get the number of rows in the file (i.e. number of records).
     *
     * @param filename a CSV file with the highscore data
     * @return the number of rows in the file
     * @throws IOException exception to be thrown if anything goes wrong with the file loading.
     */
    public static int countRecordsInFile(File filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        int lineCounter = 0;

        while (reader.readLine() != null) {
            lineCounter += 1;
        }
        return lineCounter;
    }
}