package org.example.shop.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.example.shop.model.Products;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {
    Connection connection;
    private static final Logger logger = LoggerFactory.getLogger(ProductDao.class);

    private static final String GET_ALL_PRODUCTS = "SELECT * FROM products";
    private static final String INSERT_PRODUCT = "INSERT INTO products (name, description, price, quantity, image_url) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_PRODUCT = "UPDATE products SET name = ?, description = ?, price = ?, quantity = ?, image_url = ? WHERE id = ?";
    private static final String DELETE_PRODUCT = "DELETE FROM products WHERE id = ?";
    private static final String GET_PRODUCT_BY_ID = "SELECT * FROM products WHERE id = ?";
    private static final String ADD_TO_CART = "INSERT INTO order_products (order_id, ) VALUES (?, ?)";

    public ProductDao(Connection connection) {
        this.connection = connection;
    }

    public List<Products> getAllProducts() {
        List<Products> products = new ArrayList<>();
        try(PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_PRODUCTS)) {
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                // Додаємо продукти до списку
                products.add(new Products(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getInt("price"),
                        resultSet.getInt("quantity"),
                        resultSet.getString("image_url")
                ));
            }
        } catch (Exception e) {
            logger.error("Database query error: {}", e.getMessage());
            throw new RuntimeException("Database query error", e);
        }
        return products;
    }

    public Products getProductById(int id) {
        Products product = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_PRODUCT_BY_ID)) {
            preparedStatement.setInt(1, id);
            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                product = new Products(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getInt("price"),
                        resultSet.getInt("quantity"),
                        resultSet.getString("image_url")
                );
            }
        } catch (Exception e) {
            logger.error("Database query error: {}", e.getMessage());
            throw new RuntimeException("Database query error", e);
        }
        return product;
    }

    public void createProduct(Products product) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCT)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setInt(3, product.getPrice());
            preparedStatement.setInt(4, product.getQuantity());
            preparedStatement.setString(5, product.getImage_url());
            int rowsAdded = preparedStatement.executeUpdate();

            if(rowsAdded > 0) {
                logger.info("Product {} added successfully.", product.getName());
//                System.out.println("Product added successfully.");
            } else {
                logger.error("Failed to add product.");
//                System.out.println("Failed to add product.");
            }

        } catch (Exception e) {
            logger.error("Database query error: {}", e.getMessage());
            throw new RuntimeException("Database query error", e);
        }
    }

    public void updateProduct (Products product) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_PRODUCT_BY_ID)) {
            preparedStatement.setInt(1, product.getId());
            System.out.println("Received product in productDaoClass with id: " + product.getId());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String currentName = resultSet.getString("name");
                String currentDescription = resultSet.getString("description");
                int currentPrice = resultSet.getInt("price");
                int currentQuantity = resultSet.getInt("quantity");
                String currentImageUrl = resultSet.getString("image_url");
                //отримуємо інформацію про опис кожного поля який наразі є в бд

                logger.info("Current product details in DAO CLASS: id: {}, name: {}, description: {}, price: {}, quantity: {}, image_url: {}",
                        product.getId(), currentName, currentDescription, currentPrice, currentQuantity, currentImageUrl);

                String newName = product.getName();
                String newDescription = product.getDescription();
                int newPrice = product.getPrice() ;
                int newQuantity = product.getQuantity();
                String newImageUrl = product.getImage_url();
                //отримуємо інформацію про опис кожного поля який адміністратор хочее оновити

                if(!currentName.equals(newName) || !currentDescription.equals(newDescription) || currentPrice !=newPrice || currentQuantity != newQuantity || !currentImageUrl.equals(newImageUrl)) {
                    try (PreparedStatement preparedStatement1 = connection.prepareStatement(UPDATE_PRODUCT)) {
                        preparedStatement1.setString(1, newName);
                        preparedStatement1.setString(2, newDescription);
                        preparedStatement1.setInt(3, newPrice);
                        preparedStatement1.setInt(4, newQuantity);
                        preparedStatement1.setString(5, newImageUrl);
                        preparedStatement1.setInt(6, product.getId());
                        logger.info("Received product in productDaoClass with id: {}", product.getId());


                        int rowsUpdated = preparedStatement1.executeUpdate();

                        if(rowsUpdated > 0) {
                            logger.info("Product {} updated successfully.", currentName);
                        } else {
                            logger.error("Failed to update product.");
                        }
                    }
                }
                else {
                    logger.info("No changes detected. Product not updated.");
                }
            }
            else {
                logger.error("Product with id {} not found.", product.getId());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


        //        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PRODUCT)) {
//            preparedStatement.setString(1, product.getName());
//            preparedStatement.setString(2, product.getDescription());
//            preparedStatement.setInt(3, product.getPrice());
//            preparedStatement.setInt(4, product.getQuantity());
//            preparedStatement.setString(5, product.getImage_url());
//            preparedStatement.setInt(6, product.getId());
//            int rowsUpdated = preparedStatement.executeUpdate();
//
//            if(rowsUpdated > 0) {
//                System.out.println("Product" + product.getName() + " updated successfully.");
//            } else {
//                System.out.println("Failed to update product.");
//            }
//
//        } catch (Exception e) {
//            throw new RuntimeException("Database query error", e);
//        }


    public void deleteProduct(int id){
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PRODUCT)) {
            preparedStatement.setInt(1, id);
            int rowsDeleted = preparedStatement.executeUpdate();

            if(rowsDeleted > 0) {
                System.out.println("Product deleted successfully.");
            } else {
                System.out.println("Failed to delete product.");
            }

        } catch (Exception e) {
            throw new RuntimeException("Database query error", e);
        }
    }
}
