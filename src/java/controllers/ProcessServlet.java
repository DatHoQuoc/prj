/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author datho
 */
public class ProcessServlet extends HttpServlet {
    private final String loginPage = "login.jsp";
    private final String loginSerlet = "LoginServlet"; 
    private final String searchCustomerServlet = "SearchCustServlet";
    private final String reportServlet = "ReportServlet";
    private final String carServlet = "CarServlet";
    private final String createCustomerServlet = "CreateCustomerServlet";
    private final String updateCustomerServlet = "UpdateCustomerServlet";
    private final String updateCustomerServletTwo = "UpdateCustomerServletTwo";
    private final String createCarServlet = "CreateCarServlet";
    private final String deleteCarServlet = "DeleteCarServlet";
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String button = request.getParameter("btnAction");
            
            String url = loginPage;
            
            if(button == null){
                //do nothing
            }else if(button.equals("Login")){
                url = loginSerlet;
            }else if(button.equals("Search")){
                url = searchCustomerServlet;
            }else if(button.equals("Report")){
                url = reportServlet;
            }else if(button.equals("Car")){
                url = carServlet;
            }else if(button.equals("CreateCust")){
                url = createCustomerServlet;
            }else if(button.equals("UpdateCust")){
                url = updateCustomerServlet;
            }else if(button.equals("UpdateCustTwo")){
                url = updateCustomerServletTwo;
            }else if(button.equals("CreateCar")){
                url = createCarServlet;
            }else if(button.equals("DeleteCar")){
                url = deleteCarServlet;
            }
            
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
