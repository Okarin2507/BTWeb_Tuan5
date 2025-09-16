<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<head>
    <title>Quản Lý Video</title>
</head>
<body>
<div class="container">
    <div class="card">
        <div class="card-header">
            <h2>🎬 ${viewTitle}</h2>
            <p class="page-subtitle">Quản lý và tổ chức các video trong hệ thống.</p>
        </div>
        <div class="card-body">
            <div class="toolbar">
                <a href="${pageContext.request.contextPath}/manage/video?action=add" class="btn">+ Thêm Video Mới</a>
            </div>

            <div class="table-container">
                <table class="styled-table">
                    <thead>
                        <tr>
                            <th>Poster</th>
                            <th>Tiêu Đề</th>
                            <th>Người Tạo</th>
                            <th style="width: 15%;">Hành Động</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="video" items="${videos}">
                            <tr>
                                <td>
                                    <c:if test="${not empty video.poster}">
                                        <img src="${pageContext.request.contextPath}/uploads/${video.poster}" alt="${video.title}" class="category-icon">
                                    </c:if>
                                </td>
                                <td>${video.title}</td>
                                <td>${video.user.username}</td>
                                <td class="actions">
                                    <div class="d-flex gap-2">
                                        <a href="${pageContext.request.contextPath}/manage/video?action=edit&id=${video.id}" class="btn btn-sm btn-outline-primary">✏️ Sửa</a>
                                        <a href="${pageContext.request.contextPath}/manage/video?action=delete&id=${video.id}" 
                                           onclick="return confirm('Bạn có chắc muốn xóa video \'${video.title}\'?')" class="btn btn-sm btn-outline-danger">🗑️ Xóa</a>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</body>