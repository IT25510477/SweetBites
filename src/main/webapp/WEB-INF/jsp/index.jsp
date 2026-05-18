<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sweet Bites Bakery - Home</title>
    <meta name="description" content="Welcome to Sweet Bites Bakery – handcrafted pastries, breads, and cakes made daily with passion.">
    <script src="https://cdn.tailwindcss.com"></script>
    <link href="https://fonts.googleapis.com/css2?family=Playfair+Display:ital,wght@0,400;0,700;0,800;0,900;1,400;1,700&family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

    <script>
        tailwind.config = {
            theme: {
                extend: {
                    colors: {
                        primary: '#E8960E',
                        dark:    '#1A1A1A',
                        cream:   '#F8F5F0',
                    },
                    fontFamily: {
                        serif: ['Playfair Display', 'serif'],
                        sans:  ['Inter', 'sans-serif'],
                    }
                }
            }
        }
    </script>

    <style>
        * { font-family: 'Inter', sans-serif; }
        .font-serif, h1, h2, h3 { font-family: 'Playfair Display', serif; }

        /* Diagonal clip matching the reference image exactly */
        .hero-image-clip {
            clip-path: polygon(12% 0%, 100% 0%, 100% 100%, 0% 100%);
        }
        .hero-orange-stripe {
            clip-path: polygon(12% 0%, 22% 0%, 10% 100%, 0% 100%);
        }

        /* Subtle bounce on hero buttons */
        .btn-hover:hover {
            transform: translateY(-2px);
            transition: transform 0.2s ease;
        }

        /* Nav underline active */
        .nav-active {
            border-bottom: 2px solid #E8960E;
            color: #E8960E !important;
        }

        /* Menu card hover */
        .menu-card:hover .menu-img {
            transform: scale(1.06);
        }
        .menu-img {
            transition: transform 0.4s ease;
        }

        /* Footer brand styling */
        .footer-brand {
            font-family: 'Playfair Display', serif;
            font-size: 1.6rem;
            font-weight: 800;
            line-height: 1.2;
        }
    </style>
</head>
<body class="bg-cream text-dark antialiased">

<!-- ===================== NAVBAR ===================== -->
<jsp:include page="_navbar.jsp"/>

<!-- ===================== HERO SECTION ===================== -->
<section class="w-full bg-white overflow-hidden" style="min-height: 540px;">
    <div class="flex flex-col md:flex-row w-full" style="min-height: 540px;">

        <!-- LEFT: Text Content -->
        <div class="md:w-[45%] flex flex-col justify-center px-12 lg:px-20 xl:px-28 py-16 bg-white relative z-10">
            <p class="text-primary text-xs font-bold tracking-[0.25em] uppercase mb-5">SINCE 2010</p>

            <h1 class="font-serif leading-[1.08] mb-6">
                <span class="block text-4xl lg:text-5xl xl:text-[3.4rem] font-bold text-dark mb-1" style="font-family:'Playfair Display',serif; font-weight:700;">Welcome to</span>
                <span class="block text-[2.8rem] lg:text-[3.8rem] xl:text-[4.5rem] text-primary uppercase leading-none" style="font-family:'Playfair Display',serif; font-weight:900;">SWEET BITES</span>
                <span class="block text-[2.8rem] lg:text-[3.8rem] xl:text-[4.5rem] text-primary uppercase leading-none" style="font-family:'Playfair Display',serif; font-weight:900;">BAKERY</span>
            </h1>

            <p class="font-serif italic text-xl text-gray-700 leading-snug mb-5">
                Handcrafted daily,<br>with passion.
            </p>

            <p class="text-gray-500 text-sm leading-relaxed mb-10 max-w-[340px]">
                Step into a world of pure indulgence at Sweet Bites Bakery, where artisanal craftsmanship meets daily freshness.
            </p>

            <div class="flex gap-4 flex-wrap">
                <a href="${pageContext.request.contextPath}/product/list" id="order-btn" class="btn-hover bg-primary text-white font-bold px-8 py-3.5 rounded-lg flex items-center gap-2 hover:bg-orange-500 transition-colors shadow-md text-sm">
                    <i class="fas fa-shopping-bag"></i> Order Online
                </a>
                <a href="${pageContext.request.contextPath}/custom-booking/new" id="explore-btn" class="btn-hover border-2 border-dark text-dark bg-white font-bold px-8 py-3.5 rounded-lg hover:bg-gray-50 transition-colors text-sm">
                    Customize Cake
                </a>
            </div>
        </div>

        <!-- RIGHT: Diagonal Orange Stripe + Image -->
        <div class="md:w-[55%] relative" style="min-height: 540px;">
            <!-- Full orange background behind the image area -->
            <div class="absolute inset-0 bg-primary"></div>

            <!-- The orange diagonal stripe that shows between text and image -->
            <div class="absolute inset-0 bg-primary hero-orange-stripe z-10"></div>

            <!-- Bakery Hero Image -->
            <img
                src="/images/hero_bakery.png"
                alt="Artisan bakery items on marble surface"
                class="absolute inset-0 w-full h-full object-cover hero-image-clip z-20"
                onerror="this.src='/images/ref.jpeg'"
            >
        </div>
    </div>
