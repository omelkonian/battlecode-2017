package teams;

import units.Farmer;

import static battlecode.common.RobotType.*;

public class SingleFarmerTeam extends SingleRobotTeam {
    public SingleFarmerTeam() {
        super(GARDENER);
    }

    @Override
    protected void runSingle() {
        new Farmer().run();
    }
}
