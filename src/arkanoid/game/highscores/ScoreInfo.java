package arkanoid.game.highscores;

/**
 * Class Name: ScoreInfo.
 * <p>
 * This class is represents the block of data that contains the player's name and the score.
 *
 * @author Rony Utevsky - ronyut@gmail.com - 319384657 - utevskr
 * @version 1.0.0
 * @since 06 June 2019
 */
public class ScoreInfo implements Comparable<ScoreInfo> {
    private String name;
    private int score;

    /**
     * Constructor.
     *
     * @param n  the player's name
     * @param s the player's score
     */
    public ScoreInfo(String n, int s) {
        this.name = n;
        this.score = s;
    }

    /**
     * getName: get the player's name.
     *
     * @return the player's name
     */
    public String getName() {
        return this.name;
    }

    /**
     * getName: get the player's score.
     *
     * @return the player's score
     */
    public int getScore() {
        return this.score;
    }

    @Override
    public int compareTo(ScoreInfo s) {
        return this.score - s.getScore();
    }
}