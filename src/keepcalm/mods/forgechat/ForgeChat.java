package keepcalm.mods.forgechat;

import static keepcalm.mods.forgecore.ForgeModsVersioning.DISPLAY_VER;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import keepcalm.mods.forgechat.mute.CommandMute;
import keepcalm.mods.forgechat.mute.MutedPlayerStopper;
import keepcalm.mods.forgechat.mute.MutedRegistry;
import keepcalm.mods.forgechat.urlshortener.ShortenURLHandler;
import keepcalm.mods.forgecore.ForgeCoreModContainer;

import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.CrashReport;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.GuiErrorScreen;
import net.minecraft.src.MinecraftException;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Property;


import cpw.mods.fml.client.CustomModLoadingErrorDisplayException;
import cpw.mods.fml.client.GuiCustomModLoadingErrorScreen;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Metadata;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.Mod.ServerStarting;
import cpw.mods.fml.common.Mod.ServerStopping;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.versioning.ArtifactVersion;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.server.FMLServerHandler;
import cpw.mods.fml.common.ModContainer;

/**
 * @author keepcalm
 *
 */
@Mod(modid="ForgeChat", name="ForgeChat", version="1.4.2-0")
//@Mod(modid="ForgeMute",name="ForgeMute",version="1.4.2-0")
public class ForgeChat {
	private static File cfg;
	
	public static Properties conf;
	
	public static boolean shortenURLs = true;
	public static boolean parseChatColours = false;
	
	@PreInit
	public void preInit(FMLPreInitializationEvent ev) {
		File tmp = ev.getModConfigurationDirectory();
		cfg = new File(tmp, "/ForgeMute.properties");
		
		conf = new Properties();
		try {
			conf.load(new FileReader(cfg));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Configuration cfg = new Configuration(ev.getSuggestedConfigurationFile());
		cfg.load();
		
		Property enableURLShortening = cfg.get(cfg.CATEGORY_GENERAL, "allowChat", true);
		enableURLShortening.comment = "Automatically shorten URLs using TinyURL.com";
		shortenURLs = enableURLShortening.getBoolean(true);
		
		Property chatParse = cfg.get(cfg.CATEGORY_GENERAL, "useChatColours", false);
		chatParse.comment = "Change valid letters prefixed by an '&' symbol to colours and underlines.";
		parseChatColours = chatParse.getBoolean(false);
		//System.out.println("Parse chat colours: " + parseChatColours);
		
		cfg.save();
		
		
	}
	
	@Init
	public void init(FMLInitializationEvent ev) {
		
		MinecraftForge.EVENT_BUS.register(new MutedPlayerStopper());
		MinecraftForge.EVENT_BUS.register(new ShortenURLHandler());
		ForgeCoreModContainer.registerCommand(new CommandMute());
	}
	

	@ServerStopping
	public void serverStopping(FMLServerStoppingEvent ev) {
		MutedRegistry.saveConfiguration();
		try {
			conf.store(new FileWriter(cfg), "Can various players talk");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

