package strategies.donation;

import battlecode.common.GameActionException;

import static utils.Constants.bulletsLeftToWin;
import static utils.Current.I;

/**
 * Donates for an instant win only.
 */
public class InstaWin implements DonationStrategy {
    @Override
    public void donate() {
        float leftToWin = bulletsLeftToWin();
        if (I.getTeamBullets() >= leftToWin)
            try {
                I.donate(leftToWin);
            } catch (GameActionException e) {
                System.out.println("Cannot donate");
                e.printStackTrace();
            }
    }
}
