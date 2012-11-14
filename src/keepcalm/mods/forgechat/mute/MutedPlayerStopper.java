package keepcalm.mods.forgechat.mute;

import keepcalm.mods.forgecore.ChatColor;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.ServerChatEvent;

public class MutedPlayerStopper {

	
	@ForgeSubscribe
	public void serverChat(ServerChatEvent ev) {
		if (!MutedRegistry.canPlayerChat(ev.player.username)) {
			ev.setCanceled(true);
			ev.player.sendChatToPlayer(ChatColor.RED + "You cannot speak!" + ChatColor.RESET);
		}
	}
}
