/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

handleCarSelect();

function handleCarSelect() {
    document.addEventListener('DOMContentLoaded', () => {
        const customSelects = document.querySelectorAll('.custom-select');
        const form = document.getElementById('carSearchForm');
        customSelects.forEach(select => {
            initializeCustomSelect(select, customSelects);
        });

        initializeGlobalClickHandler(customSelects);
    });
}

function initializeCustomSelect(select, customSelects) {
    const selected = select.querySelector('.select-selected');
    const items = select.querySelector('.select-items');
    const selectItems = items.querySelectorAll('.select-item');

    selected.addEventListener('click', handleSelectClick(select, selected, items, customSelects));

    selectItems.forEach(item => {
        item.addEventListener('click', handleItemClick(selected, items, selectItems, item, select))
    });
}

function handleSelectClick(select, selected, items, customSelects) {
    return function (e) {
        e.stopPropagation();
        closeAllSelectsExcept(customSelects, select);
        toggleSelectDropdown(items, selected);
    };
}

function closeAllSelectsExcept(customSelects, currentSelects) {
    customSelects.forEach(otherSelects => {
        if (otherSelects !== currentSelects) {
            const items = otherSelects.querySelector('.select-items');
            const selected = otherSelects.querySelector('.select-selected');
            closeDropDrow(items, selected);
        }
    });
}

function closeDropDrow(items, selected) {
    items.classList.remove('show');
    selected.classList.remove('active');
}

function toggleSelectDropdown(items, selected) {
    items.classList.toggle('show');
    selected.classList.toggle('active');
}

function handleItemClick(selected, items, selectItems, item, select) {
    return function () {
        updateSelectedText(selected, item);
        updateSelectedStyles(selectItems, item);
        closeDropDrow(items, selected);
        dispatchChangeEvent(select, item);
    }
}

function updateSelectedText(selected, item) {
    selected.textContent = item.textContent;
    selected.setAttribute('data-value', item.getAttribute('data-value'));
}

function updateSelectedStyles(selectItems, selectedItem) {
    selectItems.forEach(item => item.classList.remove('selected'));
    selectedItem.classList.add('selected');
}

function dispatchChangeEvent(select, item) {
    const changeEvent = new CustomEvent('change', {
        detail: {
            value: item.getAttribute('data-value'),
            text: item.textContent
        }
    });
    select.dispatchEvent(changeEvent);
}

function initializeGlobalClickHandler(customSelects) {
    document.addEventListener('click', () => {
        customSelects.forEach(select => {
            const items = select.querySelector('.select-items');
            const selected = select.querySelector('.select-selected');
            closeDropDrow(items, selected);
        });
    });
}

document.querySelectorAll('.custom-select').forEach(select => {
    select.addEventListener('change', (event) => {
        console.log('Selected value:', event.detail.value);
        console.log('Selected text:', event.detail.text);
    });
});



document.querySelectorAll('.custom-select').forEach(select => {
    select.addEventListener('change', (event) => {

        const selected = select.querySelector('.select-selected');
        const field = selected.getAttribute('data-field');
        const value = selected.getAttribute('data-value'); // Get value from select-selected

        const hiddenInput = document.getElementById(field);

        if (hiddenInput) {
            hiddenInput.value = value; // Use value from select-selected
        }

        const form = document.getElementById('carSearchForm');
        if (form) {
            form.submit();
        }
    });
});

document.querySelector('.input').addEventListener('change', () => {

    const form = document.getElementById('carSearchForm');
    if (form) {
        form.submit();
    }
});

const carData = document.getElementById('carData').textContent.trim();
let car = [];
try {
    car = JSON.parse(carData);
} catch (error) {
    console.log("faild to parse value due to null");
}

const carId = document.getElementById('carId').textContent.trim();
let carIds = [];
try {
    carIds = JSON.parse(carId);
    console.log(carIds);
} catch (error) {
    console.log("faild to parse value due to null");
}

document.querySelectorAll('.js-popup-details').forEach((button) => {
    button.addEventListener('click', () => {
        const carID = button.dataset.carDetail.trim();
        let matching;
        car.forEach((item) => {
            const itemID = String(item.id).trim();
            if (carID === itemID) {
                matching = item;
            }
        });
        if (matching) {
            generateHTML(matching);
            //put in website
            document.querySelector('.js-popup-modal').innerHTML = carPopup;
            //show
            showPopup();
            const closeButton = document.querySelector('.js-popup-close');
            if (closeButton) {
                closeButton.addEventListener('click', closePopup);
            }
            upDateButtonListener(matching);
            deleteButtonListener(matching.id);
        } else {
            console.log('No matching customer found');
        }
    });
});

