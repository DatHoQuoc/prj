/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dao.CustomerDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.SalePerson;
import mylib.Validation;

/**
 *
 * @author datho
 */
@WebServlet(name = "CreateCustomerServlet", urlPatterns = {"/CreateCustomerServlet"})
public class CreateCustomerServlet extends HttpServlet {

    private final String loginPage = "login.jsp";
    private final String createCustomerPage = "createNewCustomer.jsp";

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
            request.setCharacterEncoding("utf-8");
            HttpSession session = request.getSession(false);
            SalePerson salePerson = Validation.validateSalePersonSession(request, response,session);
            if(salePerson == null){
                response.sendRedirect(loginPage);
                return;
            }
            String fullName = Validation.normalize(request.getParameter("fullName"));
            String phone = Validation.normalize(request.getParameter("phone"));
            String gender = Validation.normalize(request.getParameter("customerGender"));
            String address = Validation.normalize(request.getParameter("address"));
            boolean isValidName = Validation.isValidFullName(fullName);
            boolean isValidPhone = Validation.isValidPhoneNumber(phone);
            String[] error = new String[3];
            int errorCount = 0;
            

            if (isValidPhone && isValidName) {
                Long phoneNumber = Validation.parseLong(phone);
                if (Validation.doesExistCustomer(fullName, phoneNumber)) {
                    request.setAttribute("ERROR", "The customer name and phone already exists");
                } else {
                    insertCustomer(request, fullName, phoneNumber, address, gender);
                }
            }
            if (!phone.isEmpty()) {
                if (!Validation.isValidPhoneNumber(phone)) {
                    error[errorCount] = "The phone number is from 10 to 15 digits";
                    errorCount++;
                }
            }
            if (!fullName.isEmpty()) {
                if (!Validation.isValidFullName(fullName)) {
                    error[errorCount] = "Please enter the full name";
                    errorCount++;
                }
            }
            if (errorCount > 0) {
                request.setAttribute("ERRORS", error);
            }

            request.getRequestDispatcher(createCustomerPage).forward(request, response);
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

    private void insertCustomer(HttpServletRequest request, String name, long phone, String address, String sex) {
        CustomerDAO dao = new CustomerDAO();
        String gender = sex.equals("male") ? "M" : "F";
        long cusID = dao.insertCustomer(name, phone, address, gender);
        if (cusID > 0) {
            request.setAttribute("SUCCESS", "Create Successfully");
        } else {
                request.setAttribute("ERROR", "Create Fail");
        }
    }
}
