package arkanoid.game;

import arkanoid.Ass7Game;
import arkanoid.game.animation.AnimationRunner;
import arkanoid.game.animation.GameLevel;
import arkanoid.game.animation.HighScoresAnimation;
import arkanoid.game.animation.KeyPressStoppableAnimation;
import arkanoid.game.animation.EndScreen;
import arkanoid.game.highscores.HighScoresTable;
import arkanoid.game.highscores.ScoreInfo;
import arkanoid.game.levels.LevelInformation;
import arkanoid.util.Counter;
import biuoop.DialogManager;
import biuoop.KeyboardSensor;

import java.io.IOException;
import java.util.List;

/**
 * Class Name: GameFlow.
 * <p>
 * This class represents level the game flow, i.e. moving from one level to the next one.
 *
 * @author Rony Utevsky - ronyut@gmail.com - 319384657 - utevskr
 * @version 1.0.0
 * @since 25 May 2019
 */
public class GameFlow {
    public static final int LEVEL_COMPLETE_SCORE = 100;
    public static final int WINDOW_WIDTH = GameLevel.WINDOW_WIDTH;
    public static final int WINDOW_HEIGHT = GameLevel.WINDOW_HEIGHT;

    private AnimationRunner animationRunner;
    private KeyboardSensor keyboardSensor;
    private Counter score;
    private Counter lives;
    private HighScoresTable highscore;

    /**
     * Constructor.
     *
     * @param ar    the animation runner
     * @param ks    the keyboard sensor
     * @param lives the lives counter
     * @param hs    the highscores table
     */
    public GameFlow(AnimationRunner ar, KeyboardSensor ks, Counter lives, HighScoresTable hs) {
        this.animationRunner = ar;
        this.keyboardSensor = ks;
        this.lives = lives;
        this.score = new Counter();
        this.highscore = hs;
    }

    /**
     * runLevels: runs a game, level after level.
     *
     * @param levels the list of level to run
     */
    public void runLevels(List<LevelInformation> levels) {

        for (LevelInformation levelInfo : levels) {

            Counter blocks = new Counter();
            GameLevel level = new GameLevel(levelInfo, this.keyboardSensor, this.animationRunner, this.lives,
                    this.score, blocks);
            level.initialize();

            // play the same level over and over again as long as the player still has lives left
            while (this.lives.getValue() > 0) {
                level.playOneTurn();

                // if no blocks are left - move to the next level
                if (blocks.getValue() == 0) {
                    // if all blocks are dead by now, add 100 to the score
                    this.score.increase(LEVEL_COMPLETE_SCORE);
                    break;
                } else {
                    // subtract 1 life
                    this.lives.decrease(1);

                    // if no more lives are left, the game's over
                    if (this.lives.getValue() == 0) {
                        break;
                    }
                }
            }

            // if no more lives are left, the game's over
            if (this.lives.getValue() == 0) {
                break;
            }
        }

        // ask the user for his name if his score is going to be on the highscore table
        if (this.highscore.isHighScore(this.score.getValue())) {
            DialogManager dialog = this.animationRunner.gui().getDialogManager();
            String name = dialog.showQuestionDialog("Name", "What is your name?", "").trim();

            // if no name was entered
            if (name.equals("uninitializedValue") || name.isEmpty()) {
                name = "Anonymous";
            }

            // remove commas
            if (name.contains(",")) {
                name = name.replace(",", "");
            }

            // add the record to the list
            this.highscore.add(new ScoreInfo(name.trim(), this.score.getValue()));

            try {
                // save the data
                this.highscore.save();
            } catch (IOException ioe) {
                System.err.println(ioe.getMessage());
                System.exit(-1);
            }
        }

        // show end screen - win or lose
        this.animationRunner.run(new KeyPressStoppableAnimation(
                this.keyboardSensor, "space", new EndScreen(this.lives, this.score)));
        // show highscores table
        this.animationRunner.run(new KeyPressStoppableAnimation(
                this.keyboardSensor, "space", new HighScoresAnimation(this.highscore)));

        // when the game ends reset counters
        this.score = new Counter();
        this.lives = new Counter(Ass7Game.LIVES);
    }
}
