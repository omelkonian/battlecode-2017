package utils;

import java.util.HashMap;
import java.util.Map;

import teams.*;

/**
 * Configuration variables.
 */
public class Config {
    // Players
    public static RobotTeam Player1 = new SingleFarmerTeam();
    public static RobotTeam Player2 = new IdleTeam();

    // Channels
    public enum Channel {
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
    }};

}
