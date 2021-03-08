package me.emmetion.rewardsbungee.utils;

import me.emmetion.rewardsbungee.RewardsBungee;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.bukkit.entity.Player;

public class Util {

    public static ProxiedPlayer getProxiedPlayerFromPlayer(Player player){
        return RewardsBungee.plugin.getProxy().getPlayer(player.getUniqueId().toString());
    }

}
