<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Profile Settings - Sweet Bites Bakery</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link href="https://fonts.googleapis.com/css2?family=Playfair+Display:wght@400;600;700&family=Inter:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <script>
        tailwind.config = {
            theme: {
                extend: {
                    colors: { primary: '#E99B45', dark: '#1F2937', lightbg: '#F8F9FA', inputbg: '#F3F4F6' },
                    fontFamily: { serif: ['Playfair Display', 'serif'], sans: ['Inter', 'sans-serif'] }
                }
            }
        }

        function validatePasswords() {
            var newPassword = document.getElementById("newPassword").value;
            var confirmPassword = document.getElementById("confirmPassword").value;
            if (newPassword !== "" && newPassword !== confirmPassword) {
                alert("New Password and Confirm New Password do not match!");
                return false;
            }
            return true;
        }
    </script>
    <style>body { font-family: 'Inter', sans-serif; background-color: #F8F9FA; }</style>
</head>
<body class="text-gray-800 flex flex-col min-h-screen">

    <!-- Header -->
    <jsp:include page="_navbar.jsp"/>

    <!-- Main Content Container -->
    <div class="flex-grow container mx-auto px-6 py-12 flex justify-center">
        <div class="bg-white rounded-3xl shadow-lg w-full max-w-5xl flex flex-col md:flex-row overflow-hidden border border-gray-100">
            
            <!-- Left Sidebar -->
            <div class="md:w-1/3 bg-white p-10 flex flex-col items-center border-r border-gray-100">
                <div class="relative mb-4">
                    <div class="w-28 h-28 rounded-2xl overflow-hidden shadow-md border border-gray-200 flex items-center justify-center bg-gray-50">
                        <c:choose>
                            <c:when test="${not empty userToEdit.profilePicture}">
                                <img src="${pageContext.request.contextPath}${userToEdit.profilePicture}" alt="Profile Picture" class="w-full h-full object-cover">
                            </c:when>
                            <c:otherwise>
                                <div class="w-full h-full bg-[#E8960E] text-white text-4xl font-bold flex items-center justify-center">
                                    ${not empty userToEdit.username ? userToEdit.username.substring(0,1).toUpperCase() : 'U'}
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="absolute -bottom-1 -right-1 w-5 h-5 bg-green-500 border-2 border-white rounded-full"></div>
                </div>
                <h3 class="text-xl font-bold text-dark text-center mt-3">${userToEdit.username}</h3>
                <p class="text-xs text-gray-400 tracking-widest font-semibold uppercase mt-1">Morning Rise Member</p>
                <p class="text-[11px] text-gray-400 mt-2 mb-8">Joined: <span class="font-bold text-dark">${userToEdit.formattedCreatedAt}</span></p>
                
                <a href="${pageContext.request.contextPath}/" class="w-full bg-white border border-gray-200 text-gray-600 font-semibold py-3 rounded-lg flex items-center justify-center hover:bg-gray-50 transition">
                    <i class="fas fa-chevron-left mr-2 text-sm"></i> Back to Shop
                </a>
            </div>
            
            <!-- Right Content -->
            <div class="md:w-2/3 p-10 relative">
                <h2 class="text-3xl font-serif text-[#E8960E] mb-2 font-bold" style="font-family: 'Playfair Display', serif;">Profile Settings</h2>
                <p class="text-gray-500 text-sm mb-8">Manage your personal information and security preferences.</p>
                
                <c:if test="${param.success == 'profile_updated'}">
                    <div class="bg-green-100 border-l-4 border-green-500 text-green-700 p-4 mb-6 text-sm rounded">Profile details updated successfully!</div>
                </c:if>
                <c:if test="${param.success == 'password_updated'}">
                    <div class="bg-green-100 border-l-4 border-green-500 text-green-700 p-4 mb-6 text-sm rounded">Password changed successfully!</div>
                </c:if>
                <c:if test="${param.error == 'invalid_password'}">
                    <div class="bg-red-100 border-l-4 border-red-500 text-red-700 p-4 mb-6 text-sm rounded">Current password is incorrect!</div>
                </c:if>
                <c:if test="${param.error == 'password_empty'}">
                    <div class="bg-red-100 border-l-4 border-red-500 text-red-700 p-4 mb-6 text-sm rounded">Password fields cannot be empty!</div>
                </c:if>
                <c:if test="${param.error == 'password_failed'}">
                    <div class="bg-red-100 border-l-4 border-red-500 text-red-700 p-4 mb-6 text-sm rounded">Password update failed!</div>
                </c:if>
                <c:if test="${param.error == 'profile_failed'}">
                    <div class="bg-red-100 border-l-4 border-red-500 text-red-700 p-4 mb-6 text-sm rounded">Profile update failed!</div>
                </c:if>

                <!-- FORM 1: Profile Details -->
                <form action="user" method="post" enctype="multipart/form-data" class="mb-10">
                    <input type="hidden" name="action" value="update-profile">
                    <input type="hidden" name="id" value="${userToEdit.id}">
                    
                    <div class="grid grid-cols-1 md:grid-cols-2 gap-6 mb-6">
                        <div>
                            <label class="block text-xs font-bold text-gray-400 uppercase tracking-wider mb-2">Username</label>
                            <input type="text" name="username" value="${userToEdit.username}" required class="w-full bg-inputbg text-gray-800 rounded-lg py-3 px-4 focus:outline-none focus:ring-2 focus:ring-primary">
                        </div>
                        <div>
                            <label class="block text-xs font-bold text-gray-400 uppercase tracking-wider mb-2">Email Address</label>
                            <div class="relative">
                                <i class="far fa-envelope absolute left-4 top-3.5 text-gray-400"></i>
                                <input type="email" name="email" value="${userToEdit.email}" required class="w-full bg-inputbg text-gray-800 rounded-lg py-3 pl-10 pr-4 focus:outline-none focus:ring-2 focus:ring-primary">
                            </div>
                        </div>
                    </div>
                    
                    <div class="grid grid-cols-1 md:grid-cols-2 gap-6 mb-6">
                        <div>
                            <label class="block text-xs font-bold text-gray-400 uppercase tracking-wider mb-2">Profile Picture</label>
                            <input type="file" name="profilePictureFile" accept="image/*" class="w-full bg-inputbg text-gray-800 rounded-lg py-2 px-4 focus:outline-none focus:ring-2 focus:ring-primary text-sm">
                        </div>
                        <div>
                            <label class="block text-xs font-bold text-gray-400 uppercase tracking-wider mb-2">Role (Read Only)</label>
                            <input type="text" value="${userToEdit.role}" readonly class="w-full bg-inputbg text-gray-500 rounded-lg py-3 px-4 cursor-not-allowed">
                        </div>
                    </div>
                    
                    <button type="submit" class="bg-[#E8960E] hover:bg-orange-500 text-white font-bold py-3 px-8 rounded-lg transition shadow-md">
                        Save Profile Details
                    </button>
                </form>

                <hr class="border-gray-100 my-8">

                <!-- FORM 2: Password Security -->
                <form action="user" method="post" onsubmit="return validatePasswords()">
                    <input type="hidden" name="action" value="update-password">
                    <input type="hidden" name="id" value="${userToEdit.id}">

                    <h3 class="text-lg font-bold text-dark mb-6 flex items-center">
                        <i class="fas fa-key text-[#E8960E] mr-2"></i> Password Security
                    </h3>
                    
                    <div class="mb-6">
                        <label class="block text-xs font-bold text-gray-400 uppercase tracking-wider mb-2">Current Password <span class="text-red-500 text-[10px] font-semibold lowercase tracking-normal">(required to change password)</span></label>
                        <input type="password" name="currentPassword" required placeholder="Verify your current password" class="w-full bg-inputbg text-gray-800 rounded-lg py-3 px-4 focus:outline-none focus:ring-2 focus:ring-primary">
                    </div>
                    
                    <div class="grid grid-cols-1 md:grid-cols-2 gap-6 mb-8">
                        <div>
                            <label class="block text-xs font-bold text-gray-400 uppercase tracking-wider mb-2">New Password</label>
                            <input type="password" id="newPassword" name="newPassword" required placeholder="Min. 8 characters" class="w-full bg-inputbg text-gray-800 rounded-lg py-3 px-4 focus:outline-none focus:ring-2 focus:ring-primary">
                        </div>
                        <div>
                            <label class="block text-xs font-bold text-gray-400 uppercase tracking-wider mb-2">Confirm New Password</label>
                            <input type="password" id="confirmPassword" required placeholder="Repeat new password" class="w-full bg-inputbg text-gray-800 rounded-lg py-3 px-4 focus:outline-none focus:ring-2 focus:ring-primary">
                        </div>
                    </div>
                    
                    <button type="submit" class="bg-[#E8960E] hover:bg-orange-500 text-white font-bold py-3 px-8 rounded-lg transition shadow-md">
                        Change Password
                    </button>
                </form>
                
                <form action="user" method="get" class="absolute bottom-10 left-10 md:left-auto md:right-10" onsubmit="return confirm('Are you sure you want to delete your account? This action cannot be undone.');">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="id" value="${userToEdit.id}">
                    <button type="submit" class="text-xs font-bold text-red-500 hover:text-red-700 tracking-wider flex items-center">
                        <i class="far fa-trash-alt mr-1"></i> DELETE ACCOUNT
                    </button>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
