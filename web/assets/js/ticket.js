/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

document.addEventListener('DOMContentLoaded', function () {
    // Configuration
    const recordsPerPage = 5; // Number of rows per page
    let currentPage = 1;
    const toastSystem = new ToastNotificationSystem();
    window.toastSystem = toastSystem;
    const messageHandler = new ServerMessageHandler(toastSystem);
    messageHandler.processMessages();

    // Get table elements
    const tableContainer = document.querySelector('.recent-orders'); // Changed from '.table-container'
    const table = tableContainer.querySelector('table');

    // Check if table exists before proceeding
    if (!table) {
        console.log('No table found. Pagination not initialized.');
        return;
    }

    const tbody = table.querySelector('tbody');
    const rows = tbody.querySelectorAll('tr');
    const totalRows = rows.length;

    // Only set up pagination if we have rows
    if (totalRows === 0) {
        console.log('No rows found. Pagination not initialized.');
        return;
    }

    const totalPages = Math.ceil(totalRows / recordsPerPage);

    // Create pagination container
    const paginationContainer = document.createElement('div');
    paginationContainer.className = 'pagination-container';
    paginationContainer.style.marginTop = '20px';
    paginationContainer.style.textAlign = 'center';
    tableContainer.appendChild(paginationContainer);

    // Function to show rows for the current page
    function displayRows() {
        const start = (currentPage - 1) * recordsPerPage;
        const end = start + recordsPerPage;

        // Hide all rows
        rows.forEach(row => {
            row.style.display = 'none';
        });

        // Show only rows for current page
        for (let i = start; i < end && i < totalRows; i++) {
            rows[i].style.display = '';
        }

        updatePaginationUI();
    }

    // Update pagination UI elements
    function updatePaginationUI() {
        // Update active button
        document.querySelectorAll('.pagination-btn').forEach(btn => {
            btn.classList.remove('active');
            btn.style.backgroundColor = '';
            btn.style.color = '';
            btn.style.borderColor = '';
        });

        const activeBtn = document.querySelector(`.pagination-btn[data-page="${currentPage}"]`);
        if (activeBtn) {
            activeBtn.classList.add('active');
            activeBtn.style.backgroundColor = '#007bff';
            activeBtn.style.color = 'white';
            activeBtn.style.borderColor = '#007bff';
        }

        // Update prev/next buttons state
        const prevBtn = document.querySelector('.prev-btn');
        const nextBtn = document.querySelector('.next-btn');

        if (prevBtn)
            prevBtn.disabled = currentPage === 1;
        if (nextBtn)
            nextBtn.disabled = currentPage === totalPages;

        // Update page info
        const pageInfo = document.querySelector('.page-info');
        if (pageInfo) {
            pageInfo.textContent = `Page ${currentPage} of ${totalPages}`;
        }
    }

    // Create pagination buttons
    function setupPagination() {
        // Clear pagination container
        paginationContainer.innerHTML = '';

        // Previous button
        if (totalPages > 1) {
            const prevBtn = document.createElement('button');
            prevBtn.textContent = 'Previous';
            prevBtn.className = 'pagination-nav-btn prev-btn';
            prevBtn.style.margin = '0 5px';
            prevBtn.style.padding = '5px 10px';
            prevBtn.disabled = currentPage === 1;
            prevBtn.addEventListener('click', function () {
                if (currentPage > 1) {
                    currentPage--;
                    displayRows();
                }
            });
            paginationContainer.appendChild(prevBtn);
        }

        // Page number buttons
        for (let i = 1; i <= totalPages; i++) {
            const btn = document.createElement('button');
            btn.textContent = i;
            btn.className = 'pagination-btn';
            btn.dataset.page = i;
            btn.style.margin = '0 5px';
            btn.style.padding = '5px 10px';

            if (i === currentPage) {
                btn.classList.add('active');
                btn.style.backgroundColor = '#007bff';
                btn.style.color = 'white';
                btn.style.borderColor = '#007bff';
            }

            btn.addEventListener('click', function () {
                currentPage = parseInt(this.dataset.page);
                displayRows();
            });

            paginationContainer.appendChild(btn);
        }

        // Next button
        if (totalPages > 1) {
            const nextBtn = document.createElement('button');
            nextBtn.textContent = 'Next';
            nextBtn.className = 'pagination-nav-btn next-btn';
            nextBtn.style.margin = '0 5px';
            nextBtn.style.padding = '5px 10px';
            nextBtn.disabled = currentPage === totalPages;
            nextBtn.addEventListener('click', function () {
                if (currentPage < totalPages) {
                    currentPage++;
                    displayRows();
                }
            });
            paginationContainer.appendChild(nextBtn);
        }

        // Page info
        const pageInfo = document.createElement('div');
        pageInfo.className = 'page-info';
        pageInfo.style.marginTop = '10px';
        pageInfo.textContent = `Page ${currentPage} of ${totalPages}`;
        paginationContainer.appendChild(pageInfo);
    }

    // Initialize pagination
    setupPagination();
    displayRows();
});

