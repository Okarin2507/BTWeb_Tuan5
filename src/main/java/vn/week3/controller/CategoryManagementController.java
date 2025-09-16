package vn.week3.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.week3.dao.CategoryDAO;
import vn.week3.dao.UserDAO;
import vn.week3.model.Category;
import vn.week3.model.User;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/manage-categories")
public class CategoryManagementController extends HttpServlet {
	private CategoryDAO categoryDAO;
	private UserDAO userDAO;

	@Override
	public void init() {
		categoryDAO = new CategoryDAO();
		userDAO = new UserDAO();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User currentUser = (User) req.getSession().getAttribute("user");
		List<Category> categories;

		if (currentUser.getRoleId() == 3) {
			categories = categoryDAO.findAll();
			req.setAttribute("viewTitle", "Tất Cả Danh Mục Trong Hệ Thống");

			List<User> allUsers = userDAO.findAll();
			List<User> managers = allUsers.stream().filter(u -> u.getRoleId() == 2 || u.getRoleId() == 3)
					.collect(Collectors.toList());
			req.setAttribute("managers", managers);

		}

		else if (currentUser.getRoleId() == 2) {

			categories = categoryDAO.findAllManagerCategories();
			req.setAttribute("viewTitle", "Danh Mục Của Các Manager");
		}

		else {
			resp.sendRedirect(req.getContextPath() + "/views/common/error.jsp");
			return;
		}

		req.setAttribute("categories", categories);
		req.getRequestDispatcher("/views/common/category-management.jsp").forward(req, resp);
	}
}