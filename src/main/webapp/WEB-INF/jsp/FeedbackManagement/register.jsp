<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register - Sweet Bites Bakery</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link href="https://fonts.googleapis.com/css2?family=Playfair+Display:wght@400;600;700&family=Inter:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <script>
        tailwind.config = {
            theme: {
                extend: {
                    colors: { primary: '#E99B45', dark: '#1F2937' },
                    fontFamily: { serif: ['Playfair Display', 'serif'], sans: ['Inter', 'sans-serif'] }
                }
            }
        }
    </script>
    <style>
        body { font-family: 'Inter', sans-serif; }
        .glass-panel {
            background: rgba(255, 255, 255, 0.1);
            backdrop-filter: blur(10px);
            -webkit-backdrop-filter: blur(10px);
            border: 1px solid rgba(255, 255, 255, 0.2);
        }
        .bg-image {
            background-image: url('https://images.unsplash.com/photo-1509440159596-0249088772ff?ixlib=rb-4.0.3&auto=format&fit=crop&w=1920&q=80');
            background-size: cover;
            background-position: center;
        }
    </style>
</head>
<body class="bg-image min-h-screen flex flex-col relative">
    <div class="absolute inset-0 bg-black/40 z-0"></div>

    <!-- Navigation -->
    <jsp:include page="_navbar.jsp"/>

    <!-- Register Container -->
    <div class="relative z-10 flex-grow flex items-center justify-center p-6">
        <div class="glass-panel rounded-3xl w-full max-w-4xl flex flex-col md:flex-row overflow-hidden shadow-2xl">
            
            <div class="md:w-1/2 relative min-h-[400px]">
                <img src="https://images.unsplash.com/photo-1558961363-fa8fdf82db35?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80" alt="Baking" class="absolute inset-0 w-full h-full object-cover rounded-l-3xl p-4">
                <div class="absolute inset-0 bg-black/30 p-4 rounded-l-3xl"></div>
                <div class="absolute bottom-10 left-10 right-10 text-white text-center">
                    <h2 class="text-2xl font-serif font-bold mb-2">Join Our Community</h2>
                    <p class="text-sm text-gray-200">Sign up to receive exclusive offers, place orders online, and manage your bookings effortlessly.</p>
                </div>
            </div>
            
            <div class="md:w-1/2 p-10 flex flex-col justify-center text-white">
                <h2 class="text-4xl font-serif text-[#8B4513] mb-1" style="font-family: 'Playfair Display', serif;">Create Account</h2>
                <p class="text-xs text-gray-300 mb-8 font-semibold">Join Morning Rise bakery for fresh updates.</p>
                
                <form action="user" method="post" class="space-y-4">
                    <input type="hidden" name="action" value="register">
                    
                    <div class="relative">
                        <div class="absolute inset-y-0 left-0 pl-4 flex items-center pointer-events-none">
                            <i class="far fa-user text-gray-300"></i>
                        </div>
                        <input type="text" name="username" required placeholder="Username" 
                               class="w-full pl-12 pr-4 py-3 bg-white/20 border border-gray-400/30 rounded-xl text-white placeholder-gray-300 focus:ring-2 focus:ring-primary focus:outline-none transition">
                    </div>

                    <div class="relative">
                        <div class="absolute inset-y-0 left-0 pl-4 flex items-center pointer-events-none">
                            <i class="far fa-envelope text-gray-300"></i>
                        </div>
                        <input type="email" name="email" required placeholder="Email Address" 
                               class="w-full pl-12 pr-4 py-3 bg-white/20 border border-gray-400/30 rounded-xl text-white placeholder-gray-300 focus:ring-2 focus:ring-primary focus:outline-none transition">
                    </div>
                    
                    <div class="relative">
                        <div class="absolute inset-y-0 left-0 pl-4 flex items-center pointer-events-none">
                            <i class="fas fa-lock text-gray-300"></i>
                        </div>
                        <input type="password" name="password" required placeholder="Password" 
                               class="w-full pl-12 pr-4 py-3 bg-white/20 border border-gray-400/30 rounded-xl text-white placeholder-gray-300 focus:ring-2 focus:ring-primary focus:outline-none transition">
                    </div>
                    
                    <button type="submit" class="w-full bg-[#F39C12] hover:bg-orange-500 text-white font-bold py-3 px-4 rounded-xl transition shadow-lg mt-2 tracking-wide">
                        Sign Up
                    </button>

                    <div class="text-center mt-6">
                        <p class="text-sm text-gray-300">Already have an account? <a href="${pageContext.request.contextPath}/login" class="text-primary hover:text-white transition font-semibold underline underline-offset-4">Sign In</a></p>
                    </div>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
