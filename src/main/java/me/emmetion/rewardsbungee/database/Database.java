package me.emmetion.rewardsbungee.database;

import me.emmetion.rewardsbungee.RewardsBungee;
import me.emmetion.rewardsbungee.managers.RewardDataManager;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.bukkit.entity.Player;

import java.sql.*;

public class Database {

    public Database() {

    }

    /**
     * If database doesn't exist, it will create a new one at the desired location.
     * Database contains 4 values, (PlayerUUID, Tier1, Tier2, Tier3)
     */

    public static void initializeDatabase() {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection connection = Database.getConnection();
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Rewards(PlayerUUID varchar, Tier1 int, Tier2 int, Tier3 int);");

            preparedStatement.execute();

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Issue creating table!");
        }

    }

    /**
     * Prepare Database Statements with this method!
     * @return Connection
     * @throws SQLException
     */

    public static Connection getConnection() {
        Connection connection = null;
        try {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                System.out.println("Driver Error!");
                e.printStackTrace();
                return connection;
            }
            connection = DriverManager.getConnection(RewardsBungee.connectionURL);
            return connection;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return connection;
    }

    public static boolean hasRewardsData(ProxiedPlayer player) {
        Connection connection = getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Rewards WHERE PlayerUUID=?");
            statement.setString(1, player.getUniqueId().toString());

            ResultSet resultSet = statement.executeQuery();
            int t = 0;

            while (resultSet.next()) {
                t++;
            }
            if (t > 0) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Issue Selecting from Rewards Database! containsPlayer() method");
        }
        return false;
    }

    public static boolean hasRewardsData(Player player) {
        ProxiedPlayer proxiedPlayer = RewardsBungee.plugin.getProxy().getPlayer(player.getUniqueId().toString());
        Connection connection = getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Rewards WHERE PlayerUUID=?");
            statement.setString(1, proxiedPlayer.getUniqueId().toString());

            ResultSet resultSet = statement.executeQuery();
            int t = 0;

            while (resultSet.next()) {
                t++;
            }
            if (t > 0) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Issue Selecting from Rewards Database! containsPlayer() method");
        }
        return false;
    }

    public static void saveRewardsData(ProxiedPlayer player) {

        if (!hasRewardsData(player)) {
            return;
        }

        Connection connection = getConnection();
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement("UPDATE Rewards SET Tier1 = ?, Tier2 = ?, Tier3 = ? WHERE PlayerUUID = ?;");

            RewardData data = RewardDataManager.getRewardData(player);

            statement.setInt(1, data.getTier1count());
            statement.setInt(2, data.getTier2count());
            statement.setInt(3, data.getTier3count());

            statement.setString(4, player.getUniqueId().toString());

            statement.execute();


            System.out.println("Successfully updated players rewarddata!");

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public static void saveRewardsData(Player player) {
        ProxiedPlayer proxiedPlayer = RewardsBungee.plugin.getProxy().getPlayer(player.getUniqueId().toString());

        if (!hasRewardsData(proxiedPlayer)) {
            return;
        }

        Connection connection = getConnection();
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement("UPDATE Rewards SET Tier1 = ?, Tier2 = ?, Tier3 = ? WHERE PlayerUUID = ?;");

            RewardData data = RewardDataManager.getRewardData(proxiedPlayer);

            statement.setInt(1, data.getTier1count());
            statement.setInt(2, data.getTier2count());
            statement.setInt(3, data.getTier3count());

            statement.setString(4, player.getUniqueId().toString());

            statement.execute();


            System.out.println("Successfully updated players rewarddata!");

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public static void createRewardsData(ProxiedPlayer player) {

        if (hasRewardsData(player)) {
            return;
        }

        Connection connection = getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Rewards(PlayerUUID, Tier1, Tier2, Tier3) VALUES (?, ?, ?, ?)");

            statement.setString(1, player.getUniqueId().toString());
            statement.setInt(2, 0);
            statement.setInt(3, 0);
            statement.setInt(4, 0);

            statement.execute();

            connection.close();

        } catch (SQLException e) {
            System.out.println("Couldn't create RewardsData for " + player.getDisplayName() + "!");
        }

    }

}


