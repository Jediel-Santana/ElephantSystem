var ElephantSystem = ElephantSystem || {};


ElephantSystem.ModalUsuario = (function(){
	
	function ModalUsuario(){
		this.btn = $('.js-btn-edicao-usuario');
		this.containerModalUsuario = $('.container-modal-usuario');
		this.botaoForm = $('.js-btn-form-usuario');
		this.novoUsuario = $('.btn-novo-usuario');
	}
	
	ModalUsuario.prototype.iniciar = function(){
		this.btn.on('click', onBotaoEdicaoUsuarioClicado.bind(this));
		this.botaoForm.on('click', onBotaoSubmitClicado.bind(this));
		this.novoUsuario.on('click', onBotaoNovoUsuarioClicado.bind(this));
	}
	
	function onBotaoNovoUsuarioClicado(evento){
		evento.preventDefault();
		var botao = $(evento.currentTarget);
		var url = botao.data('url');
		
		$.ajax({
			url: url,
			type: 'GET',
			success: onUsuarioBuscado.bind(this)
			
		});
		
		abrirModal.call(this);
		
	}
		
	function onBotaoEdicaoUsuarioClicado(evento){
		var botao = $(evento.currentTarget);
		var url = botao.data('url');
		
		$.ajax({
			url: url,
			type: 'GET',
			success: onUsuarioBuscado.bind(this)
			
		});
		
		abrirModal.call(this);
		
		//detalheVenda.modal().show()
		
	}
	
	function abrirModal(){
		$('.modal-backdrop').show();
		$('#modalUsuario').modal().show('linear');
	}
	
	function onUsuarioBuscado(html){
		this.containerModalUsuario.html(html);
	}
	
	function onBotaoSubmitClicado(evento){
		evento.preventDefault();
		var botao = $(evento.currentTarget);
		var url = botao.data('url');
		var data = $('#form-venda').serialize();
		
		$('.alert-success').remove();
		$.ajax({
			url: url,
			type: 'POST',
			success: onUsuarioBuscado.bind(this), 
			data: data
		});
					
	}
	
	return ModalUsuario;
}());



$(function(){
	var modalUsuario = new ElephantSystem.ModalUsuario();
	modalUsuario.iniciar();
	
});