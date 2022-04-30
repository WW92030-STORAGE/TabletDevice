package tablet.mod.procedures;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import tablet.mod.util.Reference;
import tablet.mod.util.WorldData;


@Mod.EventBusSubscriber(modid = Reference.MODID)
public class GeneralProcedure {
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public static void playerConnected(PlayerEvent.PlayerLoggedInEvent event) {
		WorldData wd = WorldData.get(event.player.world);
		System.out.println("TOTAL SHARDS - " + wd.getShards());
	}
	
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public static void playerDisconnected(PlayerEvent.PlayerLoggedOutEvent event) {
		WorldData wd = WorldData.get(event.player.world);
		System.out.println("SAVING WORLD - TOTAL SHARDS = " + wd.getShards());
	}
	
	public void preInit(FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(this);
	}
}
