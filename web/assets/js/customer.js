/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


const customersData = document.getElementById('customersData').textContent;
let cust = [];
try {
    cust = JSON.parse(customersData);
} catch (error) {
    console.log("faild to parse value due to null");
}


document.querySelectorAll('.js-popup-details').forEach((button) => {
    button.addEventListener('click', () => {
        const custID = button.dataset.customerDetail.trim();
        let matching;
        cust.forEach((item) => {
            const itemID = String(item.id).trim();
            if (custID === itemID) {
                matching = item;
            }
        });
        if (matching) {
            generateHTML(matching);
            //put in website
            document.querySelector('.js-popup-modal').innerHTML = custPopup;
            //show
            showPopup();
            const closeButton = document.querySelector('.js-popup-close');
            if (closeButton) {
                closeButton.addEventListener('click', closePopup);
            }
            upDateButtonListener(matching);
            deleteButtonListener();
        } else {
            console.log('No matching customer found');
        }
    });
});
let custPopup = '';
function generateHTML(matching) {
    custPopup = `
        <div class="modal">
            <div class="modal-header">
                <h2>Customer Profile</h2>
                <button class="close-btn js-popup-close">&times;</button>
            </div>
            
            <div class="modal-content">
                <div class="detail-grid">
                    <div class="detail-item">
                        <span class="detail-label">Full Name</span>
                        <p class="detail-value">${matching.name}</p>
                    </div>
                    <div class="detail-item">
                        <span class="detail-label">Phone</span>
                        <p class="detail-value">
                            <span class="status-badge">0${matching.phone}</span>
                        </p>
                    </div>
                    <div class="detail-item">
                        <span class="detail-label">Gender</span>
                        <p class="detail-value">${matching.sex.trim() === 'M' ? 'Male' : 'Female'}</p>
                    </div>
                    <div class="detail-item">
                        <span class="detail-label">Address</span>
                        <p class="detail-value">${matching.address}</p>
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
    window.location.href = "ProcessServlet?btnAction=CreateCust";
});

function upDateButtonListener(matching){
    const updateButton = document.querySelector('.btn-update');
    if(updateButton){
        updateButton.addEventListener('click', ()=>{
            handleUpdateClick(matching);
        });
    }
}
function deleteButtonListener(){
    const deleteButton = document.querySelector('.btn-delete');
    if(deleteButton){
        deleteButton.addEventListener('click', ()=>{
            handleDeleteClick();
        });
    }
}
function handleDeleteClick(){
    const confirmationOverlay = document.createElement('div');
    confirmationOverlay.className = 'confirmation-overlay';
    confirmationOverlay.innerHTML = `
        <div class="confirmation-dialog">
            <p style = "color:red;">You do not have permission to delete this customer</p>
            <div class="confirmation-buttons">
                <button type="button" id="confirm-no" class="btn btn-no">Cancel</button>
            </div>
        </div>
    `;
    document.body.appendChild(confirmationOverlay);
    confirmationOverlay.style.display = 'flex';
    document.getElementById('confirm-no').addEventListener('click', function() {
        confirmationOverlay.style.display = 'none';
    });
}
function handleUpdateClick(matching){
    const customerData = extractCustomerData(matching);
    submitFormWithData('ProcessServlet','UpdateCust',customerData);
}
function extractCustomerData(matching){
    return{
        id: `${matching.id}`,
        name: `${matching.name}`,
        phone: `0${matching.phone}`,
        sex: `${matching.sex}`,
        address: `${matching.address}`
    };
}

function submitFormWithData(actionUrl, actionType, dataObject){
    const form = createForm(actionUrl, 'POST');
    addHiddenField(form, 'btnAction', actionType);
    
    Object.entries(dataObject).forEach(([key, value]) =>{
       addHiddenField(form, `customer${capitalizeFirstLetter(key)}`,value); 
    });
    submitForm(form);
}

function createForm(action, method){
    const form = document.createElement('form');
    form.action = action;
    form.method = method;
    return form;
}

function addHiddenField(form, name, value){
    const input = document.createElement('input');
    input.type = 'hidden';
    input.name = name;
    input.value = value;
    form.appendChild(input);
}

function submitForm(form){
    document.body.appendChild(form);
    form.submit();
    document.body.removeChild(form);
}

function capitalizeFirstLetter(string){
    return string.charAt(0).toUpperCase() + string.slice(1);
}