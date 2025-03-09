/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

document.querySelector('.js-popup-close').addEventListener('click', () => {
    window.location.href = "ProcessServlet?btnAction=Car";
});

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

document.addEventListener('DOMContentLoaded', () => {
    const toastSystem = new ToastNotificationSystem();
    window.toastSystem = toastSystem;
    const messageHandler = new ServerMessageHandler(toastSystem);
    messageHandler.processMessages();
    const form = document.getElementById('updateCarForm');
    const updateButton = form.querySelector('.btn-create');
    
    const confirmationOverlay = document.createElement('div');
    confirmationOverlay.className = 'confirmation-overlay';
    confirmationOverlay.innerHTML = `
        <div class="confirmation-dialog">
            <p>Are you sure you want to update this car information?</p>
            <div class="confirmation-buttons">
                <button type="button" id="confirm-yes" class="btn btn-yes">Yes</button>
                <button type="button" id="confirm-no" class="btn btn-no">No</button>
            </div>
        </div>
    `;
    document.body.appendChild(confirmationOverlay);
    
    updateButton.addEventListener('click', function(event) {
        event.preventDefault(); 
        confirmationOverlay.style.display = 'flex';
    });
    
    
    document.getElementById('confirm-yes').addEventListener('click', function() {
        confirmationOverlay.style.display = 'none'; 
        if (form) {
            form.submit();
        } 
    });
    
    
    document.getElementById('confirm-no').addEventListener('click', function() {
        confirmationOverlay.style.display = 'none'; 
    });
});
