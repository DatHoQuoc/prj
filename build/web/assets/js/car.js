/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

handleCarSelect();

function handleCarSelect(){
    document.addEventListener('DOMContentLoaded',()=>{
       const customSelects = document.querySelectorAll('.custom-select');
       const form = document.getElementById('carSearchForm');
       customSelects.forEach(select =>{
            initializeCustomSelect(select, customSelects);
       });
       
       initializeGlobalClickHandler(customSelects);
    });
}

function initializeCustomSelect(select, customSelects){
    const selected = select.querySelector('.select-selected');
    const items = select.querySelector('.select-items');
    const selectItems = items.querySelectorAll('.select-item');
    
    selected.addEventListener('click',handleSelectClick(select, selected, items, customSelects));
    
    selectItems.forEach(item =>{
       item.addEventListener('click', handleItemClick(selected, items, selectItems, item, select)) 
    }); 
}

function handleSelectClick(select, selected, items, customSelects){
    return function(e){
      e.stopPropagation();
      closeAllSelectsExcept(customSelects, select);
      toggleSelectDropdown(items, selected);
    };
}

function closeAllSelectsExcept(customSelects, currentSelects){
    customSelects.forEach(otherSelects => {
       if(otherSelects !== currentSelects){
           const items = otherSelects.querySelector('.select-items');
           const selected = otherSelects.querySelector('.select-selected');
            closeDropDrow(items, selected);
       } 
    });
}

function closeDropDrow(items, selected){
    items.classList.remove('show');
    selected.classList.remove('active');
}

function toggleSelectDropdown(items, selected){
    items.classList.toggle('show');
    selected.classList.toggle('active');
}

function handleItemClick(selected, items, selectItems, item, select){
    return function(){
        updateSelectedText(selected, item);
        updateSelectedStyles(selectItems, item);
        closeDropDrow(items, selected);
        dispatchChangeEvent(select, item);
    }
}

function updateSelectedText(selected, item){
    selected.textContent = item.textContent;
    selected.setAttribute('data-value', item.getAttribute('data-value'));
}

function updateSelectedStyles(selectItems, selectedItem){
    selectItems.forEach(item => item.classList.remove('selected'));
    selectedItem.classList.add('selected');
}

function dispatchChangeEvent(select, item){
    const changeEvent = new CustomEvent('change',{
       detail: {
           value: item.getAttribute('data-value'),
           text: item.textContent
       }
    });
    select.dispatchEvent(changeEvent);
}

function initializeGlobalClickHandler(customSelects){
    document.addEventListener('click', () =>{
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

document.querySelector('.input').addEventListener('change',()=>{
    
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
            deleteButtonListener();
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
function deleteButtonListener(){
    const deleteButton = document.querySelector('.btn-delete');
    if(deleteButton){
        deleteButton.addEventListener('click',()=>{
           handleDeleteClick(); 
        });
    }
}

function handleDeleteClick(matching){
    const carData = extractCarData(matching);
    submitFormWithData('ProcessServlet','DeleteCar',carData);
}
function extractCarData(matching){
    return{
        id: `${matching.id}`,
        serial: `${matching.serialNumber}`,
        model: `0${matching.model}`,
        year: `${matching.year}`,
        color: `${matching.color}`
    };
}
function submitFormWithData(actionUrl, actionType, dataObject){
    const form = createForm(actionUrl, 'POST');
    addHiddenField(form, 'btnAction', actionType);
    
    Object.entries(dataObject).forEach(([key, value]) =>{
       addHiddenField(form, `car${capitalizeFirstLetter(key)}`,value); 
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


