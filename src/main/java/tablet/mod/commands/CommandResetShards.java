package tablet.mod.commands;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import tablet.mod.util.WorldData;

public class CommandResetShards extends CommandBase implements ICommand {

	@Override
	public int compareTo(ICommand c) {
		return getName().compareTo(c.getName());
	}

	@Override
	public String getName() {
		return "resetshards";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/resetshards [<arguments>]";
	}

	@Override
	public List<String> getAliases() {
		return new ArrayList();
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		WorldData.get(sender.getEntityWorld()).setShards(0);
		System.out.println("RESET SHARDS TO 0");
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		return true;
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos targetPos) {
		return new ArrayList();
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		return true;
	}
	
	public int getRequiredPermissionLevel()
    {
        return 2;
    }
}
