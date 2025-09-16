<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Thông Tin Cá Nhân</title>
</head>
<body>
<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="card mt-5 shadow-lg">
                <div class="card-header bg-dark text-white text-center">
                    <h2>✏️ Chỉnh Sửa Thông Tin Cá Nhân</h2>
                </div>
                <div class="card-body p-4">

                    <c:if test="${not empty successMessage}">
                        <div class="alert alert-success">${successMessage}</div>
                    </c:if>

                    <form action="${pageContext.request.contextPath}/profile" method="post" enctype="multipart/form-data">
                        <div class="text-center mb-4">
                            <c:choose>
                                <c:when test="${not empty sessionScope.user.image}">
                                    <img src="<c:url value='/uploads/${sessionScope.user.image}' />" 
                                         alt="Avatar" class="img-thumbnail rounded-circle" style="width: 150px; height: 150px; object-fit: cover;">
                                </c:when>
                                <c:otherwise>
                                    <img src="https://via.placeholder.com/150" alt="Default Avatar" class="img-thumbnail rounded-circle">
                                </c:otherwise>
                            </c:choose>
                        </div>

                        <div class="mb-3">
                            <label for="fullname" class="form-label fw-bold">Họ và Tên</label>
                            <input type="text" class="form-control" id="fullname" name="fullname" 
                                   value="${sessionScope.user.fullname}" required>
                        </div>

                        <div class="mb-3">
                            <label for="phone" class="form-label fw-bold">Số Điện Thoại</label>
                            <input type="text" class="form-control" id="phone" name="phone"
                                   value="${sessionScope.user.phone}">
                        </div>

                        <div class="mb-3">
                            <label for="image" class="form-label fw-bold">Ảnh Đại Diện (Bỏ trống nếu không muốn đổi)</label>
                            <input class="form-control" type="file" id="image" name="image" accept="image/*">
                        </div>

                        <div class="d-grid">
                            <button type="submit" class="btn btn-primary btn-lg">💾 Lưu Thay Đổi</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>