package arkanoid;

import arkanoid.game.GameFlow;
import arkanoid.game.animation.AnimationRunner;
import arkanoid.game.animation.GameLevel;
import arkanoid.game.animation.KeyPressStoppableAnimation;
import arkanoid.game.animation.menu.MenuAnimation;
import arkanoid.game.animation.HighScoresAnimation;
import arkanoid.game.animation.menu.Task;
import arkanoid.game.highscores.HighScoresTable;
import arkanoid.game.levels.LevelSet;
import arkanoid.game.levels.LevelSetsReader;
import arkanoid.game.levels.LevelSpecificationReader;
import arkanoid.game.levels.LevelInformation;
import arkanoid.util.Counter;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import biuoop.Sleeper;

import java.io.File;
import java.util.List;

/**
 * Class Name: Ass7Game
 * <p>
 * This class runs the Arkanoid game.
 *
 * @author Rony Utevsky - ronyut@gmail.com - 319384657 - utevskr
 * @version 1.0.2
 * @since 19 June 2019
 */
public class Ass7Game {
    public static final String PATH = "";
    public static final String LEVEL_SETS = PATH + "level_sets.txt";
    public static final String HIGH_SCORES_FILE = "highscores";
    public static final int LIVES = 7;

    /**
     * main: create a new game and run it.
     *
     * @param args path to level sets file (optional)
     */
    public static void main(String[] args) {
        // GUI window
        GUI gui = new GUI(GameLevel.GAME_TITLE, GameLevel.WINDOW_WIDTH, GameLevel.WINDOW_HEIGHT);
        // keyboard sensor
        KeyboardSensor keyboard = gui.getKeyboardSensor();
        // sleeper
        Sleeper sleeper = new Sleeper();
        // animation runner
        AnimationRunner ar = new AnimationRunner(gui, sleeper);
        // high scores
        HighScoresTable hs = HighScoresTable.loadFromFile(new File(HIGH_SCORES_FILE));
        // set game flow
        GameFlow gameFlow = new GameFlow(ar, keyboard, new Counter(LIVES), hs);

        // add the main menu and its choices
        MenuAnimation menu = new MenuAnimation<String>(keyboard);

        menu.addSelection("s", "Start Game", new Task<Void>() {
            public Void run() {
                // the submenu
                MenuAnimation submenu = new MenuAnimation<String>(keyboard);

                // set the level sets file
                String levelSetsFile = LEVEL_SETS;
                if (args.length > 0) {
                    levelSetsFile = args[0];
                }

                // get the level sets
                List<LevelSet> levelSets = LevelSetsReader.getReader(levelSetsFile);

                for (LevelSet levelSet : levelSets) {
                    // add selection to submenu
                    menu.addSubMenu(levelSet.getKey(), levelSet.getName(), submenu, new Task<Void>() {
                        public Void run() {
                            // add the level according to the chosen level set
                            List<LevelInformation> levels;
                            levels = LevelSpecificationReader.getReader(PATH + levelSet.getPath());
                            // run levels
                            gameFlow.runLevels(levels);
                            return null;
                        }
                    });
                }

                // run the submenu
                ar.run(submenu);
                Task<Void> task = (Task) submenu.getStatus();
                task.run();
                return null;
            }
        });

        menu.addSelection("h", "High Scores", new Task<Void>() {
            public Void run() {
                ar.run(new KeyPressStoppableAnimation(keyboard, "space", new HighScoresAnimation(hs)));
                return null;
            }
        });

        menu.addSelection("q", "Quit", new Task<Void>() {
            public Void run() {
                gui.close();
                System.exit(0);
                return null;
            }
        });

        // run the main menu
        while (true) {
            ar.run(menu);
            menu.shouldStop(false);
            Task<Void> task = (Task) menu.getStatus();
            task.run();
        }
    }
}