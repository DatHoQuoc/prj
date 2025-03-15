/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

const service = document.getElementById('serviceData').textContent;
const part = document.getElementById('partData').textContent;

document.addEventListener('DOMContentLoaded', function () {
    const today = new Date().toISOString().split('T')[0];
    document.getElementById('date-received').value = today;

    let  serviceRates = [];
    try {
        serviceRates = JSON.parse(service);
    } catch (error) {
        console.log("faild to parse value of year due to null");
    }
    let  partsData = [];
    try {
        partsData = JSON.parse(part);
        console.log(partsData);
    } catch (error) {
        console.log("faild to parse value of year due to null");
    }
    let selectedParts = new Set();

    document.getElementById('add-service').addEventListener('click', addServiceRow);
    document.getElementById('add-part').addEventListener('click', addPartRow);

    updatePartDropdowns();

    document.addEventListener('click', function (e) {
        if (e.target.classList.contains('btn-icon--delete')) {
            const row = e.target.closest('tr');
            if (row.querySelector('select[name="part_name[]"]')) {
                const partSelect = row.querySelector('select[name="part_name[]"]');
                if (partSelect.value) {
                    selectedParts.delete(parseInt(partSelect.value));
                    updatePartDropdowns();
                }
            }

            if (row.parentNode.querySelectorAll('tr').length > 1) {
                row.remove();
            }
        }
    });

    document.addEventListener('input', function (e) {
        // Calculate service totals
        if (e.target.classList.contains('service-hours') || e.target.classList.contains('service-rate')) {
            calculateServiceRowTotal(e.target.closest('tr'));
        }

        // Calculate parts totals
        if (e.target.classList.contains('part-quantity') || e.target.classList.contains('part-price')) {
            const row = e.target.closest('tr');
            const quantity = parseInt(row.querySelector('.part-quantity').value) || 0;
            const price = parseFloat(row.querySelector('.part-price').value) || 0;
            const total = quantity * price;
            row.querySelector('.part-total').value = total.toFixed(2) + ' VND';
        }
    });

    document.addEventListener('change', function (e) {
        if (e.target.name === 'service[]') {
            const serviceId = e.target.value;
            const row = e.target.closest('tr');
            const rateInput = row.querySelector('.service-rate');

            if (serviceId && serviceRates[serviceId]) {
                const rate = Object.values(serviceRates[serviceId])[0];

                rateInput.value = parseFloat(rate).toFixed(2);

                calculateServiceRowTotal(row);
            }
        }
    });

    document.addEventListener('change', function (e) {
        if (e.target.name === 'part_name[]') {
            const partId = parseInt(e.target.value);
            const row = e.target.closest('tr');
            const priceInput = row.querySelector('.part-price');
            const oldValue = e.target.previousValue;

            if (oldValue) {
                selectedParts.delete(parseInt(oldValue));
            }

            if (partId) {
                selectedParts.add(partId);
                e.target.dataset.previousValue = partId;

                const part = partsData.find(p => p.id === partId);
                if (part) {
                    priceInput.value = part.price;

                    // Trigger calculation if there's a quantity
                    const quantityInput = row.querySelector('.part-quantity');
                    if (quantityInput && quantityInput.value) {
                        const event = new Event('input', {bubbles: true});
                        quantityInput.dispatchEvent(event);
                    }
                } else {
                    priceInput.value = '';
                }
                updatePartDropdowns();
            }
        }
    });

    function updatePartDropdowns() {
        const partDropdowns = document.querySelectorAll('select[name="part_name[]"]');

        partDropdowns.forEach(dropdown => {
            const currentValue = dropdown.value;

            dropdown.innerHTML = '<option value="">Select Part</option>';

            partsData.forEach(part => {
                if (!selectedParts.has(part.id) || part.id === parseInt(currentValue)) {
                    const option = document.createElement('option');
                    option.value = part.id;
                    option.textContent = part.name;

                    if (part.id === parseInt(currentValue)) {
                        option.selected = true;
                    }

                    dropdown.appendChild(option);
                }
            });
        });
    }

    function addPartRow() {
        const tbody = document.getElementById('parts-tbody');
        const firstRow = tbody.querySelector('tr');
        const newRow = firstRow.cloneNode(true);

        // Clear input values
        newRow.querySelectorAll('input').forEach(input => {
            input.value = '';
        });

        const select = newRow.querySelector('select');
        select.selectedIndex = 0;
        delete select.dataset.previousValue;

        tbody.appendChild(newRow);

        updatePartDropdowns();
    }
});

function calculateServiceRowTotal(row) {
    const hours = parseFloat(row.querySelector('.service-hours').value) || 0;
    const rate = parseFloat(row.querySelector('.service-rate').value) || 0;
    const total = hours * rate;
    row.querySelector('.service-total').value = total.toFixed(2) + ' VND';
}

function addServiceRow() {
    const tbody = document.getElementById('service-tbody');
    const firstRow = tbody.querySelector('tr');
    const newRow = firstRow.cloneNode(true);

    // Clear input values
    newRow.querySelectorAll('input').forEach(input => {
        input.value = '';
    });

    // Reset select
    newRow.querySelector('select').selectedIndex = 0;

    tbody.appendChild(newRow);
}




