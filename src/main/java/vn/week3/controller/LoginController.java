package vn.week3.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.week3.dao.UserDAO;
import vn.week3.model.User;

import java.io.IOException;

@WebServlet(urlPatterns = { "/login", "" })
public class LoginController extends HttpServlet {
	private UserDAO userDAO;

	@Override
	public void init() {
		userDAO = new UserDAO();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/views/common/login.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");

		User user = userDAO.findByUsername(username);

		if (user != null && user.getPassword().equals(password)) {
			HttpSession session = req.getSession();
			session.setAttribute("user", user);

			String redirectURL = req.getContextPath();
			switch (user.getRoleId()) {
			case 1:
				redirectURL += "/views/user/home";
				break;
			case 2:
				redirectURL += "/manage-categories";
				break;
			case 3:
				redirectURL += "/admin/users";
				break;
			default:
				redirectURL += "/login";
				break;
			}
			resp.sendRedirect(redirectURL);
		} else {
			req.setAttribute("error", "Tên đăng nhập hoặc mật khẩu không chính xác.");
			req.getRequestDispatcher("/views/common/login.jsp").forward(req, resp);
		}
	}

}