package org.example.shop.servlet;

import java.io.*;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.example.shop.dao.ProductDao;
import org.example.shop.model.Products;
import org.example.shop.util.DbConnection;

@WebServlet(name = "AdminManagementPanelServlet", urlPatterns = "/adminManagementPanel")
public class AdminManagementPanelServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(AdminManagementPanelServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductDao productDao = new ProductDao(DbConnection.getConnection());
        List<Products> productsList = productDao.getAllProducts();
        request.setAttribute("productsList", productsList);
        if(!productsList.isEmpty()){
            logger.info("Products found: {}", productsList.size());
//            System.out.println("Products found: " + productsList.size());
        }
        for(Products product: productsList){
            logger.info("Product name: {}", product.getName());
//            System.out.println(" Product Name: "+ product.getName());
        }

        getServletContext().getRequestDispatcher("/WEB-INF/views/adminPage.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}