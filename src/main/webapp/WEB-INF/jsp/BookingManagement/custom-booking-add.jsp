<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Customize Your Cake - Sweet Bites</title>
    <link href="https://fonts.googleapis.com/css2?family=Playfair+Display:ital,wght@0,400;0,700;0,800;0,900;1,400;1,700&family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <style>
        :root {
            --primary: #E8960E;
            --primary-hover: #f59e0b;
            --dark: #1A1A1A;
            --cream: #F8F5F0;
            --gray-light: #F3F4F6;
            --gray-text: #6B7280;
            --border: #E5E7EB;
        }

        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
        }

        body {
            font-family: 'Inter', sans-serif;
            background-color: var(--cream);
            color: var(--dark);
            -webkit-font-smoothing: antialiased;
        }

        .font-serif {
            font-family: 'Playfair Display', serif;
        }

        .page-container {
            max-width: 1200px;
            margin: 50px auto;
            display: flex;
            background: #fff;
            border-radius: 24px;
            box-shadow: 0 10px 40px rgba(0, 0, 0, 0.05);
            overflow: hidden;
            min-height: 750px;
        }

        .visual-panel {
            flex: 1;
            position: relative;
            background-color: var(--dark);
            overflow: hidden;
            display: flex;
            flex-direction: column;
            justify-content: flex-end;
            padding: 50px;
            color: #fff;
        }

        .visual-panel img {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            object-fit: cover;
            transition: transform 0.7s ease;
        }

        .visual-panel:hover img {
            transform: scale(1.05);
        }

        .visual-overlay {
            position: absolute;
            inset: 0;
            background: linear-gradient(to top, rgba(0,0,0,0.85) 0%, rgba(0,0,0,0.3) 50%, rgba(0,0,0,0) 100%);
        }

        .visual-content {
            position: relative;
            z-index: 10;
        }

        .visual-content h2 {
            font-family: 'Playfair Display', serif;
            font-size: 3rem;
            line-height: 1.1;
            margin-bottom: 16px;
            font-weight: 800;
        }

        .visual-content p {
            font-size: 1.05rem;
            line-height: 1.6;
            opacity: 0.9;
        }

        .form-panel {
            flex: 1.3;
            padding: 60px 80px;
            display: flex;
            flex-direction: column;
            overflow-y: auto;
            background-color: #fff;
        }

        .form-header {
            margin-bottom: 40px;
        }

        .form-header h1 {
            font-family: 'Playfair Display', serif;
            font-size: 2.5rem;
            color: var(--dark);
            margin-bottom: 10px;
            font-weight: 800;
        }

        .form-header p {
            color: var(--gray-text);
            font-size: 1rem;
            line-height: 1.5;
        }

        .form-grid {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 24px;
        }

        .form-group {
            display: flex;
            flex-direction: column;
        }

        .form-group.full-width {
            grid-column: 1 / -1;
        }

        label {
            font-size: 0.8rem;
            font-weight: 700;
            color: var(--dark);
            margin-bottom: 10px;
            text-transform: uppercase;
            letter-spacing: 0.08em;
        }

        input[type="text"],
        input[type="number"],
        input[type="date"],
        select {
            padding: 16px 20px;
            border: 1px solid var(--border);
            border-radius: 12px;
            font-size: 1rem;
            font-family: 'Inter', sans-serif;
            background-color: var(--gray-light);
            color: var(--dark);
            transition: all 0.3s ease;
            outline: none;
            width: 100%;
        }

        input[type="text"]:focus,
        input[type="number"]:focus,
        input[type="date"]:focus,
        select:focus {
            border-color: var(--primary);
            background-color: #fff;
            box-shadow: 0 0 0 4px rgba(232, 150, 14, 0.1);
        }

        .checkbox-group {
            display: flex;
            align-items: center;
            gap: 12px;
            cursor: pointer;
            padding: 12px 0;
        }

        .checkbox-group input[type="checkbox"] {
            width: 22px;
            height: 22px;
            accent-color: var(--primary);
            cursor: pointer;
        }

        .checkbox-group span {
            font-size: 1rem;
            color: var(--dark);
            font-weight: 500;
        }

        .conditional-fields {
            background: #fafafa;
            border: 1px dashed var(--border);
            padding: 24px;
            border-radius: 16px;
            margin-top: 10px;
            display: none;
            animation: fadeIn 0.4s ease forwards;
            grid-column: 1 / -1;
        }
        
        .conditional-fields.active {
            display: block;
        }

        .conditional-title {
            font-family: 'Playfair Display', serif;
            font-size: 1.3rem;
            color: var(--dark);
            margin-bottom: 16px;
            display: flex;
            align-items: center;
            gap: 10px;
            font-weight: 700;
        }
        .conditional-title i {
            color: var(--primary);
        }

        .submit-btn {
            background-color: var(--primary);
            color: #fff;
            border: none;
            padding: 18px 30px;
            font-size: 1rem;
            font-weight: 700;
            border-radius: 12px;
            cursor: pointer;
            transition: all 0.3s ease;
            width: 100%;
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 12px;
            margin-top: 20px;
            text-transform: uppercase;
            letter-spacing: 0.1em;
        }

        .submit-btn:hover {
            background-color: var(--primary-hover);
            transform: translateY(-3px);
            box-shadow: 0 8px 20px rgba(232, 150, 14, 0.25);
        }

        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(-10px); }
            to { opacity: 1; transform: translateY(0); }
        }

        @media (max-width: 1024px) {
            .page-container {
                flex-direction: column;
                margin: 20px;
                min-height: auto;
            }
            .visual-panel {
                min-height: 400px;
            }
            .form-panel {
                padding: 40px;
            }
        }
        @media (max-width: 600px) {
            .form-grid {
                grid-template-columns: 1fr;
            }
        }
    </style>
