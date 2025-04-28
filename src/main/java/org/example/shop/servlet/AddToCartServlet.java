package org.example.shop.servlet;

import java.io.*;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.example.shop.dao.CartDao;
import org.example.shop.dao.ProductDao;
import org.example.shop.model.Products;
import org.example.shop.util.DbConnection;

@WebServlet(name = "AddToCartServlet", urlPatterns = "/addToCart")
public class AddToCartServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(AddToCartServlet.class);


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        HttpSession session = request.getSession();
//        session.getId();
//        int userId = (int) session.getAttribute("userId");
//        String productId = request.getParameter("productId");
//        String quantity = request.getParameter("quantity");
//
//        CartDao cartDao = new CartDao(DbConnection.getConnection());
//        cartDao.addToCart(userId, Integer.parseInt(productId), Integer.parseInt(quantity));
//        request.getRequestDispatcher("/WEB-INF/views/userShop.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.getId();
        int userId = (int) session.getAttribute("userId");
        int productId = Integer.parseInt(request.getParameter("productId")) ;
        String quantity = request.getParameter("quantity");

        logger.info("Received product id: {}, quantity: {}, userId: {} in AddToCartServlet", productId, quantity, userId);

        CartDao cartDao = new CartDao(DbConnection.getConnection());
        try {
            cartDao.addToCart(userId, productId, Integer.parseInt(quantity));
            logger.info("Product successfully added to cart for userId: {}", userId);
        } catch (SQLException e) {
            logger.error("Failed to add product to cart for userId: {}", userId, e);
            throw new RuntimeException(e);
        }


        // Отримання оновленого списку товарів
        ProductDao productDao = new ProductDao(DbConnection.getConnection());
        List<Products> productsList = productDao.getAllProducts();
        logger.info("Fetched {} products from database", productsList.size());

        // Передача списку товарів на JSP
        request.setAttribute("productsList", productsList);
        getServletContext().getRequestDispatcher("/WEB-INF/views/userShop.jsp").forward(request, response);
    }
}