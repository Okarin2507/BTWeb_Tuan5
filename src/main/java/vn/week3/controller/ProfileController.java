
package vn.week3.controller;

import vn.week3.dao.UserDAO;
import vn.week3.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@WebServlet("/profile")
@MultipartConfig
public class ProfileController extends HttpServlet {
	private UserDAO userDAO = new UserDAO();
	private static final String UPLOAD_DIRECTORY = "uploads";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/views/common/profile.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User currentUser = (User) req.getSession().getAttribute("user");

		String fullname = req.getParameter("fullname");
		String phone = req.getParameter("phone");

		currentUser.setFullname(fullname);
		currentUser.setPhone(phone);

		Part filePart = req.getPart("image");
		String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

		if (fileName != null && !fileName.isEmpty()) {
			String applicationPath = getServletContext().getRealPath("");
			String uploadPath = applicationPath + File.separator + UPLOAD_DIRECTORY;

			File uploadDir = new File(uploadPath);
			if (!uploadDir.exists())
				uploadDir.mkdir();

			if (currentUser.getImage() != null && !currentUser.getImage().isEmpty()) {
				File oldFile = new File(uploadPath + File.separator + currentUser.getImage());
				if (oldFile.exists())
					oldFile.delete();
			}

			filePart.write(uploadPath + File.separator + fileName);
			currentUser.setImage(fileName);
		}

		userDAO.update(currentUser);

		req.getSession().setAttribute("user", currentUser);

		req.setAttribute("successMessage", "Cập nhật thông tin thành công!");
		req.getRequestDispatcher("/views/common/profile.jsp").forward(req, resp);
	}
}