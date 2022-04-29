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
	
	public static ArrayList<EntityEntry> HOSTILES = new ArrayList<EntityEntry>();
	public static ArrayList<Class> HOSTILE_CLASS = new ArrayList<Class>();
	
	public static ArrayList<EntityEntry> entityList() {
		Collection<EntityEntry> c = (net.minecraftforge.fml.common.registry.ForgeRegistries.ENTITIES).getValuesCollection();
		ArrayList<EntityEntry> res = new ArrayList<EntityEntry>();
		for (EntityEntry i : c) res.add(i);
		return res;
	}
	
	public static ArrayList<EntityEntry> entityList(Class filter) {
		Collection<EntityEntry> c = (net.minecraftforge.fml.common.registry.ForgeRegistries.ENTITIES).getValuesCollection();
	//	for (EntityEntry e : c) System.out.println(e.getName() + " = " + e.getEntityClass() + " | " + e.getRegistryName());
		
		ArrayList<EntityEntry> res = new ArrayList<EntityEntry>();
		for (EntityEntry i : c) {
			Class x = i.getEntityClass();
			if (x.isAssignableFrom(filter) || filter.isAssignableFrom(x)) {
				res.add(i);
			}
		}
		return res;
	}
	
	public static void initData() {
		Reference.HOSTILES = Reference.entityList(EntityMob.class);
		for (EntityEntry e : HOSTILES) HOSTILE_CLASS.add(e.getEntityClass());
	}
	
	public static boolean rareRNG() {
		return Math.random() < 1.0 / (1<<16);
	}
	
	public static String id(EntityPlayer ep) {
		return ep.getUUID(ep.getGameProfile()).toString();
	}
}
