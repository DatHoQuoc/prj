<%-- 
    Document   : report
    Created on : Feb 14, 2025, 10:16:48 AM
    Author     : datho
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SalePerson Report Page</title>
        <link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined" rel="stylesheet" />
        <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/report.css"/>
    </head>
    <body>
         <%
            String loginpage = "login.jsp";
            if(session.getAttribute("SALEPERSON") == null){
                response.sendRedirect(loginpage);
            }
        %>
        <div class="container">
            <!--Sidebar Section-->
            <c:import url="sidebar.jsp">
                <c:param name="active" value="report" />
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
                            <span class="material-symbols-outlined active">
                                light_mode
                            </span>
                            <span class="material-symbols-outlined">
                                dark_mode
                            </span>
                        </div>
                        <div class="profile">
                            <div class="info">
                                <p>Hey,<b>${sessionScope.SALEPERSON.saleName}</b></p>
                                <small class="text-muted">Sale Person</small>
                            </div>
                            <div class="profile-photo">
                                <img src="assets/images/logo.jpg" alt="">
                            </div>
                        </div>
                    </div>
                    <!--End of nav-->
                </div>
                <div class="status">
                    <div class="header">
                        <h4 id="big">Mechanics</h4>
                        <h4 id="small">Number of cars sold by Year</h4>
                    </div>

                    <div class="items-list">
                        <c:set var="mechanicList" value="${requestScope.TOP3MECHANIC}"/>
                        <c:if test="${not empty mechanicList}" >
                            <c:forEach var="mechanic" items="${mechanicList}" varStatus="counter" >
                                <div class="item">
                                    <div class="info">
                                        <div>
                                            <h5>${mechanic.name}</h5>
                                            <p>Number of repairs: ${mechanic.totalRepair}</p>
                                            <p>Revenue: $${(mechanic.revenue)/100}</p>
                                        </div>
                                        <i class='bx bxs-car-mechanic'></i>
                                    </div>
                                    <div class="progress">
                                        <div class="bar" style="width: ${(mechanic.hours/48) * 100}%">

                                        </div>
                                    </div>
                                    <style>
                                        main .status .items-list .item:nth-child(${counter.count}) .progress::before {
                                            content: "${(mechanic.hours) * 2}%";
                                        }
                                    </style>
                                </div>

                            </c:forEach>
                        </c:if>
                        <c:if test="${empty mechanicList}" >
                            <h2>You need to fighting</h2>
                        </c:if>

                        <div class="item">
                            <canvas class="activity-chart">

                            </canvas>
                        </div>
                    </div>
                    <div class="bottom-container">
                        <div class="prog-status">
                            <div class="header">
                                <h4>Revenue Per Year</h4>
                                <div class="tabs">
                                    <a href="#" class="active">1Y</a>
                                    <a href="#">6M</a>
                                    <a href="#">3M</a>
                                </div>
                            </div>

                            <div class="details">
                                <div class="item">
                                    <h2>100.90</h2>
                                    <p>Highest Revenue</p>
                                </div>
                                <div class="separator"></div>
                                <div class="item">
                                    <h2>60.80</h2>
                                    <p> Average Revenue</p>
                                </div>
                            </div>

                            <canvas class="prog-chart"></canvas>
                        </div>

                        <div class="best-statistic">
                            <h4>Best parts used</h4>
                            <div class="item2">
                                <canvas class="best-seller">

                                </canvas>
                            </div>
                        </div>
                        <div class="best-statistic">
                            <h4>Best sellers car models</h4>
                            <div class="item3">

                                <canvas class="best-sellers">

                                </canvas>
                            </div>
                        </div>
                    </div>
            </main>
        </div>
        <div id="carByYear" style="display: none;">
            ${REPORTDATA.carByYear}
        </div>
        <div id="bestUsedParts" style="display: none;">
            ${REPORTDATA.bestUsedParts}
        </div>
        <div id="bestSellerModels" style="display: none;">
            ${REPORTDATA.bestSellerModels}
        </div>
        <div id="revenueData" style="display: none;">
            ${REPORTDATA.revenue}
        </div>
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/script.js"></script> 
        <script src="${pageContext.request.contextPath}/assets/js/report.js"></script>
    </body>
</html>
