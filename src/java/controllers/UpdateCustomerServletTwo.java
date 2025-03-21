/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dao.CustomerDAO;
import dto.CustomerDTO;
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
@WebServlet(name = "UpdateCustomerServletTwo", urlPatterns = {"/UpdateCustomerServletTwo"})
public class UpdateCustomerServletTwo extends HttpServlet {

    private final String loginPage = "login.jsp";
    private final String updateCustomerPage = "updateCustomer.jsp";

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
            String id = Validation.normalize(request.getParameter("id"));
            String name = Validation.normalize(request.getParameter("fullName"));
            String phone = Validation.normalize(request.getParameter("phone"));
            String address = Validation.normalize(request.getParameter("address"));
            String sex = Validation.normalize(request.getParameter("customerGender"));
            String gender = sex.equals("male") ? "M" : "F";
            CustomerDTO customer = new CustomerDTO(id, name, phone, gender, address);
            boolean isValidName = Validation.isValidFullName(name);
            boolean isValidPhone = Validation.isValidPhoneNumber(phone);
            String[] error = new String[3];
            int errorCount = 0;

            if (isValidName && isValidPhone) {
                Long phoneNumber = Validation.parseLong(phone);
                Long idNumber = Validation.parseLong(id);
                if (isExistCustomer(idNumber, name, phoneNumber)) {
                    request.setAttribute("ERROR", "Another customer with this name and phone already exists");
                } else {
                    
                    updateCustomer(request, idNumber, name, phoneNumber, address, gender);
                }
            }
            if (!Validation.isValidPhoneNumber(phone)) {
                error[errorCount] = "The phone number is from 10 to 15 digits";
                errorCount++;
            }
            if (!Validation.isValidFullName(name)) {
                error[errorCount] = "Please enter the full name";
                errorCount++;
            }
            if (errorCount > 0) {
                request.setAttribute("ERRORS", error);
            }
            request.setAttribute("UPDATE", customer);
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

    private void updateCustomer(HttpServletRequest request, long id, String name, long phone, String address, String gender) {
        CustomerDAO dao = new CustomerDAO();
        boolean success = dao.updateCustomer(id, name, phone, address, gender);
        if (success) {
            request.setAttribute("SUCCESS", "Update Successfully");
        } else {
            request.setAttribute("ERROR", "Update Fail");
        }
    }

    private boolean isExistCustomer(Long idNumber, String name, long phoneNumber) {
        if (idNumber == null) {
            return false;
        }
        CustomerDAO dao = new CustomerDAO();
        Long idCheck = dao.selectCustomerID(name, phoneNumber);

        return idCheck != null && !idCheck.equals(idNumber);
    }
}
