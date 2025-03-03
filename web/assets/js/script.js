/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


const darkMode = document.querySelector('.dark-mode');
const darkIcon = darkMode.querySelector('span:nth-child(2)');
const lightIcon = darkMode.querySelector('span:nth-child(1)');

// Remove any default active states first
document.addEventListener('DOMContentLoaded', () => {
    // Get the saved preference
    const isDarkMode = localStorage.getItem('darkMode') === 'true';
    
    // Remove any default active states
    darkIcon.classList.remove('active');
    lightIcon.classList.remove('active');
    
    if (isDarkMode) {
        // Apply dark mode
        document.body.classList.add('dark-mode-variables');
        darkIcon.classList.add('active');
    } else {
        // Apply light mode
        document.body.classList.remove('dark-mode-variables');
        lightIcon.classList.add('active');
    }
});

darkMode.addEventListener('click', () => {
    document.body.classList.toggle('dark-mode-variables');
    darkIcon.classList.toggle('active');
    lightIcon.classList.toggle('active');
    
    const isDarkMode = document.body.classList.contains('dark-mode-variables');
    localStorage.setItem('darkMode', isDarkMode);
});