</head>
<body>
    <jsp:include page="_navbar.jsp"/>
    
    <div class="page-container">
        <!-- Left Side: Visual Inspiration -->
        <div class="visual-panel">
            <img src="/images/custom_cake_hero.png" alt="Custom Cake Showcase">
            <div class="visual-overlay"></div>
            <div class="visual-content">
                <h2>Design Your<br>Dream Cake</h2>
                <p>Every milestone deserves a masterpiece. Tell us your vision, and our master bakers will craft perfection tailored just for you.</p>
            </div>
        </div>

        <!-- Right Side: Booking Form -->
        <div class="form-panel">
            <div class="form-header">
                <p>Fill out the details below to request a personalized cake.</p>
            </div>

            <c:if test="${param.error == 'server_error'}">
                <div class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded relative mb-6" role="alert">
                    <strong class="font-bold">Oops!</strong>
                    <span class="block sm:inline"> Something went wrong while submitting your request. Please check your details and try again.</span>
                </div>
            </c:if>

            <form action="${pageContext.request.contextPath}/custom-booking/new" method="post" enctype="multipart/form-data">
                <div class="form-grid">
                    
                    <div class="form-group full-width">
                        <label for="cakeType">Occasion / Cake Type</label>
                        <select name="cakeType" id="cakeType">
                            <option value="BIRTHDAY">Birthday</option>
                            <option value="WEDDING">Wedding</option>
                            <option value="ANNIVERSARY">Anniversary</option>
                            <option value="GRADUATION">Graduation</option>
                            <option value="BABY_SHOWER">Baby Shower</option>
                            <option value="OTHER">Other / Special Event</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="flavor">Flavor Profile</label>
                        <input type="text" id="flavor" name="flavor" placeholder="e.g. Vanilla, Chocolate" required>
                    </div>

                    <div class="form-group">
                        <label for="shape">Cake Shape</label>
                        <input type="text" id="shape" name="shape" placeholder="e.g. Round, Square" required>
                    </div>

                    <div class="form-group full-width">
                        <label for="customMessage">Custom Message</label>
                        <input type="text" id="customMessage" name="customMessage" placeholder="e.g. Happy Birthday John!" required>
                    </div>

                    <div class="form-group">
                        <label for="weightInKg">Weight (Kg)</label>
                        <input type="number" id="weightInKg" step="0.1" name="weightInKg" placeholder="1.5" required>
                    </div>

                    <div class="form-group">
                        <label for="deliveryDate">Delivery Date</label>
                        <input type="date" id="deliveryDate" name="deliveryDate" required>
                    </div>

                    <!-- Birthday Specific Fields -->
                    <div id="birthdayFields" class="conditional-fields">
                        <div class="conditional-title">
                            <i class="fas fa-birthday-cake"></i> Birthday Details
                        </div>
                        <div class="form-group">
                            <label for="numberOfCandles">Number of Candles</label>
                            <input type="number" id="numberOfCandles" name="numberOfCandles" value="0" min="0">
                        </div>
                    </div>

                    <!-- Wedding Specific Fields -->
                    <div id="weddingFields" class="conditional-fields">
                        <div class="conditional-title">
                            <i class="fas fa-ring"></i> Wedding Details
                        </div>
                        <div class="form-grid">
                            <div class="form-group">
                                <label for="tiers">Number of Tiers</label>
                                <input type="number" id="tiers" name="tiers" value="0" min="0">
                            </div>
                            <div class="form-group" style="justify-content: flex-end;">
                                <label class="checkbox-group">
                                    <input type="checkbox" name="floralDecorations" value="true">
                                    <span>Add Floral Decorations</span>
                                </label>
                            </div>
                        </div>
                    </div>

                    <div class="form-group full-width" style="margin-top: 10px; margin-bottom: 20px;">
                        <label for="inspirationImages">Add Inspiration Images (Multiple Optional)</label>
                        <div class="flex items-center gap-4" style="display: flex; align-items: center; gap: 1rem;">
                            <div style="flex: 1;">
                                <input type="file" id="inspirationImages" name="inspirationImages" accept="image/*" multiple>
                            </div>
                            <div style="color: var(--primary); font-size: 1.5rem;">
                                <i class="fas fa-images"></i>
                            </div>
                        </div>
                        <p style="font-size: 10px; color: var(--gray-text); margin-top: 8px;">You can select multiple photos of designs you love.</p>
                    </div>

                    <div class="form-group full-width" style="margin-bottom: 20px;">
                        <label for="specialNotes">Special Notes / Requests</label>
                        <textarea id="specialNotes" name="specialNotes" rows="3" placeholder="Any extra details we should know?" style="padding: 16px 20px; border: 1px solid var(--border); border-radius: 12px; font-size: 1rem; background-color: var(--gray-light); resize: none;"></textarea>
                    </div>

                    <div class="form-group full-width">
                        <button type="submit" class="submit-btn">
                            <i class="fas fa-paper-plane"></i> Submit Request
                        </button>
                    </div>

                </div>
            </form>
        </div>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            const cakeTypeSelect = document.getElementById('cakeType');
            const birthdayFields = document.getElementById('birthdayFields');
            const weddingFields = document.getElementById('weddingFields');

            function toggleFields() {
                const type = cakeTypeSelect.value;
                if (type === 'BIRTHDAY') {
                    birthdayFields.classList.add('active');
                    weddingFields.classList.remove('active');
                } else if (type === 'WEDDING') {
                    weddingFields.classList.add('active');
                    birthdayFields.classList.remove('active');
                } else {
                    birthdayFields.classList.remove('active');
                    weddingFields.classList.remove('active');
                }
            }

            cakeTypeSelect.addEventListener('change', toggleFields);
            toggleFields(); // Initial call
            
            // Set minimum date to today
            const today = new Date().toISOString().split('T')[0];
            document.getElementById('deliveryDate').setAttribute('min', today);
        });
    </script>
</body>
</html>
