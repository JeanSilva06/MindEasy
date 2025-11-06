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
const API_URL = "http://localhost:8080/api/agendamentos"; // ajuste se a porta for diferente

document.addEventListener("DOMContentLoaded", () => {
  const calendarDaysGrid = document.getElementById("calendar-days-grid");
  const currentMonthYear = document.getElementById("current-month-year");

  let currentDate = new Date();

  // Renderiza o calendário e busca agendamentos
  function renderCalendar() {
    const year = currentDate.getFullYear();
    const month = currentDate.getMonth();

    const firstDayOfMonth = new Date(year, month, 1);
    const lastDayOfMonth = new Date(year, month + 1, 0);
    const firstWeekDay = (firstDayOfMonth.getDay() + 6) % 7; 

    const daysInMonth = lastDayOfMonth.getDate();
    currentMonthYear.textContent = `${firstDayOfMonth.toLocaleString("pt-BR", { month: "long" })} ${year}`;

    calendarDaysGrid.innerHTML = "";

    // Preenche dias vazios antes do primeiro
    for (let i = 0; i < firstWeekDay; i++) {
      const empty = document.createElement("div");
      empty.classList.add("day", "empty");
      calendarDaysGrid.appendChild(empty);
    }

    // Preenche dias do mês
    for (let day = 1; day <= daysInMonth; day++) {
      const dayCell = document.createElement("div");
      dayCell.classList.add("day");
      dayCell.dataset.date = `${year}-${String(month + 1).padStart(2, "0")}-${String(day).padStart(2, "0")}`;
      dayCell.innerHTML = `<span class="day-number">${day}</span>`;
      calendarDaysGrid.appendChild(dayCell);
    }

    carregarAgendamentos(year, month + 1);
  }

  // Busca os agendamentos do backend e adiciona os cards
  async function carregarAgendamentos(ano, mes) {
    try {
      const response = await fetch(API_URL);
      if (!response.ok) throw new Error("Erro ao buscar agendamentos");

      const agendamentos = await response.json();
      agendamentos.forEach(ag => {
        const data = new Date(ag.data);
        if (data.getFullYear() === ano && data.getMonth() + 1 === mes) {
          const dia = String(data.getDate()).padStart(2, "0");
          const dataStr = `${ano}-${String(mes).padStart(2, "0")}-${dia}`;
          const diaDiv = document.querySelector(`.day[data-date="${dataStr}"]`);
          if (diaDiv) {
            const card = document.createElement("div");
            card.classList.add("agendamento-card");
            card.innerHTML = `
              <strong>${ag.titulo}</strong><br>
              <small>${data.toLocaleTimeString([], { hour: "2-digit", minute: "2-digit" })}</small>
            `;
            diaDiv.appendChild(card);
          }
        }
      });
    } catch (err) {
      console.error("Erro ao carregar agendamentos:", err);
    }
  }

  // Navegação
  document.getElementById("prev-month").addEventListener("click", () => {
    currentDate.setMonth(currentDate.getMonth() - 1);
    renderCalendar();
  });

  document.getElementById("next-month").addEventListener("click", () => {
    currentDate.setMonth(currentDate.getMonth() + 1);
    renderCalendar();
  });

  document.getElementById("today-btn").addEventListener("click", () => {
    currentDate = new Date();
    renderCalendar();
  });

  renderCalendar();
});
