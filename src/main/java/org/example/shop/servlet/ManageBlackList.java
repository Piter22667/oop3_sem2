package org.example.shop.servlet;

import java.io.*;
import java.sql.Connection;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.shop.dao.CartDao;
import org.example.shop.model.Users;
import org.example.shop.util.DbConnection;

@WebServlet(name = "manageBlackList", urlPatterns = "/manageBlackList")
public class ManageBlackList extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection connection = DbConnection.getConnection();
        CartDao cartDao = new CartDao(connection);

        List<Users> usersInCartList =  cartDao.getCartUsers();
        request.setAttribute("usersInCartList", usersInCartList);
        getServletContext().getRequestDispatcher("/WEB-INF/views/manageBlackList.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection connection = DbConnection.getConnection();
        CartDao cartDao = new CartDao(connection);

        List<Users> usersInCartList =  cartDao.getCartUsers();
        request.setAttribute("usersInCartList", usersInCartList);
        getServletContext().getRequestDispatcher("/WEB-INF/views/manageBlackList.jsp").forward(request, response);
    }
}