package tablet.mod.util;

import java.util.TreeMap;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;

public class WorldData extends WorldSavedData {
	private static final String DATA_NAME = Reference.MODID + "_Data";
	
	private final static boolean DEBUG = false;
	private int shards;
	
	// Required constructors
	public WorldData() {
		this(DATA_NAME);
	}
	
	public WorldData(String s) {
		super(s);
	}

	public static WorldData get(World world) {
		MapStorage storage = world.getMapStorage();
		WorldData instance;
		instance = (WorldData) storage.getOrLoadData(WorldData.class, DATA_NAME);
	//	instance = (WorldData) world.loadData(WorldData.class, DATA_NAME);
        
		if (instance == null) {
			System.out.println("NO DATA FOUND - CREATING NEW");
			instance = new WorldData();
			storage.setData(DATA_NAME, instance);
		}
		else if (DEBUG) {
			System.out.println("DATA FOUND FOR CURRENT WORLD");
			System.out.println("TOTAL SHARDS COLLECTED - " + instance.shards);
		}
		return instance;
	}
	
	@Override
    public void readFromNBT(NBTTagCompound nbt) {
        int tempx = nbt.getInteger("shards");
        shards = tempx;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
    	compound.setInteger("shards", shards);
        return compound;
    }
    
    public void setShards(int x) {
    	shards = x;
    	markDirty();
    }
    
    public int getShards() {
    	return this.shards;
    }
}
