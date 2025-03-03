/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dao.CarDAO;
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
@WebServlet(name = "CreateCarServlet", urlPatterns = {"/CreateCarServlet"})
public class CreateCarServlet extends HttpServlet {

    private final String createCarPage = "createNewCar.jsp";
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
            String model = Validation.normalize(request.getParameter("carModel"));
            String year = Validation.normalize(request.getParameter("carYear"));
            String color = Validation.normalize(request.getParameter("carColor"));
            String serial = Validation.normalize(request.getParameter("carSerialNumberInput"));
  
            
            ArrayList<Car> listOfCars = getDefaultListOfCars();
            HashSet<Integer> listOfYears = getListOfyear(listOfCars);
            HashSet<String> listOfModels = getListOfModels(listOfCars);
            HashSet<String> listsOfColors = getListOfColors(listOfCars);
            ArrayList<String> listOfSerialNumebr = getListOfSerialNumber(listOfCars);
            if(!year.isEmpty()){
                int yearNumber = Validation.parseInt(year);
                if(isExistCar(serial, listOfSerialNumebr)){
                    request.setAttribute("ERROR", "Car with this serial number already exists due to reloading page");
                }else{
                    insertNewCar(request, serial, model, color, yearNumber);
                }
            }
            request.setAttribute("models", listOfModels);
            request.setAttribute("years", listOfYears);
            request.setAttribute("colors", listsOfColors);
            request.getRequestDispatcher(createCarPage).forward(request, response);

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

    private HashSet<String> getListOfModels(ArrayList<Car> cars) {
        HashSet<String> models = new HashSet<>();
        for (Car car : cars) {
            String model = car.getModel();
            if (model != null && !model.isEmpty()) {
                models.add(model);
            }
        }
        return models;
    }
    
    private HashSet<String> getListOfColors(ArrayList<Car> cars){
        HashSet<String> colors = new HashSet<>();
        for (Car car : cars) {
            String color = car.getColour();
            if(color!=null && !color.isEmpty()){
                colors.add(color);
            }
        }
        return colors;
    }
     private ArrayList<String> getListOfSerialNumber(ArrayList<Car> cars) {
        ArrayList<String> serialNumbers = new ArrayList<>();
        for (Car car : cars) {
            String serial = car.getSerialNumber();
            if (serial != null && !serial.isEmpty()) {
                serialNumbers.add(serial);
            }

        }
        return serialNumbers;
    }
    
    private void insertNewCar(HttpServletRequest request, String serial, String model, String color, int year){
        CarDAO dao = new CarDAO();
        long carId = dao.insertNewCar(serial, model, color, year);
        if(carId >0){
            request.setAttribute("SUCCESS", "Create Successfully");
        }else{
            request.setAttribute("ERROR", "Create Fail");
        }
    }
    private boolean isExistCar(String serial, ArrayList<String> list){
        for (String string : list) {
            if(string.equals(serial)) return true;
        }
        return false;
    }
}
