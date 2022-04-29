package tablet.mod.items;

import java.util.TreeMap;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import tablet.mod.Main;
import tablet.mod.init.ItemInit;
import tablet.mod.util.IModel;
import tablet.mod.util.Reference;

public class Tablet extends Item implements IModel {
	public static TreeMap<String, Boolean> state = new TreeMap<String, Boolean>();
	public static TreeMap<String, Long> times = new TreeMap<String, Long>();
	
	public Tablet(String name) {
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(CreativeTabs.TOOLS);
		
		ItemInit.ITEMS.add(this);
	}
	
	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(this, 0, "inventory");
	}
	
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		long time = System.nanoTime();
		ActionResult<ItemStack> res = super.onItemRightClick(world, player, hand);
		String id = Reference.id(player);
		if (times.containsKey(id) && times.get(id) + 100 * 1000000 > time) return res;
		if (!state.containsKey(id)) state.put(id, false);
		state.put(id, !state.get(id));
		times.put(id, time);
		
		return res;
	}
	
	public static boolean get(String s) {
		if (!state.containsKey(s)) return false;
		return state.get(s);
	}
}
