package vn.week3.controller;

import vn.week3.dao.CategoryDAO;
import vn.week3.dao.VideoDAO;
import vn.week3.model.Category;
import vn.week3.model.Video; 
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/views/user/home"})
public class HomeController extends HttpServlet {
    private CategoryDAO categoryDAO = new CategoryDAO();
    private VideoDAO videoDAO = new VideoDAO(); 

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      
        List<Category> categories = categoryDAO.findAll();
        req.setAttribute("categories", categories);
        
       
        List<Video> videos = videoDAO.findAll();
        req.setAttribute("videos", videos);

        req.getRequestDispatcher("/views/user/home.jsp").forward(req, resp);
    }
}