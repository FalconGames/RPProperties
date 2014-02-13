package net.swordsofvalor.rpproperties;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class RPProperties extends JavaPlugin{

	private ArrayList<Property> AvailableProperties;
	private ArrayList<Owner> Owners;
	private HashMap<Owner, Property> SoldProperties;
	private Economy eco;
	private static RPProperties instance;
	private UpdateRunnable updater;
	private BukkitScheduler schedule;
	
	@Override
	public void onEnable(){
		
		for(Plugin p : Bukkit.getServer().getPluginManager().getPlugins()) Bukkit.getLogger().info(p.getName());
		
		Bukkit.getLogger().info("Loading cats into box...");
		slow();
		
		if(!setupEconomy()){
			Logger.getLogger("Minecraft").severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		
		Bukkit.getLogger().info("Succesfully pickpocketed midgets...");
		slow();
		
		AvailableProperties = new ArrayList<Property>();
		SoldProperties = new HashMap<Owner, Property>();
		
		Bukkit.getLogger().info("Getting construction crew...");
		slow();
		
		schedule = Bukkit.getServer().getScheduler();
		updater = new UpdateRunnable(this);
		schedule.scheduleSyncRepeatingTask(this, updater, 0, 20);
		
		Bukkit.getLogger().info("Running around in a hampster wheel...");
		slow();
		
		initProperties();
		
		Bukkit.getLogger().info("Construction crew has gone home for the night...");
		slow();
		
		instance = this;
		
		Bukkit.getLogger().info("Didn't miss the urinal...");
		slow();
		
		this.getServer().getPluginManager().registerEvents(new InteractListener(), this);
		
		Bukkit.getLogger().info("Sh! I'm trying to listen!");
		slow();
		Bukkit.getLogger().info("Succesfully started RPProperties!");
		slow();
		
	}

	@Override
	public void onDisable(){
		instance = null;
		try{
			schedule.cancelAllTasks();
		}catch(NullPointerException e){}
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(cmd.getName().equalsIgnoreCase("propertycreate") && args.length == 2 && sender.hasPermission(new Permission("RPProperties.create"))){
			if(sender instanceof Player){
				Player p = (Player) sender;
				if(InteractListener.getPlayerSelections().containsKey(p)){
					AvailableProperties.add(new Property(args[0], InteractListener.getPlayerSelections().get(p), Float.valueOf(args[1])));
					return true;
				}
			}
		}
		if(cmd.getName().equalsIgnoreCase("propertydelete") && args.length == 1 && sender.hasPermission(new Permission("RPProperties.delete"))){
			for(Property p : AvailableProperties){
				if(p.getName().equalsIgnoreCase(args[0])) AvailableProperties.remove(p);
			}
		}
		return false;
	}
	
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        eco = rsp.getProvider();
        return eco != null;
    }
	
	private void initProperties() {
	
		AvailableProperties = new ArrayList<>();
		Owners = new ArrayList<>();
		SoldProperties = new HashMap<>();
		try{
			FileInputStream fileIn = new FileInputStream("properties.xml");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			AvailableProperties = (ArrayList<Property>) in.readObject();
			Owners = (ArrayList<Owner>) in.readObject();
			SoldProperties = (HashMap<Owner, Property>) in.readObject();
			in.close();
		}catch(Exception e){
			Bukkit.getLogger().severe("[RPProperties] - Could not load properties.xml!" + e);
		}
		
	}
	
	void save(){
		
		try{
			FileOutputStream fileOut = new FileOutputStream("/properties.xml");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(AvailableProperties);
			out.writeObject(Owners);
			out.writeObject(SoldProperties);
			out.close();
			fileOut.close();
		}catch(Exception e){
			Bukkit.getLogger().severe("[RPProperties] - Could not save properties to properties.xml!" + e);
		}
		
	}
	
	public static RPProperties getInstance(){
		return instance;
	}

	public ArrayList<Property> getAvailableProperties() {
		return AvailableProperties;
	}

	public ArrayList<Owner> getOwners(){
		return Owners;
	}
	
	public HashMap<Owner, Property> getSoldProperties() {
		return SoldProperties;
	}

	public Economy getEco() {
		return eco;
	}
	
	private void slow(){
		for(int i = 0; i < 100000; i++){
			double x = Math.sin(i) / 3;
			x += Math.tan(i) % 45644;
			x *= 7.12;
		}
	}
	
}
