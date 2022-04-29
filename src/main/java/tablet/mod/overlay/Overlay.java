package tablet.mod.overlay;

import java.util.ArrayList;
import java.util.HashSet;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tablet.mod.items.Tablet;
import tablet.mod.util.Reference;
import tablet.mod.util.pair;

public class Overlay {
	static int radius = 32;
	static int size = 2 * radius + 1;
	static int depth = 16;
	
	public void rect(Entity e, int x, int y, int width, int height, int col, int opacity) {
		Minecraft.getMinecraft().ingameGUI.drawRect(x, y, x + width, y + height, col + opacity * (1<<24));
	}
	
	public int floor(double x) {
		return (int)(Math.floor(x));
	}
	
	public int ceil(double x) {
		return (int)(Math.ceil(x));
	}
	
	public IBlockState ib(World world, int x, int y, int z) {
		return world.getBlockState(new BlockPos(x, y, z));
	}
	
	public IBlockState ib(World world, double x, double y, double z) {
		return ib(world, floor(x), floor(y), floor(z));
	}
	
	static ArrayList<Entity> aabb(Entity e, int x1, int y1, int z1, int x2, int y2, int z2) {
		BlockPos bp1 = new BlockPos(x1, y1, z1);
		BlockPos bp2 = new BlockPos(x2, y2, z2);
		AxisAlignedBB aabb = new AxisAlignedBB(bp1, bp2);
		ArrayList<Entity> things = (ArrayList<Entity>) e.world.getEntitiesWithinAABBExcludingEntity(e, aabb);
		return things;
	}
	
	public float dir(Entity e) {
		double d = ((-1 * (e.rotationYaw) + 180 + 720) % 360) * Math.PI / 180.0;
		return (float)d;
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	@SideOnly(Side.CLIENT)
	public void eventHandler(RenderGameOverlayEvent event) {
		if (!event.isCancelable() && event.getType() == RenderGameOverlayEvent.ElementType.HELMET) {
			int posX = (event.getResolution().getScaledWidth()) / 2;
			int posY = (event.getResolution().getScaledHeight()) / 2;
			EntityPlayer entity = Minecraft.getMinecraft().player;
			World world = entity.world;
			
			String id = Reference.id(entity);
			if (!Tablet.get(id)) return;
			
			double x = entity.posX;
			double y = entity.posY;
			double z = entity.posZ;
			
			int x1 = floor(x - 2 * radius);
			int x2 = floor(x + 2 * radius);
			int z1 = floor(z - 2 * radius);
			int z2 = floor(z + 2 * radius);
			
			HashSet<pair> blocks = new HashSet<pair>();
			HashSet<Vec3d> entities = new HashSet<Vec3d>();
			for (int i = x1; i <= x2; i++) {
				for (int j = z1; j <= z2; j++) {
					IBlockState b1 = ib(world, i, floor(y), j);
					IBlockState b2 = ib(world, j, floor(y + 1), j);
					if (b1.getMaterial().isSolid() || b2.getMaterial().isSolid()) blocks.add(new pair(i, j));
				}	
			}
			
			ArrayList<Entity> mobs = aabb(entity, x1, 0, z1, x2, 255, z2);
			for (Entity e : mobs) {
				Class c0 = e.getClass();
				if (c0.isAssignableFrom(EntityMob.class) || (EntityMob.class).isAssignableFrom((c0))) {
					entities.add(e.getPositionVector());
				}
			}
			
			if (Reference.rareRNG()) System.out.println(entities.size() + " ENTITIES DETECTED IN THE SQUARE OF LENGTH " + size);
			
			this.rect(entity, 0, 0, size, size, 0, 255);
			
			for (pair p : blocks) {
				 Vec3d v = new Vec3d(p.x + 0.5, 0, p.y + 0.5).subtract(entity.getPositionVector());
				 v = v.rotateYaw(-1 * dir(entity));
				 v = v.add(new Vec3d(radius, 0, radius));
				 if (v.x < 0 || v.x >= size) continue;
				 if (v.z < 0 || v.z >= size) continue;
				 
				 /*
				 IBlockState ibs = ib(world, p.x, y, p.y);
				 MapColor mc = ibs.getMapColor(world, new BlockPos(v.x, y, v.z));
				 if (Reference.rareRNG()) System.out.println(mc.colorValue);
				 this.rect(entity, floor(v.x), floor(v.z), 1, 1, mc.colorValue % (1<<24), 255);
				 */
				 
				 this.rect(entity, floor(v.x), floor(v.z), 1, 1, 0x797979, 255);
			}
			
			for (Vec3d v : entities) {
				v = v.subtract(entity.getPositionVector());
				v = v.rotateYaw(-1 * dir(entity));
				v = v.add(new Vec3d(radius, 0, radius));
				if (v.x < 0 || v.x >= size) continue;
				if (v.z < 0 || v.z >= size) continue;
				
				int opacity = (int) Math.abs(v.y);
				opacity = (int)(255.0 * Math.max(0, depth - opacity) / depth);
				
				this.rect(entity, floor(v.x), floor(v.z), 1, 1, 0xFF0000, opacity);
			}
			
			this.rect(entity, radius, radius, 1, 1, 0x00FFFF, 255);
		}
	}
}
