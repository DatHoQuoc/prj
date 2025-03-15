/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dao.CarDAO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author datho
 */
@WebServlet(name = "UpdateCarImageServlet", urlPatterns = {"/UpdateCarImageServlet"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 15 // 15 MB
)
public class UpdateCarImageServlet extends HttpServlet {
    private final String carPage = "ProcessServlet?btnAction=Car";
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
            try {
                // Get the uploaded file part
                Part filePart = request.getPart("image");
                String id = request.getParameter("carId");
                // Validate file exists
                if (filePart == null || filePart.getSize() == 0) {
                    request.setAttribute("ERROR","No file was uploaded");
                    return;
                }

                // Create a safe filename - don't use submitted filename directly
                String fileName = getUniqueFileName(filePart.getSubmittedFileName());

                // Validate file type (example: only allow images)
                if (!isValidImageFile(fileName)) {
                    request.setAttribute("ERROR","Only image files are allowed");
                    return;
                }

                // Get application-relative path instead of hard-coded path
                String uploadPath = "E:\\fpt university\\Semester4\\prj301\\se1821\\staff\\web\\assets\\images";
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                // Complete path with proper separator
                String filePath = uploadPath + File.separator + fileName;

                // Write the file
                try ( InputStream input = filePart.getInputStream();  FileOutputStream output = new FileOutputStream(filePath)) {

                    byte[] buffer = new byte[8192];
                    int bytesRead;
                    while ((bytesRead = input.read(buffer)) != -1) {
                        output.write(buffer, 0, bytesRead);
                    }
                    updateCarImage(request, id, fileName);
                    // Success message
                }
            } catch (Exception e) {
                request.setAttribute("ERROR","Error: " + e.getMessage());
                e.printStackTrace();
            }finally{
                request.getRequestDispatcher(carPage).forward(request, response);
            }
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
// Helper methods

    private String getUniqueFileName(String originalFilename) {
        // Extract extension
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        // Create unique name using timestamp
        return System.currentTimeMillis() + "_" + UUID.randomUUID().toString() + extension;
    }

    private boolean isValidImageFile(String fileName) {
        String fileNameLower = fileName.toLowerCase();
        return fileNameLower.endsWith(".jpg") || fileNameLower.endsWith(".jpeg")
                || fileNameLower.endsWith(".png") || fileNameLower.endsWith(".webp");
    }
    
    private void updateCarImage(HttpServletRequest request,String id,String fileName){
        CarDAO dao  = new CarDAO();
        boolean success = dao.updateCarImage(id, fileName);
        if(success){
            request.setAttribute("SUCCESS","File uploaded successfully to: assets/images/" + fileName);
        }else{
            request.setAttribute("ERROR", "Update Fail");
        }
    }
}
