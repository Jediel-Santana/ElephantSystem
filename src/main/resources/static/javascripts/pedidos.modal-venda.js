var ElephantSystem = ElephantSystem || {}


ElephantSystem.ModalVenda = (function() {

	function ModalVenda() {
		this.btnAbrirModal = $('.js-btn-modal-venda');
		this.url = $('#url').val();
		this.containerDetalheVenda = $('.container-modal-detalhe-venda');
		this.btnSubmit = $('.js-btn-form-usuario');
	}

	ModalVenda.prototype.iniciar = function() {
		this.btnAbrirModal.on('click', onBotaoAbrirModalClicado.bind(this));
		this.btnSubmit.on('click', onBotaoSubmitClicado.bind(this));
	}

	function onBotaoAbrirModalClicado(evento) {
		var botao = $(evento.currentTarget);
		var idVenda = botao.data('id-venda');
		
		
		$.get(this.url + '/' + idVenda, onVendaRetornada.bind(this));
		$('.modal-backdrop').show();
		var detalheVenda = $('#modalDetalheVenda');
		detalheVenda.modal().show()
		modal.overlay.fadeIn('slow');

	}

	function onVendaRetornada(html) {
		this.containerDetalheVenda.html(html);
	}
	
	function onBotaoSubmitClicado(evento){
		evento.preventDefault();
		var botao = $(evento);
		var url = botao.data('url');
		var data = $('#form-venda').serialize();
				
		$.ajax({
			url: url,
			type: 'POST',
			data: data,
			success: onVendaRetornada.bind(this)
		});
			
	}
	
	
	return ModalVenda;

}());



$(function() {

	var modalVenda = new ElephantSystem.ModalVenda();
	modalVenda.iniciar();

});