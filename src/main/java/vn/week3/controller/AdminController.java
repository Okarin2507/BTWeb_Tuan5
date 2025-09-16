package vn.week3.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.week3.dao.UserDAO;
import vn.week3.model.User;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/users")
public class AdminController extends HttpServlet {
    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "list"; 
        }

        switch (action) {
            case "edit":
                showEditForm(req, resp);
                break;
            case "delete":
                deleteUser(req, resp);
                break;
            default: 
                listUsers(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        updateUser(req, resp);
    }

    private void listUsers(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> userList = userDAO.findAll();
        req.setAttribute("userList", userList);
        req.getRequestDispatcher("/views/admin/user-management.jsp").forward(req, resp);
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            User user = userDAO.findById(id);
            req.setAttribute("userToEdit", user);
            req.getRequestDispatcher("/views/admin/user-form.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/admin/users");
        }
    }

    private void updateUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        int id = Integer.parseInt(req.getParameter("id"));
        String fullname = req.getParameter("fullname");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        int roleId = Integer.parseInt(req.getParameter("roleId"));

        User user = userDAO.findById(id);
        if (user != null) {
            user.setFullname(fullname);
            user.setEmail(email);
            user.setPhone(phone);
            user.setRoleId(roleId);
            userDAO.update(user);
        }
        resp.sendRedirect(req.getContextPath() + "/admin/users");
    }

    private void deleteUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            User currentUser = (User) req.getSession().getAttribute("user");
            
            if (currentUser.getId() != id) {
                userDAO.delete(id);
            }
        } catch (NumberFormatException e) {
        }
        resp.sendRedirect(req.getContextPath() + "/admin/users");
    }
}