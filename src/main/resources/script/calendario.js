document.addEventListener("DOMContentLoaded", () => {
  const calendarDaysGrid = document.getElementById("calendar-days-grid");
  const currentMonthYear = document.getElementById("current-month-year");
  const prevMonthBtn = document.getElementById("prev-month");
  const nextMonthBtn = document.getElementById("next-month");

  let currentDate = new Date();

  function renderCalendar() {
    const year = currentDate.getFullYear();
    const month = currentDate.getMonth();

    currentMonthYear.textContent = currentDate.toLocaleDateString("pt-BR", {
      month: "long",
      year: "numeric",
    });

    const firstDay = new Date(year, month, 1);
    const lastDay = new Date(year, month + 1, 0);
    const firstWeekDay = (firstDay.getDay() + 6) % 7; // ajusta para segunda ser início
    const totalDays = lastDay.getDate();

    calendarDaysGrid.innerHTML = "";

    // Dias vazios antes do 1º dia
    for (let i = 0; i < firstWeekDay; i++) {
      const emptyDiv = document.createElement("div");
      emptyDiv.classList.add("calendar-day", "empty");
      calendarDaysGrid.appendChild(emptyDiv);
    }

    // Dias do mês
    for (let day = 1; day <= totalDays; day++) {
      const dayDiv = document.createElement("div");
      dayDiv.classList.add("calendar-day");
      dayDiv.textContent = day;

      const today = new Date();
      if (
        day === today.getDate() &&
        month === today.getMonth() &&
        year === today.getFullYear()
      ) {
        dayDiv.classList.add("today");
      }

      calendarDaysGrid.appendChild(dayDiv);
    }
  }

  prevMonthBtn.addEventListener("click", () => {
    currentDate.setMonth(currentDate.getMonth() - 1);
    renderCalendar();
  });

  nextMonthBtn.addEventListener("click", () => {
    currentDate.setMonth(currentDate.getMonth() + 1);
    renderCalendar();
  });

  renderCalendar();
});
const API_URL = "http://localhost:8080/api/agendamentos"; // ajuste se necessário

document.addEventListener("DOMContentLoaded", () => {
  const calendarDaysGrid = document.getElementById("calendar-days-grid");
  const currentMonthYear = document.getElementById("current-month-year");
  const agendaContainer = document.getElementById("agenda-container");
  const agendaTitle = document.getElementById("agenda-title");
  const agendaList = document.getElementById("agenda-list");
  const addTaskInput = document.getElementById("add-task-input");
  const addTaskButton = document.getElementById("add-task-button");

  let currentDate = new Date();
  let selectedDate = null;
  let tasks = JSON.parse(localStorage.getItem("tasks")) || {};

  function renderCalendar() {
    const year = currentDate.getFullYear();
    const month = currentDate.getMonth();

    currentMonthYear.textContent = `${currentDate.toLocaleString("pt-BR", {
      month: "long",
    })} de ${year}`;

    const firstDay = new Date(year, month, 1);
    const lastDay = new Date(year, month + 1, 0);
    const startDay = firstDay.getDay();
    const totalDays = lastDay.getDate();

    calendarDaysGrid.innerHTML = "";

    // Dias anteriores
    const prevLastDay = new Date(year, month, 0).getDate();
    for (let i = startDay - 1; i >= 0; i--) {
      const div = document.createElement("div");
      div.className = "day other-month";
      div.textContent = prevLastDay - i;
      calendarDaysGrid.appendChild(div);
    }

    // Dias atuais
    for (let day = 1; day <= totalDays; day++) {
      const div = document.createElement("div");
      div.className = "day";
      div.textContent = day;

      const dateKey = `${year}-${String(month + 1).padStart(2, "0")}-${String(
        day
      ).padStart(2, "0")}`;

      if (tasks[dateKey] && tasks[dateKey].length > 0) {
        const dot = document.createElement("span");
        dot.className = "task-dot";
        div.appendChild(dot);
      }

      div.addEventListener("click", () => {
        document.querySelectorAll(".day").forEach((d) => d.classList.remove("selected"));
        div.classList.add("selected");
        selectedDate = dateKey;
        renderAgenda();
      });

      calendarDaysGrid.appendChild(div);
    }
  }

  function renderAgenda() {
    if (!selectedDate) return;
    agendaTitle.textContent = `Tarefas de ${new Date(selectedDate).toLocaleDateString("pt-BR")}`;
    agendaList.innerHTML = "";

    const dayTasks = tasks[selectedDate] || [];

    if (dayTasks.length === 0) {
      agendaList.innerHTML = "<p class='no-tasks'>Nenhuma tarefa ainda.</p>";
      return;
    }

    dayTasks.forEach((task, index) => {
      const li = document.createElement("li");
      li.innerHTML = `
        <span>${task}</span>
        <button class="delete-task" data-index="${index}">🗑️</button>
      `;
      agendaList.appendChild(li);
    });

    document.querySelectorAll(".delete-task").forEach((btn) => {
      btn.addEventListener("click", (e) => {
        const idx = e.target.getAttribute("data-index");
        tasks[selectedDate].splice(idx, 1);
        saveTasks();
        renderAgenda();
        renderCalendar();
      });
    });
  }

  function saveTasks() {
    localStorage.setItem("tasks", JSON.stringify(tasks));
  }

  addTaskButton.addEventListener("click", () => {
    if (!selectedDate) return alert("Selecione um dia no calendário primeiro!");
    const newTask = addTaskInput.value.trim();
    if (newTask === "") return;

    tasks[selectedDate] = tasks[selectedDate] || [];
    tasks[selectedDate].push(newTask);
    saveTasks();

    addTaskInput.value = "";
    renderAgenda();
    renderCalendar();
  });

  document.getElementById("prev-month").addEventListener("click", () => {
    currentDate.setMonth(currentDate.getMonth() - 1);
    renderCalendar();
  });

  document.getElementById("next-month").addEventListener("click", () => {
    currentDate.setMonth(currentDate.getMonth() + 1);
    renderCalendar();
  });

  renderCalendar();
});
