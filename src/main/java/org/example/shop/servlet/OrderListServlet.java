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
import org.example.shop.dao.OrderDao;
import org.example.shop.model.Orders;
import org.example.shop.util.DbConnection;

@WebServlet(name = "OrderListServlet", urlPatterns = "/orderList")
public class OrderListServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(OrderListServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection connection = DbConnection.getConnection();
        OrderDao orderDao = new OrderDao(connection);
        List<Orders> ordersList = orderDao.getAllOrdersList();
        request.setAttribute("ordersList", ordersList);
        logger.info("Order List servlet is being processing");
//        System.out.println("Message from Order list servelt :");
        for(Orders order : ordersList) {

            logger.info("User id in order: {}\n, Order date: {}\n, Total price: {}\n",
                    order.getUserId(), order.getOrderDate(), order.getTotalPrice());

//            System.out.println("User id in order " +  order.getUserId());
//            System.out.println("Order date: " + order.getOrderDate());
//            System.out.println("Total price: " +  order.getTotalPrice());
        }

        getServletContext().getRequestDispatcher("/WEB-INF/views/orderList.jsp").forward(request, response);
//        List<Orders> ordersList = orderDao.getAllOrders();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}