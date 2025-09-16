package vn.week3.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.week3.dao.UserDAO;
import vn.week3.model.User;
import vn.week3.util.EmailUtil;

import java.io.IOException;

@WebServlet("/register")
public class RegisterController extends HttpServlet {
	private UserDAO userDAO;

	@Override
	public void init() {
		userDAO = new UserDAO();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/views/common/register.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String fullname = req.getParameter("fullname");
		String username = req.getParameter("username");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String confirmPassword = req.getParameter("confirm_password");
		String phone = req.getParameter("phone");

		try {
			if (!password.equals(confirmPassword)) {
				req.setAttribute("error", "Mật khẩu xác nhận không khớp!");
				req.getRequestDispatcher("/views/common/register.jsp").forward(req, resp);
				return;
			}
			if (userDAO.usernameExists(username)) {
				req.setAttribute("error", "Tên đăng nhập này đã tồn tại.");
				req.getRequestDispatcher("/views/common/register.jsp").forward(req, resp);
				return;
			}
			if (userDAO.emailExists(email)) {
				req.setAttribute("error", "Địa chỉ email này đã được sử dụng.");
				req.getRequestDispatcher("/views/common/register.jsp").forward(req, resp);
				return;
			}

			String otp = EmailUtil.generateOTP();
			EmailUtil.sendOtpEmail(email, otp);

			HttpSession session = req.getSession();
			User tempUser = new User();
			tempUser.setFullname(fullname);
			tempUser.setUsername(username);
			tempUser.setEmail(email);
			tempUser.setPassword(password);

			tempUser.setPhone(phone);

			session.setAttribute("tempUser", tempUser);
			session.setAttribute("otpCode", otp);
			session.setMaxInactiveInterval(5 * 60);

			resp.sendRedirect(req.getContextPath() + "/verify-otp");

		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("error", "Đã xảy ra lỗi hệ thống: " + e.getMessage());
			req.getRequestDispatcher("/views/common/register.jsp").forward(req, resp);
		}
	}
}