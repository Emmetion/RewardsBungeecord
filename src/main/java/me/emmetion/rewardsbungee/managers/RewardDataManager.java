package me.emmetion.rewardsbungee.managers;

import me.emmetion.rewardsbungee.database.Database;
import me.emmetion.rewardsbungee.database.RewardData;
import net.md_5.bungee.api.connection.ProxiedPlayer;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class RewardDataManager {

    public static HashMap<String, RewardData> playerDataHashMap = new HashMap<>();


    public RewardDataManager() {

    }

    public static boolean hasPlayerData(ProxiedPlayer player) {
        return playerDataHashMap.containsKey(player.getUniqueId().toString());
    }

    public static void addPlayerData(ProxiedPlayer player) {
        String uuid = "";
        int tier1 = 0, tier2 = 0, tier3 = 0;
        try {
            PreparedStatement preparedStatement = Database.getConnection().prepareStatement("SELECT * FROM Rewards WHERE PlayerUUID=?");
            System.out.println("Attempting to retrieve data from H2!");
            preparedStatement.setString(1, player.getUniqueId().toString());

            ResultSet result = preparedStatement.executeQuery();

            while (result.next()) {

                uuid = result.getString("PlayerUUID");
                tier1 = result.getInt("Tier1");
                tier2 = result.getInt("Tier2");
                tier3 = result.getInt("Tier3");

                playerDataHashMap.put(player.getUniqueId().toString(), new RewardData(uuid, tier1, tier2, tier3));

                System.out.println("successfully placed data into playerDataHashMap!");

                return;
            }


        } catch (SQLException e) {
            System.out.println("Issue when retrieving playerdata from h2!");

            e.printStackTrace();
        }


        if (uuid == "") {
            System.out.println("Issue inserting data into table, doesn't exist!");
            return;
        }

        playerDataHashMap.put(player.getUniqueId().toString(), new RewardData(uuid, tier1, tier2, tier3));
    }

    public static void removePlayerData(ProxiedPlayer player) {
        if (hasPlayerData(player)) {
            playerDataHashMap.remove(player.getUniqueId().toString());
        }
    }

    public static RewardData getRewardData(ProxiedPlayer player) {

        if (hasPlayerData(player)) {
            String uuid = player.getUniqueId().toString();
            return playerDataHashMap.get(uuid);
        }

        return null;
    }



    public HashMap<String, RewardData> getPlayerDataHashMap() {
        return playerDataHashMap;
    }

}
