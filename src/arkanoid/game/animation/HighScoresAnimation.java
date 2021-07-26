package arkanoid.game.animation;

import arkanoid.geometry.Point;
import arkanoid.sprites.Block;
import biuoop.DrawSurface;
import arkanoid.game.highscores.HighScoresTable;
import arkanoid.game.highscores.ScoreInfo;

import java.awt.Color;

/**
 * Class Name: HighScoresAnimation.
 * <p>
 * This class represents the highscore screen.
 *
 * @author Rony Utevsky - ronyut@gmail.com - 319384657 - utevskr
 * @version 1.0.0
 * @since 07 June 2019
 */
public class HighScoresAnimation implements Animation {
    public static final int WINDOW_WIDTH = GameLevel.WINDOW_WIDTH;
    public static final int WINDOW_HEIGHT = GameLevel.WINDOW_HEIGHT;

    private boolean stop;
    private HighScoresTable highscores;

    /**
     * Constructor.
     *
     * @param scores the high score table
     */
    public HighScoresAnimation(HighScoresTable scores) {
        this.highscores = scores;
        this.stop = false;
    }

    /**
     * doOneFrame: handle the logic of a single frame in the game.
     *
     * @param d the surface to draw on
     */
    public void doOneFrame(DrawSurface d) {
        Block bg = new Block(new Point(0, 0), WINDOW_WIDTH, WINDOW_HEIGHT, Color.MAGENTA);
        bg.addFill(new arkanoid.sprites.Fill.Image("background_images/xp.jpg"));
        bg.drawOn(d);

        int i = 0, diff = 50;

        d.setColor(Color.BLUE);
        d.drawText(d.getWidth() / 3 + 50, d.getHeight() / 10, "High Scores", 30);

        d.setColor(Color.YELLOW);

        // show scores table if there are any scores
        if (this.highscores.size() > 0) {
            d.drawText(d.getWidth() / 6, d.getHeight() / 5, "Player Name", 30);
            d.drawText((int) (d.getWidth() * 0.7), d.getHeight() / 5, "Score", 30);

            d.setColor(Color.BLACK);
            for (ScoreInfo record : this.highscores.getHighScores()) {
                // don't exceed max number of users that can be shown
                if (i >= this.highscores.maxSize()) {
                    break;
                }

                d.drawText(d.getWidth() / 6, d.getHeight() / 4 + i * diff + 20, record.getName(), 30);
                d.drawText((int) (d.getWidth() * 0.7), d.getHeight() / 4 + i * diff + 20,
                        Integer.toString(record.getScore()), 30);
                i++;
            }
        } else {
            d.drawText(d.getWidth() / 3 + 30, d.getHeight() / 2, "No high scores :(", 30);
        }

        d.setColor(Color.LIGHT_GRAY);
        d.drawText(d.getWidth() / 3 - 10, d.getHeight() - 50, "Press space to proceed", 30);
    }

    /**
     * shouldStop: tells if the animation should stop.
     *
     * @return whether the animation should stop or not.
     */
    public boolean shouldStop() {
        return this.stop;
    }

    /**
     * shouldStop: tell the animation to stop.
     *
     * @param shouldStop whether the animation should stop or not.
     */
    public void shouldStop(boolean shouldStop) {
        this.stop = shouldStop;
    }
}