package org.example.shop.servlet;

import java.io.*;
import java.sql.Connection;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.example.shop.dao.CartDao;
import org.example.shop.model.Cart;
import org.example.shop.util.DbConnection;

@WebServlet(name = "RedirectToCartServlet", urlPatterns = "/redirectToCart")
public class RedirectToCartServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(RedirectToCartServlet.class);



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Отримуємо сесію
        HttpSession session = request.getSession();
        // Отримуємо ID користувача з сесії
        int userId = (int) session.getAttribute("userId");
        logger.info("User id in session: {}", userId);

        Connection connection = DbConnection.getConnection();
        CartDao cartDao = new CartDao(connection);

        List<Cart> cartList = cartDao.getProductsFromCart(userId);
        request.setAttribute("cartList", cartList);
        logger.info("Cart list size: {} in Redirect to cart servlet", cartList.size());
        int totalPrice = 0; //for storing total price for certain useer
        for (Cart cart : cartList) {
            totalPrice += cart.getPrice() * cart.getQuantity();

            logger.info("Product Id: {}, Quantity: {}, Added At: {}, Product Name: {}, Price: {}",
                    cart.getProductId(), cart.getQuantity(), cart.getAddedAt(), cart.getProductName(), cart.getPrice());

//            System.out.println("Product Id: " + cart.getProductId() + ", Quantity: " + cart.getQuantity() +
//                    ", Added At: " + cart.getAddedAt() + ", Product Name: " + cart.getProductName() +
//                    ", Price: " + cart.getPrice() + " that data was reseived from servlet");
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