package org.example.shop.servlet;

import java.io.*;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.example.shop.dao.BlackListDao;
import org.example.shop.model.BlackList;
import org.example.shop.util.DbConnection;

@WebServlet(name = "GetAllUsersFromBlackListServlet", urlPatterns = "/getAllUsersFromBlackList")
public class GetAllUsersFromBlackListServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(GetAllUsersFromBlackListServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BlackListDao blackListDao = new BlackListDao(DbConnection.getConnection());
        List<BlackList> blackListedUsers = blackListDao.getAllUserFromBlackList();
        request.setAttribute("blackListedUsers", blackListedUsers);
        for (BlackList blackList : blackListedUsers) {

            logger.info("User ID: {}, Added At: {}, Status: {}",
                    blackList.getUserId(), blackList.getAddedAt(), blackList.getStatus());


//            System.out.println("User ID: " + blackList.getUserId() + ", Added At: " + blackList.getAddedAt() +
//                    ", Status: " + blackList.getStatus());
        } //for debugging
        getServletContext().getRequestDispatcher("/WEB-INF/views/blackList.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}