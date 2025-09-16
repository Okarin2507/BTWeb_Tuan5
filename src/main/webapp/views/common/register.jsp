<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng Ký Tài Khoản</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="login-container">
        <div class="card" style="max-width: 500px;">
            <div class="card-header text-center">
                <h2>✍️ Tạo Tài Khoản Mới</h2>
                <p class="page-subtitle">Tham gia hệ thống quản lý danh mục</p>
            </div>
            <div class="card-body">
                <form action="<c:url value='/register' />" method="post">
                    <div class="form-group">
                        <label for="fullname">Họ và Tên</label>
                        <input type="text" id="fullname" name="fullname" required value="${param.fullname}">
                    </div>
                     <div class="form-group">
                        <label for="username">Tên đăng nhập</label>
                        <input type="text" id="username" name="username" required value="${param.username}">
                    </div>
                     <div class="form-group">
                        <label for="email">Email</label>
                        <input type="email" id="email" name="email" required value="${param.email}">
                    </div>
                    <div class="form-group">
                        <label for="phone">Số điện thoại</label>
                        <input type="text" id="phone" name="phone" required placeholder="Nhập số điện thoại..." value="${param.phone}">
                    </div>

                    <div class="form-group">
                        <label for="password">Mật khẩu</label>
                        <input type="password" id="password" name="password" required>
                    </div>
                    <div class="form-group">
                        <label for="confirm_password">Xác nhận mật khẩu</label>
                        <input type="password" id="confirm_password" name="confirm_password" required>
                    </div>
                    
                    <c:if test="${not empty error}">
                        <div class="error-message">⚠️ ${error}</div>
                    </c:if>

                    <button type="submit" class="btn" style="width: 100%;">Tiếp Tục</button>
                </form>
                <div class="text-center mt-3">
                    <p>Đã có tài khoản? <a href="<c:url value='/login'/>">Đăng nhập ngay</a></p>
                </div>
            </div>
        </div>
    </div>
</body>
</html>