</section>

<!-- ===================== FEATURED MENU ===================== -->
<section class="max-w-[1400px] mx-auto px-8 py-20">
    <div class="text-center mb-14">
        <p class="text-primary text-xs font-bold tracking-[0.25em] uppercase mb-3">Discover Our Bestsellers</p>
        <h2 class="font-serif text-4xl font-bold text-dark">Featured Menu</h2>
        <p class="text-gray-500 mt-4 max-w-2xl mx-auto text-sm leading-relaxed">A teaser of our daily delights, from handcrafted artisanal loaves to decadent celebratory tarts.</p>
    </div>

    <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-8">

        <!-- Card 1 -->
        <div class="menu-card bg-white rounded-2xl shadow-sm hover:shadow-lg transition-shadow overflow-hidden group">
            <div class="h-52 overflow-hidden bg-gray-50">
                <img src="/images/chocolate_cake.jpg" alt="Chocolate Fudge Cake" class="menu-img w-full h-full object-cover">
            </div>
            <div class="p-5">
                <h3 class="font-bold text-dark text-sm mb-3">Chocolate Fudge Cake</h3>
                <div class="flex justify-between items-center">
                    <p class="text-primary font-bold text-sm">Rs. 1,500</p>
                    <a href="${pageContext.request.contextPath}/product/view/1" class="text-xs border border-gray-200 px-3 py-1.5 rounded-lg hover:bg-gray-50 text-gray-600 font-medium transition-colors inline-block">VIEW DETAILS</a>
                </div>
            </div>
        </div>

        <!-- Card 2 -->
        <div class="menu-card bg-white rounded-2xl shadow-sm hover:shadow-lg transition-shadow overflow-hidden group">
            <div class="h-52 overflow-hidden bg-gray-50">
                <img src="/images/vanilla_cake.jpg" alt="Vanilla Bean Cake" class="menu-img w-full h-full object-cover">
            </div>
            <div class="p-5">
                <h3 class="font-bold text-dark text-sm mb-3">Vanilla Bean Cake</h3>
                <div class="flex justify-between items-center">
                    <p class="text-primary font-bold text-sm">Rs. 1,200</p>
                    <a href="${pageContext.request.contextPath}/product/view/2" class="text-xs border border-gray-200 px-3 py-1.5 rounded-lg hover:bg-gray-50 text-gray-600 font-medium transition-colors inline-block">VIEW DETAILS</a>
                </div>
            </div>
        </div>

        <!-- Card 3 -->
        <div class="menu-card bg-white rounded-2xl shadow-sm hover:shadow-lg transition-shadow overflow-hidden group">
            <div class="h-52 overflow-hidden bg-gray-50">
                <img src="/images/assorted_cupcakes.jpg" alt="Assorted Cupcake Box" class="menu-img w-full h-full object-cover">
            </div>
            <div class="p-5">
                <h3 class="font-bold text-dark text-sm mb-3">Assorted Cupcake Box</h3>
                <div class="flex justify-between items-center">
                    <p class="text-primary font-bold text-sm">Rs. 1,200</p>
                    <a href="${pageContext.request.contextPath}/product/view/13" class="text-xs border border-gray-200 px-3 py-1.5 rounded-lg hover:bg-gray-50 text-gray-600 font-medium transition-colors inline-block">VIEW DETAILS</a>
                </div>
            </div>
        </div>

        <!-- Card 4 -->
        <div class="menu-card bg-white rounded-2xl shadow-sm hover:shadow-lg transition-shadow overflow-hidden group">
            <div class="h-52 overflow-hidden bg-gray-50">
                <img src="/images/choc_chip_cookies.jpg" alt="Chocolate Chip Cookies" class="menu-img w-full h-full object-cover">
            </div>
            <div class="p-5">
                <h3 class="font-bold text-dark text-sm mb-3">Chocolate Chip Cookies</h3>
                <div class="flex justify-between items-center">
                    <p class="text-primary font-bold text-sm">Rs. 200</p>
                    <a href="${pageContext.request.contextPath}/product/view/17" class="text-xs border border-gray-200 px-3 py-1.5 rounded-lg hover:bg-gray-50 text-gray-600 font-medium transition-colors inline-block">VIEW DETAILS</a>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- ===================== CONTACT SECTION ===================== -->
