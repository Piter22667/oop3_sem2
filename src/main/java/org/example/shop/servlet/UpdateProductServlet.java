package org.example.shop.servlet;

import java.io.*;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.shop.dao.ProductDao;
import org.example.shop.model.Products;
import org.example.shop.util.DbConnection;

@WebServlet(name = "UpdateProductServlet", urlPatterns = "/updateProduct")
public class UpdateProductServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Products existingProduct;
        ProductDao productDao = new ProductDao(DbConnection.getConnection());
        try {
            existingProduct = productDao.getProductById(id);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/editProduct.jsp");
            request.setAttribute("product", existingProduct); // передаємо продукт на сторінку редагування
            dispatcher.forward(request, response);
        } catch (ServletException | IOException | RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}