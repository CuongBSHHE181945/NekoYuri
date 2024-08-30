/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller;

import DAL.DAO;
import Model.Story;
import Model.Tag;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Admin
 */
public class NewStoryServlet extends HttpServlet {
   
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
            out.println("<title>Servlet NewStoryServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet NewStoryServlet at " + request.getContextPath () + "</h1>");
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
        ArrayList<Tag> tagList = DAO.INSTANCE.getTag();
        request.setAttribute("tagList", tagList);
        RequestDispatcher rd = request.getRequestDispatcher("View/newStory.jsp");
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
        HttpSession session = request.getSession(false);

        ArrayList<Tag> tagList = DAO.INSTANCE.getTag();
        request.setAttribute("tagList", tagList);

        String storyName = request.getParameter("newName");
        String author = request.getParameter("newAuthor");
        String description = request.getParameter("newDescription");
        String[] tags = request.getParameterValues("newTag");
        String content = request.getParameter("content");

        boolean hasError = false;
        StringBuilder errorMsg = new StringBuilder("Please fill in the following fields: ");

        if (storyName == null || storyName.trim().isEmpty()) {
            hasError = true;
            errorMsg.append("Name ");
        }
        if (author == null || author.trim().isEmpty()) {
            hasError = true;
            errorMsg.append("Author ");
        }
        if (description == null || description.trim().isEmpty()) {
            hasError = true;
            errorMsg.append("Description ");
        }
        if (tags == null || tags.length == 0) {
            hasError = true;
            errorMsg.append("Tags ");
        }
        if (content == null || content.trim().isEmpty()) {
            hasError = true;
            errorMsg.append("Content ");
        }

        if (hasError) {
            request.setAttribute("errorMsg", errorMsg.toString());
            request.setAttribute("storyName", storyName);
            request.setAttribute("author", author);
            request.setAttribute("description", description);
            request.setAttribute("tags", tags);
            request.setAttribute("content", content);
            request.setAttribute("createMsg", false);
            RequestDispatcher rd = request.getRequestDispatcher("View/newStory.jsp");
            rd.forward(request, response);
        } else {
            int accountId = (Integer) session.getAttribute("accountId");
            Date currentDate = new Date();
            DAO.INSTANCE.insertStory(new Story(storyName, description, author, accountId, currentDate), tags, content);
            request.setAttribute("createMsg", "Create successful!");
            doGet(request, response);
        }
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
