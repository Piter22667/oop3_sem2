package org.example.shop.servlet;

import java.io.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.shop.dao.ProductDao;
import org.example.shop.util.DbConnection;

@WebServlet(name = "DeleteProductServlet", urlPatterns = "/deleteProduct")
public class DeleteProductServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        ProductDao productDao = new ProductDao(DbConnection.getConnection());
        productDao.deleteProduct(id);
        response.sendRedirect(request.getContextPath() + "/adminManagementPanel");
    }
}