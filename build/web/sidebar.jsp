<%-- 
    Document   : sidebar
    Created on : Feb 26, 2025, 7:01:15 PM
    Author     : datho
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<aside>
    <div class="toggle">
        <div class="logo">
            <img src="assets/images/logo.jpg" alt="logo">
            <h2>Prj<span class="success">301</span></h2>
        </div>
        <div class="close" id="close-btn">
            <span class="material-symbols-outlined">
                close
            </span>
        </div>
    </div>
    <div class="sidebar">
        <a href="ProcessServlet?btnAction=Search" class="${param.active eq 'customer' ? 'active' : ''}">
            <span class="material-symbols-outlined">
                support_agent
            </span>
            <h3>Customer</h3>
        </a>
        <a href="ProcessServlet?btnAction=Car" class="${param.active eq 'car' ? 'active' : ''}">
            <span class="material-symbols-outlined">
                directions_car
            </span>
            <h3>Car</h3>
        </a>
        <a href="#" class="${param.active eq 'ticket' ? 'active' : ''}">
            <span class="material-symbols-outlined">
                confirmation_number
            </span>
            <h3>Ticket</h3>
        </a>
        <a href="#" class="${param.active eq 'components' ? 'active' : ''}">
            <span class="material-symbols-outlined">
                car_tag
            </span>
            <h3>Car Assemble components</h3>
        </a>
        <a href="invoice.html" class="${param.active eq 'invoice' ? 'active' : ''}">
            <span class="material-symbols-outlined">
                receipt_long
            </span>
            <h3>Invoice</h3>
        </a>
        <a href="ProcessServlet?btnAction=Report&id=part" class="${param.active eq 'report' ? 'active' : ''}">
            <span class="material-symbols-outlined">
                report
            </span>
            <h3>Report</h3>
        </a>
        <a href="ProcessServlet?btnAction=">
            <span class="material-symbols-outlined">
                logout
            </span>
            <h3>Logout</h3>
        </a>
    </div>
</aside>
