package arkanoid.game.animation.menu;

import arkanoid.game.animation.GameLevel;
import arkanoid.geometry.Point;
import arkanoid.sprites.Block;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Class Name: MenuAnimation.
 * <p>
 * This class represents a menu screen.
 *
 * @param <T> the return type of the menu options
 * @author Rony Utevsky - ronyut@gmail.com - 319384657 - utevskr
 * @version 1.0.0
 * @since 08 June 2019
 */
public class MenuAnimation<T> implements Menu<T> {
    public static final int WINDOW_WIDTH = GameLevel.WINDOW_WIDTH;
    public static final int WINDOW_HEIGHT = GameLevel.WINDOW_HEIGHT;

    private KeyboardSensor sensor;
    private boolean stop;
    private T status;
    private List<MenuChoice<T>> choices;

    /**
     * Constructor.
     *
     * @param sensor keyboard sensor
     */
    public MenuAnimation(KeyboardSensor sensor) {
        this.choices = new ArrayList<>();
        this.sensor = sensor;
        this.stop = false;
    }

    @Override
    public void addSelection(String key, String message, T returnVal) {
        this.choices.add(new MenuChoice<>(key, message, returnVal));
    }

    @Override
    public T getStatus() {
        return this.status;
    }

    @Override
    public void doOneFrame(DrawSurface surface) {
        Block bg = new Block(new Point(0, 0), WINDOW_WIDTH, WINDOW_HEIGHT, Color.MAGENTA);
        bg.addFill(new arkanoid.sprites.Fill.Image("background_images/xp.jpg"));
        bg.drawOn(surface);

        int i = 0, diff = 50;

        for (MenuChoice choice : this.choices) {
            if (sensor.isPressed(choice.getKey())) {
                this.status = (T) choice.getReturnVal();
                shouldStop(true);
            }
            String msg = choice.getMessage() + " (" + choice.getKey() + ")";
            surface.setColor(Color.WHITE);
            surface.drawText(WINDOW_WIDTH / 4 - 4, WINDOW_HEIGHT / 3 + i * diff - 1, msg, 31);
            surface.setColor(Color.BLUE);
            surface.drawText(WINDOW_WIDTH / 4, WINDOW_HEIGHT / 3 + i * diff, msg, 30);
            i++;
        }
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }

    @Override
    public void shouldStop(boolean s) {
        this.stop = s;
    }

    @Override
    public void addSubMenu(String key, String message, Menu<T> subMenu, T returnVal) {
        subMenu.addSelection(key, message, returnVal);
    }
}
