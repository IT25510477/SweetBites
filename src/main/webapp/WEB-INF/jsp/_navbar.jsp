<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:choose>
    <c:when test="${sessionScope.user != null and sessionScope.user.role == 'admin'}">
        <jsp:include page="_admin-navbar.jsp"/>
    </c:when>
    <c:otherwise>
<style>
    /* Navbar specific styles to match index.jsp's Tailwind design */
    .sweet-navbar-wrapper {
        background: #fff;
        width: 100%;
        position: sticky;
        top: 0;
        z-index: 1000;
        box-shadow: 0 1px 2px 0 rgba(0, 0, 0, 0.05);
        font-family: 'Inter', sans-serif;
    }
    .sweet-navbar {
        max-width: 1400px;
        margin: 0 auto;
        padding: 20px 32px;
        display: flex;
        align-items: center;
        justify-content: space-between;
    }
    .sweet-nav-logo {
        display: flex;
        align-items: center;
        font-family: 'Playfair Display', serif;
        font-size: 1.4rem;
        font-weight: 700;
        letter-spacing: 0.025em;
        white-space: nowrap;
        text-decoration: none;
        color: #1A1A1A;
    }
    .sweet-nav-logo span {
        color: #E8960E;
    }
    .sweet-nav-links {
        display: none;
    }
    @media (min-width: 768px) {
        .sweet-nav-links {
            display: flex;
            align-items: center;
            gap: 40px;
            font-size: 0.875rem;
            font-weight: 700;
            letter-spacing: 0.1em;
        }
    }
    .sweet-nav-links a {
        color: #1A1A1A;
        text-decoration: none;
        transition: color 0.2s;
    }
    .sweet-nav-links a:hover {
        color: #E8960E;
    }
    .sweet-nav-active {
        border-bottom: 2px solid #E8960E;
        color: #E8960E !important;
        padding-bottom: 4px;
    }
    .sweet-nav-actions {
        display: flex;
        align-items: center;
        gap: 20px;
    }
    .sweet-icon-btn {
        color: #1A1A1A;
        text-decoration: none;
        font-size: 1.25rem;
        transition: color 0.2s;
        position: relative;
        display: inline-flex;
    }
    .sweet-icon-btn:hover {
        color: #E8960E;
    }
    .sweet-cart-badge {
        position: absolute;
        top: -8px;
        right: -8px;
        background-color: #E8960E;
        color: white;
        font-size: 10px;
        font-weight: 700;
        border-radius: 9999px;
        width: 16px;
        height: 16px;
        display: flex;
        align-items: center;
        justify-content: center;
    }
    .sweet-nav-divider {
        border-left: 1px solid #d1d5db;
        height: 24px;
    }
    .sweet-btn-outline {
        color: #1A1A1A;
        font-size: 0.875rem;
        font-weight: 700;
        text-decoration: none;
        border: 1px solid #d1d5db;
        padding: 6px 16px;
        border-radius: 9999px;
        transition: color 0.2s;
    }
    .sweet-btn-outline:hover {
        color: #E8960E;
    }
    .sweet-btn-filled {
        background-color: #E8960E;
        color: white;
        font-size: 0.875rem;
        font-weight: 700;
        text-decoration: none;
        padding: 10px 24px;
        border-radius: 9999px;
        transition: background-color 0.2s;
        letter-spacing: 0.025em;
    }
    .sweet-btn-filled:hover {
        background-color: #f97316;
    }
    .sweet-nav-text-link {
        font-size: 0.875rem;
        font-weight: 700;
        color: #1A1A1A;
        text-decoration: none;
        letter-spacing: 0.1em;
        transition: color 0.2s;
    }
    .sweet-nav-text-link:hover {
        color: #E8960E;
    }
</style>

<div class="sweet-navbar-wrapper">
    <div class="sweet-navbar">
        <!-- Logo -->
        <a href="${pageContext.request.contextPath}/" class="sweet-nav-logo">
            SWEET<span>BITES BAKERY</span>
        </a>

        <!-- Links -->
        <div class="sweet-nav-links">
            <a href="${pageContext.request.contextPath}/" class="${pageContext.request.requestURI eq '/' or pageContext.request.requestURI eq '/index.jsp' ? 'sweet-nav-active' : ''}">HOME</a>
            <a href="${pageContext.request.contextPath}/product/list" class="${pageContext.request.requestURI.endsWith('/product/list') ? 'sweet-nav-active' : ''}">MENU</a>
            <a href="${pageContext.request.contextPath}/category/list" class="${pageContext.request.requestURI.endsWith('/category/list') ? 'sweet-nav-active' : ''}">CATEGORY</a>
            <a href="${pageContext.request.contextPath}/custom-booking/new" class="${pageContext.request.requestURI.endsWith('/custom-booking/new') ? 'sweet-nav-active' : ''}">CUSTOM CAKE</a>
            <a href="${pageContext.request.contextPath}/#contact-us">CONTACT US</a>
        </div>

        <!-- Right Icons -->
        <div class="sweet-nav-actions">
            <a href="${pageContext.request.contextPath}/product/list" class="sweet-icon-btn"><i class="fas fa-search"></i></a>
            <c:if test="${sessionScope.user.role != 'admin'}">
                <a href="${pageContext.request.contextPath}/cart" class="sweet-icon-btn">
                    <i class="fas fa-shopping-cart"></i>
                </a>
            </c:if>

            <div class="sweet-nav-divider"></div>

            <c:choose>
                <c:when test="${not empty sessionScope.user}">
                    <a href="${pageContext.request.contextPath}/user?action=edit" class="sweet-icon-btn" style="color: #E8960E; display: inline-flex; align-items: center; justify-content: center; width: 32px; height: 32px; border-radius: 50%; overflow: hidden; border: 1.5px solid #E8960E; vertical-align: middle;">
                        <c:choose>
                            <c:when test="${not empty sessionScope.user.profilePicture}">
                                <img src="${pageContext.request.contextPath}${sessionScope.user.profilePicture}" alt="Avatar" class="w-full h-full object-cover">
                            </c:when>
                            <c:otherwise>
                                <i class="fas fa-user-circle" style="font-size: 1.25rem;"></i>
                            </c:otherwise>
                        </c:choose>
                    </a>
                    <a href="${pageContext.request.contextPath}/user?action=logout" class="sweet-btn-outline">LOGOUT</a>
                    <c:choose>
                        <c:when test="${sessionScope.user.role == 'admin'}">
                            <a href="${pageContext.request.contextPath}/order/admin" class="sweet-btn-outline">MANAGE ORDERS</a>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/order/history" class="sweet-btn-outline">MY ORDERS</a>
                        </c:otherwise>
                    </c:choose>
                    <c:if test="${sessionScope.user.role == 'admin'}">
                        <a href="${pageContext.request.contextPath}/admin/dashboard" class="sweet-btn-outline" style="border-color:#E8960E; color:#E8960E;">
                            <i class="fas fa-shield-alt"></i> ADMIN
                        </a>
                    </c:if>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/login" class="sweet-nav-text-link">LOGIN</a>
                    <a href="${pageContext.request.contextPath}/register" class="sweet-btn-filled">SIGN UP</a>
                    <a href="${pageContext.request.contextPath}/login" class="sweet-icon-btn" style="color: #E8960E;">
                        <i class="fas fa-shield-alt"></i>
                    </a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
    </c:otherwise>
</c:choose>
