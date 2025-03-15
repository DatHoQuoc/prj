<%-- 
    Document   : car
    Created on : Feb 17, 2025, 11:33:56 AM
    Author     : datho
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Car Page</title>
        <link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined" rel="stylesheet" />
        <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/car.css"/>
    </head>
    <body>
        <c:set var="loginpage" value="login.jsp"/>
        <c:if test="${empty sessionScope.SALEPERSON}">
            <c:redirect url="${loginpage}"/>
        </c:if>
        <div class="container">
            <!--Sidebar Section-->
            <c:import url="sidebar.jsp">
                <c:param name="active" value="car" />
            </c:import>
            <!--End of sidebar Section-->

            <!--Begin main-->
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
                    <form id="carSearchForm" action="ProcessServlet" method="POST" accept-charset="utf-8">

                        <input type="hidden" name="btnAction" value="Car">
                        <div class="detail-item">
                            <select class="input" name="typeCar"  required>
                                <option value="">Select Type</option>
                                <option value="sale" ${param.typeCar == 'sale' ? 'selected' : ''}>Sale</option>
                                <option value="all" ${param.typeCar == 'all' ? 'selected' : ''}>All</option>
                            </select>
                        </div>


                        <input type="hidden" name="btnAction" value="Car">
                        <input type="hidden" id="serialNumber" name="serialNumber" value="${sessionScope.a[0]}">
                        <input type="hidden" id="model" name="model" value="${sessionScope.a[1]}">
                        <input type="hidden" id="year" name="year" value="${sessionScope.a[2]}">
                        <div class="car-search">
                            <div class="custom-select">
                                <div class="select-selected" data-field="serialNumber">
                                    ${serialLabel}
                                </div>
                                <div class="select-items">
                                    <div class="select-item" data-value="all">All</div>
                                    <c:forEach items="${serialNumbers}" var="serial" >
                                        <div class="select-item" data-value="${serial}">${serial}</div>
                                    </c:forEach>
                                </div>
                            </div>

                            <div class="custom-select">
                                <div  class="select-model select-selected" data-field="model">
                                    ${modelLabel}
                                </div>
                                <div class="select-items">
                                    <div class="select-item" data-value="all">All</div>
                                    <c:forEach items="${models}" var="carModel">
                                        <div class="select-item" data-value="${carModel}">${carModel}</div>
                                    </c:forEach>
                                </div>
                            </div>

                            <div class="custom-select">
                                <div  class="select-year select-selected" data-field="year">
                                    ${yearLabel}
                                </div>
                                <div class="select-items">
                                    <div class="select-item" data-value="all">All</div>
                                    <c:forEach items="${years}" var="carYear">
                                        <div class="select-item" data-value="${carYear}">${carYear}</div>
                                    </c:forEach>
                                </div>
                            </div>

                        </div>

                    </form>

                </div>

                <div class="recent-orders">
                    <div class="table-container">
                        <table>
                            <thead>
                                <tr>
                                    <th>No.</th>
                                    <th>Serial Number</th>
                                    <th>Model</th>
                                    <th>Year</th>
                                    <th></th>
                                        <c:if test="${not empty sessionScope.SERVICETICKETCUSTOMER}">
                                        <th></th>
                                        </c:if>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${cars}" var="car" varStatus="counter">
                                    <tr>
                                        <td>${counter.count}</td>
                                        <td>${car.serialNumber}</td>
                                        <td>${car.model}</td>
                                        <td>${car.year}</td>
                                        <td class="primary js-popup-details" data-car-detail="${car.carID}">Details</td>
                                        <c:if test="${not empty sessionScope.SERVICETICKETCUSTOMER}">
                                            <td>
                                                <form action="ProcessServlet?btnAction=addCarToServiceTicket" method="post">
                                                    <input type="hidden" name="carId" value="${car.carID}" />
                                                   
                                                        
                                                        <button type="submit">Add to Ticket</button>
                                                    
                                                    
                                                </form>
                                            </td>
                                        </c:if>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <div class="reminders">
                        <div class="notification add-reminder">
                            <div>
                                <span class="material-symbols-outlined">
                                    add
                                </span>
                                <h3>Add New Car</h3>
                            </div>
                        </div>
                    </div>
                </div>
            </main>
            <!--Ending main-->


        </div>
        <div id="carData" style="display: none;">
            ${carJson}
        </div>
        <div id="carId" style="display: none;">
            ${idJson}
        </div>
        <div id="successMessage" class="hidden">${SUCCESS}</div>
        <div id="errorMessage" class="hidden">${ERROR}</div>
        <div class="modal-background js-popup-modal">
        </div>
        <script src="${pageContext.request.contextPath}/assets/js/script.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/car.js"></script>
    </body>
</html>