<section id="contact-us" class="max-w-[1400px] mx-auto px-8 py-20 bg-[#FDFBF9]">
    <div class="flex flex-col lg:flex-row gap-12">
        
        <!-- Left: Our Bakery Info -->
        <div class="lg:w-1/3 bg-white p-10 rounded-3xl shadow-sm border border-gray-100">
            <h2 class="font-serif text-3xl font-bold text-dark mb-8">Our Bakery</h2>
            
            <div class="space-y-8">
                <div class="flex gap-5">
                    <div class="w-12 h-12 bg-[#FDEBD0] rounded-full flex items-center justify-center flex-shrink-0">
                        <i class="fas fa-map-marker-alt text-[#D35400] text-xl"></i>
                    </div>
                    <div>
                        <h4 class="font-bold text-dark mb-1">Location</h4>
                        <p class="text-gray-500 text-sm leading-relaxed">123 Bakery Street<br>Colombo 03, Sri Lanka</p>
                    </div>
                </div>

                <div class="flex gap-5">
                    <div class="w-12 h-12 bg-[#FDEBD0] rounded-full flex items-center justify-center flex-shrink-0">
                        <i class="fas fa-envelope text-[#D35400] text-xl"></i>
                    </div>
                    <div>
                        <h4 class="font-bold text-dark mb-1">Email Us</h4>
                        <p class="text-gray-500 text-sm leading-relaxed">hello@sweetbites.lk<br>orders@sweetbites.lk</p>
                    </div>
                </div>

                <div class="flex gap-5">
                    <div class="w-12 h-12 bg-[#FDEBD0] rounded-full flex items-center justify-center flex-shrink-0">
                        <i class="fas fa-phone-alt text-[#D35400] text-xl"></i>
                    </div>
                    <div>
                        <h4 class="font-bold text-dark mb-1">Call Us</h4>
                        <p class="text-gray-500 text-sm leading-relaxed">+94 112 345 678<br>+94 77 123 4567</p>
                    </div>
                </div>

                <div class="flex gap-5">
                    <div class="w-12 h-12 bg-[#FDEBD0] rounded-full flex items-center justify-center flex-shrink-0">
                        <i class="fas fa-clock text-[#D35400] text-xl"></i>
                    </div>
                    <div>
                        <h4 class="font-bold text-dark mb-1">Opening Hours</h4>
                        <p class="text-gray-500 text-sm leading-relaxed">Mon - Fri: 8:00 AM - 8:00 PM<br>Sat - Sun: 9:00 AM - 9:00 PM</p>
                    </div>
                </div>
            </div>
        </div>

        <!-- Right: Send a Message Form -->
        <div class="lg:w-2/3 bg-white p-10 rounded-3xl shadow-sm border border-gray-100">
            <h2 class="font-serif text-3xl font-bold text-dark mb-8">Send a Message</h2>
            <c:if test="${param.success == 'sent'}">
                <div class="bg-green-100 border border-green-400 text-green-700 px-4 py-3 rounded relative mb-6" role="alert">
                    <span class="block sm:inline">Message sent! We'll get back to you soon.</span>
                </div>
            </c:if>
            <c:if test="${param.error == 'send_failed'}">
                <div class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded relative mb-6" role="alert">
                    <span class="block sm:inline">Sorry, we couldn't send your message. Please try again.</span>
                </div>
            </c:if>
            <form action="${pageContext.request.contextPath}/contact/send" method="post" class="space-y-6">
                <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                    <div>
                        <label class="block text-[10px] uppercase font-bold text-gray-400 mb-2 tracking-widest">Your Name</label>
                        <input type="text" name="name" required placeholder="John Doe" class="w-full px-5 py-4 bg-gray-50 border border-gray-100 rounded-xl focus:outline-none focus:ring-2 focus:ring-primary/20 transition text-gray-700">
                    </div>
                    <div>
                        <label class="block text-[10px] uppercase font-bold text-gray-400 mb-2 tracking-widest">Email Address</label>
                        <input type="email" name="email" required placeholder="john@example.com" class="w-full px-5 py-4 bg-gray-50 border border-gray-100 rounded-xl focus:outline-none focus:ring-2 focus:ring-primary/20 transition text-gray-700">
                    </div>
                </div>
                
                <div>
                    <label class="block text-[10px] uppercase font-bold text-gray-400 mb-2 tracking-widest">Subject</label>
                    <input type="text" name="subject" required placeholder="How can we help?" class="w-full px-5 py-4 bg-gray-50 border border-gray-100 rounded-xl focus:outline-none focus:ring-2 focus:ring-primary/20 transition text-gray-700">
                </div>

                <div>
                    <label class="block text-[10px] uppercase font-bold text-gray-400 mb-2 tracking-widest">Your Message</label>
                    <textarea name="message" rows="5" required placeholder="Type your message here..." class="w-full px-5 py-4 bg-gray-50 border border-gray-100 rounded-xl focus:outline-none focus:ring-2 focus:ring-primary/20 transition text-gray-700 resize-none"></textarea>
                </div>

                <button type="submit" class="w-full bg-[#F39C12] hover:bg-orange-500 text-white font-bold py-4 rounded-xl transition shadow-lg flex items-center justify-center gap-2">
                    <i class="fas fa-paper-plane"></i> Send Message
                </button>
            </form>
        </div>
    </div>
