<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Booking - Sweet Bites</title>
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
    <c:choose><c:when test="${sessionScope.user.role == 'admin'}"><jsp:include page="_admin-navbar.jsp"/></c:when><c:otherwise><jsp:include page="_navbar.jsp"/></c:otherwise></c:choose>
    
    <div class="page-container">
        <!-- Left Side: Visual Inspiration -->
        <div class="visual-panel">
            <img src="/images/custom_cake_hero.png" alt="Custom Cake Showcase">
            <div class="visual-overlay"></div>
            <div class="visual-content">
                <h2>Edit Your<br>Masterpiece</h2>
                <p>Modify the details of your requested cake. Our team is ready to accommodate your changes.</p>
            </div>
        </div>

        <!-- Right Side: Booking Form -->
        <div class="form-panel">
            <div class="form-header">
                <h1>Edit Booking #${booking.id}</h1>
                <p>Update the specifics of your personalized cake request.</p>
            </div>

            <form action="${pageContext.request.contextPath}/custom-booking/edit/${booking.id}" method="post">
                <div class="form-grid">
                    
                    <div class="form-group">
                        <label for="flavor">Flavor Profile</label>
                        <input type="text" id="flavor" name="flavor" value="${booking.customCake.flavor}" required>
                    </div>

                    <div class="form-group">
                        <label for="shape">Cake Shape</label>
                        <input type="text" id="shape" name="shape" value="${booking.customCake.shape}" required>
                    </div>

                    <div class="form-group full-width">
                        <label for="customMessage">Custom Message</label>
                        <input type="text" id="customMessage" name="customMessage" value="${booking.customCake.customMessage}" required>
                    </div>

                    <div class="form-group">
                        <label for="weightInKg">Weight (Kg)</label>
                        <input type="number" id="weightInKg" step="0.1" name="weightInKg" value="${booking.customCake.weightInKg}" required>
                    </div>

                    <div class="form-group">
                        <label for="deliveryDate">Delivery Date</label>
                        <input type="date" id="deliveryDate" name="deliveryDate" value="${booking.deliveryDate}" required>
                    </div>

                    <c:if test="${sessionScope.user.role == 'admin'}">
                        <div class="form-group full-width">
                            <label for="status">Status</label>
                            <select id="status" name="status">
                                <option value="PENDING" ${booking.bookingStatus == 'PENDING' ? 'selected' : ''}>PENDING</option>
                                <option value="CONFIRMED" ${booking.bookingStatus == 'CONFIRMED' ? 'selected' : ''}>CONFIRMED</option>
                                <option value="CANCELLED" ${booking.bookingStatus == 'CANCELLED' ? 'selected' : ''}>CANCELLED</option>
                            </select>
                        </div>
                    </c:if>

                    <div class="form-group full-width">
                        <button type="submit" class="submit-btn">
                            <i class="fas fa-save"></i> Update Request
                        </button>
                    </div>

                </div>
            </form>
        </div>
    </div>
</body>
</html>
