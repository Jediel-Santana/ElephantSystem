
var ElephantSystem = ElephantSystem || {};



ElephantSystem.GraficoVendasPorMes = (function() {


	function GraficoVendasPorMes() {
		this.ctx = document.getElementById('vendaNoMes').getContext('2d');
	}

	GraficoVendasPorMes.prototype.iniciar = function() {
		$.get('/vendas/totalNoMes', onVendasRetornadas.bind(this))
	}

	function onVendasRetornadas(vendasMes) {

		var meses = [];
		var valores = [];
		vendasMes.forEach(function(obj) {
			meses.unshift(obj.mes);
			valores.unshift(obj.total);
		});

		var graficoVendasPorMes = new Chart(this.ctx, {
			type: 'line',
			data: {
				labels: meses,
				datasets: [{
					label: 'Vendas no mês',
					data: valores,
					backgroundColor: '#fff',
					borderColor: "transparent",
					pointRadius: "0",
					borderWidth: 3
				}]
			},
			options: {
				maintainAspectRatio: false,
				legend: {
					display: false,
					labels: {
						fontColor: '#ddd',
						boxWidth: 40
					}
				},
				tooltips: {
					displayColors: false
				},
				scales: {
					xAxes: [{
						ticks: {
							beginAtZero: true,
							fontColor: '#ddd'
						},
						gridLines: {
							display: true,
							color: "rgba(221, 221, 221, 0.08)"
						},
					}],
					yAxes: [{
						ticks: {
							beginAtZero: true,
							fontColor: '#ddd'
						},
						gridLines: {
							display: true,
							color: "rgba(221, 221, 221, 0.08)"
						},
					}]
				}

			}
		});
	}

	return GraficoVendasPorMes;

}());

ElephantSystem.GraficoVendasPorCategoria = (function() {


	function GraficoVendasPorCategoria() {
		this.ctx = document.getElementById("vendasPorCategoria").getContext('2d');
	}

	GraficoVendasPorCategoria.prototype.iniciar = function() {
		$.get('/vendas/totalPorCategoria', onVendasRetornadas.bind(this))
	}

	function onVendasRetornadas(vendasCategoria) {

		var categorias = [];
		var valores = [];

		vendasCategoria.forEach(function(obj) {
			if (obj.total > 0) {
				categorias.unshift(obj.categoria);
				valores.unshift(obj.total);
			}
		});

		var graficoVendasPorCategoria = new Chart(this.ctx, {
			type: 'doughnut',
			data: {
				labels: categorias,
				datasets: [{
					backgroundColor: [
						"#ffffff",
						"rgba(255, 255, 255, 0.70)",
						"rgba(255, 255, 255, 0.50)",
						"rgba(255, 255, 255, 0.20)"
					],
					data: valores,
					borderWidth: [0, 0, 0, 0]
				}]
			},
			options: {
				maintainAspectRatio: false,
				legend: {
					position: "bottom",
					display: false,
					labels: {
						fontColor: '#ddd',
						boxWidth: 15
					}
				}
				,
				tooltips: {
					displayColors: false
				}
			}
		});
	}

	return GraficoVendasPorCategoria;

}());



$(function() {

	var graficosVendasPorMes = new ElephantSystem.GraficoVendasPorMes();
	graficosVendasPorMes.iniciar();

	var graficosVendasPorCategoria = new ElephantSystem.GraficoVendasPorCategoria();
	graficosVendasPorCategoria.iniciar();

});




