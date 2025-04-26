package org.example.shop.servlet.filter;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.example.shop.dao.UsersDao;
import org.example.shop.util.DbConnection;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;


@WebFilter(filterName = "CheckIfUserLoggedIn", urlPatterns = {"/products", "/cart", "/orderList", "/userOrderList", "/adminManagementPanel" })
public class CheckIfUserLoggedIn implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        Connection connection = DbConnection.getConnection();
        UsersDao user = new UsersDao(connection);

        PrintWriter out = response.getWriter();
        HttpSession session = ((HttpServletRequest) request).getSession();
//        String isAdmin = (String) session.getAttribute("isAdmin");
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");


        if(isAdmin == null) {
            // Якщо користувач не знайдений, перенаправляємо на сторінку входу з повідомленням про помилку
            out.println("You should be  logged in to access this page. " );
            request.setAttribute("error", "You must be logged in to access this page.");
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            return;
        }
        chain.doFilter(request, response);
    }
}