<%-- 
    Document   : updateCar
    Created on : Mar 5, 2025, 1:59:17 PM
    Author     : datho
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Car Page</title>
        <link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined" rel="stylesheet" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/updateCustomer.css">
    </head>
    <body>
        <%
            String loginpage = "login.jsp";
            if (session.getAttribute("SALEPERSON") == null) {
                response.sendRedirect(loginpage);
            }
        %>
        <div class="container">
            <!--Sidebar Section-->
            <c:import url="sidebar.jsp">
                <c:param name="active" value="car" />
            </c:import>
            <!--End of sidebar Section-->

            <!--Main content-->
            <main>
                <div class="right-section">
                    <div class="nav">
                        <button id="menu-btn">
                            <span class="material-symbols-outlined">
                                menu
                            </span>
                        </button>
                        <div class="dark-mode">
                            <span class="material-symbols-outlined">
                                light_mode
                            </span>
                            <span class="material-symbols-outlined">
                                dark_mode
                            </span>
                        </div>
                        <div class="profile">
                            <div class="info">
                                <p><span style="color: var(--color-primary);">Hey,</span><b>${sessionScope.SALEPERSON.saleName}</b></p>
                                <small class="text-muted">Sale Person</small>
                            </div>
                            <div class="profile-photo">
                                <img src="assets/images/logo.jpg" alt="">
                            </div>
                        </div>
                    </div>
                    <!--End of nav-->
                </div>
                <!--Recent Orders Table-->
                <div class="modal-background-create js-popup-modal-create">
                    <div class="modal">
                        <div class="modal-header">
                            <h2>Car Information</h2>
                            <button class="close-btn js-popup-close">&times;</button>
                        </div>

                        <form id="updateCarForm" accept-charset="utf-8" method="post" action="ProcessServlet?btnAction=UpdateCarServletTwo">
                            <div class="detail-grid">
                                <div class="detail-item">
                                    <span class="detail-label required">Serial Number</span>
                                    <p id="carSerial" name="carSerial" value="${UPDATE.serial}">${UPDATE.serial}</p>

                                </div>

                                <div class="detail-item">
                                    <span class="detail-label required">Model</span>
                                    <p id="carModel" name="carModel" value="${UPDATE.serial}">${UPDATE.model}</p>
                                </div>
                                
                                <div class="detail-item">
                                    <span class="detail-label required">Year</span>
                                    <select class="input" name="carYear" id="carYear"  required>
                                        <option value="">Select Year</option>
                                        <c:forEach items="${years}" var="year">
                                            <option value="${year}" ${year == UPDATE.year ? 'selected' : ''}>${year}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                
                                <div class="detail-item">
                                    <span class="detail-label required">Color</span>
                                    <select class="input" name="carColor" id="carColor"  required>
                                        <option value="">Select Color</option>
                                        <c:forEach items="${colors}" var="color">
                                            <option value="${color}" ${color == UPDATE.color ? 'selected' : ''}>${color}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>

                            <div class="modal-actions">
                                <button type="button" class="btn btn-create ">
                                    <span class="material-symbols-outlined">update</span>
                                    Update
                                </button>
                            </div>
                            <input style="display: none;" name="carId" value="${UPDATE.id}">
                        </form>
                    </div>
                </div>
                <!--End of Recent Orders-->
            </main>
            <!--End main content-->
            <div id="successMessage" class="hidden">${SUCCESS}</div>
            <div id="errorMessage" class="hidden">${ERROR}</div>
            <c:forEach var="error" items="${ERRORS}">
                <div  class="hidden js-error">${error}</div>
            </c:forEach>
            <div id="toast-container"></div>
        </div>
        <script src="${pageContext.request.contextPath}/assets/js/script.js"></script> 
        <script src="${pageContext.request.contextPath}/assets/js/updateCar.js"></script> 
    </body>
</html>
