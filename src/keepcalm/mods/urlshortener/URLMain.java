package keepcalm.mods.urlshortener;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Property;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid="URLShorten", name="URL Shortener", version="1.4.2-1")
public class URLMain {
	public static boolean shortenURLs = true;
	public static boolean parseChatColours = false;
	public static String shortenURL(String currentURL) {
		try {
			URL libDownload = new URL("http://tinyurl.com/api-create.php?url=" + currentURL);
			URLConnection connection = libDownload.openConnection();
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);
			connection.setRequestProperty("User-Agent", "MC URL Shortener");
			char[] cb = new char[700];
			InputStream is = connection.getInputStream();
			InputStreamReader bis = new InputStreamReader(is);
			bis.read(cb);
			String x = String.valueOf(cb);
			x = x.replace('\0', ' ').trim();
			/*int lastIndex = 699;
			for (int i = 0; i < x.length(); i++) {
				if (x.charAt(i) == '\0') {
					lastIndex = i;
					break;
				}
			}*/
			//String res = x.substring(0, lastIndex);
			System.out.println(x);
			return x;
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return currentURL;
		}

	}
	
	@PreInit
	public void preInit(FMLPreInitializationEvent ev) {
		Configuration cfg = new Configuration(ev.getSuggestedConfigurationFile());
		cfg.load();
		
		Property enableURLShortening = cfg.get(cfg.CATEGORY_GENERAL, "allowChat", true);
		enableURLShortening.comment = "Automatically shorten URLs using TinyURL.com";
		shortenURLs = enableURLShortening.getBoolean(true);
		
		Property chatParse = cfg.get(cfg.CATEGORY_GENERAL, "useChatColours", false);
		chatParse.comment = "Change valid letters prefixed by an '&' symbol to colours and underlines.";
		parseChatColours = chatParse.getBoolean(false);
		System.out.println("Parse chat colours: " + parseChatColours);
		
		cfg.save();
		
	}

	@Init
	public void init(FMLInitializationEvent ev) {
		MinecraftForge.EVENT_BUS.register(new EventHandler());
	}



}
