package vn.week3.controller;

import vn.week3.dao.CategoryDAO;
import vn.week3.model.Category;
import vn.week3.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.Part;
import java.io.File;
import java.nio.file.Paths;
import java.io.IOException;

@WebServlet("/manage/category") 
@MultipartConfig
public class CategoryController extends HttpServlet {
	private CategoryDAO categoryDAO = new CategoryDAO();
	private static final String UPLOAD_DIRECTORY = "uploads";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter("action");
		User currentUser = (User) req.getSession().getAttribute("user");

		if (action == null) {
			action = "list";
		}

		switch (action) {
		case "add":
			req.setAttribute("category", new Category());
			req.getRequestDispatcher("/views/manager/category-form.jsp").forward(req, resp);
			break;
		case "edit":
			try {
				int idToEdit = Integer.parseInt(req.getParameter("id"));
				Category category = categoryDAO.findById(idToEdit);

				if (category != null && (category.getUser().getId() == currentUser.getId() || currentUser.getRoleId() == 3)) {
					req.setAttribute("category", category);
					req.getRequestDispatcher("/views/manager/category-form.jsp").forward(req, resp);
				} else {
					resp.sendRedirect(req.getContextPath() + "/views/common/error.jsp");
				}
			} catch (NumberFormatException e) {
				resp.sendRedirect(req.getContextPath() + "/views/common/error.jsp");
			}
			break;
		case "delete":
			try {
				int idToDelete = Integer.parseInt(req.getParameter("id"));
				Category catToDelete = categoryDAO.findById(idToDelete);

				if (catToDelete != null && (catToDelete.getUser().getId() == currentUser.getId() || currentUser.getRoleId() == 3)) {
					String applicationPath = getServletContext().getRealPath("");
					String uploadPath = applicationPath + File.separator + UPLOAD_DIRECTORY;
					categoryDAO.delete(idToDelete, uploadPath);
				}
			} catch (NumberFormatException e) {
			}
			resp.sendRedirect(req.getContextPath() + "/manage-categories");
			break;
		default: 
			resp.sendRedirect(req.getContextPath() + "/manage-categories");
			break;
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    req.setCharacterEncoding("UTF-8");
	    String action = req.getParameter("action");
	    
	    if (action == null) { 
	        action = "save";
	    }

	    if ("changeOwner".equals(action)) {
	        int categoryId = Integer.parseInt(req.getParameter("categoryId"));
	        int newOwnerId = Integer.parseInt(req.getParameter("newOwnerId"));
	        categoryDAO.changeOwner(categoryId, newOwnerId);
	        resp.sendRedirect(req.getContextPath() + "/manage-categories");
	        return;
	    }


	    String idParam = req.getParameter("id");
	    String name = req.getParameter("name");
	    User currentUser = (User) req.getSession().getAttribute("user");

	    Category category;
	    boolean isAdding = (idParam == null || idParam.isEmpty());

	    if (isAdding) {
	        category = new Category();
	        category.setUser(currentUser);
	    } else {
	        category = categoryDAO.findById(Integer.parseInt(idParam));
	        if (category == null || (category.getUser().getId() != currentUser.getId() && currentUser.getRoleId() != 3)) {
	            resp.sendRedirect(req.getContextPath() + "/views/common/error.jsp");
	            return;
	        }
	    }
	    category.setName(name);

	    Part filePart = req.getPart("icon");
	    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

	    if (fileName != null && !fileName.isEmpty()) {
	        String applicationPath = getServletContext().getRealPath("");
	        String uploadPath = applicationPath + File.separator + UPLOAD_DIRECTORY;
	        File uploadDir = new File(uploadPath);
	        if (!uploadDir.exists()) uploadDir.mkdir();

	        if (!isAdding && category.getIcon() != null && !category.getIcon().isEmpty()) {
	            File oldFile = new File(uploadPath + File.separator + category.getIcon());
	            if (oldFile.exists()) oldFile.delete();
	        }

	        filePart.write(uploadPath + File.separator + fileName);
	        category.setIcon(fileName);
	    }

	    if (isAdding) {
	        categoryDAO.add(category);
	    } else {
	        categoryDAO.update(category);
	    }

	    resp.sendRedirect(req.getContextPath() + "/manage-categories");
	}
}