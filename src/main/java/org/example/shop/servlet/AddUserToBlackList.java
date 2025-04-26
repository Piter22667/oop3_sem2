package org.example.shop.servlet;

import java.io.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.shop.dao.BlackListDao;
import org.example.shop.util.DbConnection;

@WebServlet(name = "AddUserToBlackList", urlPatterns = "/addUserToBlackList")
public class AddUserToBlackList extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       int userId = Integer.parseInt(request.getParameter("userId"));

       BlackListDao blackListDao = new BlackListDao(DbConnection.getConnection());
       blackListDao.addUserToBlackList(userId);
       System.out.println("User with ID " + userId + " has been added to the blacklist.");
       getServletContext().getRequestDispatcher("/WEB-INF/views/manageBlackList.jsp").forward(request, response);
    }
}