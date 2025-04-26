package org.example.shop.servlet.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.example.shop.dao.UsersDao;
import org.example.shop.model.Users;
import org.example.shop.util.DbConnection;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

@WebFilter(filterName = "AdminProductFilter", urlPatterns = {"/adminManagementPanel", "/manageBlackList", "/updateProduct" , "/orderList", "/redirectToCart", "/getAllUsersFromBlackList", "/addProduct" })
public class AdminProductFilter implements Filter {
//    public void init(FilterConfig config) throws ServletException {
//    }
//
//    public void destroy() {
//    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        Connection connection = DbConnection.getConnection();
        UsersDao usersDao = new UsersDao(connection);

        PrintWriter out = response.getWriter();
        HttpSession session = ((HttpServletRequest) request).getSession();
        String username = (String) session.getAttribute("username");
        if (username == null) {
            // Якщо користувач не знайдений, перенаправляємо на сторінку входу з повідомленням про помилку
            request.setAttribute("error", "You must be logged in to access this page.");
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            return;
        }
        // Перевіряємо, чи є користувач адміністратором
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        if (isAdmin == null || !isAdmin) {
            request.setAttribute("error", "You do not have permission to access this page.");
            out.println("You do not have permission to access this page. " +  username + "is admin: ?" + isAdmin);
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            return;
        }

        if(isAdmin == true){
            System.out.println("User is admin");
        } else {
            System.out.println("User is not admin");
        }



        chain.doFilter(request, response);
    }
}