/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Customer;
import models.SalePerson;
import mylib.Validation;

/**
 *
 * @author datho
 */
@WebServlet(name = "UpdateCustomerServlet", urlPatterns = {"/UpdateCustomerServlet"})
public class UpdateCustomerServlet extends HttpServlet {

    private final String updateCustomerPage = "updateCustomer.jsp";
    private final String loginPage = "login.jsp";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */

            HttpSession session = request.getSession(false);

            SalePerson salePerson = Validation.validateSalePersonSession(request, response, session);
            if (salePerson == null) {
                response.sendRedirect(loginPage);
                return;
            }
            String id = Validation.normalize(request.getParameter("customerId"));
            String name = Validation.normalize(request.getParameter("customerName"));
            String phone = Validation.normalize(request.getParameter("customerPhone"));
            String sex = Validation.normalize(request.getParameter("customerSex"));
            String address = Validation.normalize(request.getParameter("customerAddress"));
            if (id.isEmpty() || phone.isEmpty()) {
                request.getRequestDispatcher("ProcessServlet?btnAction=Search").forward(request, response);
                return;
            } else {
                Long idNumber = Validation.parseLong(id);
                Long phoneNumber = Validation.parseLong(phone);

                Customer customer = new Customer(idNumber, name, phoneNumber, sex, address);
                request.setAttribute("UPDATE", customer);
            }
            request.getRequestDispatcher(updateCustomerPage).forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
