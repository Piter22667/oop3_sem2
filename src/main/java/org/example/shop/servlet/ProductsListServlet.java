package org.example.shop.servlet;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.shop.dao.ProductDao;
import org.example.shop.model.Products;
import org.example.shop.util.DbConnection;

    @WebServlet(name = "ProductsListServlet", urlPatterns = "/products")
public class ProductsListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Отримуємо з'єднання через синглтон
        Connection connection = DbConnection.getConnection();
//        DbConnection dbConnectioction = new DbConnection();
//        connection = DbConnection.getConnection();
        ProductDao productDao = new ProductDao(connection);
        List<Products> productsList = productDao.getAllProducts();
        request.setAttribute("productsList", productsList);
        for (Products product : productsList) {
            System.out.println("Product: " + product.getName() + ", Description: " + product.getDescription() +
                    ", Price: " + product.getPrice() + ", Image URL: " + product.getImage_url());
        } //for debugging


        getServletContext().getRequestDispatcher("/WEB-INF/views/userShop.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}