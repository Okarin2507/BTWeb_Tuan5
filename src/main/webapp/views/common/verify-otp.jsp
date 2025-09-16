<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Xác Thực OTP</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="login-container">
        <div class="card">
            <div class="card-header text-center">
                <h2>🔑 Xác Thực Tài Khoản</h2>
                <p class="page-subtitle">Một mã OTP đã được gửi đến email <strong>${sessionScope.tempUser.email}</strong>. Vui lòng nhập mã vào đây để hoàn tất.</p>
            </div>
            <div class="card-body">
                <form action="<c:url value='/verify-otp' />" method="post">
                    <div class="form-group">
                        <label for="otp">Mã OTP (6 chữ số)</label>
                        <input type="text" id="otp" name="otp" required maxlength="6" pattern="\d{6}" title="Mã OTP phải có 6 chữ số" style="text-align: center; font-size: 1.5rem; letter-spacing: 5px;">
                    </div>

                    <c:if test="${not empty error}">
                        <div class="error-message">⚠️ ${error}</div>
                    </c:if>

                    <button type="submit" class="btn" style="width: 100%;">Xác Nhận</button>
                </form>
                 <div class="text-center mt-3">
                    <p>Không nhận được mã? <a href="<c:url value='/register'/>">Gửi lại</a></p>
                </div>
            </div>
        </div>
    </div>
</body>
</html>