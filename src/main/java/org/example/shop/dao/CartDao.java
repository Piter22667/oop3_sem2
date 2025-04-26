package org.example.shop.dao;

import org.example.shop.model.Cart;
import org.example.shop.model.Users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CartDao {
    Connection connection;

    public CartDao(Connection connection) {
        this.connection = connection;
    }

    private static final String ADD_TO_CART = "INSERT INTO cart (user_id, product_id, quantity) VALUES (?, ?, ?)";
    private static final String GET_CART = "SELECT * FROM cart WHERE user_id = ?";
    private static final String GET_PRODUCT_BY_ID = "SELECT * FROM cart WHERE id = ?";
    private static final String UPDATE_QUANTITY = "UPDATE cart SET quantity = ? WHERE user_id = ? AND product_id = ?";


    //    private static final String GET_NAME_BY_PRODUCT_ID = "SELECT name FROM products WHERE id = ?";

    private static final String GET_PRODUCTS_FOR_USER = "SELECT * FROM cart WHERE user_id = ? AND product_id =?";
    private static final String GET_NAME_BY_PRODUCT_ID = "SELECT c.user_id, c.product_id, c.quantity, c.added_at, p.name AS product_name, p.price\n" +
            "FROM cart c\n" +
            "         JOIN products p ON c.product_id = p.id\n" +
            "WHERE c.user_id = ?\n";

    //transaction paying queries
    private static final String CREATE_ORDER = "INSERT INTO orders (user_id, total_price) VALUES (?, ?) RETURNING id";
    private static final String GET_ALL_PRODUCTS_FOR_USER_WITH_ID = "SELECT product_id, quantity FROM cart WHERE user_id = ?";
    private static final String INSER_INTO_ORDER_PRODUCTS = "INSERT INTO order_products (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
    private static final String DELET_FROM_CART_FOR_USER_WITH_ID = "DELETE FROM cart WHERE user_id = ?";

    private static final String GET_PRICE_FOR_PRODUCT = "SELECT price FROM products WHERE id = ?";
    private static final String SET_BLACKLISTED_FALSE = "UPDATE users SET is_blacklisted = false WHERE id = ?";

    //TODO idea for blacklist users
     private static final String UPDATE_USER_UNPAID_STATUS_CART = "UPDATE cart SET is_paid = false WHERE user_id = ?";


    public void addToCart(int userId, int productId, int quantity) throws SQLException {

        try{
            connection.setAutoCommit(false);


            try (PreparedStatement preparedStatement = connection.prepareStatement(GET_PRODUCTS_FOR_USER)) {

                preparedStatement.setInt(1, userId);
                preparedStatement.setInt(2, productId);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    int existingQuantity = resultSet.getInt("quantity");
                    int newQuantity = existingQuantity + quantity;

                    try (PreparedStatement prepareStatement = connection.prepareStatement(UPDATE_QUANTITY)) {
                        prepareStatement.setInt(1, newQuantity);
                        prepareStatement.setInt(2, userId);
                        prepareStatement.setInt(3, productId);
                        prepareStatement.executeUpdate();
                        System.out.println("Product already in cart. Updated quantity to: " + newQuantity);
                    }
                } else {
                    try (PreparedStatement prepareStatement = connection.prepareStatement(ADD_TO_CART)) {
                        prepareStatement.setInt(1, userId);
                        prepareStatement.setInt(2, productId);
                        prepareStatement.setInt(3, quantity);
                        prepareStatement.executeUpdate();
                        System.out.println("Product added to cart.");
                    }
                }
                try (PreparedStatement isPaidStatement = connection.prepareStatement(UPDATE_USER_UNPAID_STATUS_CART)) {
                    isPaidStatement.setInt(1, userId);
                    isPaidStatement.executeUpdate();
                    System.out.println("User with ID: " + userId + " is set to unpaid status after adding product " + productId + " to cart");
                }

                connection.commit();

            }
                catch(SQLException e) {
                if (connection != null) {
                    connection.rollback();
                }
                throw new RuntimeException("Transaction failed, rolled back", e);
            } finally {
                if (connection != null) {
                    connection.setAutoCommit(true);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        //        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_TO_CART)) {
//        preparedStatement.setInt(1, userId);
//        preparedStatement.setInt(2, productId);
//        preparedStatement.setInt(3, quantity);
//
//
//
//
//        int rowsAffected = preparedStatement.executeUpdate();
//        if (rowsAffected > 0) {
//            System.out.println("Product" + productId + "added to cart successfully.");
//        } else {
//            System.out.println("Failed to add product to cart.");
//        }
//
//        } catch (Exception e) {
//            throw new RuntimeException("Database query error", e);
//        }
    }

    public List<Cart> getProductsFromCart(int id) {
        List<Cart> cartList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_NAME_BY_PRODUCT_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int prodId = resultSet.getInt("product_id");
                boolean isProductInCart = false;
                for (Cart cart : cartList) {
                    //якщо товар вже є в кошику, просто додаємо кількість
                    if (cart.getProductId() == prodId) {
                        cart.setQuantity(cart.getQuantity() + resultSet.getInt("quantity"));
                        cart.setPrice(cart.getPrice() + resultSet.getInt("price") + resultSet.getInt("price"));
                        isProductInCart = true;
                        break;
                    }
                }
                if (!isProductInCart) {
                    // Додаємо продукти до списку
//                Cart cart = new Cart();
                    cartList.add(new Cart(
                            resultSet.getInt("user_id"),
                            resultSet.getInt("product_id"),
                            resultSet.getInt("quantity"),
                            resultSet.getTimestamp("added_at"), // отримуємо timestamp
                            resultSet.getString("product_name"),
                            resultSet.getInt("price")
                    ));
                }
                for (Cart cart : cartList) {
                    System.out.println("Product Id: " + cart.getProductId() + ", Quantity: " + cart.getQuantity() +
                            ", Added At: " + cart.getAddedAt() + ", Product Name: " + cart.getProductName() +
                            ", Price: " + cart.getPrice() + " that data was reseived from servlettttttt"); // лог для перевірки вмісту списку
                }
                System.out.println("Products from User" + id + "in cart: ");
                System.out.println("Function getProductsFromCart was triggered in CartDao");
            }
            return cartList;
        } catch (Exception e) {
            throw new RuntimeException("Database query error", e);
        }
    }

    public void payForProducts(int userId) throws SQLException {
    try{
        connection.setAutoCommit(false);
        String totalPriceQuery = "SELECT c.product_id, c.quantity, p.price, (p.price * c.quantity) AS total_item_price " +
                "FROM cart c JOIN products p ON c.product_id = p.id WHERE c.user_id = ?";

        PreparedStatement totalPriceStatement = connection.prepareStatement(totalPriceQuery);
        totalPriceStatement.setInt(1, userId);
        ResultSet totalPriceResultSet = totalPriceStatement.executeQuery();

        List<Cart> cart = new ArrayList<>();
        int totalPrice = 0;


        while(totalPriceResultSet.next()){
            int productId = totalPriceResultSet.getInt("product_id");
            int quantity = totalPriceResultSet.getInt("quantity");
            int price = totalPriceResultSet.getInt("price");
            int totalItemPrice = totalPriceResultSet.getInt("total_item_price");
            totalPrice += totalItemPrice;


            cart.add(new Cart(userId, productId, quantity, null, null, price));
            System.out.println("Product ID: " + productId + ", Quantity: " + quantity + ", Price: " + price + "from CartDaoCheckout");
        }

        PreparedStatement preparedStatement = connection.prepareStatement(CREATE_ORDER);
        preparedStatement.setInt(1, userId);
        preparedStatement.setInt(2, totalPrice);

        ResultSet orderIdResultSet = preparedStatement.executeQuery();
        int orderId = 0;
        if (orderIdResultSet.next()) {
            orderId = orderIdResultSet.getInt("id"); //receive newly created order id from orders table
        }


//        PreparedStatement setBlacklistFalseStatement = connection.prepareStatement(SET_BLACKLISTED_FALSE);
//        setBlacklistFalseStatement.setInt(1, userId);
//        setBlacklistFalseStatement.executeUpdate();
//        System.out.println("User with ID: " + userId + " is set to not blacklisted after finishing payment");




        // Insert into order_products
        PreparedStatement orderProductsStatement = connection.prepareStatement(INSER_INTO_ORDER_PRODUCTS);
        for (Cart item : cart) {
            orderProductsStatement.setInt(1, orderId);
            orderProductsStatement.setInt(2, item.getProductId());
            orderProductsStatement.setInt(3, item.getQuantity());
            orderProductsStatement.setInt(4, item.getPrice());
            orderProductsStatement.addBatch();
        }

        orderProductsStatement.executeBatch();

        PreparedStatement deleteFromCartStatement = connection.prepareStatement(DELET_FROM_CART_FOR_USER_WITH_ID);
        deleteFromCartStatement.setInt(1, userId);
        deleteFromCartStatement.executeUpdate();
        System.out.println("Deleted all products from cart for user ID: " + userId);



        connection.commit();
    } catch (SQLException e) {
        if (connection != null) {
            connection.rollback();
        }
        throw new RuntimeException("Transaction failed, rolled back", e);
    } finally {
        if (connection != null) {
            connection.setAutoCommit(true);
        }
    }
    }


    public List<Users> getCartUsers(){
        List<Users> cartUsers = new ArrayList<>();
        try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT DISTINCT c.user_id, u.username FROM public.cart c JOIN public.users u ON c.user_id = u.id")){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int userId = resultSet.getInt("user_id");
                String username = resultSet.getString("username");
                cartUsers.add(new Users(userId, username));
            }
            return cartUsers;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }








//    public void insertUserToBlackList(int id) throws SQLException {
//        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO blacklist (user_id, added_at, status)  VALUES (?, ?, ?)")) {
//            preparedStatement.setInt(1, id);
//            preparedStatement.setTimestamp(2, new java.sql.Timestamp(System.currentTimeMillis()));
//            preparedStatement.setString(3, "blacklisted");
//            preparedStatement.executeUpdate();
//            System.out.println("User with ID: " + id + " was blacklisted");
//        } catch (SQLException e) {
//            throw new RuntimeException("Database query error", e);
//        }
//    }


}






    //        List<Cart> cartList = new ArrayList<>();
//        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_CART)){
//            preparedStatement.setInt(1, id);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            while (resultSet.next()) {
//                // Додаємо продукти до списку
//                Cart cart = new Cart();
//                cart.setUserId(resultSet.getInt("user_id"));
//                cart.setProductId(resultSet.getInt("product_id"));
//                cart.setQuantity(resultSet.getInt("quantity"));
//                cart.setAddedAt(resultSet.getTimestamp("addedAt")); // отримуємо timestamp
//                cartList.add(cart);
//            }
//        } catch (Exception e) {
//            throw new RuntimeException("Database query error", e);
//        }
//        return null;


