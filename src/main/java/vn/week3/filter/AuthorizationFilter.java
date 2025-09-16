package vn.week3.filter;

import vn.week3.model.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(urlPatterns = {
    "/admin/*", 
    "/manage-categories", "/manage/category",
    "/manage-videos", "/manage/video",
    "/views/user/*",
    "/profile"
})
public class AuthorizationFilter implements Filter {

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
	    HttpServletRequest request = (HttpServletRequest) servletRequest;
	    HttpServletResponse response = (HttpServletResponse) servletResponse;
	    HttpSession session = request.getSession(false);
	    String contextPath = request.getContextPath();
	    String requestURI = request.getRequestURI();

        // --- BẮT ĐẦU KHU VỰC DEBUG ---
        System.out.println("\n--- AUTHORIZATION FILTER DEBUG ---");
        System.out.println("Request URI: " + requestURI);

	    if (session == null || session.getAttribute("user") == null) {
            System.out.println("Decision: No session or user found. Redirecting to /login.");
            System.out.println("------------------------------------");
	        response.sendRedirect(contextPath + "/login");
	        return;
	    }

	    User user = (User) session.getAttribute("user");
	    int roleId = user.getRoleId();
        System.out.println("User in session: " + user.getUsername() + ", RoleID: " + roleId);

	    if (requestURI.startsWith(contextPath + "/admin/")) {
            System.out.println("Path matches /admin/*. Checking for Admin role (3).");
	        if (roleId != 3) {
                System.out.println("Decision: FAILED. User is not Admin. Redirecting to error page.");
                System.out.println("------------------------------------");
	            sendError(request, response);
	            return;
	        }
	    }
	    
	    else if (requestURI.startsWith(contextPath + "/manage")) {
            System.out.println("Path matches /manage*. Checking for Manager (2) or Admin (3) role.");
	        if (roleId != 2 && roleId != 3) {
                System.out.println("Decision: FAILED. User is not Manager or Admin. Redirecting to error page.");
                System.out.println("------------------------------------");
	             sendError(request, response);
	             return;
	        }
	    }
	    
	    else if (requestURI.startsWith(contextPath + "/views/user/")) {
            System.out.println("Path matches /views/user/*. Checking for User role (1).");
	        if (roleId != 1) {
                System.out.println("Decision: FAILED. User is not a standard User. Redirecting to error page.");
                System.out.println("------------------------------------");
	            sendError(request, response);
	            return;
	        }
	    }

        System.out.println("Decision: PASSED. Allowing request to continue.");
        System.out.println("------------------------------------");
        // --- KẾT THÚC KHU VỰC DEBUG ---

	    filterChain.doFilter(servletRequest, servletResponse);
	}

	private void sendError(HttpServletRequest request, HttpServletResponse response) throws IOException {
	    response.sendRedirect(request.getContextPath() + "/views/common/error.jsp");
	}
}