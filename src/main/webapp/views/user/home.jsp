<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<head>
    <title>Trang Chủ</title>
    <style>
        .videos-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
            gap: 1.5rem;
        }
        .video-card {
            border: 1px solid var(--border-color);
            border-radius: var(--border-radius);
            overflow: hidden;
            transition: transform 0.2s ease, box-shadow 0.2s ease;
        }
        .video-card:hover {
            transform: translateY(-5px);
            box-shadow: var(--shadow);
        }
        .video-card a {
            text-decoration: none;
            color: inherit;
        }
        .video-poster img {
            width: 100%;
            aspect-ratio: 16 / 9;
            object-fit: cover;
            display: block;
        }
        .video-info {
            padding: 1rem;
        }
        .video-title {
            font-weight: 600;
            margin: 0;
            font-size: 1rem;
        }
    </style>
</head>
<body>
<div class="container">

    <div class="card mb-4">
        <div class="card-header"><h2>🛍️ Danh Mục Sản Phẩm</h2></div>
        <div class="card-body">
            <c:if test="${not empty categories}">
                <div class="categories-grid">
                    <c:forEach var="cat" items="${categories}">
                        <div class="category-card">
                            <div class="category-card-icon-wrapper"><img src="<c:url value='/uploads/${cat.icon}' />" alt="${cat.name}" class="category-card-icon"></div>
                            <div class="category-card-info"><h3 class="category-card-title">${cat.name}</h3></div>
                        </div>
                    </c:forEach>
                </div>
            </c:if>
        </div>
    </div>


    <div class="card">
        <div class="card-header"><h2>🎬 Video Mới Nhất</h2></div>
        <div class="card-body">
            <c:choose>
                <c:when test="${empty videos}">
                    <div class="empty-state">Chưa có video nào.</div>
                </c:when>
                <c:otherwise>
                    <div class="videos-grid">
                        <c:forEach var="video" items="${videos}">
                            <div class="video-card">
                                <a href="${video.url}" target="_blank">
                                    <div class="video-poster">
                                        <c:choose>
                                            <c:when test="${not empty video.poster}">
                                                <img src="<c:url value='/uploads/${video.poster}' />" alt="${video.title}">
                                            </c:when>
                                            <c:otherwise>
                                                <img src="https://via.placeholder.com/400x225.png?text=No+Poster" alt="No Poster">
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                    <div class="video-info">
                                        <h3 class="video-title">${video.title}</h3>
                                    </div>
                                </a>
                            </div>
                        </c:forEach>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
</body>