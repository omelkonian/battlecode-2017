package teams;

import utils.Current;
import units.*;
import units.soldiers.*;

public class SimpleEvaderSoldierTeam implements RobotTeam {
 
	public void run() {
		try {
		switch(Current.MyType()) {
	      case ARCHON: new DefaultArchon().run(); 
	      case GARDENER: new DefaultGardener().run(); 
	      case SOLDIER: new SimpleEvaderSoldier().run();
	      case LUMBERJACK: new DefaultLumberjack().run();
	      //case SCOUT: new DefaultScout().run();
	      //case TANK: new DefaultTank().run();
		}
	} catch (Exception e) {
		//TODO
	}
	}
	
}