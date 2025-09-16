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

@WebServlet("/verify-otp")
public class VerifyOTPController extends HttpServlet {
    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/views/common/verify-otp.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String enteredOtp = req.getParameter("otp");
        String sessionOtp = (String) session.getAttribute("otpCode");
        User tempUser = (User) session.getAttribute("tempUser");

        if (sessionOtp == null || tempUser == null) {
            req.setAttribute("error", "Phiên làm việc đã hết hạn. Vui lòng đăng ký lại.");
            req.getRequestDispatcher("/views/common/register.jsp").forward(req, resp);
            return;
        }

        if (enteredOtp != null && enteredOtp.equals(sessionOtp)) {
            try {
                userDAO.registerUser(tempUser);
                session.removeAttribute("tempUser");
                session.removeAttribute("otpCode");

                session.setAttribute("successMessage", "Đăng ký thành công! Bây giờ bạn có thể đăng nhập.");
                resp.sendRedirect(req.getContextPath() + "/login");
            } catch (Exception e) {
                e.printStackTrace();
                req.setAttribute("error", "Không thể tạo tài khoản. Vui lòng thử lại.");
                req.getRequestDispatcher("/views/common/register.jsp").forward(req, resp);
            }
        } else {
            req.setAttribute("error", "Mã OTP không chính xác. Vui lòng thử lại.");
            req.getRequestDispatcher("/views/common/verify-otp.jsp").forward(req, resp);
        }
    }
}