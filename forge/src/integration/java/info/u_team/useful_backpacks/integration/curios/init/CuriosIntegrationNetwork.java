package info.u_team.useful_backpacks.integration.curios.init;

import java.util.Optional;

import info.u_team.useful_backpacks.UsefulBackpacksMod;
import info.u_team.useful_backpacks.integration.curios.network.OpenBackpackMessage;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class CuriosIntegrationNetwork {
	
	public static final String PROTOCOL = "1.19-1";
	
	public static final SimpleChannel NETWORK = NetworkRegistry.newSimpleChannel(new ResourceLocation(UsefulBackpacksMod.MODID, "curios"), () -> PROTOCOL, PROTOCOL::equals, PROTOCOL::equals);
	
	public static void setup(FMLCommonSetupEvent event) {
		NETWORK.registerMessage(0, OpenBackpackMessage.class, OpenBackpackMessage::encode, OpenBackpackMessage::decode, OpenBackpackMessage.Handler::handle, Optional.of(NetworkDirection.PLAY_TO_SERVER));
	}
	
	public static void registerMod(IEventBus bus) {
		bus.addListener(CuriosIntegrationNetwork::setup);
	}
	
}