/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dao.CustomerDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
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
public class ServiceTicketServlet extends HttpServlet {

    private final String loginPage = "login.jsp";
    private final String carPage = "car.jsp";
    private final String servicePage = "ProcessServlet?btnAction=Ticket";

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
            SalePerson salePerson = Validation.validateSalePersonSession(request, response, session);
            if (salePerson == null) {
                response.sendRedirect(loginPage);
                return;
            }
            String url = carPage;
            String fullName = Validation.normalize(request.getParameter("fullName"));
            String phone = Validation.normalize(request.getParameter("phone"));
            boolean isValidName = Validation.isValidFullName(fullName);
            boolean isValidPhone = Validation.isValidPhoneNumber(phone);
            if (isValidName && isValidPhone) {
                Long phoneNumber = Validation.parseLong(phone);
                if (Validation.doesExistCustomer(fullName, phoneNumber)) {
                    Customer customer = getCustomerInformation(fullName, phoneNumber);
                    session.setAttribute("SERVICETICKETCUSTOMER", customer);
                } else {
                    request.setAttribute("ERROR", "CUSTOMER NOT FOUND");
                    url = servicePage;
                }
            } else {
                request.setAttribute("ERROR", "INVALID NAME OR PHONE");
                url = servicePage;
            }
            request.getRequestDispatcher(url).forward(request, response);
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

    private Customer getCustomerInformation(String name, long phone) {
        CustomerDAO dao = new CustomerDAO();
        Long customerID = dao.selectCustomerID(name, phone);
        return dao.selectCustomer(customerID);
    }
}