let carPopup = '';
function generateHTML(matching) {
    carPopup = `
        <div class="modal">
            <div class="modal-header">
                <h2>Customer Profile</h2>
                <button class="close-btn js-popup-close">&times;</button>
            </div>
            
            <div class="modal-content">
                <div class="detail-grid">
                    <div class="detail-item">
                        <span class="detail-label">Serial Number</span>
                        <p class="detail-value">${matching.serialNumber}</p>
                    </div>
                    <div class="detail-item">
                        <span class="detail-label">Model</span>
                        <p class="detail-value">
                            <span class="status-badge">${matching.model}</span>
                        </p>
                    </div>
                    <div class="detail-item">
                        <span class="detail-label">Year</span>
                        <p class="detail-value">${matching.year}</p>
                    </div>
                    <div class="detail-item">
                        <span class="detail-label">Color</span>
                        <p class="detail-value">${matching.color}</p>
                    </div>
                </div>
    
                 <div class="modal-actions">
                    <button class="btn btn-update">
                        <span class="material-symbols-outlined">edit</span>
                        Update
                    </button>
                    <button class="btn btn-delete">
                        <span class="material-symbols-outlined">delete</span>
                        Delete
                    </button>
                </div>
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

document.querySelector('.reminders').addEventListener('click', () => {
    window.location.href = "ProcessServlet?btnAction=CreateCar";
});
function deleteButtonListener(id) {
    const deleteButton = document.querySelector('.btn-delete');
    if (deleteButton) {
        deleteButton.addEventListener('click', () => {
            handleDeleteClick(id);
        });
    }
}

function handleDeleteClick(id) {
    const confirmationOverlay = document.createElement('div');
    confirmationOverlay.className = 'confirmation-overlay';
    confirmationOverlay.innerHTML = `
        <div class="confirmation-dialog">
            <p style="color: black">Are you sure you want to delete this car?</p>
            <div class="confirmation-buttons">
                <button type="button" id="confirm-yes" class="btn btn-yes">Yes</button>
                <button type="button" id="confirm-no" class="btn btn-no">No</button>
            </div>
        </div>
    `;
    document.body.appendChild(confirmationOverlay);
    confirmationOverlay.style.display = 'flex';
    
    console.log(id);
    document.getElementById('confirm-yes').addEventListener('click',function(){
        if(carIds.includes(id)){
            alert('You can not delete this car due to be sold');
            confirmationOverlay.style.display = 'none';
        }else{
            window.location.href = `ProcessServlet?btnAction=DeleteCar&id=${id}`;
        }
    });
    
    document.getElementById('confirm-no').addEventListener('click', function () {
        confirmationOverlay.style.display = 'none';
    });
    
}
function upDateButtonListener(matching){
    const updateButton = document.querySelector('.btn-update');
    if(updateButton){
        updateButton.addEventListener('click', ()=>{
            handleUpdateClick(matching);
        });
    }
}

function handleUpdateClick(matching){
    const customerData = extractCarData(matching);
    submitFormWithData('ProcessServlet','UpdateCar',customerData);
}
function extractCarData(matching) {
    return{
        id: `${matching.id}`,
        serial: `${matching.serialNumber}`,
        model: `${matching.model}`,
        year: `${matching.year}`,
        color: `${matching.color}`
    };
}
function submitFormWithData(actionUrl, actionType, dataObject) {
    const form = createForm(actionUrl, 'POST');
    addHiddenField(form, 'btnAction', actionType);

    Object.entries(dataObject).forEach(([key, value]) => {
        addHiddenField(form, `car${capitalizeFirstLetter(key)}`, value);
    });
    submitForm(form);
}

function createForm(action, method) {
    const form = document.createElement('form');
    form.action = action;
    form.method = method;
    return form;
}

function addHiddenField(form, name, value) {
    const input = document.createElement('input');
    input.type = 'hidden';
    input.name = name;
    input.value = value;
    form.appendChild(input);
}

function submitForm(form) {
    document.body.appendChild(form);
    form.submit();
    document.body.removeChild(form);
}

function capitalizeFirstLetter(string) {
    return string.charAt(0).toUpperCase() + string.slice(1);
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

document.addEventListener('DOMContentLoaded', () =>{
   const toastSystem = new ToastNotificationSystem();
    window.toastSystem = toastSystem;
    const messageHandler = new ServerMessageHandler(toastSystem);
    messageHandler.processMessages(); 
});

// Table Pagination Script
document.addEventListener('DOMContentLoaded', function() {
  // Configuration
  const recordsPerPage = 5; // Number of rows per page
  let currentPage = 1;
  
  // Get table elements
  const tableContainer = document.querySelector('.table-container');
  const table = tableContainer.querySelector('table');
  const tbody = table.querySelector('tbody');
  const rows = tbody.querySelectorAll('tr');
  const totalRows = rows.length;
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
    
    if (prevBtn) prevBtn.disabled = currentPage === 1;
    if (nextBtn) nextBtn.disabled = currentPage === totalPages;
    
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
      prevBtn.addEventListener('click', function() {
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
      
      btn.addEventListener('click', function() {
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
      nextBtn.addEventListener('click', function() {
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
