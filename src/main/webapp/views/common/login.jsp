<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng Nhập Hệ Thống</title>
    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <!-- Custom CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="login-container">
        <div class="card">
            <div class="card-header text-center">
                <h2 class="login-title">🔐 Đăng Nhập Hệ Thống</h2>
                <p class="page-subtitle">Vui lòng nhập thông tin để tiếp tục</p>
            </div>
            
            <div class="card-body">
                <c:if test="${not empty sessionScope.successMessage}">
                    <div class="alert alert-success" style="background-color: #d4edda; color: #155724; border-color: #c3e6cb; padding: 1rem; border-radius: var(--border-radius); margin-bottom: 1.5rem; text-align: center;">
                        ${sessionScope.successMessage}
                    </div>
                    <c:remove var="successMessage" scope="session"/>
                </c:if>

                <form action="<c:url value='/login' />" method="post">
                    <div class="form-group">
                        <label for="username">👤 Tên đăng nhập</label>
                        <input type="text" 
                               id="username" 
                               name="username" 
                               required 
                               placeholder="Nhập tên đăng nhập..."
                               autocomplete="username">
                    </div>
                    
                    <div class="form-group">
                        <label for="password">🔒 Mật khẩu</label>
                        <input type="password" 
                               id="password" 
                               name="password" 
                               required 
                               placeholder="Nhập mật khẩu..."
                               autocomplete="current-password">
                    </div>
                    
                    <c:if test="${not empty error}">
                        <div class="error-message">
                            ⚠️ ${error}
                        </div>
                    </c:if>

                    <div class="form-group mt-4">
                        <button type="submit" class="btn" style="width: 100%;">
                            🚀 Đăng Nhập
                        </button>
                    </div>
                </form>
                
                <div class="text-center mt-3">
                    <p>Chưa có tài khoản? <a href="<c:url value='/register'/>" style="color: var(--primary-color); text-decoration: none; font-weight: 500;">Tạo tài khoản mới</a></p>
                </div>
            </div>
        </div>
    </div>

    <script>
        document.getElementById('username').focus();
    </script>
</body>
</html>