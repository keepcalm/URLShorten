package keepcalm.mods.urlshortener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Joiner;

import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.ServerChatEvent;

public class EventHandler {

	@ForgeSubscribe
	public void onChat(ServerChatEvent ev) {
		String msg = ev.line;
		
		String[] spaces = msg.split(" ");
		
		boolean foundUsername = false;
		String origUserString = "";
		
		Pattern p = Pattern.compile("(?i)\\b((?:https?://|www\\d{0,3}[.]|[a-z0-9.\\-]+[.][a-z]{2,4}/)(?:[^\\s()<>]+|\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\))+(?:\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\)|[^\\s`!()\\[\\]{};:'\".,<>?«»“”‘’]))");
		int x = 0;
		for (String i : spaces) {
			/*if (!foundUsername && !i.matches(ev.username)) {
				System.out.println(i);
				origUserString += i + " ";
				continue;
			}
			else if (i.matches(ev.username) && !foundUsername) {
				origUserString += i + " ";
				System.out.println(origUserString);
				foundUsername = true;
				continue;
			}*/
			Matcher j = p.matcher(i);
			if (j.matches()) {
				//System.out.println("Got a URL: " + i);
				//ev.player.sendChatToPlayer("Got a URL in your message: " + i);
				spaces[x] = URLMain.shortenURL(i);
			}
			x++;
		}
		ev.line = Joiner.on(" ").join(spaces);
	}
}
