package tablet.mod.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLever;
import net.minecraft.command.server.CommandTestForBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.text.TextComponentString;

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
	
	public static void send(EntityPlayer e, String s) {
		e.sendStatusMessage(new TextComponentString(s), false);
	}
	
	CommandTestForBlock x = new CommandTestForBlock();
	BlockLever x2;
	Block b = Blocks.REDSTONE_BLOCK;
	Block b2 = Blocks.REDSTONE_LAMP;
	
	int dx[] = {01, 00, 00, -1, 00, 00};
	int dy[] = {00, 01, 00, 00, -1, 00};
	int dz[] = {00, 00, 01, 00, 00, -1};
}
