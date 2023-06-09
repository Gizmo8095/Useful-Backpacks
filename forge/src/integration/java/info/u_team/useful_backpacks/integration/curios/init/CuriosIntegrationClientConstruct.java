package info.u_team.useful_backpacks.integration.curios.init;

import info.u_team.u_team_core.api.integration.Integration;
import info.u_team.u_team_core.api.integration.ModIntegration;
import info.u_team.u_team_core.util.registry.BusRegister;
import info.u_team.useful_backpacks.UsefulBackpacksMod;

@Integration(modid = UsefulBackpacksMod.MODID, integration = "curios", client = true)
public class CuriosIntegrationClientConstruct implements ModIntegration {
	
	@Override
	public void construct() {
		BusRegister.registerMod(CuriosIntegrationKeys::registerMod);
		BusRegister.registerMod(CuriosIntegrationRenderers::registerMod);
		
		BusRegister.registerForge(CuriosIntegrationKeys::registerForge);
	}
	
}