document.querySelectorAll('.js-popup-details').forEach((button) => {
    button.addEventListener('click', () => {
        const ticketID = button.dataset.ticketDetail.trim();
        window.location.href = `ProcessServlet?btnAction=ViewTicket&id=${ticketID}`;
    });
});

const buttonCreate = document.getElementById("create-new");
buttonCreate.addEventListener('click', () => {
    generateHTML();
    document.querySelector('.js-popup-modal').innerHTML = custPopup;
    showPopup();
    const closeButton = document.querySelector('.js-popup-close');
    if (closeButton) {
        closeButton.addEventListener('click', closePopup);
    }
})

let custPopup = '';
function generateHTML() {
    custPopup = `
        <div class="modal-background-create js-popup-modal-create">
                    <div class="modal">
                        <div class="modal-header">
                            <h2>Customer Information</h2>
                            <button class="close-btn js-popup-close">&times;</button>
                        </div>

                        <form id="createCustomerForm" accept-charset="utf-8" method="post" action="ProcessServlet?btnAction=ServiceTicket">
                            <div class="detail-grid">
                                <div class="detail-item">
                                    <span class="detail-label required">Full Name</span>
                                    <input class="input" id="name" type="text"  name="fullName" placeholder="Nguyễn Văn A" value="" onkeyup="validateName()" required>
                                    <span id="name-error" style="color:red"></span>
                                </div>

                                <div class="detail-item">
                                    <span class="detail-label required">Phone Number</span>
                                    <input class="input" type="tel" id="phone" name="phone" placeholder="0966121318" value="" onkeyup="validatePhone()" required>
                                    <span id="phone-error" style="color:red"></span>
                                </div>

                                
                            </div>

                            <div class="modal-actions">
                                <button type="submit" class="btn btn-create ">
                                    <span class="material-symbols-outlined">check</span>
                                    Check
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
`;
}

const closePopup = () => {
    const bodyClassList = document.body.classList;
    if (bodyClassList.contains("open")) {
        bodyClassList.remove("open");
        bodyClassList.add("closed");
    }
}

const showPopup = () => {
    const bodyClassList = document.body.classList;
    if (!(bodyClassList.contains("open"))) {
        bodyClassList.remove("closed");
        bodyClassList.add("open");
    }
}
var nameError = document.getElementById('name-error');
var phoneError = document.getElementById('phone-error');
function validateName() {
    var name = document.getElementById('name').value;

    if (name.length == 0) {
        nameError.innerHTML = 'Name is requierd';
        return false;
    }
    if (!name.match(/^[\p{L}'-]+(\s[\p{L}'-]+)+$/u)) {
        nameError.innerHTML = 'Write full name';
        return false;
    }
    nameError.innerHTML = '';
    return true;
}

