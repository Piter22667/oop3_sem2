package org.example.shop.servlet;

import java.io.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.shop.dao.ProductDao;
import org.example.shop.model.Products;
import org.example.shop.util.DbConnection;

@WebServlet(name = "EditProductServlet", urlPatterns = "/editProduct")
public class EditProductServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        int price = Integer.parseInt(request.getParameter("price")) ;
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String image_url = request.getParameter("image_url");


        System.out.println("Received product details:");
        System.out.println("id: " + id);
        System.out.println("name: " + name);
        System.out.println("description: " + description);
        System.out.println("price: " + price);
        System.out.println("quantity: " + quantity);
        System.out.println("image_url: " + image_url);



        Products product = new Products(id, name, description, price, quantity, image_url);
        ProductDao productDao = new ProductDao(DbConnection.getConnection());
        productDao.updateProduct(product);
        response.sendRedirect(request.getContextPath() + "/adminManagementPanel");
    }
}