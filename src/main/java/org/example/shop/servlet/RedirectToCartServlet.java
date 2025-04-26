package org.example.shop.servlet;

import java.io.*;
import java.sql.Connection;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.shop.dao.CartDao;
import org.example.shop.model.Cart;
import org.example.shop.util.DbConnection;

@WebServlet(name = "RedirectToCartServlet", urlPatterns = "/redirectToCart")
public class RedirectToCartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Отримуємо сесію
        HttpSession session = request.getSession();
        // Отримуємо ID користувача з сесії
        int userId = (int) session.getAttribute("userId");
        System.out.println("User ID from session: " + userId + " from do get");

        Connection connection = DbConnection.getConnection();
        CartDao cartDao = new CartDao(connection);

        List<Cart> cartList = cartDao.getProductsFromCart(userId);
        request.setAttribute("cartList", cartList);
        System.out.println("Cart list size: " + cartList.size() + " in Redirect to cart servlet"); // лог для перевірки кількості елементів
        int totalPrice = 0; //for storing total price for certain useer
        for (Cart cart : cartList) {
            totalPrice += cart.getPrice() * cart.getQuantity();

            System.out.println("Product Id: " + cart.getProductId() + ", Quantity: " + cart.getQuantity() +
                    ", Added At: " + cart.getAddedAt() + ", Product Name: " + cart.getProductName() +
                    ", Price: " + cart.getPrice() + " that data was reseived from servlet"); // лог для перевірки вмісту списку
        }
        System.out.println("Total price: " + totalPrice + " that data was received from servlet"); // лог для перевірки загальної вартості

        request.setAttribute("totalPrice", totalPrice);
        // Перенаправляємо на сторінку кошика
        getServletContext().getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(request, response);

        //        // Отримуємо сесію
//        HttpSession session = request.getSession();
//        // Отримуємо ID користувача з сесії
//        int userId = (int) session.getAttribute("userId");
//        System.out.println("User ID from session: " + userId + "from do get");
//
//        CartDao cartDao = new CartDao(DbConnection.getConnection());
//
//         List<Cart> cartList = cartDao.getProductsFromCart(userId);
//         request.setAttribute("cartList", cartList);
//
//        // Перенаправляємо на сторінку кошика
//        request.getRequestDispatcher("cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}