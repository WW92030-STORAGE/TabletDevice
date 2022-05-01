package tablet.mod.blocks;

import java.util.Random;
import java.util.TreeMap;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import tablet.mod.Main;
import tablet.mod.init.BlockInit;
import tablet.mod.init.ItemInit;
import tablet.mod.util.IModel;
import tablet.mod.util.Reference;
import tablet.mod.util.WorldData;

public class BlockShardChecker extends Block implements IModel {
	public static TreeMap<String, Long> times = new TreeMap<String, Long>();
	public boolean powered = false;
	private int state = 0;
	private int threshold = 0;
	
	public void incrementState() {
		state = (state + 301) % 3;
	}
	
	public int getState() {
		return state;
	}
	
	public void setThreshold(int x) {
		threshold = x;
	}
	
	public int getThreshold() {
		return threshold;
	}
	
	public BlockShardChecker(String name, Material mat, CreativeTabs tab) {
		super(mat);
		setUnlocalizedName(name);
		setRegistryName(name);
		setSoundType(SoundType.METAL);
		setHarvestLevel("pickaxe", 1);
		setHardness(1F);
		setResistance(10F);
		setLightLevel(0F);
		setLightOpacity(255);
		setCreativeTab(tab);
		
		// this.setDefaultState(this.blockState.getBaseState().withProperty(STATE, Integer.valueOf(0)));
		
		BlockInit.BLOCKS.add(this);
		ItemInit.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}

	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}
	
	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		super.onBlockAdded(world, pos, state);
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		world.scheduleUpdate(new BlockPos(x, y, z), this, this.tickRate(world));
	}
	
	@Override
	public boolean canProvidePower(IBlockState state)
	{
    	return true;
	}

	@Override
	public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return this.powered ? 15 : 0;
	}

	@Override
	public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return this.powered ? 15 : 0;
	}
	
	public void display(EntityPlayer entity, BlockPos pos) {
		switch(this.getState()) {
		case 1:
			Reference.send(entity, pos + " now checks SHARDS > " + threshold);
			break;
		case 2:
			Reference.send(entity, pos + " now checks SHARDS < " + threshold);
			break;
		default:
			Reference.send(entity, pos + " now checks SHARDS == " + threshold);
			break;
		}
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer entity, EnumHand hand, EnumFacing direction, float hitX, float hitY, float hitZ) {
		long time = System.nanoTime();
		String id = Reference.id(entity);
		if (times.containsKey(id) && times.get(id) + 100 * 1000000 > time) return true;
		times.put(id, time);
		
		if (entity.getHeldItemMainhand().getItem() == Items.STICK) this.incrementState();
		else if (entity.getHeldItemMainhand().getItem() == Items.PAPER) {
			String s = entity.getHeldItemMainhand().getDisplayName();
			try {
				this.setThreshold(Integer.parseInt(s));
			}
			catch (Exception e) {
				
			}
		}
		
		display(entity, pos);
		return true;
	}
	
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random random) {
		super.updateTick(world, pos, state, random);
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		int s = WorldData.get(world).getShards();
		powered = condition(s);
		
		world.scheduleUpdate(new BlockPos(x, y, z), this, this.tickRate(world));
		world.notifyNeighborsOfStateChange(pos, this, false);
		
		switch(this.getState()) {
		case 1:
			break;
		case 2:
			break;
		default:
			break;
		}
	}
	
	public boolean condition(int x) {
		switch(this.getState()) {
		case 1:
			return x > threshold;
		case 2:
			return x < threshold;
		default:
			return x == threshold;
		}
	}
}
