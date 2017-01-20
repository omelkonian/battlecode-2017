package teams;

import units.gardeners.Farmer;

import static battlecode.common.RobotType.*;

public class SingleFarmerTeam extends SingleRobotTeam {
    public SingleFarmerTeam() {
        super(GARDENER);
    }

    @Override
    protected void runSingle() {
        new Farmer(4).run();
    }
}
