/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dao.CarDAO;
import dao.CustomerDAO;
import dao.PartDAO;
import dao.ServiceDAO;
import dao.ServiceTicketDAO;
import dto.PartDTO;
import dto.ServiceDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.*;
import mylib.Validation;

/**
 *
 * @author datho
 */
@WebServlet(name = "ViewTicketServlet", urlPatterns = {"/ViewTicketServlet"})
public class ViewTicketServlet extends HttpServlet {

    private final String loginPage = "login.jsp";
    private final String ticketDetail = "viewServiceTicket.jsp";
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
            
            if(!id.isEmpty()){
                Integer idNumber = Validation.parseInt(id);
                if(idNumber!=null){
                    ServiceTicket serviceTiket = getServiceTicket(idNumber);
                    long custid = serviceTiket.getCustID();
                    long carid = serviceTiket.getCarID();
                    Customer customer = getCustomer(custid);
                    Car car = getCar(carid);
                    ArrayList<ServiceDTO> services = getServiceListById(idNumber);
                    ArrayList<PartDTO> parts = getParts(idNumber);
                    
                    request.setAttribute("PartDetail", parts);
                    request.setAttribute("ServiceDetail", services);
                    request.setAttribute("CarDetail", car);
                    request.setAttribute("CustomerDetail", customer);
                    request.setAttribute("TicketDetail", serviceTiket);
                }
            }
            request.getRequestDispatcher(ticketDetail).forward(request, response);
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

    private ArrayList<ServiceDTO> getServiceListById(int id) {
        ServiceDAO dao = new ServiceDAO();
        dao.SelectServiceByServiceTicketID(id);
        return dao.getListOfService();
    }

    private ServiceTicket getServiceTicket(int id) {
        ServiceTicketDAO dao = new ServiceTicketDAO();
        return dao.selectServiceTicket(id);
    }
    private Customer getCustomer(long id){
        CustomerDAO dao = new CustomerDAO();
        return dao.selectCustomer(id);
    }
    private Car getCar(long id){
        CarDAO dao = new CarDAO();
        return dao.selectCar(id);
    }
    private ArrayList<PartDTO> getParts(int id){
        PartDAO dao = new PartDAO();
        dao.SelectPartyServiceTicketID(id);
        return dao.getListOfPart();
    }
}
