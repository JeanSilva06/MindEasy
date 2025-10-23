document.addEventListener('DOMContentLoaded', function() {
    const calendarDaysGrid = document.getElementById('calendar-days-grid');
    const currentMonthYearElement = document.getElementById('current-month-year');
    
    const now = new Date(2025, 0, 1); 
    let year = now.getFullYear();
    let month = now.getMonth();

    const monthNames = [
        "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
        "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"
    ];

    function renderCalendar() {
        calendarDaysGrid.innerHTML = '';
        currentMonthYearElement.textContent = `${monthNames[month]} ${year}`;

        const firstDayOfMonth = new Date(year, month, 1).getDay(); 
        const daysInMonth = new Date(year, month + 1, 0).getDate();
        let dayOffset = (firstDayOfMonth === 0) ? 6 : firstDayOfMonth - 1;

        const daysInPrevMonth = new Date(year, month, 0).getDate();
        for (let i = dayOffset; i > 0; i--) {
            const dayElement = document.createElement('div');
            dayElement.classList.add('day', 'other-month');
            dayElement.innerHTML = `<span class="day-number">${daysInPrevMonth - i + 1}</span>`;
            calendarDaysGrid.appendChild(dayElement);
        }

        const today = new Date();
        for (let i = 1; i <= daysInMonth; i++) {
            const dayElement = document.createElement('div');
            dayElement.classList.add('day');
            if (i === today.getDate() && month === today.getMonth() && year === today.getFullYear()) {
                dayElement.classList.add('today');
            }
            dayElement.innerHTML = `<span class="day-number">${i}</span>`;
            calendarDaysGrid.appendChild(dayElement);
        }

        const totalCells = dayOffset + daysInMonth;
        const remainingCells = (totalCells % 7 === 0) ? 0 : 7 - (totalCells % 7);
        for (let i = 1; i <= remainingCells; i++) {
            const dayElement = document.createElement('div');
            dayElement.classList.add('day', 'other-month');
            dayElement.innerHTML = `<span class="day-number">${i}</span>`;
            calendarDaysGrid.appendChild(dayElement);
        }
    }

    renderCalendar();
    if (typeof feather !== 'undefined') {
        feather.replace();
    }
});

// sidebar.js
document.addEventListener("DOMContentLoaded", () => {
    const sidebar = document.querySelector('.sidebar');
    const handle = document.getElementById('sidebar-handle');

    if (handle) {
        handle.addEventListener('mouseenter', () => {
            sidebar.classList.add('shown');
        });
    }

    sidebar.addEventListener('mouseleave', () => {
        sidebar.classList.remove('shown');
    });
});
