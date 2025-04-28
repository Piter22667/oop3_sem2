package org.example.shop.servlet;

import java.awt.*;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.example.shop.dao.ProductDao;
import org.example.shop.model.Products;
import org.example.shop.model.Users;
import org.example.shop.util.DbConnection;

@WebServlet(name = "AdminProductsServlet", urlPatterns = "/adminProductsFunctions")
public class AdminProductsServlet extends HttpServlet {

    private static Logger logger = LoggerFactory.getLogger(AdminProductsServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Users user = (Users) request.getSession().getAttribute("user");
//        if (user == null || !user.isAdmin()) {
//            response.sendRedirect(request.getContextPath() + "/login");
//            return;
//        }
        ProductDao productDao = new ProductDao(DbConnection.getConnection());
        List<Products> productsList = productDao.getAllProducts();
        request.setAttribute("productsList", productsList);

        for (Products product : productsList) {
            logger.info("Product name: {}, Description: {}, Price: {}, Image URL: {}",
                    product.getName(), product.getDescription(), product.getPrice(), product.getImage_url());

//            System.out.println("Product: " + product.getName() + ", Description: " + product.getDescription() +
//                    ", Price: " + product.getPrice() + ", Image URL: " + product.getImage_url());
        } //for debugging
        String action = request.getServletPath();
        try {
            actionHandle(action, request, response);
        } catch (SQLException e) {
            logger.error("Error while handling action: {}", action, e);
            throw new RuntimeException(e);
        }
        getServletContext().getRequestDispatcher("/WEB-INF/views/adminPage.jsp").forward(request, response);

    }

    private void actionHandle(String action, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        switch (action) {
            case "/addProduct":
                showNewProductForm(request, response);
                break;
            case "/editProduct":
                    updateProduct(request, response);
                break;
            case "/deleteProduct":
                    deleteProduct(request, response);
                break;
            case "/createProduct":
                    addNewProduct(request, response);
                break;
            case "/updateProduct":
                    editProduct(request, response);
            default:
                showAdminProductPage(request, response);
                break;
        }
    }


    private void showNewProductForm (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/views/addProduct.jsp").forward(request, response);
    }

    private void addNewProduct (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        int price = Integer.parseInt(request.getParameter("price")) ;
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String imageUrl = request.getParameter("image_url");

        Products product = new Products(name, description, price, quantity, imageUrl);
        ProductDao productDao = new ProductDao(DbConnection.getConnection());
        productDao.createProduct(product);
        response.sendRedirect(request.getContextPath() + "/adminProducts");
    }

    private void deleteProduct (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        ProductDao productDao = new ProductDao(DbConnection.getConnection());
        productDao.deleteProduct(id);
        response.sendRedirect(request.getContextPath() + "/adminProducts");
    }

    private void updateProduct (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        Products existingProduct;
        ProductDao productDao = new ProductDao(DbConnection.getConnection());
        try {
            existingProduct = productDao.getProductById(id);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/editProduct.jsp");
            request.setAttribute("product", existingProduct);
            dispatcher.forward(request, response);
        } catch (ServletException | IOException | RuntimeException e) {
            logger.error("Error while updating product with ID: {}", id, e);
            throw new RuntimeException(e);
        }
    }

    private void editProduct (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        int price = Integer.parseInt(request.getParameter("price")) ;
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String imageUrl = request.getParameter("image_url");

        Products product = new Products(id, name, description, price, quantity, imageUrl);
        ProductDao productDao = new ProductDao(DbConnection.getConnection());
        productDao.updateProduct(product);
        response.sendRedirect(request.getContextPath() + "/adminProducts");
    }

    private void showAdminProductPage (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       getServletContext().getRequestDispatcher("/WEB-INF/views/adminPage.jsp").forward(request, response);
    }



    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();

        try {
            actionHandle(action, request, response);
        } catch (SQLException e) {
            logger.error("Error while handling POST request: {}", action, e);
            throw new ServletException("Error while handling POST request", e);
        }
    }
}