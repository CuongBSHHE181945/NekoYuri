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
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class SearchServlet extends HttpServlet {
   
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
            out.println("<title>Servlet SearchServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SearchServlet at " + request.getContextPath () + "</h1>");
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
            RequestDispatcher rd = request.getRequestDispatcher("View/search.jsp");
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
        int roleId = (Integer)session.getAttribute("roleId");
        int accountId = (Integer)session.getAttribute("accountId");
        
        String keyword = request.getParameter("keyword");
        String[] tag = request.getParameterValues("tagCheck");
        ArrayList<Tag> tagList = DAO.INSTANCE.getTag();
        request.setAttribute("tagList", tagList);
        ArrayList<Story> resultList = new ArrayList<>();

        String source = request.getParameter("source");
        if ("userpage".equals(source)) {
            resultList = DAO.INSTANCE.loadStory(keyword, tag);
            request.setAttribute("resultList", resultList);
            RequestDispatcher rd = request.getRequestDispatcher("View/search.jsp");
            rd.forward(request, response);
        } else if ("managementpage".equals(source)) {
            if (roleId == 1){
                resultList = DAO.INSTANCE.loadStory(keyword, tag);
            }
            if (roleId == 2){
                resultList = DAO.INSTANCE.loadStoryAuthor(keyword, tag, accountId);
            }
            request.setAttribute("resultList", resultList);
            RequestDispatcher rd = request.getRequestDispatcher("View/storyManagement.jsp");
            rd.forward(request, response);
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
