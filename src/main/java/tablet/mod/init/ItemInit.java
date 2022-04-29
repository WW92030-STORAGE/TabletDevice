package tablet.mod.init;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;
import tablet.mod.items.Tablet;

public class ItemInit {
	public static final List<Item> ITEMS = new ArrayList<Item>();
	
	public static final Item TABLET = new Tablet("tablet");
}