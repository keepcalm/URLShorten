package keepcalm.mods.forgechat.mute;

import keepcalm.mods.forgecore.ChatColor;
import keepcalm.mods.forgecore.api.permissions.Permissions;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.CommandBase;
import net.minecraft.src.CommandException;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ICommandSender;
import net.minecraft.src.Packet3Chat;

public class CommandMute extends CommandBase {
	
	
	@Override
	public String getCommandName() {
		return "mute";
	}

	@Override
	public String getCommandUsage(ICommandSender ics) {
		return "/mute <player>";
	}
	
	@Override
	public boolean canCommandSenderUseCommand(ICommandSender ics) {
		if (!(ics instanceof EntityPlayer)) {
			return true;
		}
		return Permissions.doesPlayerHavePermission("forgechat.mute", (EntityPlayer) ics);
	}
	
	@Override
	public void processCommand(ICommandSender var1, String[] var2) {
		if (var2.length < 1) {
			throw new CommandException("/mute <player>");
		}
		
		try {
			MutedRegistry.toggleMute(var2[0]);
		}
		catch (Exception e) {
			throw new CommandException(var2[0] + " is not logged on right now!");
		}
		
		MinecraftServer.getServer().getConfigurationManager().sendPacketToAllPlayers(new Packet3Chat(ChatColor.GRAY + "[" + var1.getCommandSenderName() + ": Muting " + var2[0] +"]"));
		var1.sendChatToPlayer("Muted.");
		
	}

}
