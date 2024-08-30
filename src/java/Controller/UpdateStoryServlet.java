/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller;

import DAL.DAO;
import Model.*;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class UpdateStoryServlet extends HttpServlet {
   
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
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UpdateServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        int updateStoryId = Integer.parseInt(request.getParameter("updateStoryId"));
        Story updateStory = DAO.INSTANCE.loadStory(updateStoryId);
        request.setAttribute("updateStory", updateStory);
        Content content = DAO.INSTANCE.getContent(updateStoryId);
        request.setAttribute("content", content);

        ArrayList<Tag> tagList = DAO.INSTANCE.getTag();
        request.setAttribute("tagList", tagList);

        // Check for updateMsg parameter and set attribute
        String updateMsg = request.getParameter("updateMsg");
        if (updateMsg != null && updateMsg.equals("true")) {
            request.setAttribute("updateMsg", "Update successful!");
        }

        RequestDispatcher rd = request.getRequestDispatcher("View/update.jsp");
        rd.forward(request, response);
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
        int updateId = Integer.parseInt(request.getParameter("updateId"));
        String nameUpdate = request.getParameter("nameUpdate");
        String authorUpdate = request.getParameter("authorUpdate");
        String descriptionUpdate = request.getParameter("descriptionUpdate");
        String[] tagUpdate = request.getParameterValues("tagUpdate");
        String content = request.getParameter("contentUpdate");
        DAO.INSTANCE.updateStory(new Story(updateId, nameUpdate, descriptionUpdate, authorUpdate, 
            DAO.INSTANCE.getTag(tagUpdate)), content);

        // Redirect to doGet with updateStoryId and updateMsg parameters
        response.sendRedirect("Update?updateStoryId=" + updateId + "&updateMsg=true");
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
