package me.emmetion.rewardsbungee.listeners;

import me.emmetion.rewardsbungee.database.RewardData;
import me.emmetion.rewardsbungee.managers.RewardDataManager;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.*;
import net.md_5.bungee.api.plugin.Event;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import org.bukkit.entity.Player;

public class JoinQuitListener implements Listener {

    @EventHandler
    public void onEvent(ServerConnectedEvent event){

        ProxiedPlayer player = event.getPlayer();
        
        ComponentBuilder component = new ComponentBuilder();

        component.append("Event Handled!");

        player.sendMessage(component.create());

        if (RewardDataManager.hasPlayerData(player)){
            return;
        }

        RewardDataManager.addPlayerData(player);


    }

    @EventHandler
    public void onQuitEvent(PlayerDisconnectEvent event){
        ProxiedPlayer player = event.getPlayer();
        if (RewardDataManager.hasPlayerData(player)){
            RewardDataManager.removePlayerData(player);
        }

    }


}
