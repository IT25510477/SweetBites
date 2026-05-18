<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Custom Bookings - Sweet Bites Bakery</title>
    <link href="https://fonts.googleapis.com/css2?family=Playfair+Display:wght@400;700;800&family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <script src="https://cdn.tailwindcss.com"></script>
    <style>
        * { font-family: 'Inter', sans-serif; }
        .font-serif { font-family: 'Playfair Display', serif; }
    </style>
</head>
<body class="bg-[#F8F5F0] text-[#1A1A1A] antialiased min-h-screen">
<jsp:include page="_admin-navbar.jsp"/>

<div class="bg-dark text-white py-10 px-10">
    <div class="max-w-7xl mx-auto flex justify-between items-center">
        <div>
            <p class="text-primary text-[10px] font-bold tracking-[0.2em] uppercase mb-1">
                <c:choose>
                    <c:when test="${sessionScope.user.role == 'admin'}">Admin Portal</c:when>
                    <c:otherwise>Customer Panel</c:otherwise>
                </c:choose>
            </p>
            <h1 class="font-serif text-4xl font-bold">Custom Cake Bookings</h1>
        </div>
        <c:if test="${sessionScope.user.role != 'admin'}">
            <a href="${pageContext.request.contextPath}/custom-booking/new" class="bg-primary hover:bg-orange-600 text-white font-bold py-3 px-6 rounded-xl transition shadow-lg flex items-center gap-2">
                <i class="fas fa-plus"></i> New Request
            </a>
        </c:if>
    </div>
</div>

<div class="max-w-7xl mx-auto px-6 py-12">
    <c:if test="${param.success == 'created'}">
        <div class="bg-green-100 border border-green-400 text-green-700 px-4 py-3 rounded relative mb-8" role="alert">
            <span class="block sm:inline">Booking request submitted successfully!</span>
        </div>
    </c:if>

    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
        <c:forEach var="booking" items="${bookings}">
            <div class="bg-white rounded-3xl shadow-sm border border-gray-100 overflow-hidden hover:shadow-md transition group">
                <div class="relative h-48 bg-gray-100">
                    <c:choose>
                        <c:when test="${not empty booking.inspirationImageUrls}">
                            <div class="flex h-full overflow-x-auto snap-x snap-mandatory scrollbar-hide">
                                <c:forEach var="imgUrl" items="${booking.inspirationImageUrls}">
                                    <img src="${imgUrl}" alt="Inspiration" class="h-full min-w-full object-cover snap-center group-hover:scale-105 transition duration-500">
                                </c:forEach>
                            </div>
                            <c:if test="${booking.inspirationImageUrls.size() > 1}">
                                <div class="absolute bottom-2 right-2 bg-black/50 text-white text-[10px] px-2 py-0.5 rounded-full backdrop-blur-sm">
                                    <i class="fas fa-images mr-1"></i> ${booking.inspirationImageUrls.size()}
                                </div>
                            </c:if>
                        </c:when>
                        <c:otherwise>
                            <div class="w-full h-full flex flex-col items-center justify-center text-gray-300">
                                <i class="fas fa-cake-candles text-5xl mb-2"></i>
                                <span class="text-xs font-bold uppercase tracking-widest">No Inspiration Image</span>
                            </div>
                        </c:otherwise>
                    </c:choose>
                    <div class="absolute top-4 left-4">
                        <span class="px-3 py-1.5 rounded-lg text-[10px] font-bold uppercase tracking-widest shadow-sm
                            ${booking.bookingStatus == 'PENDING' ? 'bg-yellow-100 text-yellow-700' : 
                              booking.bookingStatus == 'CONFIRMED' ? 'bg-green-100 text-green-700' : 
                              'bg-red-100 text-red-700'}">
                            ${booking.bookingStatus}
                        </span>
                    </div>
                </div>
                
                <div class="p-6">
                    <div class="flex justify-between items-start mb-4">
                        <div>
                            <h3 class="font-serif text-xl font-bold text-dark">${booking.customCake.displayName}</h3>
                            <p class="text-xs text-gray-400 mt-1">Booking #${booking.id}</p>
                        </div>
                        <div class="text-right">
                            <p class="text-[10px] font-bold text-gray-400 uppercase tracking-widest mb-1">Delivery</p>
                            <p class="text-sm font-bold text-dark">${booking.deliveryDate}</p>
                        </div>
                    </div>
                    
                    <div class="space-y-3 mb-6">
                        <div class="flex items-center gap-3 text-sm text-gray-600">
                            <i class="fas fa-cookie-bite w-5 text-primary"></i>
                            <span>Flavor: <span class="font-medium text-dark">${booking.customCake.flavor}</span></span>
                        </div>
                        <div class="flex items-center gap-3 text-sm text-gray-600">
                            <i class="fas fa-weight-hanging w-5 text-primary"></i>
                            <span>Weight: <span class="font-medium text-dark">${booking.customCake.weightInKg} Kg</span></span>
                        </div>
                        <c:if test="${not empty booking.customCake.customMessage}">
                            <div class="flex items-start gap-3 text-sm text-gray-600">
                                <i class="fas fa-comment-dots w-5 text-primary mt-1"></i>
                                <span>Msg: <span class="font-medium text-dark italic">"${booking.customCake.customMessage}"</span></span>
                            </div>
                        </c:if>
                        <c:if test="${not empty booking.specialNotes}">
                            <div class="flex items-start gap-3 text-sm text-gray-600 bg-gray-50 p-3 rounded-xl border border-gray-100">
                                <i class="fas fa-sticky-note w-5 text-primary mt-1"></i>
                                <div>
                                    <p class="text-[10px] font-bold uppercase tracking-wider text-gray-400 mb-1">Special Notes</p>
                                    <p class="text-xs italic text-gray-700">${booking.specialNotes}</p>
                                </div>
                            </div>
                        </c:if>
                        <c:if test="${sessionScope.user.role == 'admin'}">
                            <div class="pt-2 border-t border-gray-50 flex items-center gap-3 text-sm text-gray-600">
                                <i class="fas fa-user w-5 text-primary"></i>
                                <span>Customer: <span class="font-bold text-dark">${booking.user.username}</span></span>
                            </div>
                        </c:if>
                    </div>
                    
                    <div class="flex gap-2">
                        <a href="${pageContext.request.contextPath}/custom-booking/edit/${booking.id}" class="flex-1 bg-gray-50 hover:bg-gray-100 text-gray-700 font-bold py-2.5 rounded-xl text-center text-xs transition border border-gray-100">
                            <i class="fas fa-edit mr-1"></i> Edit
                        </a>
                        <a href="${pageContext.request.contextPath}/custom-booking/delete/${booking.id}" onclick="return confirm('Cancel this booking?')" class="flex-1 bg-red-50 hover:bg-red-100 text-red-600 font-bold py-2.5 rounded-xl text-center text-xs transition border border-red-100">
                            <i class="fas fa-trash-alt mr-1"></i> Delete
                        </a>
                    </div>
                </div>
            </div>
        </c:forEach>
        
        <c:if test="${empty bookings}">
            <div class="col-span-full py-20 text-center text-gray-400">
                <i class="fas fa-calendar-xmark text-6xl mb-4 block opacity-20"></i>
                <h3 class="font-serif text-2xl font-bold text-gray-300">No bookings found</h3>
                <p class="mt-2">Requests for custom cakes will appear here.</p>
            </div>
        </c:if>
    </div>
</div>

</body>
</html>
