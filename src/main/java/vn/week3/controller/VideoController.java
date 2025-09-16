package vn.week3.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import vn.week3.dao.VideoDAO;
import vn.week3.model.User;
import vn.week3.model.Video;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@WebServlet("/manage/video")
@MultipartConfig
public class VideoController extends HttpServlet {
    private VideoDAO videoDAO;
    private static final String UPLOAD_DIRECTORY = "uploads";

    @Override
    public void init() {
        videoDAO = new VideoDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        User currentUser = (User) req.getSession().getAttribute("user");

        if (action == null) {
            resp.sendRedirect(req.getContextPath() + "/manage-videos");
            return;
        }

        switch (action) {
            case "add":
                req.setAttribute("video", new Video());
                req.getRequestDispatcher("/views/common/video-form.jsp").forward(req, resp);
                break;
            case "edit":
                try {
                    int id = Integer.parseInt(req.getParameter("id"));
                    Video video = videoDAO.findById(id);
                    if (video != null && (video.getUser().getId() == currentUser.getId() || currentUser.getRoleId() == 3)) {
                        req.setAttribute("video", video);
                        req.getRequestDispatcher("/views/common/video-form.jsp").forward(req, resp);
                    } else {
                        resp.sendRedirect(req.getContextPath() + "/views/common/error.jsp");
                    }
                } catch (NumberFormatException e) {
                    resp.sendRedirect(req.getContextPath() + "/views/common/error.jsp");
                }
                break;
            case "delete":
                try {
                    int id = Integer.parseInt(req.getParameter("id"));
                    Video video = videoDAO.findById(id);
                    if (video != null && (video.getUser().getId() == currentUser.getId() || currentUser.getRoleId() == 3)) {
                        String applicationPath = getServletContext().getRealPath("");
                        String uploadPath = applicationPath + File.separator + UPLOAD_DIRECTORY;
                        videoDAO.delete(id, uploadPath);
                    }
                } catch (NumberFormatException e) {
                    // ignore
                }
                resp.sendRedirect(req.getContextPath() + "/manage-videos");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        User currentUser = (User) req.getSession().getAttribute("user");
        
        String idParam = req.getParameter("id");
        String title = req.getParameter("title");
        String url = req.getParameter("url");
        String description = req.getParameter("description");
        
        Video video;
        boolean isAdding = (idParam == null || idParam.isEmpty() || "0".equals(idParam));

        if (isAdding) {
            video = new Video();
        } else {
            video = videoDAO.findById(Integer.parseInt(idParam));
            if (video == null || (video.getUser().getId() != currentUser.getId() && currentUser.getRoleId() != 3)) {
                resp.sendRedirect(req.getContextPath() + "/views/common/error.jsp");
                return;
            }
        }
        
        video.setTitle(title);
        video.setUrl(url);
        video.setDescription(description);

        Part filePart = req.getPart("poster");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

        if (fileName != null && !fileName.isEmpty()) {
            String applicationPath = getServletContext().getRealPath("");
            String uploadPath = applicationPath + File.separator + UPLOAD_DIRECTORY;
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdir();

            if (!isAdding && video.getPoster() != null && !video.getPoster().isEmpty()) {
                File oldFile = new File(uploadPath + File.separator + video.getPoster());
                if (oldFile.exists()) oldFile.delete();
            }

            filePart.write(uploadPath + File.separator + fileName);
            video.setPoster(fileName);
        }

        if (isAdding) {
            videoDAO.add(video, currentUser.getId()); 
        } else {
            videoDAO.update(video);
        }

        resp.sendRedirect(req.getContextPath() + "/manage-videos");
    }
}