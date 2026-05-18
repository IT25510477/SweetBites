<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Welcome - Sweet Bites Bakery</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link href="https://fonts.googleapis.com/css2?family=Playfair+Display:wght@400;600;700&family=Inter:wght@300;400;500;600&display=swap" rel="stylesheet">
    <script>
        tailwind.config = {
            theme: {
                extend: {
                    colors: { primary: '#E99B45' },
                    fontFamily: { serif: ['Playfair Display', 'serif'], sans: ['Inter', 'sans-serif'] }
                }
            }
        }

        // Simulate loading then redirect to home
        setTimeout(() => {
            window.location.href = "${pageContext.request.contextPath}/";
        }, 3000);
    </script>
    <style>
        body { font-family: 'Inter', sans-serif; }
        .bg-loading {
            background-image: url('/images/IMG-20260514-WA0031.jpg');
            background-size: cover;
            background-position: center;
        }
        
        .progress-bar {
            width: 0%;
            animation: fillBar 2.5s ease-in-out forwards;
        }

        @keyframes fillBar {
            0% { width: 0%; }
            100% { width: 100%; }
        }
    </style>
</head>
<body class="bg-loading h-screen flex flex-col items-center justify-center text-white relative">
    
    <div class="text-center z-10 w-full max-max-2xl px-6 mt-40">
        <!-- Progress Bar Container -->
        <div class="w-full max-w-xl mx-auto relative">
            <div class="h-[3px] w-full bg-gray-800 rounded">
                <div class="h-full bg-[#F39C12] progress-bar shadow-[0_0_15px_#F39C12]"></div>
            </div>
            
            <div class="flex justify-between w-full mt-3 text-xs font-bold text-gray-400 tracking-[0.2em]">
                <span>LOADING</span>
                <span class="text-[#F39C12]">100%</span>
            </div>
        </div>
    </div>
    
</body>
</html>
