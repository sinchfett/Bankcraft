package de.hotmail.gurkilein.bankcraft;
//All of the imports.
import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class MinecraftChatListener implements Listener{
    private Bankcraft bankcraft;
    
    private HashMap <Player, String> chosenInteraction = new HashMap <Player, String>(); 

	public MinecraftChatListener(Bankcraft bankcraft) {
		this.bankcraft = bankcraft; 
	}

	@EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
		if (!((Integer)bankcraft.getInteractionHandler().getChatSignMap().get(event.getPlayer()) == 0)) {
		//1 = deposit, xpdeposit, withdraw etc.
			if ((Integer)bankcraft.getInteractionHandler().getChatSignMap().get(event.getPlayer()) == 1) {
			if (bankcraft.getInteractionHandler().getTypeMap().containsKey(event.getMessage())) {
				chosenInteraction.put(event.getPlayer(), event.getMessage());
				bankcraft.getInteractionHandler().getChatSignMap().put(event.getPlayer(), 2);
				bankcraft.getConfigurationHandler().printMessage(event.getPlayer(), "message.specifyAnAmount", "", event.getPlayer().getName());
			}
			else
			{
				//We will add an error message here, if it is not a correct interaction.
				bankcraft.getConfigurationHandler().printMessage(event.getPlayer(), "message.nonExistingInteraction", "", event.getPlayer().getName());
			}
		}else
			//Waiting for player to input a value.
			if ((Integer)bankcraft.getInteractionHandler().getChatSignMap().get(event.getPlayer()) == 2) {
				if (event.getMessage().equalsIgnoreCase("all") ||  Util.isInteger(event.getMessage())) {
					
					//Start interaction
					bankcraft.getInteractionHandler().interact(chosenInteraction.get(event.getPlayer()),event.getMessage(), event.getPlayer(), event.getPlayer().getName());
					
					//Reset interact
					bankcraft.getInteractionHandler().getChatSignMap().put(event.getPlayer(), 0);
					
				} else {
					//Send error message
					bankcraft.getConfigurationHandler().printMessage(event.getPlayer(), "message.wrongAmountSyntax", "", event.getPlayer().getName());
				}
			}
		}
	}
}
