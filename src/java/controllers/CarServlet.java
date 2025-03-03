/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dao.CarDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Array;
import java.util.ArrayList;
import java.util.HashSet;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObjectBuilder;
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
@WebServlet(name = "CarServlet", urlPatterns = {"/CarServlet"})
public class CarServlet extends HttpServlet {

    private final String loginPage = "login.jsp";
    private final String carPage = "car.jsp";

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
        request.setCharacterEncoding("utf-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            HttpSession session = request.getSession(false);

            SalePerson salePerson = Validation.validateSalePersonSession(request, response, session);
            if (salePerson == null) {
                response.sendRedirect(loginPage);
                return;
            }
            String serial = Validation.normalize(request.getParameter("serialNumber"));
            String model = Validation.normalize(request.getParameter("model"));
            String year = Validation.normalize(request.getParameter("year"));
            String type = Validation.normalize(request.getParameter("typeCar"));

            serial = "all".equalsIgnoreCase(serial) ? "" : serial;
            model = "all".equalsIgnoreCase(model) ? "" : model;
            year = "all".equalsIgnoreCase(year) ? "" : year;
            String[] a = {serial, model, year};
            
            ArrayList<Car> listOfCars = new ArrayList<>();
            JsonBuilderFactory factory = Json.createBuilderFactory(null);
            String result = "";
            if(type.equals("sale")){
                listOfCars = getDefaultListOfCars(salePerson.getSaleID());
                result = convertCarListToJson(factory, listOfCars);
            }else if(type.equals("all")){
                listOfCars = getDefaultListOfCars();
                result = convertCarListToJson(factory, listOfCars);
            }

            ArrayList<Car> filteredCars = filterCars(listOfCars, serial, model, year);

            ArrayList<String> availableSerials = getAvailableSerials(listOfCars, serial, model, year);
            HashSet<String> availableModels = getAvailableModels(listOfCars, serial, model, year);
            HashSet<Integer> availableYears = getAvailableYears(listOfCars, serial, model, year);
            String[] types = {"Sale","All"};
            
            session.setAttribute("a", a);
            
            request.setAttribute("carJson", result);
            request.setAttribute("cars", filteredCars);
            request.setAttribute("serialNumbers", availableSerials);
            request.setAttribute("models", availableModels);
            request.setAttribute("years", availableYears);
            request.setAttribute("types", types);
            setLabelAttributes(request, serial, model, year, type);

            request.getRequestDispatcher(carPage).forward(request, response);
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

    private ArrayList<Car> getDefaultListOfCars(long saleID) {
        CarDAO dao = new CarDAO();
        dao.selectListOfCars(saleID);
        ArrayList<Car> list = dao.getListOfCars();

        return list;
    }
    private ArrayList<Car> getDefaultListOfCars() {
        CarDAO dao = new CarDAO();
        dao.selectListOfCars();
        ArrayList<Car> list = dao.getListOfCars();

        return list;
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

    private ArrayList<Car> getListOfCarsBySeiral(ArrayList<Car> cars, String serial) {
        ArrayList<Car> filter = new ArrayList<>();
        for (Car car : cars) {
            if (car.getSerialNumber().equals(serial)) {
                filter.add(car);
            }
        }
        return filter;
    }

    private ArrayList<Car> getListOfCarsByModel(ArrayList<Car> cars, String model) {
        ArrayList<Car> filter = new ArrayList<>();
        for (Car car : cars) {
            if (car.getModel().equals(model)) {
                filter.add(car);
            }
        }
        return filter;
    }

    private ArrayList<Car> getListOfCarsByYear(ArrayList<Car> cars, int year) {
        ArrayList<Car> filtered = new ArrayList<>();
        for (Car car : cars) {
            if (car.getYear() == year) {
                filtered.add(car);
            }
        }
        return filtered;
    }

    private ArrayList<Car> filterCars(ArrayList<Car> cars, String serial, String model, String year) {
        ArrayList<Car> filtered = new ArrayList<>();
        Integer yearFilter = Validation.parseInt(year);

        for (Car car : cars) {
            boolean match = true;

            if (!serial.isEmpty() && !car.getSerialNumber().equals(serial)) {
                match = false;
            }
            if (!model.isEmpty() && !car.getModel().equals(model)) {
                match = false;
            }
            if (yearFilter != null && car.getYear() != yearFilter) {
                match = false;
            }

            if (match) {
                filtered.add(car);
            }
        }
        return filtered;
    }

    private HashSet<String> getAvailableModels(ArrayList<Car> cars, String currSerial, String currModel, String currYear) {
        ArrayList<Car> filtered = filterCars(cars, currSerial, "", currYear);
        return getListOfModels(filtered);
    }

    private HashSet<Integer> getAvailableYears(ArrayList<Car> cars, String currSerial, String currModel, String currYear) {
        ArrayList<Car> filtered = filterCars(cars, currSerial, currModel, "");
        return getListOfyear(filtered);
    }

    private ArrayList<String> getAvailableSerials(ArrayList<Car> cars, String currSerial, String currModel, String currYear) {
        ArrayList<Car> filterd = filterCars(cars, "", currModel, currYear);
        return getListOfSerialNumber(filterd);
    }

    private void setLabelAttributes(HttpServletRequest request, String serial, String model, String year,String type) {
        request.setAttribute("serialLabel", serial.isEmpty() ? "Serial Number" : serial);
        request.setAttribute("modelLabel", model.isEmpty() ? "Model" : model);
        request.setAttribute("yearLabel", year.isEmpty() ? "Year" : year);
        request.setAttribute("typeLabel", year.isEmpty() ? "Type" : type);
    }
    
    private String convertCarListToJson(JsonBuilderFactory factory,ArrayList<Car> list){
        JsonArrayBuilder arrayBuilder = factory.createArrayBuilder();
        if(list!=null){
            for (Car car : list) {
                JsonObjectBuilder objectBuilder = factory.createObjectBuilder();
                objectBuilder.add("id",car.getCarID())
                        .add("serialNumber", car.getSerialNumber())
                        .add("model",car.getModel())
                        .add("year",car.getYear())
                        .add("color",car.getColour());
                arrayBuilder.add(objectBuilder);
            }
        }
        JsonArray array = arrayBuilder.build();
        String jsonString = array.toString();
        return jsonString;
    }
}
