package org.example.shop.servlet;

import java.io.*;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.shop.dao.BlackListDao;
import org.example.shop.model.BlackList;
import org.example.shop.util.DbConnection;

@WebServlet(name = "GetAllUsersFromBlackListServlet", urlPatterns = "/getAllUsersFromBlackList")
public class GetAllUsersFromBlackListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BlackListDao blackListDao = new BlackListDao(DbConnection.getConnection());
        List<BlackList> blackListedUsers = blackListDao.getAllUserFromBlackList();
        request.setAttribute("blackListedUsers", blackListedUsers);
        for (BlackList blackList : blackListedUsers) {
            System.out.println("User ID: " + blackList.getUserId() + ", Added At: " + blackList.getAddedAt() +
                    ", Status: " + blackList.getStatus());
        } //for debugging
        getServletContext().getRequestDispatcher("/WEB-INF/views/blackList.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}