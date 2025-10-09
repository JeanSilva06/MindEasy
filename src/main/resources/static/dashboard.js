// Dados (poderia vir do backend)
const meses = ['Jan','Fev','Mar','Abr','Mai','Jun','Jul','Ago','Set','Out','Nov','Dez'];
const valores = [30, 35, 42, 45, 25, 50, 41, 38, 44, 46, 39, 48]; // total de atendimentos

// Dimensões do "mundo" do gráfico (casam com o viewBox)
const W = 900, H = 320;
const m = {top:20, right:30, bottom:40, left:50}; // margens internas
const plotW = W - m.left - m.right;
const plotH = H - m.top - m.bottom;

const max = Math.max(...valores);
// arredonda topo para uma grade bonita (ex.: múltiplos de 10)
const niceMax = Math.ceil(max / 10) * 10;

// escalas (valor -> pixel)
const xStep = plotW / valores.length;
const barWidth = xStep * 0.6;
const x = i => m.left + i * xStep + (xStep - barWidth)/2;
const y = v => m.top + plotH - (v / niceMax) * plotH;

const plot = document.getElementById('plot');

// grid horizontal + ticks
const linhas = 5; // 5 linhas guias
for (let i = 0; i <= linhas; i++){
    const val = (niceMax/linhas) * i;
    const yPos = m.top + plotH - (val / niceMax) * plotH;

    // linha guia
    const line = document.createElementNS('http://www.w3.org/2000/svg','line');
    line.setAttribute('x1', m.left);
    line.setAttribute('x2', m.left + plotW);
    line.setAttribute('y1', yPos);
    line.setAttribute('y2', yPos);
    line.setAttribute('class','axis');
    plot.appendChild(line);

    // rótulo do eixo Y
    const t = document.createElementNS('http://www.w3.org/2000/svg','text');
    t.setAttribute('x', m.left - 8);
    t.setAttribute('y', yPos + 4);
    t.setAttribute('class','tickLabel');
    t.textContent = val;
    plot.appendChild(t);
}

// eixo Y (linha vertical)
const yAxis = document.createElementNS('http://www.w3.org/2000/svg','line');
yAxis.setAttribute('x1', m.left);
yAxis.setAttribute('x2', m.left);
yAxis.setAttribute('y1', m.top);
yAxis.setAttribute('y2', m.top + plotH);
yAxis.setAttribute('class','axis');
plot.appendChild(yAxis);

// barras + rótulos X
valores.forEach((v, i) => {
    const rect = document.createElementNS('http://www.w3.org/2000/svg','rect');
    rect.setAttribute('x', x(i));
    rect.setAttribute('y', y(v));
    rect.setAttribute('width', barWidth);
    rect.setAttribute('height', m.top + plotH - y(v));
    rect.setAttribute('rx', 8);
    rect.setAttribute('class','bar');
    rect.setAttribute('aria-label', `${meses[i]}: ${v}`);
    plot.appendChild(rect);

    const lab = document.createElementNS('http://www.w3.org/2000/svg','text');
    lab.setAttribute('x', m.left + i * xStep + xStep/2);
    lab.setAttribute('y', m.top + plotH + 18);
    lab.setAttribute('class','label');
    lab.textContent = meses[i];
    plot.appendChild(lab);
});