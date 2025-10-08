document.addEventListener('DOMContentLoaded', function() {
    const calendarDaysGrid = document.getElementById('calendar-days-grid');
    const currentMonthYearElement = document.getElementById('current-month-year');
    
    // Usando uma data fixa para corresponder ao design (Janeiro de 2025)
    // Para usar a data atual, seria: const now = new Date();
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

        const firstDayOfMonth = new Date(year, month, 1).getDay(); // 0 (Dom) - 6 (Sáb)
        const daysInMonth = new Date(year, month + 1, 0).getDate();
        
        // Ajuste para a semana começando na Segunda-feira
        // O calendário do design começa em Segunda. getDay() começa em Domingo.
        // Se Domingo (0), queremos 6. Se Segunda (1), queremos 0.
        let dayOffset = (firstDayOfMonth === 0) ? 6 : firstDayOfMonth - 1;

        // Preenche os dias do mês anterior
        const daysInPrevMonth = new Date(year, month, 0).getDate();
        for (let i = dayOffset; i > 0; i--) {
            const dayElement = document.createElement('div');
            dayElement.classList.add('day', 'other-month');
            dayElement.innerHTML = `<span class="day-number">${daysInPrevMonth - i + 1}</span>`;
            calendarDaysGrid.appendChild(dayElement);
        }

        // Preenche os dias do mês atual
        const today = new Date(); // Para destacar o dia atual real
        for (let i = 1; i <= daysInMonth; i++) {
            const dayElement = document.createElement('div');
            dayElement.classList.add('day');
            
            // Destaca o dia de hoje (apenas se o calendário for do mês/ano atual)
            if (i === today.getDate() && month === today.getMonth() && year === today.getFullYear()) {
                dayElement.classList.add('today');
            }

            dayElement.innerHTML = `<span class="day-number">${i}</span>`;
            calendarDaysGrid.appendChild(dayElement);
        }
        
        // Preenche os dias do próximo mês para completar a grade
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
    
    // Adiciona o feather icons (já chamado no HTML, mas é bom ter aqui também)
    if (typeof feather !== 'undefined') {
        feather.replace();
    }
});