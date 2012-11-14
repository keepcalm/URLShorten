package keepcalm.mods.forgechat.mute;

import java.util.HashMap;
import java.util.Map;

public class MutedRegistry {
	
	private static Map<String,Boolean> canChat = new HashMap<String, Boolean>();
	
	public static void setPlayerCanChat(String name, boolean chat) {
		canChat.put(name.toLowerCase(), chat);
	}
	
	public static boolean canPlayerChat(String name) {
		return canChat.get(name.toLowerCase());
	}
	
	public static void toggleMute(String name) {
		boolean old = canPlayerChat(name);
		setPlayerCanChat(name, !old);
	}
	
	
	public static void saveConfiguration() {
		for (String i : canChat.keySet()) {
			MuteContainer.conf.setProperty(i, canChat.get(i).toString());
		}
	}
}
