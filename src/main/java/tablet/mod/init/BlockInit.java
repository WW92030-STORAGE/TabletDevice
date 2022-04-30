package tablet.mod.init;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import tablet.mod.blocks.BlockSoulShard;

public class BlockInit {
	public static final List<Block> BLOCKS = new ArrayList<Block>();
	
	public static final Block SHARD = new BlockSoulShard("soulshard", Material.GLASS, CreativeTabs.REDSTONE);
}
