package tablet.mod.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import tablet.mod.Main;
import tablet.mod.init.BlockInit;
import tablet.mod.init.ItemInit;
import tablet.mod.util.IModel;
import tablet.mod.util.WorldData;

public class BlockSoulShard extends Block implements IModel{
	
	public BlockSoulShard(String name, Material mat, CreativeTabs tab) {
		super(mat);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(tab);
		setHarvestLevel("pickaxe", 1);
		setHardness(1F);
		setResistance(10F);
		setLightLevel(10F);
		setLightOpacity(0);
		setBlockUnbreakable();
		setSoundType(SoundType.GLASS);
		
		BlockInit.BLOCKS.add(this);
		ItemInit.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}
	
	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isTranslucent(IBlockState state) {
		return true;
	}
	
	@Override
	@javax.annotation.Nullable
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return NULL_AABB;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		double a = 3.0 / 8.0;
		double b = 5.0 / 8.0;
		return new AxisAlignedBB(a, a, a, b, b, b);
	}
	
	@Override
	public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
		return true;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.TRANSLUCENT;
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
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {
		super.onEntityCollidedWithBlock(world, pos, state, entity);
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		Block block = this;
		
		if (entity instanceof EntityPlayer) {
			BlockPos bp = new BlockPos(x, y, z);
			world.destroyBlock(bp, false);
			world.notifyNeighborsOfStateChange(bp, world.getBlockState(bp).getBlock(), true);
			
			System.out.println("SOUL SHARD DESTROYED AT " + x + " " + y + " " + z);
			WorldData.get(world).setShards(WorldData.get(world).getShards() + 1);
			System.out.println("TOTAL SHARDS - " + WorldData.get(world).getShards());
		}
	}

}