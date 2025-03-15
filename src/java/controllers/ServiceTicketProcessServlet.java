/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controllers;

import dao.CarDAO;
import dao.MechanicDAO;
import dao.PartDAO;
import dao.ServiceDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
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
import models.Car;
import models.Mechanic;
import models.Part;
import models.SalePerson;
import models.Service;
import mylib.Validation;

/**
 *
 * @author datho
 */
@WebServlet(name="ServiceTicketProcessServlet", urlPatterns={"/ServiceTicketProcessServlet"})
public class ServiceTicketProcessServlet extends HttpServlet {
    private final String loginPage = "login.jsp";
    private final String createTicketPage = "createTicketPage.jsp";
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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            HttpSession session = request.getSession(false);

            SalePerson salePerson = Validation.validateSalePersonSession(request, response, session);
            if (salePerson == null) {
                response.sendRedirect(loginPage);
                return;
            }
            String carid = Validation.normalize(request.getParameter("carId"));
            
            JsonBuilderFactory factory = Json.createBuilderFactory(null);
            if(!carid.isEmpty()){
                Long caridNumber = Validation.parseLong(carid);
                if(caridNumber !=null){
                    Car car = getCarbyID(caridNumber);
                    session.setAttribute("CARTICKET", car);
                    
                    ArrayList<Service> services = getAllService();
                    ArrayList<Mechanic> mechanics = getAllMechanic();
                    ArrayList<Part> parts = getAllPart();
                    if(services!=null){
                        String jsonString = generateServiceJson(factory, services);
                        
                        request.setAttribute("JSONSERVICE", jsonString);
                        request.setAttribute("SERVICES", services);
                    }
                    if(mechanics!=null){
                        request.setAttribute("MECHANICS", mechanics);
                    }
                    if(parts !=null){
                        String jsonString = generatePartJson(factory, parts);
                        
                        request.setAttribute("PARTS", parts);
                        request.setAttribute("JSONPART", jsonString);
                    }
                    
                }
            }
            
            
            request.getRequestDispatcher(createTicketPage).forward(request, response);
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
    
    private Car getCarbyID(long id){
        CarDAO dao = new CarDAO();
        return dao.selectCar(id);
    }
    private ArrayList<Service> getAllService(){
        ServiceDAO dao = new ServiceDAO();
        dao.SelectAllService();
        return dao.getAllServices();
    }
    
    private String generateServiceJson(JsonBuilderFactory factory, ArrayList<Service> services){
        JsonArrayBuilder arrayBuilder = factory.createArrayBuilder();
        if(services!=null){
            for (Service service : services) {
                JsonObjectBuilder objectBuilder = factory.createObjectBuilder();
                Integer id = service.getId();
                objectBuilder.add(id.toString(),service.getHourlyRate());
                arrayBuilder.add(objectBuilder);
            }
        }
        JsonArray jsonArray = arrayBuilder.build();
        String jsonString = jsonArray.toString();
        return jsonString;
    }
    
    private String generatePartJson(JsonBuilderFactory factory, ArrayList<Part> parts){
        JsonArrayBuilder arrayBuilder = factory.createArrayBuilder();
        if(parts !=null){
            for (Part part : parts) {
                JsonObjectBuilder objectBuilder = factory.createObjectBuilder();
                objectBuilder.add("id", part.getPartID())
                        .add("name", part.getPartName())
                        .add("price", part.getRetailPrice());
                arrayBuilder.add(objectBuilder);
            }
        }
        JsonArray jsonArray = arrayBuilder.build();
        String jsonString = jsonArray.toString();
        return jsonString;
    }
    
    private ArrayList<Mechanic> getAllMechanic(){
        MechanicDAO dao = new MechanicDAO();
        dao.selectAllMechanic();
        return dao.getListAllMechanic();
    }
    
    private ArrayList<Part> getAllPart(){
        PartDAO dao = new PartDAO();
        dao.SelectAllPart();
        return dao.getListParts();
    }
}
