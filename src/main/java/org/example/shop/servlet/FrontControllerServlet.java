package org.example.shop.servlet;

import java.io.*;
import java.util.Enumeration;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "FrontControllerServlet", urlPatterns = "/frontController")
public class FrontControllerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRoute(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRoute(request, response);
    }

    private void processRoute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        request.setAttribute("action", action);
        System.out.println("Action received: " + action);
        System.out.println("IN FRONT CONTROLLER ==!==!!==!!");


        if(action == null || action.isEmpty()){
            System.out.println("Wrong or unknown action, redirecting to error page.");
            getServletContext().getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
            return;
        }

        try{
            switch (action) {
                case "addProduct":
                System.out.println("Add Product servlet was triggered");
                request.getRequestDispatcher("/addProduct").forward(request, response);
                break;
                case "createProduct":
                System.out.println("Create Product servlet was triggered");
                request.getRequestDispatcher("/createProduct").forward(request, response);
                break;
                case "editProduct":
                System.out.println("Edit Product servlet was triggered");
                request.getRequestDispatcher("/editProduct").forward(request, response);
                break;
                case "updateProduct":
                System.out.println("Update Product servlet was triggered");
                request.getRequestDispatcher("/updateProduct").forward(request, response);
                break;
                case "deleteProduct":
                System.out.println("Delete Product servlet was triggered");
                request.getRequestDispatcher("/deleteProduct").forward(request, response);
                break;
                case "addToCart":
                System.out.println("Add to Cart servlet was triggered");
                request.getRequestDispatcher("/addToCart").forward(request, response);
                break;
                case "redirectToCart":
                System.out.println("Redirect to Cart servlet was triggered");
                request.getRequestDispatcher("/redirectToCart").forward(request, response);
                break;
                case "checkout":
                System.out.println("Checkout servlet was triggered");
                request.getRequestDispatcher("/checkout").forward(request, response);
                break;
                case "orderList":
                System.out.println("Order List servlet was triggered");
                request.getRequestDispatcher("/orderList").forward(request, response);
                break;
                case "manageBlackList":
                System.out.println("Manage Black List servlet was triggered");
                request.getRequestDispatcher("/manageBlackList").forward(request, response);
                break;
                case "addUserToBlackList":
                System.out.println("Add User to Black List servlet was triggered");
                request.getRequestDispatcher("/addUserToBlackList").forward(request, response);
                break;
                case "getAllUsersFromBlackList":
                System.out.println("Get All Users from Black List servlet was triggered");
                request.getRequestDispatcher("/getAllUsersFromBlackList").forward(request, response);
                break;
                case "userOrderList":
                System.out.println("User Order List servlet was triggered");
                request.getRequestDispatcher("/userOrderList").forward(request, response);
                break;
                case "logOut":
                System.out.println("Log Out servlet was triggered");
                request.getRequestDispatcher("/logOut").forward(request, response);
                break;
                default:
                System.out.println("Error servlet was triggered");
                getServletContext().getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
                break;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



}