package arkanoid.listeners;

/**
 * Interface Name: HitNotifier.
 * <p>
 * This interface represents all objects that send notifications when they are being hit.
 *
 * @author Rony Utevsky - ronyut@gmail.com - 319384657 - utevskr
 * @version 1.0.2
 * @since 11 May 2019
 */
public interface HitNotifier {
    /**
     * addHitListener: Add hl as a listener to hit events.
     *
     * @param hl the HitListener that will receive notifications from the HitNotifier
     */
    void addHitListener(HitListener hl);

    /**
     * addHitListener: Remove hl from the list of listeners to hit events.
     *
     * @param hl the HitListener that will stop receiving notifications from the HitNotifier
     */
    void removeHitListener(HitListener hl);
}