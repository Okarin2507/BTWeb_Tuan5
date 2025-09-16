package vn.week3.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.week3.dao.VideoDAO;
import vn.week3.model.User;
import vn.week3.model.Video;

import java.io.IOException;
import java.util.List;

@WebServlet("/manage-videos")
public class VideoManagementController extends HttpServlet {
    private VideoDAO videoDAO;

    @Override
    public void init() {
        videoDAO = new VideoDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User currentUser = (User) req.getSession().getAttribute("user");
        List<Video> videos;

        if (currentUser.getRoleId() == 2 || currentUser.getRoleId() == 3) { 
            videos = videoDAO.findAll(); 
            req.setAttribute("viewTitle", "Quản Lý Video");
        } else {
            resp.sendRedirect(req.getContextPath() + "/views/common/error.jsp");
            return;
        }

        req.setAttribute("videos", videos);
        req.getRequestDispatcher("/views/common/video-management.jsp").forward(req, resp);
    }
}