function validatePhone() {
    var phone = document.getElementById('phone').value;

    if (phone.length == 0) {
        phoneError.innerHTML = "Phone number is required";
        return false;
    }
    if (phone.length < 10 || phone.length > 15) {
        phoneError.innerHTML = 'Phone number is from 10 to 15 digits';
        return false;
    }
    if (!phone.match(/^[0-9]{10}$/)) {
        phoneError.innerHTML = 'Phone number is required';
        return false;
    }
    phoneError.innerHTML = '';
    return true;
}
class ToastNotificationSystem {
    constructor() {
        this.container = document.getElementById('toast-container');
        if (!this.container) {
            this.container = document.createElement('div');
            this.container.id = 'toast-container';
            document.body.appendChild(this.container);
        }
        this.toasts = [];
    }
    /**
     * Display a new toast notification
     * @param {string} type - Type of toast (success, error, info, warning)
     * @param {string} title - Toast title
     * @param {string} message - Toast message content
     * @param {number} duration - Time in milliseconds before auto-dismiss
     * @returns {string} - ID of the created toast
     */

    show(type, title, message, duration = 5000) {
        const id = 'toast-' + Math.random().toString(36).substr(2, 9) + '-' + Date.now();

        const toast = document.createElement('div');
        toast.id = id;
        toast.className = `toast ${type}`;
        toast.innerHTML = `<div class="toast-icon">${this.getIcon(type)}</div>
                    <div class="toast-content">
                        <div class="toast-title">${title}</div>
                        <div class="toast-message">${message}</div>
                    </div>
                    <div class="toast-close" onclick="toastSystem.dismiss('${id}')">&times;</div>
                    <div class="toast-progress">
                        <div class="toast-progress-bar"></div>
                    </div>`;
        this.container.appendChild(toast);

        const progressBar = toast.querySelector('.toast-progress-bar');
        progressBar.style.transition = `transform ${duration / 1000}s linear`;

        void toast.offsetWidth;

        progressBar.style.transform = 'scaleX(0)';

        const timeout = setTimeout(() => {
            this.dismiss(id);
        }, duration);

        this.toasts.push({id, element: toast, timeout});

        return id;
    }
    /**
     * Dismiss a specific toast by ID
     * @param {string} id - ID of the toast to dismiss
     */
    dismiss(id) {
        const toastIndex = this.toasts.findIndex(t => t.id === id);
        if (toastIndex === -1)
            return;

        const {element, timeout} = this.toasts[toastIndex];

        if (timeout)
            clearTimeout(timeout);

        element.classList.add('hidding');

        setTimeout(() => {
            if (element.parentNode) {
                element.parentNode.removeChild(element);
            }
        }, 500);

        this.toasts.splice(toastIndex, 1);
    }
    dismissAll() {
        [...this.toasts].forEach(toast => {
            this.dismiss(toast.id);
        });
    }

    getIcon(type) {
        switch (type) {
            case 'success':
                return '✓';
            case 'error':
                return '✗';
            case 'info':
                return 'ℹ';
            case 'warning':
                return '⚠';
            default:
                return '';
        }
    }
}

class ServerMessageHandler {
    constructor(toastSystem) {
        this.toastSystem = toastSystem;
        this.successContainer = document.getElementById('successMessage');
        this.errorContainer = document.getElementById('errorMessage');
        this.errorContainers = document.querySelectorAll('.js-error');
    }

    processMessages() {
        const successMessage = this.successContainer.textContent.trim();
        const errorMessage = this.errorContainer.textContent.trim();
        const errors = [];
        if (errorMessage) {
            this.toastSystem.show('error', 'Error', errorMessage);
        }
        this.errorContainers.forEach(errorElement => {
            const message = errorElement.textContent.trim();
            if (message) {
                errors.push(message);
            }
        });

        errors.forEach((error, index) => {
            setTimeout(() => {
                this.toastSystem.show('error', 'Error', error);
            }, index * 100);
        });
        if (successMessage) {
            this.toastSystem.show('success', 'Success', successMessage);
        }
    }
}