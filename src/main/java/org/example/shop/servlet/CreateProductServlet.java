package org.example.shop.servlet;
import java.io.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.shop.dao.ProductDao;
import org.example.shop.model.Products;
import org.example.shop.util.DbConnection;

@WebServlet(name = "CreateProductServlet", urlPatterns = "/createProduct")
public class CreateProductServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        int price = Integer.parseInt(request.getParameter("price")) ;
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String imageUrl = request.getParameter("image_url");

        Products product = new Products(name, description, price, quantity, imageUrl);
        ProductDao productDao = new ProductDao(DbConnection.getConnection());
        productDao.createProduct(product);
        response.sendRedirect(request.getContextPath() + "/adminManagementPanel");
    }
}