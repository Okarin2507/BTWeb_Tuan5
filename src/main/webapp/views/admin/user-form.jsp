<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<head>
    <title>Chỉnh Sửa Người Dùng</title>
</head>
<body>
<div class="container">
    <div class="card">
        <div class="card-header">
            <h2>✏️ Chỉnh Sửa Thông Tin Người Dùng</h2>
            <p class="page-subtitle">Cập nhật chi tiết cho tài khoản: <strong>${userToEdit.username}</strong></p>
        </div>
        <div class="card-body">
            <form action="<c:url value='/admin/users' />" method="post">
                <input type="hidden" name="id" value="${userToEdit.id}">

                <div class="form-group">
                    <label for="username">Tên đăng nhập (Không thể thay đổi)</label>
                    <input type="text" id="username" name="username" value="${userToEdit.username}" readonly class="form-control-plaintext">
                </div>
                <div class="form-group">
                    <label for="fullname">Họ và Tên</label>
                    <input type="text" id="fullname" name="fullname" value="${userToEdit.fullname}" required>
                </div>
                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="email" id="email" name="email" value="${userToEdit.email}" required>
                </div>
                <div class="form-group">
                    <label for="phone">Số điện thoại</label>
                    <input type="text" id="phone" name="phone" value="${userToEdit.phone}">
                </div>
                <div class="form-group">
                    <label for="roleId">Vai Trò</label>
                    <select id="roleId" name="roleId" class="form-select">
                        <option value="1" ${userToEdit.roleId == 1 ? 'selected' : ''}>User</option>
                        <option value="2" ${userToEdit.roleId == 2 ? 'selected' : ''}>Manager</option>
                        <option value="3" ${userToEdit.roleId == 3 ? 'selected' : ''}>Admin</option>
                    </select>
                </div>

                <div class="button-group">
                    <button type="submit" class="btn">💾 Lưu Thay Đổi</button>
                    <a href="<c:url value='/admin/users' />" class="btn btn-secondary">❌ Hủy Bỏ</a>
                </div>
            </form>
        </div>
    </div>
</div>
</body>