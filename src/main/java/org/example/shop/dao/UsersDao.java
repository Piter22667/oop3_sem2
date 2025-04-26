package org.example.shop.dao;

import org.example.shop.model.Users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsersDao {
    Connection connection;

    private static final String CHECK_USER_LOGIN_PASS = "SELECT * FROM users WHERE username = ? AND password = ?";
//    private static final String GET_ALL_USERS_UNPAID = "SELECT users.id AS user_id,\n" +
//            "users.username,\n" +
//            "cart.product_id,\n" +
//            "cart.quantity,\n" +
//            "cart.added_at\n" +
//            "FROM users\n" +
//            "JOIN\n" +
//            "    cart ON users.id = cart.user_id;";

    public UsersDao(Connection connection) {
        this.connection = connection;
    }


    public Users isValidUser(String username, String password) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(CHECK_USER_LOGIN_PASS)) {
            // Встановлюємо параметри
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // успішний логін
                return new Users(
                        resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getBoolean("is_admin")
                );
            } else {
                // Якщо логін або пароль неправильні
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database query error", e);
        }
    }


}
