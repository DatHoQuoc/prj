<%-- 
    Document   : updateCustomer
    Created on : Feb 26, 2025, 11:37:57 PM
    Author     : datho
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Customer Page</title>
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
                <c:param name="active" value="customer" />
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
                            <h2>Customer Information</h2>
                            <button class="close-btn js-popup-close">&times;</button>
                        </div>

                        <form id="updateCustomerForm" accept-charset="utf-8" method="post" action="ProcessServlet?btnAction=UpdateCustTwo">
                            <div class="detail-grid">
                                <div class="detail-item">
                                    <span class="detail-label required">Full Name</span>
                                    <input class="input" id="name" type="text"  name="fullName" placeholder="Nguyễn Văn A" value="${UPDATE.custName != null ? UPDATE.custName : param.fullName}" onkeyup="validateName()" required>
                                    <span id="name-error" style="color:red"></span>
                                </div>

                                <div class="detail-item">
                                    <span class="detail-label required">Phone Number</span>
                                    <input class="input" type="tel" id="phone" name="phone" placeholder="0966121318" value="${UPDATE.phone}" onkeyup="validatePhone()" required>
                                    <span id="phone-error" style="color:red"></span>
                                </div>

                                <div class="detail-item">
                                    <span class="detail-label required">Gender</span>
                                    <select class="input" name="customerGender"  required>
                                        <option value="">Select Gender</option>
                                        <option value="male" ${UPDATE.sex != null ? (UPDATE.sex == 'M' ? 'selected' : '') : (param.customerGender == 'male' ? 'selected' : '')}>Male</option>
                                        <option value="female" ${UPDATE.sex != null ? (UPDATE.sex == 'F' ? 'selected' : '') : (param.customerGender == 'female' ? 'selected' : '')}>Female</option>
                                    </select>
                                </div>
                                    
                                <div class="detail-item">
                                    <span class="detail-label">Address</span>
                                    <input class="input" type="text" name="address" placeholder="Thành Phố Thủ Đức, Hồ Chí Minh" value="${UPDATE.cusAddress != null ? UPDATE.cusAddress : param.address}">
                                </div>

                           
                            </div>

                            <div class="modal-actions">
                                <button type="button" class="btn btn-create ">
                                    <span class="material-symbols-outlined">update</span>
                                    Update
                                </button>
                            </div>
                            <input style="display: none;" name="id" value="${UPDATE.custID}">
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
        <script src="${pageContext.request.contextPath}/assets/js/updateCustomer.js"></script> 
    </body>
</html>
