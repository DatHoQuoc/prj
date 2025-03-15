<%-- 
    Document   : viewServiceTicket
    Created on : Mar 11, 2025, 10:18:08 PM
    Author     : datho
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Service Ticket #${TicketDetail.id}</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/viewTicket.css">
    </head>
    <body>
        <main class="container">
            <header class="ticket-header">
                <h1 class="ticket-header__title">
                    <span class="sr-only">AutoService Pro - </span>
                    Service Ticket #${TicketDetail.id}
                </h1>
                <div class="status status--in-progress">
                    In Progress
                </div>
            </header>

            <section class="info-section">
                <h2 class="info-section__heading">Ticket Details</h2>
                <div class="info-grid">
                    <div class="info-group">
                        <span class="info-group__label">Ticket ID</span>
                        <div class="info-group__value">${TicketDetail.id}</div>
                    </div>
                    <div class="info-group">
                        <span class="info-group__label">Date Received</span>
                        <div class="info-group__value">
                            <fmt:formatDate value="${TicketDetail.dateReceived}" pattern="MMMM d, yyyy" />
                        </div>
                    </div>
                    <div class="info-group">
                        <span class="info-group__label">Estimated Return</span>
                        <div class="info-group__value">
                            <fmt:formatDate value="${TicketDetail.dateReturned}" pattern="MMMM d, yyyy" />
                        </div>
                    </div>
                </div>
            </section>

            <section class="info-section">
                <h2 class="info-section__heading">Customer Information</h2>
                <div class="info-grid">
                    <div class="info-group">
                        <span class="info-group__label">Name</span>
                        <div class="info-group__value">${CustomerDetail.custName}</div>
                    </div>
                    <div class="info-group">
                        <span class="info-group__label">Contact</span>
                        <div class="info-group__value">0${CustomerDetail.phone}</div>
                    </div>
                    <div class="info-group">
                        <span class="info-group__label">Address</span>
                        <div class="info-group__value">${CustomerDetail.cusAddress}</div>
                    </div>
                </div>
            </section>

            <section class="info-section">
                <h2 class="info-section__heading">Vehicle Details</h2>
                <div class="info-grid">
                    <div class="info-group">
                        <span class="info-group__label">Vehicle</span>
                        <div class="info-group__value">${CarDetail.year} ${CarDetail.model}</div>
                    </div>
                    <div class="info-group">
                        <span class="info-group__label">Serail Number</span>
                        <div class="info-group__value">${CarDetail.serialNumber}</div>
                    </div>
                </div>
            </section>

            <section class="info-section">
                <h2 class="info-section__heading">Service Details</h2>
                <div class="table-responsive">
                    <table class="data-table">
                        <caption class="sr-only">Service items and costs</caption>
                        <thead>
                            <tr>
                                <th scope="col">Service</th>
                                <th scope="col">Mechanic</th>
                                <th scope="col">Hours</th>
                                <th scope="col">Hour Price</th>
                                <th scope="col">Total</th>
                                <th scope="col">Comment</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${ServiceDetail}" var="service">
                                <tr>
                                    <td>${service.serviceName}</td>
                                    <td>${service.mechanicName}</td>
                                    <td>${service.hours}</td>
                                    <td>${service.rate} VND</td>
                                    <td>${service.total} VND</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${empty service.comment}">No comment</c:when>
                                            <c:otherwise>${service.comment}</c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </section>

            <section class="info-section">
                <h2 class="info-section__heading">Parts Used</h2>
                <div class="table-responsive">
                    <table class="data-table">
                        <caption class="sr-only">Parts inventory used</caption>
                        <thead>
                            <tr>
                                <th scope="col">Part Name</th>
                                <th scope="col">Quantity</th>
                                <th scope="col">Unit Price</th>
                                <th scope="col">Total</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${PartDetail}" var="part">
                                <tr>
                                    <td>${part.partName}</td>
                                    <td>${part.number}</td>
                                    <td>${part.price} VND</td>
                                    <td>${part.total} VND</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </section>

            <section class="info-section">
                <div class="totals">
                    <div class="total-row">
                        <span>Labor Total:</span>
                        <span>$275.00</span>
                    </div>
                    <div class="total-row">
                        <span>Parts Total:</span>
                        <span>$82.44</span>
                    </div>
                    <div class="total-row">
                        <span>Tax (8%):</span>
                        <span>$28.60</span>
                    </div>
                    <div class="total-row total-row--grand">
                        <span>Grand Total:</span>
                        <span>$386.04</span>
                    </div>
                </div>
            </section>

            <footer class="action-bar">
                <button type="button" class="button button--primary">
                    Print Ticket
                </button>
                <button type="button" class="button button--secondary">
                    Update Status
                </button>
                <button type="button" class="button button--success">
                    Complete Service
                </button>
            </footer>
        </main>
    </body>
</html>
