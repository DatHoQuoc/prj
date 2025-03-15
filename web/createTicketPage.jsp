<%-- 
    Document   : createTicketPage
    Created on : Mar 14, 2025, 6:32:08 AM
    Author     : datho
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create New Service Ticket</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/createTicket.css"/>
    </head>
    <body>
        <main class="container">
            <header class="ticket-header">
                <h1 class="ticket-header__title">
                    <span class="sr-only"> </span>
                    Create New Service Ticket
                </h1>
            </header>

            <form id="create-ticket-form">
                <section class="form-section">
                    <h2 class="form-section__heading">Ticket Details</h2>
                    <div class="form-grid">
                        <div class="form-group">
                            <label for="ticket-id" class="form-label">Ticket ID</label>
                            <input type="text" id="ticket-id" class="form-control" value="ST-" placeholder="Auto-generated" readonly>
                        </div>
                        <div class="form-group">
                            <label for="date-received" class="form-label">Date Received</label>
                            <input type="date" id="date-received" class="form-control" value="">
                        </div>
                        <div class="form-group">
                            <label for="estimated-return" class="form-label">Estimated Return</label>
                            <input type="date" id="estimated-return" class="form-control" value="">
                        </div>
                    </div>
                </section>

                <section class="form-section">
                    <h2 class="form-section__heading">Customer Information</h2>
                    <div class="form-grid">
                        <div class="form-group">
                            <label for="customer-name" class="form-label">Name</label>
                            <div class="info-group__value">${sessionScope.SERVICETICKETCUSTOMER.custName}</div>
                        </div>
                        <div class="form-group">
                            <label for="customer-phone" class="form-label">Phone</label>
                            <div class="info-group__value">0${sessionScope.SERVICETICKETCUSTOMER.phone}</div>
                        </div>

                        <div class="form-group">
                            <label for="customer-address" class="form-label">Address</label>
                            <div class="info-group__value">${sessionScope.SERVICETICKETCUSTOMER.cusAddress}</div>
                        </div>
                    </div>
                </section>

                <section class="form-section">
                    <h2 class="form-section__heading">Vehicle Details</h2>
                    <div class="form-grid">
                        <div class="form-group">
                            <label for="vehicle-year" class="form-label">Year</label>
                            <div class="info-group__value">${sessionScope.CARTICKET.year}</div>
                        </div>
                        <div class="form-group">
                            <label for="vehicle-make" class="form-label">Model</label>
                            <div class="info-group__value">${sessionScope.CARTICKET.model}</div>
                        </div>
                        <div class="form-group">
                            <label for="vehicle-model" class="form-label">Serival Number</label>
                            <div class="info-group__value">${sessionScope.CARTICKET.serialNumber}</div>
                        </div>

                    </div>
                </section>

                <section class="form-section">
                    <h2 class="form-section__heading">Service Details</h2>
                    <div class="table-responsive">
                        <table class="service-table">
                            <thead>
                                <tr>
                                    <th scope="col">Service</th>
                                    <th scope="col">Mechanic Name</th>
                                    <th scope="col">Hours Price</th>
                                    <th scope="col">Hours</th>
                                    <th scope="col">Total</th>
                                    <th scope="col">Actions</th>
                                </tr>
                            </thead>
                            <tbody id="service-tbody">
                                <tr>
                                    <td>
                                        <select class="form-control" name="service[]">
                                            <option value="">Select Service</option>
                                            <c:forEach items="${SERVICES}" var="service">
                                                <option value="${service.id}">${service.serviceName}</option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                    <td>
                                        <select class="form-control" name="mechanic[]">
                                            <option value="">Select Mechanic</option>
                                            <c:forEach items="${MECHANICS}" var="mechanic">
                                                <option value="${mechanic.mechanicID}">${mechanic.mechanicName}</option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                    <td>
                                        <input type="number" class="form-control service-rate" name="rate[]" min="0" step="0.01" placeholder="0.00" readonly="">
                                    </td>
                                    <td>
                                        <input type="number" class="form-control service-hours" name="hours[]" min="0" max="100" placeholder="0.0">
                                    </td>
                                    <td>
                                        <input type="text" class="form-control service-total" name="total[]" readonly placeholder="0.00 VND">
                                    </td>
                                    <td>
                                        <div class="row-actions">
                                            <button type="button" class="btn-icon btn-icon--delete" title="Remove Service">✕</button>
                                        </div>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                        <button type="button" class="add-row-btn" id="add-service">Add Service</button>
                    </div>
                </section>

                <section class="form-section">
                    <h2 class="form-section__heading">Parts</h2>
                    <div class="table-responsive">
                        <table class="parts-table">
                            <thead>
                                <tr>
                                    <th scope="col">Part Name</th>
                                    <th scope="col">Unit Price</th>
                                    <th scope="col">Quantity</th>
                                    <th scope="col">Total</th>
                                    <th scope="col">Actions</th>
                                </tr>
                            </thead>
                            <tbody id="parts-tbody">
                                <tr>
                                    <td>
                                        <select class="form-control" name="part_name[]">
                                            <option value="">Select Part</option>
                                            <c:forEach items="${PARTS}" var="part">
                                                <option value="${part.partID}">${part.partName}</option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                    <td>
                                        <input type="number" class="form-control part-price" name="unit_price[]" min="0" step="0.01" placeholder="0.00" readonly="">
                                    </td>
                                    <td>
                                        <input type="number" class="form-control part-quantity" name="quantity[]" min="1" placeholder="1">
                                    </td>
                                    
                                    <td>
                                        <input type="text" class="form-control part-total" name="part_total[]" readonly placeholder="$0.00">
                                    </td>
                                    <td>
                                        <div class="row-actions">
                                            <button type="button" class="btn-icon btn-icon--delete" title="Remove Part">✕</button>
                                        </div>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                        <button type="button" class="add-row-btn" id="add-part">Add Part</button>
                    </div>
                </section>

                <section class="form-section">
                    <h2 class="form-section__heading">Notes</h2>
                    <div class="form-group">
                        <label for="service-notes" class="form-label">Service Notes</label>
                        <textarea id="service-notes" class="form-control" rows="4" placeholder="Enter any additional notes or customer requests here"></textarea>
                    </div>
                </section>

                <footer class="action-bar">
                    <button type="submit" class="button button--success">
                        Create Ticket
                    </button>
                    <button type="button" class="button button--secondary">
                        Save as Draft
                    </button>
                    <button type="reset" class="button button--danger">
                        Clear Form
                    </button>
                </footer>
            </form>
        </main>
        <div id="serviceData" style="display: none;">
            ${JSONSERVICE}
        </div>
        <div id="partData" style="display: none;">
            ${JSONPART}
        </div>
        <script src="${pageContext.request.contextPath}/assets/js/createTicket.js"></script>
    </body>
</html>
