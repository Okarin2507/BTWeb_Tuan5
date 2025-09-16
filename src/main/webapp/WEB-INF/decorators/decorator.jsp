<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title><sitemesh:write property='title' /> - Project Manager</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">

    <sitemesh:write property='head' />
</head>
<body>

    <header>
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark shadow-sm fixed-top">
            <div class="container-fluid">
                <a class="navbar-brand fw-bold" href="${pageContext.request.contextPath}/">🚀 Project Manager</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav ms-auto mb-2 mb-lg-0 align-items-center">
                       
                        <c:if test="${not empty sessionScope.user}">
                            
                          
                            <li class="nav-item">
                                <c:set var="homeUrl">
                                    <c:choose>
                                        <c:when test="${sessionScope.user.roleId == 1}"><c:url value="/views/user/home"/></c:when>
                                        <c:when test="${sessionScope.user.roleId == 2}"><c:url value="/manage-categories"/></c:when>
                                        <c:when test="${sessionScope.user.roleId == 3}"><c:url value="/admin/users"/></c:when>
                                    </c:choose>
                                </c:set>
                                <a class="nav-link active" href="${homeUrl}">Trang Chủ</a>
                            </li>


                            <c:if test="${sessionScope.user.roleId == 3}">
                                <li class="nav-item">
                                    <a class="nav-link" href="<c:url value='/admin/users' />">Quản Lý User</a>
                                </li>
                            </c:if>

                          
                            <c:if test="${sessionScope.user.roleId == 2 || sessionScope.user.roleId == 3}">
                                 <li class="nav-item">
                                    <a class="nav-link" href="<c:url value='/manage-categories' />">Quản Lý Category</a>
                                </li>
                            </c:if>


                            <c:if test="${sessionScope.user.roleId == 2 || sessionScope.user.roleId == 3}">
                                 <li class="nav-item">
                                    <a class="nav-link" href="<c:url value='/manage-videos' />">Quản Lý Video</a>
                                </li>
                            </c:if>

                           
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/profile">
                                    Chào, <strong>${sessionScope.user.fullname}</strong>
                                </a>
                            </li>
                            

                            <li class="nav-item">
                                <a class="nav-link btn btn-danger btn-sm text-white" href="${pageContext.request.contextPath}/logout">Đăng Xuất</a>
                            </li>
                        </c:if>
                    </ul>
                </div>
            </div>
        </nav>
    </header>

    <main>
       
        <sitemesh:write property='body' />
    </main>

    <footer class="text-center text-secondary py-4 mt-auto bg-light">
        <div class="container">
            <p class="mb-0">&copy; 2025 - All Rights Reserved by Lê Ngô Nhựt Tân</p>
        </div>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>