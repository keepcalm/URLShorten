package keepcalm.mods.forgechat.urlshortener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import keepcalm.mods.forgecore.ChatColor;

import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.ServerChatEvent;

import com.google.common.base.Joiner;

public class ShortenURLHandler {

	@ForgeSubscribe
	public void onChat(ServerChatEvent ev) {
		String msg = ev.line;

		String[] spaces = msg.split(" ");
		Pattern urlMatcher = Pattern.compile("(?i)\\b((?:https?://|www\\d{0,3}[.]|[a-z0-9.\\-]+[.][a-z]{2,4}/)(?:[^\\s()<>]+|\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\))+(?:\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\)|[^\\s`!()\\[\\]{};:'\".,<>?«»“”‘’]))");
		Pattern chatMatcher = Pattern.compile(".*&(0|1|2|3|4|5|6|7|8|9|a|b|c|d|e|f|k|l|m|n|o|r).*");
		if (URLMain.parseChatColours) {
			int j = 0;
			for (String i : spaces) {
				Matcher x = urlMatcher.matcher(i);
				if (x.matches()) {
					// in a URL - don't touch it.
					j++;
					continue;
				}
				
				x = chatMatcher.matcher(i);
				if (x.matches()) {
					System.out.println(i + " is a string with chat colours!");
					
					spaces[j] = ChatColor.translateAlternateColorCodes('&', i);
				}
				j++;
				
				
			}
			ev.line = Joiner.on(' ').join(spaces);
		}
		if (URLMain.shortenURLs) {
			boolean foundUsername = false;
			String origUserString = "";

			String result = "";
			int x = 0;
			for (String i : spaces) {
				Matcher j = urlMatcher.matcher(i);
				if (j.matches()) {
					result = URLMain.shortenURL(i) + " ";
				}
				x++;
			}
			ev.line = Joiner.on(" ").join(spaces);
		}
		
	}
}
