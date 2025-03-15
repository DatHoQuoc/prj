/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dao.CarDAO;
import dto.CarDTO;
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
@WebServlet(name = "UpdateCarServletTwo", urlPatterns = {"/UpdateCarServletTwo"})
public class UpdateCarServletTwo extends HttpServlet {

    private final String loginPage = "login.jsp";
    private final String updateCarPage = "updateCar.jsp";

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
            String id = Validation.normalize(request.getParameter("carId"));
            String serial = Validation.normalize(request.getParameter("carSerial"));
            String model = Validation.normalize(request.getParameter("carModel"));
            String year = Validation.normalize(request.getParameter("carYear"));
            String color = Validation.normalize(request.getParameter("carColor"));
            CarDTO dto = new CarDTO(id, serial, model, year, color);
            if(!year.isEmpty()){
                Integer yearNumber = Validation.parseInt(year);
                if(yearNumber == null){
                    request.setAttribute("ERROR", "Update Faile");
                }else{
                    updateCar(request, id, color, year);
                }
            }
            
            request.setAttribute("UPDATE", dto);
            request.getRequestDispatcher(updateCarPage).forward(request, response);
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
    private void updateCar(HttpServletRequest request, String id, String color, String year){
        CarDAO dao = new CarDAO();
        boolean succes = dao.updateCar(id, color, year);
        if(succes){
            request.setAttribute("SUCCESS", "Update Successfully");
        }else{
            request.setAttribute("ERROR", "Update Fail");
        }
    }
}
