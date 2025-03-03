/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

//get data
const carByYear = document.getElementById('carByYear').textContent;
const parts = document.getElementById('bestUsedParts').textContent;
const models = document.getElementById('bestSellerModels').textContent;
const revenues = document.getElementById('revenueData').textContent;
let carSalesData = [];
let partsUsed = [];
let modelsSeller = [];
let revenueDatas = [];
//parse Data
try{
     carSalesData = JSON.parse(carByYear);
}catch(error){
    console.log("faild to parse value of year due to null");
}

try{
    partsUsed = JSON.parse(parts);
}catch(error){
    console.log("faild to parse value of parts due to null");
}

try{
    modelsSeller = JSON.parse(models);
}catch(error){
    console.log("faild to parse value of models due to null");
}

try{
    revenueDatas = JSON.parse(revenues);
}catch(error){
    console.log("faild to parst value of revenue due to null");
}
//Show Data
console.log(carSalesData);
console.log(partsUsed);
console.log(modelsSeller);
console.log(revenueDatas);
//extract data

//Chart 1
const ctx = document.querySelector('.activity-chart');
const chart = createCarSalesChart(ctx, carSalesData);
function createCarSalesChart(ctx, carSalesData) {
    const yearLabels = carSalesData.map(item => item.year);
    const modelNames = [...new Set(carSalesData.flatMap(item => Object.keys(item.models)))];
    
    // Define colors once
    const colors = ['#1e293b', '#3b82f6', '#6366f1', '#8b5cf6', '#ec4899', '#f97316'];
    const hoverColors = ['#60a5fa', '#93c5fd', '#a5b4fc', '#c4b5fd', '#f9a8d4', '#fdba74'];

    const datasets = modelNames.map((modelName, index) => ({
        label: modelName,
        data: carSalesData.map(yearData => yearData.models[modelName] || 0),
        backgroundColor: colors[index % colors.length],
        borderWidth: 1,
        borderRadius: 6,
        hoverBackgroundColor: hoverColors[index % hoverColors.length]
    }));

    return new Chart(ctx, {
        type: 'bar',
        data: { labels: yearLabels, datasets },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            scales: {
                x: {
                    grid: { color: 'rgba(255, 255, 255, 0.1)' }
                },
                y: {
                    ticks: { beginAtZero: true }, // Ensure scale starts at 0
                    grid: { color: 'rgba(255, 255, 255, 0.1)' }
                }
            },
            plugins: {
                legend: { display: true, position: 'top' },
                tooltip: {
                    callbacks: {
                        label: (context) => `${context.dataset.label}: ${context.raw} cars`
                    }
                }
            },
            animation: { duration: 1000, easing: 'easeInOutQuad' }
        }
    });
}


//Chart 2
const nameOfParts = partsUsed.map(items => items.name);
const numberOfParts = partsUsed.map(items => items.number);
const ctx3 = document.querySelector('.best-seller');
let chart3 = new Chart(ctx3, {
    type: 'bar',
    data: {
        labels: nameOfParts,
        datasets: [{
            label: 'Number of selling',
            data: numberOfParts,
            backgroundColor: 'var(--color-primary)',
            borderWidth: 3,
            borderRadius: 6,
            hoverBackgroundColor: '#60a5fa'
        }]
    },
    options: {
        responsive: true,
        maintainAspectRatio: false,
        scales: {
            x: {
                border: {
                    display: true
                },
                grid: {
                    display: true,
                    color: 'rgba(255, 255, 255, 0.1)'
                }
            },
            y: {
                ticks: {
                    display: false
                }
            }
        },
        plugins: {
            legend: {
                display: false
            }
        },
        animation: {
            duration: 1000,
            easing: 'easeInOutQuad',
        }
    }
});
// chart 3
const modelLabels = modelsSeller.map(items => items.model);
const modelDatas = modelsSeller.map(items => items.number);
const ctx4 = document.querySelector('.best-sellers');
let chart4 = new Chart(ctx4, {
    type: 'bar',
    data: {
        labels: modelLabels,
        datasets: [{
            label: 'Number of selling',
            data: modelDatas,
            backgroundColor: 'var(--color-primary)',
            borderWidth: 3,
            borderRadius: 6,
            hoverBackgroundColor: '#60a5fa'
        }]
    },
    options: {
        responsive: true,
        maintainAspectRatio: false,
        scales: {
            x: {
                border: {
                    display: true
                },
                grid: {
                    display: true,
                    color: 'rgba(255, 255, 255, 0.1)'
                }
            },
            y: {
                ticks: {
                    display: false
                }
            }
        },
        plugins: {
            legend: {
                display: false
            }
        },
        animation: {
            duration: 1000,
            easing: 'easeInOutQuad',
        }
    }
});

//chart5
const revenueLabels = revenueDatas.map(items => items.year);
const dataLabor = revenueDatas.map(items => items.laborCost);
const dataPart = revenueDatas.map(items => items.partCost);
const ctx2 = document.querySelector('.prog-chart');
let linechart =  new Chart(ctx2, {
    type: 'line',
    data: {
        labels: revenueLabels,
        datasets: [{
            label: 'laborRevenue',
            data: dataLabor,
            borderColor: '#0891b2',
            tension: 0.4
        },
        {
            label: 'PartsRevenue',
            data: dataPart,
            borderColor: '#ca8a04',
            tension: 0.4
        }
        ]
    },
    options: {
        responsive: true,
        maintainAspectRatio: true,
        scales: {
            x: {
                grid: {
                    display: false,
                }
            },
            y: {
                ticks: {
                    display: false
                },
                border: {
                    display: false,
                    dash: [5, 5]
                }
            }
        },
        plugins: {
            legend: {
                display: false
            }
        },
        animation: {
            duration: 1000,
            easing: 'easeInOutQuad',
        }
    }
});
