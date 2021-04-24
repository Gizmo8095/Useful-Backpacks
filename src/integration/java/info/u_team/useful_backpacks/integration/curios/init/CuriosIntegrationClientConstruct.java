package info.u_team.useful_backpacks.integration.curios.init;

import info.u_team.u_team_core.api.integration.IModIntegration;
import info.u_team.u_team_core.api.integration.Integration;
import info.u_team.u_team_core.util.registry.BusRegister;
import info.u_team.useful_backpacks.UsefulBackpacksMod;

@Integration(modid = UsefulBackpacksMod.MODID, integration = "curios", client = true)
public class CuriosIntegrationClientConstruct implements IModIntegration {
	
	@Override
	public void construct() {
		BusRegister.registerMod(CuriosIntegrationKeys::registerMod);
		
		BusRegister.registerForge(CuriosIntegrationKeys::registerForge);
	}
	
}
