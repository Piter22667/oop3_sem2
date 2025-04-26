package org.example.shop.servlet;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.shop.dao.UsersDao;
import org.example.shop.model.Users;
import org.example.shop.util.DbConnection;


@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //            DbConnection dbConnection = new DbConnection();
        Connection connection = DbConnection.getConnection();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String isAdmin = request.getParameter("isAdmin");
        UsersDao usersDao = new UsersDao(connection);
        Users user = usersDao.isValidUser(username, password);

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("username", username); // зберігаємо ім'я користувача в сесії
            session.setAttribute("userId", user.getId());
            session.setAttribute("isAdmin", user.isAdmin());
            if(user.isAdmin()){
                // Якщо користувач є адміністратором, перенаправляємо на сторінку адміністратора
                response.sendRedirect(request.getContextPath() + "/adminManagementPanel");
//                getServletContext().getRequestDispatcher("/WEB-INF/views/adminPage.jsp").forward(request, response);
            } else{
                // Якщо користувач знайдений, зберігаємо його в сесії
                response.sendRedirect(request.getContextPath() + "/products");
            }
        }  else {
            // Якщо користувач не знайдений, перенаправляємо на сторінку входу з повідомленням про помилку
            request.setAttribute("error", "Invalid username or password");
            request.setAttribute("username", username);
            request.getSession().setAttribute("password", password);
            getServletContext().getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
        }
    }
}