</section>

<!-- ===================== FOOTER ===================== -->
<footer class="bg-white border-t border-gray-100 py-14 mt-4">
    <div class="max-w-[1400px] mx-auto px-8 grid grid-cols-1 md:grid-cols-4 gap-10">
        <div>
            <div class="footer-brand mb-3">
                THE <span class="text-primary">FRESH</span><br>BAKERY
            </div>
            <p class="text-gray-500 text-sm leading-relaxed">Colombo, Sri Lanka – Handcrafting daily delights, with passion and precision.</p>
        </div>
        <div>
            <h4 class="font-bold text-dark mb-4 tracking-wide">Explore</h4>
            <ul class="space-y-2.5 text-sm text-gray-500">
                <li><a href="${pageContext.request.contextPath}/product/list" class="hover:text-primary transition-colors">Menu</a></li>
                <li><a href="${pageContext.request.contextPath}/category/list" class="hover:text-primary transition-colors">Categories</a></li>
                <li><a href="${pageContext.request.contextPath}/custom-booking/new" class="hover:text-primary transition-colors">Custom Cake Order</a></li>
            </ul>
        </div>
        <div>
            <h4 class="font-bold text-dark mb-4 tracking-wide">Support</h4>
            <ul class="space-y-2.5 text-sm text-gray-500">
                <li><a href="${pageContext.request.contextPath}/#contact-us" class="hover:text-primary transition-colors">FAQs</a></li>
                <li><a href="${pageContext.request.contextPath}/#contact-us" class="hover:text-primary transition-colors">Contact</a></li>
                <li><a href="#" class="hover:text-primary transition-colors">Privacy Policy</a></li>
            </ul>
        </div>
        <div>
            <h4 class="font-bold text-dark mb-4 tracking-wide">Stay Connected</h4>
            <p class="text-gray-500 text-sm mb-5 leading-relaxed">Sign up for exclusive offers and daily fresh updates.</p>
            <div class="flex gap-4 text-xl">
                <a href="https://twitter.com" target="_blank" class="text-gray-400 hover:text-primary transition-colors"><i class="fab fa-twitter"></i></a>
                <a href="https://instagram.com" target="_blank" class="text-gray-400 hover:text-primary transition-colors"><i class="fab fa-instagram"></i></a>
                <a href="mailto:hello@sweetbites.lk" class="text-gray-400 hover:text-primary transition-colors"><i class="far fa-envelope"></i></a>
            </div>
        </div>
    </div>
</footer>

</body>
</html>
