package arkanoid.util;

/**
 * Class Name: Counter.
 * <p>
 * This class represent counters that can count.
 *
 * @author Rony Utevsky - ronyut@gmail.com - 319384657 - utevskr
 * @version 1.0.0
 * @since 11 May 2019
 */
public class Counter {
    private int count;

    /**
     * Constructor 1.
     *
     * @param number the initial count
     */
    public Counter(int number) {
        this.count = number;
    }

    /**
     * Constructor 2.
     */
    public Counter() {
        this.count = 0;
    }

    /**
     * increase: add number to current count.
     *
     * @param number the number to add to the count
     */
    public void increase(int number) {
        this.count += number;
    }

    /**
     * increase: subtract number from current count.
     *
     * @param number the number to subtract from the count
     */
    public void decrease(int number) {
        this.count -= number;
    }

    /**
     * getValue: get current count.
     *
     * @return the current value
     */
    public int getValue() {
        return this.count;
    }
}