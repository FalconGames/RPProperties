package net.swordsofvalor.rpproperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.configuration.serialization.*;

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
		
		if(!setupEconomy()){
			Logger.getLogger("Minecraft").severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		
		AvailableProperties = new ArrayList<Property>();
		SoldProperties = new HashMap<Owner, Property>();
		
		schedule = Bukkit.getServer().getScheduler();
		updater = new UpdateRunnable(this);
		schedule.scheduleSyncRepeatingTask(this, updater, 0, 20);
		
		initProperties();
		
		instance = this;
		
		this.getServer().getPluginManager().registerEvents(new InteractListener(), this);
		
	}

	@Override
	public void onDisable(){
		instance = null;
		schedule.cancelAllTasks();
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
	
}
