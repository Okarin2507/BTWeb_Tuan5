<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<head>
    <title>Quản Lý Danh Mục</title>
</head>
<body>
<div class="container">
    <div class="card">
        <div class="card-header">
            <h2>📂 ${viewTitle}</h2>
            <p class="page-subtitle">Quản lý và tổ chức các danh mục trong hệ thống.</p>
        </div>
        <div class="card-body">
            <div class="toolbar">
                <form class="search-form" action="#" method="get"><input type="text" name="search" placeholder="Tìm kiếm..."><button type="submit" class="btn">Tìm</button></form>
               
                <a href="${pageContext.request.contextPath}/manage/category?action=add" class="btn">+ Thêm Mới</a>
            </div>

            <div class="table-container">
                <table class="styled-table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Tên</th>
                            <th>Icon</th>
                            <c:if test="${sessionScope.user.roleId == 2 || sessionScope.user.roleId == 3}">
                                <th>Người Tạo</th>
                            </c:if>
                            <th style="width: 25%;">Hành Động</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="cat" items="${categories}">
                            <tr>
                                <td><strong>#${cat.id}</strong></td>
                                <td>${cat.name}</td>
                                <td><c:if test="${not empty cat.icon}"><img src="${pageContext.request.contextPath}/uploads/${cat.icon}" alt="${cat.name}" class="category-icon"></c:if></td>
                                
                                <c:if test="${sessionScope.user.roleId == 2 || sessionScope.user.roleId == 3}">
                                    <td>
                                        <c:if test="${sessionScope.user.roleId == 3}">
                                           
                                            <form action="<c:url value='/manage/category' />" method="post" class="d-flex align-items-center">
                                                <input type="hidden" name="action" value="changeOwner">
                                                <input type="hidden" name="categoryId" value="${cat.id}">
                                                <select name="newOwnerId" class="form-select form-select-sm" style="min-width: 120px;">
                                                    <c:forEach var="manager" items="${managers}">
                                                        <option value="${manager.id}" ${manager.id == cat.user.id ? 'selected' : ''}>
                                                            ${manager.username}
                                                        </option>
                                                    </c:forEach>
                                                </select>
                                                <button type="submit" class="btn btn-sm btn-outline-secondary ms-2">Gán</button>
                                            </form>
                                        </c:if>
                                        <c:if test="${sessionScope.user.roleId == 2}">
                                            ${cat.user.username}
                                        </c:if>
                                    </td>
                                </c:if>

                                <td class="actions">
                                    <div class="d-flex gap-2">
                                     
                                        <a href="${pageContext.request.contextPath}/manage/category?action=edit&id=${cat.id}" class="btn btn-sm btn-outline-primary">✏️ Sửa</a>
                                        <a href="${pageContext.request.contextPath}/manage/category?action=delete&id=${cat.id}" 
                                           onclick="return confirm('Xóa danh mục \'${cat.name}\'?')" class="btn btn-sm btn-outline-danger">🗑️ Xóa</a>
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