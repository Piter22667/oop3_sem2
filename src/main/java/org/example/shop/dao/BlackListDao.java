package org.example.shop.dao;

import org.example.shop.model.BlackList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BlackListDao {
    Connection connection;

    public BlackListDao(Connection connection) {
        this.connection = connection;
    }

    private static final String ADD_USER_TO_BLACK_LIST = "INSERT INTO blacklist (user_id, status) VALUES (?, ?)";
    private static final String GET_ALL_USER_FROM_BLACK_LIST = "SELECT user_id, added_at, status FROM blacklist";

    public void addUserToBlackList(int userId) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_USER_TO_BLACK_LIST)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, "blacklisted");
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<BlackList> getAllUserFromBlackList() {
        List<BlackList> blackListedUsers = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_USER_FROM_BLACK_LIST)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                blackListedUsers.add(new BlackList(
                        resultSet.getInt("user_id"),
                        resultSet.getTimestamp("added_at"),
                        resultSet.getString("status")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return blackListedUsers;
    }

}
