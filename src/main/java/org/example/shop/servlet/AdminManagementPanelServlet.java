package org.example.shop.servlet;

import java.io.*;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.shop.dao.ProductDao;
import org.example.shop.model.Products;
import org.example.shop.util.DbConnection;

@WebServlet(name = "AdminManagementPanelServlet", urlPatterns = "/adminManagementPanel")
public class AdminManagementPanelServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductDao productDao = new ProductDao(DbConnection.getConnection());
        List<Products> productsList = productDao.getAllProducts();
        request.setAttribute("productsList", productsList);
        if(!productsList.isEmpty()){
            System.out.println("Products found: " + productsList.size());
        }
        for(Products product: productsList){

            System.out.println(" Product Name: "+ product.getName());
        }

        getServletContext().getRequestDispatcher("/WEB-INF/views/adminPage.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}