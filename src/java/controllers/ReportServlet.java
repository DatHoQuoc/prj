/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dao.*;
import dto.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.json.*;
import models.SalePerson;
import mylib.Validation;

/**
 *
 * @author datho
 */
@WebServlet(name = "ReportServlet", urlPatterns = {"/ReportServlet"})
public class ReportServlet extends HttpServlet {

    private final String loginPage = "login.jsp";
    private final String reportPage = "report.jsp";

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
            HashMap<String, String> finalResult = new HashMap<>();
            JsonBuilderFactory factory = Json.createBuilderFactory(null);//safety thread
            //Begin saleCarByYears
            String resultSaleCar = selectDataSaleCarByYears(factory, salePerson);
            finalResult.put("carByYear", resultSaleCar);
            //End saleCarByYear

            //Begin best part used
            String resultBestPartUsed = selectBestPartUsed(factory, salePerson);
            finalResult.put("bestUsedParts", resultBestPartUsed);
            //End best part used

            //Begin best sellers model
            String resultBestSellers = selectBestSellersModel(factory, salePerson);
            finalResult.put("bestSellerModels", resultBestSellers);
            //end best sellers model

            //Begin top 3 mechanic
            MechanicDAO dao3 = new MechanicDAO();
            dao3.selectTop3Mechanic(salePerson.getSaleID());
            ArrayList<BestMechanicDTO> mechanicList = dao3.getListTop3OfMechanic();

            //End top 3 mechanic
            //begin revenue
            String revenue = selectRevenue(factory, salePerson);
            finalResult.put("revenue", revenue);
            //end revenue

            //set request
            request.setAttribute("TOP3MECHANIC", mechanicList != null ? mechanicList : new ArrayList<>());
            request.setAttribute("REPORTDATA", finalResult);
            request.getRequestDispatcher(reportPage).forward(request, response);

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

    private String selectDataSaleCarByYears(JsonBuilderFactory factory, SalePerson salePerson) {
        int endYear = LocalDate.now().getYear();
        int startYear = endYear - 5;
        SaleInvoiceDAO dao = new SaleInvoiceDAO();
        dao.statisticCarsSoldByYear(startYear, endYear, salePerson.getSaleID());
        ArrayList<SalesReportDTO> result = dao.statisticCarsSoldByYear(startYear, endYear, salePerson.getSaleID());

        JsonArrayBuilder arrayBuilder = factory.createArrayBuilder();
        if (result != null && !result.isEmpty()) {
            for (SalesReportDTO yearReport : result) {
                JsonObjectBuilder yearObjectBuilder = factory.createObjectBuilder();
                JsonObjectBuilder modelsObjectBuilder = factory.createObjectBuilder();
                Map<String, Integer> models = yearReport.getModels();
                for (Map.Entry<String, Integer> modelEntry : models.entrySet()) {
                    String model = modelEntry.getKey();
                    int carsSold = modelEntry.getValue();
                    modelsObjectBuilder.add(model, carsSold);
                }
                yearObjectBuilder.add("year", String.valueOf(yearReport.getSaleYear()))
                        .add("models", modelsObjectBuilder);

                // Add this year's object to the array
                arrayBuilder.add(yearObjectBuilder);
            }
        }
        JsonArray jsonArray = arrayBuilder.build();
        String jsonString = jsonArray.toString();
        return jsonString;
    }

    private String selectBestPartUsed(JsonBuilderFactory factory, SalePerson salePerson) {
        PartUsedDAO dao = new PartUsedDAO();
        dao.findBestUsedParts(salePerson.getSaleID());
        ArrayList<PartReportDTO> result = dao.getListBestUsedParts();

        JsonArrayBuilder arrayBuilder = factory.createArrayBuilder();
        if (result != null) {
            for (PartReportDTO partReportDTO : result) {
                if (partReportDTO != null) {
                    JsonObjectBuilder objectBuilder = factory.createObjectBuilder();
                    objectBuilder.add("id", partReportDTO.getPartID())
                            .add("name", partReportDTO.getPartName())
                            .add("number", partReportDTO.getUsed_parts());
                    arrayBuilder.add(objectBuilder);
                }
            }
        }
        JsonArray jsonArray = arrayBuilder.build();
        String jsonString = jsonArray.toString();
        return jsonString;
    }

    private String selectBestSellersModel(JsonBuilderFactory factory, SalePerson salePerson) {
        CarDAO dao = new CarDAO();
        dao.selectBestSellerCars(salePerson.getSaleID());
        ArrayList<BestSellerModelReportDTO> result = dao.getBestSellerCarsList();

        JsonArrayBuilder arrayBuilder = factory.createArrayBuilder();
        if (result != null) {
            for (BestSellerModelReportDTO bestSellerModelReportDTO : result) {
                if (bestSellerModelReportDTO != null) {
                    JsonObjectBuilder objectBuilder = factory.createObjectBuilder();
                    objectBuilder.add("model", bestSellerModelReportDTO.getModel())
                            .add("number", bestSellerModelReportDTO.getNum());
                    arrayBuilder.add(objectBuilder);
                }
            }
        }
        JsonArray jsonArray = arrayBuilder.build();
        String jsonString = jsonArray.toString();
        return jsonString;
    }

    private String selectRevenue(JsonBuilderFactory factory, SalePerson salePerson) {
        SaleInvoiceDAO dao = new SaleInvoiceDAO();
        dao.selectRevenueList(salePerson.getSaleID());
        ArrayList<RevuenueDTO> revenue = dao.getRevenueList();
        JsonArrayBuilder arrayBuilder = factory.createArrayBuilder();
        if (revenue != null) {
            for (RevuenueDTO revuenueDTO : revenue) {
                if (revuenueDTO != null) {
                    JsonObjectBuilder object = factory.createObjectBuilder();
                    object.add("year", revuenueDTO.getYear())
                            .add("laborCost", revuenueDTO.getLaborCost())
                            .add("partCost", revuenueDTO.getPartsCost());
                    arrayBuilder.add(object);
                }
            }
        }
        JsonArray array = arrayBuilder.build();
        String jsonString = array.toString();
        return jsonString;
    }
}
