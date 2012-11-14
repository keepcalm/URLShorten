package keepcalm.mods.forgechat.mute;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import keepcalm.mods.forgecore.ForgeCoreModContainer;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.Mod.ServerStarting;
import cpw.mods.fml.common.Mod.ServerStopping;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;

@Mod(modid="ForgeMute",name="ForgeMute",version="1.4.2-0")
public class MuteContainer {
	private static File cfg;
	
	public static Properties conf;
	
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
		
		
		
	}
	
	@Init
	public void init(FMLInitializationEvent ev) {
		MinecraftForge.EVENT_BUS.register(new MutedPlayerStopper());
	}
	
	@ServerStarting
	public void serverStarting(FMLServerStartingEvent ev) {
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
