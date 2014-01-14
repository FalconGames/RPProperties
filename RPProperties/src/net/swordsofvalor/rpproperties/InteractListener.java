package net.swordsofvalor.rpproperties;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class InteractListener implements Listener{

	static HashMap<Player, SelectionArea> PlayerSelections;
	
	private enum Clicks{
		RIGHT, LEFT
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e){
		
		Player p = e.getPlayer();
		
		int x = e.getClickedBlock().getX();
		int y = e.getClickedBlock().getY();
		int z = e.getClickedBlock().getZ();
		
		if(!PlayerSelections.containsKey(p)) PlayerSelections.put(p, new SelectionArea());
		Action a = e.getAction();
		if(p.getItemInHand() == new ItemStack(Material.WOOD_AXE)){
			switch(checkClick(a)){
			case RIGHT:
				PlayerSelections.put(p, PlayerSelections.get(p).setX2(x).setY2(y).setZ(z));
				break;
			case LEFT:
				PlayerSelections.put(p, PlayerSelections.get(p).setX(x).setY(y).setZ2(z));
				break;
			}
		}
		else if(e.getClickedBlock().getState() instanceof Sign){
			Sign s = (Sign) e.getClickedBlock().getState();
			if(s.getLine(0).equals("BUY PROPERTY")){
				RPProperties plugin = RPProperties.getInstance();
				for(Property prop : plugin.getAvailableProperties()){
					if(!plugin.getSoldProperties().containsValue(prop)){
						if(prop.getName().equals(s.getLine(1))){
							if(plugin.getEco().getBalance(p.getName()) > prop.getPrice()){
								plugin.getEco().withdrawPlayer(p.getName(), prop.getPrice());
								plugin.getSoldProperties().put(new Owner(p, 24), prop);
								plugin.getOwners().add(new Owner(p, 24));
							}
						}
					}
				}
			}
		}
		
	}
	
	private Clicks checkClick(Action e){
		if(e == Action.LEFT_CLICK_AIR || e == Action.LEFT_CLICK_BLOCK) return Clicks.LEFT;
		return Clicks.RIGHT;
	}

	public static HashMap<Player, SelectionArea> getPlayerSelections() {
		return PlayerSelections;
	}
	
}
