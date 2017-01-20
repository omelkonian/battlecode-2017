package strategies.donation;

import battlecode.common.GameActionException;

import static utils.Constants.bulletsLeftToWin;
import static utils.Current.I;

/**
 * Donates bullets greedily.
 */
public class GreedyDonation implements DonationStrategy {
    @Override
    public void donate() {
        int noSpendCeil = 2000;
        int donationAmount = 1000;
        if (I.getTeamBullets() > (noSpendCeil + donationAmount))
            try {
                I.donate(donationAmount);
            } catch (GameActionException e) {
                System.out.println("Cannot donate");
                e.printStackTrace();
            }
    }
}
