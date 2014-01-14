package net.swordsofvalor.rpproperties;

import org.bukkit.scheduler.BukkitRunnable;

public class UpdateRunnable extends BukkitRunnable{

	private RPProperties plugin;
	
	public UpdateRunnable(RPProperties plugin){
		this.plugin = plugin;
	}
	
	@Override
	public void run() {
		for(Owner o : plugin.getOwners()){
			o.updateTime();
			if(o.getSecondsRemaining() <= 0){
				plugin.getSoldProperties().remove(o);
				plugin.getOwners().remove(o);
			}
		}
	}

}
