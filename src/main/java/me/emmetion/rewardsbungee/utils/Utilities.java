package me.emmetion.rewardsbungee.utils;

import me.emmetion.rewardsbungee.RewardsBungee;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.bukkit.entity.Player;

public class Utilities {

    public static ProxiedPlayer getPlayerFromProxy(Player player){

        ProxiedPlayer proxPlayer = RewardsBungee.plugin.getProxy().getPlayer(player.getUniqueId().toString());

        if (proxPlayer == null){
            System.out.println("RewardsBungee >> getPlayerFromProxy() - Player Doesn't Exist!");
            return null;
        }

        return proxPlayer;
    }

}
