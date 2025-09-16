<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>${not empty category.id && category.id != 0 ? 'Chỉnh Sửa' : 'Thêm Mới'} Danh Mục</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
</head>
<body>
    <div class="container">
        <div class="card">
            <div class="card-header">
                <div class="page-header">
                    <h2>
                        <c:choose>
                            <c:when test="${not empty category.id && category.id != 0}">✏️ Chỉnh Sửa Danh Mục</c:when>
                            <c:otherwise>➕ Tạo Danh Mục Mới</c:otherwise>
                        </c:choose>
                    </h2>
                    <p class="page-subtitle">
                        <c:choose>
                            <c:when test="${not empty category.id && category.id != 0}">Cập nhật thông tin danh mục "${category.name}"</c:when>
                            <c:otherwise>Điền thông tin để tạo danh mục mới</c:otherwise>
                        </c:choose>
                    </p>
                </div>
            </div>
            
            <div class="card-body">
                <div class="form-container">
                   
                    <form action="<c:url value='/manage/category' />" method="post" enctype="multipart/form-data">
                        
                        <c:if test="${not empty category.id && category.id != 0}">
                             <input type="hidden" name="id" value="${category.id}">
                        </c:if>
                        
                        <div class="form-group">
                            <label for="name">📝 Tên Danh Mục</label>
                            <input type="text" id="name" name="name" value="${category.name}" required placeholder="Ví dụ: Lập Trình, Thời Trang...">
                        </div>
                        
                        <div class="form-group">
                            <label for="icon">🖼️ Icon Danh Mục</label>
                            <input type="file" id="icon" name="icon" class="form-control" accept="image/*">
                            <c:if test="${not empty category.icon}">
                                <div class="mt-2">
                                    <strong>Icon hiện tại:</strong><br>
                                    <img src="<c:url value='/uploads/${category.icon}' />" alt="Current Icon" style="max-width: 100px; margin-top: 10px;">
                                </div>
                            </c:if>
                        </div>
                        
                        <div class="button-group mt-4">
                            <button type="submit" class="btn">
                                <c:choose>
                                    <c:when test="${not empty category.id && category.id != 0}">💾 Cập Nhật</c:when>
                                    <c:otherwise>✅ Tạo Mới</c:otherwise>
                                </c:choose>
                            </button>

                            <a href="<c:url value='/manage-categories' />" class="btn btn-secondary">❌ Hủy Bỏ</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</body>
</html>