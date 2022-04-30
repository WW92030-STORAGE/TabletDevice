package tablet.mod.util;

import java.util.ArrayList;
import java.util.Collection;

import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.registry.EntityEntry;

public class Reference {
	public static final String MODID = "tablet";
	public static final String NAME = "Map View";
	public static final String VERSION = "1.0.0";
	public static final String COMMON = "tablet.mod.proxy.CommonProxy";
	public static final String CLIENT = "tablet.mod.proxy.ClientProxy";
	
	public static boolean rareRNG() {
		return Math.random() < 1.0 / (1<<16);
	}
	
	public static String id(EntityPlayer ep) {
		return ep.getUUID(ep.getGameProfile()).toString();
	}
}
