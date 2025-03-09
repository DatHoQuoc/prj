/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dao.CarDAO;
import dto.CarDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Car;
import models.SalePerson;
import mylib.Validation;

/**
 *
 * @author datho
 */
@WebServlet(name = "UpdateCarServlet", urlPatterns = {"/UpdateCarServlet"})
public class UpdateCarServlet extends HttpServlet {

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
            if (id.isEmpty() || year.isEmpty()) {
                request.getRequestDispatcher("ProcessServlet?btnAction=Search").forward(request, response);
                return;
            } else {
                CarDTO car = new CarDTO(id, serial, model, year, color);
                ArrayList<Car> listOfCars = getDefaultListOfCars();
                HashSet<String> listsOfColors = getListOfColors(listOfCars);
                HashSet<Integer> listOfYears = getListOfyear(listOfCars);
                request.setAttribute("years", listOfYears);
                request.setAttribute("colors", listsOfColors);
                request.setAttribute("UPDATE", car);
            }
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

    private ArrayList<Car> getDefaultListOfCars() {
        CarDAO dao = new CarDAO();
        dao.selectListOfCars();
        ArrayList<Car> list = dao.getListOfCars();

        return list;
    }

    private HashSet<Integer> getListOfyear(ArrayList<Car> cars) {
        HashSet<Integer> years = new HashSet<>();
        for (Car car : cars) {
            Integer year = car.getYear();
            if (year != null) {
                years.add(year);
            }
        }
        return years;
    }

    private HashSet<String> getListOfColors(ArrayList<Car> cars) {
        HashSet<String> colors = new HashSet<>();
        for (Car car : cars) {
            String color = car.getColour();
            if (color != null && !color.isEmpty()) {
                colors.add(color);
            }
        }
        return colors;
    }
}
