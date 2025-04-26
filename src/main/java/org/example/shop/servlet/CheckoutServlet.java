package org.example.shop.servlet;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.shop.dao.CartDao;
import org.example.shop.util.DbConnection;

@WebServlet(name = "CheckoutServlet", urlPatterns = "/checkout")
public class CheckoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("userId");
        String username = (String) session.getAttribute("username");
        System.out.println("User ID from session: " + userId + " from Checkout Servlet");
//        request.setAttribute("userId", userId);

        Connection connection = DbConnection.getConnection();
        CartDao cartDao = new CartDao(connection);
        try {
            cartDao.payForProducts(userId);
            session.setAttribute("paymentStatus", "Дякуємо за замовлення," + username + " оплата пройшла успішно!!!");
            response.sendRedirect("/products");
        } catch (SQLException e) {
            e.printStackTrace();
            session.setAttribute("paymentStatus", "Помилка при обробці оплати: " + e.getMessage());
            response.sendRedirect("/products");
            return;
        }
        System.out.println("Payment successful for user ID: " + userId);


    }
}