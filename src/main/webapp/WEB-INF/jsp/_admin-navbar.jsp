<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<nav style="background:#1A1A1A; padding:0 32px; height:60px; display:flex; align-items:center; justify-content:space-between; position:sticky; top:0; z-index:1000; box-shadow:0 2px 12px rgba(0,0,0,0.3);">

    <!-- Logo -->
    <a href="${pageContext.request.contextPath}/admin/dashboard" style="display:flex; align-items:center; gap:10px; text-decoration:none;">
        <div style="width:36px; height:36px; background:#E8960E; border-radius:50%; display:flex; align-items:center; justify-content:center; font-size:1rem;">
            <i class="fas fa-shield-alt" style="color:#fff;"></i>
        </div>
        <span style="font-family:'Playfair Display',serif; color:#fff; font-size:1rem; font-weight:800; letter-spacing:0.02em;">
            <span style="color:#E8960E;">SWEET</span>BITES <span style="font-size:0.65rem; font-weight:600; color:#9ca3af; letter-spacing:0.15em; text-transform:uppercase; margin-left:4px; border:1px solid #374151; padding:2px 8px; border-radius:4px;">ADMIN</span>
        </span>
    </a>

    <!-- Center Nav Links -->
    <div style="display:flex; align-items:center; gap:4px;">
        <a href="${pageContext.request.contextPath}/admin/dashboard" style="color:#d1d5db; text-decoration:none; font-size:0.78rem; font-weight:600; padding:8px 14px; border-radius:8px; transition:all 0.2s; letter-spacing:0.05em; display:flex; align-items:center; gap:6px;"
           onmouseover="this.style.background='#374151';this.style.color='#fff'" onmouseout="this.style.background='transparent';this.style.color='#d1d5db'">
            <i class="fas fa-chart-bar"></i> DASHBOARD
        </a>
        <a href="${pageContext.request.contextPath}/product/list" style="color:#d1d5db; text-decoration:none; font-size:0.78rem; font-weight:600; padding:8px 14px; border-radius:8px; transition:all 0.2s; letter-spacing:0.05em; display:flex; align-items:center; gap:6px;"
           onmouseover="this.style.background='#374151';this.style.color='#fff'" onmouseout="this.style.background='transparent';this.style.color='#d1d5db'">
            <i class="fas fa-bread-slice"></i> PRODUCTS
        </a>
        <a href="${pageContext.request.contextPath}/category/list" style="color:#d1d5db; text-decoration:none; font-size:0.78rem; font-weight:600; padding:8px 14px; border-radius:8px; transition:all 0.2s; letter-spacing:0.05em; display:flex; align-items:center; gap:6px;"
           onmouseover="this.style.background='#374151';this.style.color='#fff'" onmouseout="this.style.background='transparent';this.style.color='#d1d5db'">
            <i class="fas fa-tags"></i> CATEGORIES
        </a>
        <a href="${pageContext.request.contextPath}/order/admin" style="color:#d1d5db; text-decoration:none; font-size:0.78rem; font-weight:600; padding:8px 14px; border-radius:8px; transition:all 0.2s; letter-spacing:0.05em; display:flex; align-items:center; gap:6px;"
           onmouseover="this.style.background='#374151';this.style.color='#fff'" onmouseout="this.style.background='transparent';this.style.color='#d1d5db'">
            <i class="fas fa-clipboard-list"></i> ORDERS
        </a>
        <a href="${pageContext.request.contextPath}/custom-booking/list" style="color:#d1d5db; text-decoration:none; font-size:0.78rem; font-weight:600; padding:8px 14px; border-radius:8px; transition:all 0.2s; letter-spacing:0.05em; display:flex; align-items:center; gap:6px;"
           onmouseover="this.style.background='#374151';this.style.color='#fff'" onmouseout="this.style.background='transparent';this.style.color='#d1d5db'">
            <i class="fas fa-birthday-cake"></i> BOOKINGS
        </a>
        <a href="${pageContext.request.contextPath}/admin/reviews" style="color:#d1d5db; text-decoration:none; font-size:0.78rem; font-weight:600; padding:8px 14px; border-radius:8px; transition:all 0.2s; letter-spacing:0.05em; display:flex; align-items:center; gap:6px;"
           onmouseover="this.style.background='#374151';this.style.color='#fff'" onmouseout="this.style.background='transparent';this.style.color='#d1d5db'">
            <i class="fas fa-star"></i> REVIEWS
        </a>
        <a href="${pageContext.request.contextPath}/admin/messages" style="color:#d1d5db; text-decoration:none; font-size:0.78rem; font-weight:600; padding:8px 14px; border-radius:8px; transition:all 0.2s; letter-spacing:0.05em; display:flex; align-items:center; gap:6px;"
           onmouseover="this.style.background='#374151';this.style.color='#fff'" onmouseout="this.style.background='transparent';this.style.color='#d1d5db'">
            <i class="fas fa-envelope"></i> MESSAGES
        </a>
    </div>

    <!-- Right: Admin info + Logout -->
    <div style="display:flex; align-items:center; gap:12px;">
        <div style="display:flex; align-items:center; gap:8px;">
            <div style="width:32px; height:32px; border-radius:50%; overflow:hidden; border:1.5px solid #E8960E; display:flex; align-items:center; justify-content:center; background:#E8960E;">
                <c:choose>
                    <c:when test="${not empty sessionScope.user.profilePicture}">
                        <img src="${pageContext.request.contextPath}${sessionScope.user.profilePicture}" alt="Avatar" style="width:100%; height:100%; object-fit:cover;">
                    </c:when>
                    <c:otherwise>
                        <span style="font-size:0.8rem; font-weight:700; color:#fff;">
                            ${not empty sessionScope.user.username ? sessionScope.user.username.substring(0,1).toUpperCase() : 'A'}
                        </span>
                    </c:otherwise>
                </c:choose>
            </div>
            <div>
                <p style="color:#fff; font-size:0.78rem; font-weight:700; line-height:1.2;">${sessionScope.user.username}</p>
                <p style="color:#E8960E; font-size:0.62rem; font-weight:700; letter-spacing:0.1em; text-transform:uppercase;">Administrator</p>
            </div>
        </div>
        <div style="width:1px; height:28px; background:#374151;"></div>
        <a href="${pageContext.request.contextPath}/user?action=logout"
           style="display:flex; align-items:center; gap:6px; background:#dc2626; color:#fff; padding:8px 16px; border-radius:8px; font-size:0.78rem; font-weight:700; text-decoration:none; transition:all 0.2s; letter-spacing:0.05em;"
           onmouseover="this.style.background='#b91c1c'" onmouseout="this.style.background='#dc2626'">
            <i class="fas fa-sign-out-alt"></i> LOGOUT
        </a>
    </div>
</nav>
