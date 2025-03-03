<%-- 
    Document   : salePersonDashboard
    Created on : Feb 12, 2025, 8:27:04 PM
    Author     : datho
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sale Person DashBoard</title>
        <link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined" rel="stylesheet" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/searchCustomer.css">
    </head>
    <body>
        <%
            String loginpage = "login.jsp";
            if (session.getAttribute("SALEPERSON") == null) {
                response.sendRedirect(loginpage);
            }
        %>
        <div class="container">
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
                <div class="recent-orders">
                    <form action="ProcessServlet" class="search" accept-charset="utf-8">
                        <input type="text" placeholder="Search Name" name="txtSearchValue" value="${param.txtSearchValue}">
                        <button type="submit" class="search-button" value="Search" name="btnAction">
                            <span class="material-symbols-outlined">
                                Search
                            </span>
                        </button>

                    </form>

                    <c:set var="searchValue" value="${param.txtSearchValue}"/>

                    <c:set var="result" value="${requestScope.SEARCHRESULT}"/>
                    <c:if test="${not empty result}">
                        <table>
                            <thead>
                                <tr>
                                    <th>No.</th>
                                    <th>Name</th>
                                    <th>Sex</th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="customer" items="${result}" varStatus="counter" >
                                    <tr>
                                        <td>${counter.count}</td>
                                        <td>${customer.custName}</td>
                                        <td>${customer.sex}</td>
                                        <td class="primary js-popup-details" data-customer-detail="${customer.custID}">Details</td>
                                    </tr>

                                </c:forEach>
                            </tbody>
                        </table>
                    </c:if>
                    <c:if test="${empty result}" >
                        <div class="no-result">
                            <img src="assets/images/noresult.jpg" alt="">
                        </div>
                    </c:if>
                    <div class="reminders">
                        <div class="notification add-reminder">
                            <div>
                                <span class="material-symbols-outlined">
                                    add
                                </span>
                                <h3>Add New Customer</h3>
                            </div>
                        </div>
                    </div>
                </div>
                <!--End of Recent Orders-->
            </main>
            <!--End main content-->
        </div>
        <div id="customersData" style="display: none;">
            ${customersJson}
        </div>
        <div class="modal-background js-popup-modal">
        </div>

        <script src="${pageContext.request.contextPath}/assets/js/script.js"></script> 
        <script src="${pageContext.request.contextPath}/assets/js/customer.js"></script>  
    </body>
</html>
