<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Truy Cập Bị Từ Chối</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
 
</head>
<body>
    <div class="error-container">
        <div class="card">
            <div class="card-header">
                <span class="error-icon">🚫</span>
                <h1 class="error-title">Truy Cập Bị Từ Chối</h1>
                <p class="error-subtitle">Access Denied</p>
            </div>
            
            <div class="card-body">
                <div class="error-message">
                    Bạn không có quyền truy cập trang này hoặc tài nguyên được yêu cầu không tồn tại.
                </div>
                
                <div class="error-details">
                    <div class="error-details-title">Chi tiết lỗi:</div>
                    <div class="error-details-text">
                        You do not have permission to access this page or the requested resource does not exist.
                    </div>
                </div>

                <div class="help-section">
                    <div class="help-title">
                        💡 Gợi ý giải quyết
                    </div>
                    <div class="help-text">
                        Có thể bạn cần thực hiện một trong các hành động sau:
                    </div>
                    <ul class="help-list">
                        <li>Đăng nhập với tài khoản có quyền phù hợp</li>
                        <li>Kiểm tra lại đường dẫn URL</li>
                        <li>Liên hệ quản trị viên nếu bạn tin rằng đây là lỗi</li>
                        <li>Quay lại trang trước đó hoặc trang chủ</li>
                    </ul>
                </div>

                <div class="actions">
                    <a href="${pageContext.request.contextPath}/login" class="btn">
                        🔑 Đăng Nhập
                    </a>
                    <a href="javascript:history.back()" class="btn btn-secondary">
                        ← Quay Lại
                    </a>
                </div>
            </div>
        </div>
    </div>

    <script>
        let redirectTimer = setTimeout(function() {
            if (confirm('Bạn có muốn chuyển đến trang đăng nhập không?')) {
                window.location.href = '${pageContext.request.contextPath}/login';
            }
        }, 10000);

        document.addEventListener('click', function() {
            clearTimeout(redirectTimer);
        });

        document.querySelectorAll('.btn').forEach(function(btn) {
            btn.addEventListener('click', function() {
                this.style.transform = 'scale(0.95)';
                setTimeout(() => {
                    this.style.transform = '';
                }, 150);
            });
        });
    </script>
</body>
</html>