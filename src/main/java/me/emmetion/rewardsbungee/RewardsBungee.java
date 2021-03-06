package me.emmetion.rewardsbungee;

import me.emmetion.rewardsbungee.database.Database;
import me.emmetion.rewardsbungee.database.RewardData;
import me.emmetion.rewardsbungee.listeners.JoinQuitListener;
import me.emmetion.rewardsbungee.managers.RewardDataManager;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;


import java.util.concurrent.TimeUnit;

public final class RewardsBungee extends Plugin {

    public static String connectionURL = "";
    public static RewardsBungee plugin;

    @Override
    public void onEnable() {
        connectionURL = "jdbc:h2:" + getProxy().getPluginsFolder().getAbsolutePath() + "/data/rewards";

        Database.initializeDatabase();

        plugin = this;

        getProxy().getPluginManager().registerListener(this, new JoinQuitListener());

        getProxy().getScheduler().schedule(this,() -> {
            System.out.println("ran");
            for (ProxiedPlayer p : getProxy().getPlayers()){

                if (RewardDataManager.hasPlayerData(p) == false){



                    return;
                }

                if (RewardDataManager.getRewardData(p).getChanged() == false){
                    continue;
                }
                RewardDataManager.getRewardData(p).setChanged(false);


                RewardData data = RewardDataManager.getRewardData(p);

                data.print();

                Database.saveRewardsData(p);

            }

            getProxy().getPluginManager().registerListener(this, new JoinQuitListener());


        },0L,5L, TimeUnit.SECONDS);









    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
