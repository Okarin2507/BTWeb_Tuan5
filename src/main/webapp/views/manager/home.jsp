<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<head>
    <title>Quản Lý Danh Mục</title>
</head>

<div class="container">
    <div class="card">
        <div class="card-header">
            <h2>Danh Sách Danh Mục Của Bạn</h2>
            <p class="page-subtitle">Quản lý và tổ chức các danh mục bạn đã tạo</p>
        </div>
        
        <div class="card-body">
            <div class="toolbar">
                <form class="search-form" action="#" method="get">
                    <input type="text" name="search" placeholder="Tìm kiếm danh mục...">
                    <button type="submit" class="btn">Tìm Kiếm</button>
                </form>
                <a href="${pageContext.request.contextPath}/views/manager/category?action=add" class="btn">
                    + Thêm Danh Mục Mới
                </a>
            </div>

            <div class="table-container">
                <table class="styled-table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Tên Danh Mục</th>
                            <th>Icon</th>
                            <th>Hành Động</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${empty categories}">
                                <tr>
                                    <td colspan="4" class="empty-state">
                                        <div class="empty-state-title">Chưa có danh mục nào</div>
                                        <div class="empty-state-subtitle">Hãy thêm danh mục đầu tiên của bạn</div>
                                    </td>
                                </tr>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="cat" items="${categories}">
                                    <tr>
                                        <td><strong>#${cat.id}</strong></td>
                                        <td>${cat.name}</td>
                                        <td>
                                            <c:if test="${not empty cat.icon}">
                                                <img src="${pageContext.request.contextPath}/uploads/${cat.icon}" alt="${cat.name}" class="category-icon">
                                            </c:if>
                                        </td>
                                        <td class="actions">
                                            <a href="${pageContext.request.contextPath}/views/manager/category?action=edit&id=${cat.id}">✏️ Sửa</a>
                                            <a href="${pageContext.request.contextPath}/views/manager/category?action=delete&id=${cat.id}" 
                                               onclick="return confirm('Bạn có chắc chắn muốn xóa danh mục này?')">🗑️ Xóa</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>