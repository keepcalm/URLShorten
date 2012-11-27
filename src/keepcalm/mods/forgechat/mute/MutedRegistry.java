package keepcalm.mods.forgechat.mute;

import java.util.HashMap;
import java.util.Map;

import keepcalm.mods.forgechat.ForgeChat;
import keepcalm.mods.forgecore.api.permissions.Permissions;

public class MutedRegistry {
	
	private static Map<String,Boolean> canChat = new HashMap<String, Boolean>();
	
	public static void setPlayerCanChat(String name, boolean chat) {
		canChat.put(name.toLowerCase(), chat);
	}
	
	public static boolean canPlayerChat(String name) {
		try {
			return canChat.get(name.toLowerCase());
		}
		catch (Exception e) {
			return true;
		}
	}
	
	public static void toggleMute(String name) {
		boolean old = canPlayerChat(name);
		setPlayerCanChat(name, !old);
	}
	
	
	public static void saveConfiguration() {
		for (String i : canChat.keySet()) {
			ForgeChat.conf.setProperty(i, canChat.get(i).toString());
		}
	}
}
