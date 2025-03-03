<%-- 
    Document   : createNewCar
    Created on : Mar 1, 2025, 11:02:22 AM
    Author     : datho
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create New Car Page</title>
        <link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined" rel="stylesheet" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/createCustomer.css">
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
                            <h2>Create New Car</h2>
                            <button class="close-btn js-popup-close">&times;</button>
                        </div>

                        <form id="createCustomerForm" accept-charset="utf-8" method="post" action="ProcessServlet?btnAction=CreateCar">
                            <div class="detail-grid">
                                <div class="detail-item">
                                    <span class="detail-label required">Model</span>
                                    <select class="input" name="carModel" id="carModel"  required>
                                        <option value="">Select Model</option>
                                        <c:forEach items="${models}" var="model">
                                            <option value="${model}" ${model == param.carModel ? 'selected' : ''}>${model}</option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div class="detail-item">
                                    <span class="detail-label required">Year</span>
                                    <select class="input" name="carYear" id="carYear"  required>
                                        <option value="">Select Year</option>
                                        <c:forEach items="${years}" var="year">
                                            <option value="${year}" ${year == param.carYear ? 'selected' : ''}>${year}</option>
                                        </c:forEach>
                                    </select>
                                </div>


                                <div class="detail-item">
                                    <span class="detail-label required">Color</span>
                                    <select class="input" name="carColor" id="carColor"  required>
                                        <option value="">Select Color</option>
                                        <c:forEach items="${colors}" var="color">
                                            <option value="${color}" ${color == param.carColor ? 'selected' : ''}>${color}</option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div class="detail-item">
                                    <span class="detail-label required">Serial Number</span>
                                    <h2 id="carSerialNumber" name="carSerialNumber">${serialNumber}</h2>
                                    <input type="hidden" name="carSerialNumberInput" id="carSerialNumberInput" value="${serialNumber}">
                                </div>
                            </div>

                            <div class="modal-actions">
                                <button type="submit" class="btn btn-create ">
                                    <span class="material-symbols-outlined">save</span>
                                    Create
                                </button>
                            </div>
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
        <script src="${pageContext.request.contextPath}/assets/js/createNewCar.js"></script>
    </body>
</html>
