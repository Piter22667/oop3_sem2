package org.example.shop.dao;

import jdk.jshell.spi.ExecutionControl;
import org.example.shop.model.Orders;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDao {
    Connection connection;

    public OrderDao(Connection connection) {
        this.connection = connection;
    }

    private static final String GET_ORDERS_FOR_USER = "SELECT total_price FROM orders WHERE user_id = ?";


    public List<Orders> getAllOrdersList(){
        List<Orders> ordersList = new ArrayList<>();
        try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT user_id, order_date, total_price  FROM orders")) {
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ordersList.add(new Orders(
                        resultSet.getInt("user_id"),
                        resultSet.getTimestamp("order_date"),
                        resultSet.getInt("total_price")
                ));
            }
            return ordersList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

//    private List<Orders> ordersList(int userId) throws SQLException {
//        List<Orders> ordersList = new ArrayList<>();
//        connection.setAutoCommit(false);
//
//        try(PreparedStatement preparedStatement = connection.prepareStatement(GET_ORDERS_FOR_USER)){
//            preparedStatement.setInt(1, userId);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            while (resultSet.next()) {
//                int productId = resultSet.getInt("product_id");
//                int totalPrice = resultSet.getInt("total_price");
//
//                try(PreparedStatement productStatement = connection.prepareStatement(GET_PRODUCT_NAME_AND_IMAGE)){
//                    productStatement.setInt(1, productId);
//                    ResultSet productResultSet = productStatement.executeQuery();
//                    if (productResultSet.next()) {
//                        String name = productResultSet.getString("name");
//                        String imageUrl = productResultSet.getString("image_url");
//                        ordersList.add(new Orders(productId, userId, totalPrice, imageUrl));
//                    }
//                }
//            }
//        }
//
//    }

}
