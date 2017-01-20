package units.archons;

import static utils.Config.donationStrategy;

/**
 * Checks for victory points each round.
 *
 * @author Orestis Melkonian
 */
public interface DonatorMixin extends Archon {
    @Override
    default void prerun() {
        donationStrategy.donate();
    }
}
