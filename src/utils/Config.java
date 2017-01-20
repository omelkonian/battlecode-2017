package utils;

import java.util.HashMap;
import java.util.Map;

import teams.*;
import strategies.donation.*;

/**
 * Configuration variables.
 */
public class Config {
    // Players
    public static RobotTeam Player1 = new SingleFarmerTeam();
    public static RobotTeam Player2 = new IdleTeam();

    // Strategies
    public static DonationStrategy donationStrategy = new GreedyDonation();

    // Channels
    public enum Channel {
        ARCHON_SPAWNED,
        VERTICAL,
        HORIZONTAL;

        /**
         * @return actual index on team's array
         */
        public int index() {
            return channels.get(this);
        }
    }

    private static Map<Channel, Integer> channels = new HashMap<Channel, Integer>() {{
        put(Channel.VERTICAL, 0);
        put(Channel.HORIZONTAL, 1);
        put(Channel.ARCHON_SPAWNED, 666);
    }};

}
