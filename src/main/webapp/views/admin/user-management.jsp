<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<head>
    <title>Quản Lý Người Dùng</title>
</head>
<body>
<div class="container">
    <div class="card">
        <div class="card-header">
            <h2>👥 Quản Lý Người Dùng</h2>
            <p class="page-subtitle">Xem và chỉnh sửa thông tin người dùng trong hệ thống.</p>
        </div>
        <div class="card-body">
            <div class="table-container">
                <table class="styled-table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Tên Đăng Nhập</th>
                            <th>Họ và Tên</th>
                            <th>Email</th>
                            <th>Vai Trò</th>
                            <th style="width: 15%;">Hành Động</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${empty userList}">
                                <tr>
                                    <td colspan="6" class="empty-state">Không có người dùng nào.</td>
                                </tr>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="user" items="${userList}">
                                    <tr>
                                        <td><strong>#${user.id}</strong></td>
                                        <td>${user.username}</td>
                                        <td>${user.fullname}</td>
                                        <td>${user.email}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${user.roleId == 1}"><span class="badge bg-secondary">User</span></c:when>
                                                <c:when test="${user.roleId == 2}"><span class="badge bg-primary">Manager</span></c:when>
                                                <c:when test="${user.roleId == 3}"><span class="badge bg-success">Admin</span></c:when>
                                            </c:choose>
                                        </td>

                                        <td class="actions">
                                            <div class="d-flex gap-2">
                                                <a href="<c:url value='/admin/users?action=edit&id=${user.id}' />" class="btn btn-sm btn-outline-primary">✏️ Sửa</a>
                                                <c:if test="${sessionScope.user.id != user.id}">
                                                    <a href="<c:url value='/admin/users?action=delete&id=${user.id}' />" 
                                                       onclick="return confirm('Bạn có chắc chắn muốn xóa người dùng \'${user.username}\'?')" class="btn btn-sm btn-outline-danger">🗑️ Xóa</a>
                                                </c:if>
                                            </div>
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
</body>