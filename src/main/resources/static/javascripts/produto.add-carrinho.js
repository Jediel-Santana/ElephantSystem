
ElephantSystem.AdicionarAoCarrinho = (function() {

	function AdicionarAoCarrinho() {
		this.botaoAdicionar = $('.js-btn-add-cart');
		this.formulario = $('#form-dados-produto');
		this.quantidade = $('.js-quantidade-produto');
		this.selectTamanho = $('.js-tamanho-produto');
		
		this.idProduto = $('#idProduto');
		this.emitter = $({});
		this.on = this.emitter.on.bind(this.emitter);
	}

	AdicionarAoCarrinho.prototype.iniciar = function() {
		this.botaoAdicionar.on('click', onBotaoAdicionarCarrinhoClicado.bind(this));
	}
	
	function configuracoesToast(){
		toastr.options = {
			"closeButton": false,
			"debug": false,
			"newestOnTop": true,
			"progressBar": true,
			"positionClass": "toast-bottom-right",
			"preventDuplicates": false,
			"onclick": null,
			"showDuration": "1",
			"hideDuration": "1000",
			"timeOut": "2000",
			"extendedTimeOut": "1000",
			"showEasing": "swing",
			"hideEasing": "linear",
			"showMethod": "fadeIn",
			"hideMethod": "fadeOut"
		}
	}
		
	function onBotaoAdicionarCarrinhoClicado(event) {
		event.preventDefault();
		var url = this.formulario.attr('action');
		var botao = $(event.target);
		var idTamanho = this.selectTamanho.val();
		configuracoesToast();
		if(idTamanho != 0){
			$.ajax({
				url: '/api/public/carrinho/item/' + this.idProduto.val(),
				method: 'POST',
				data: {
					quantidade: this.quantidade.val(),
					idTamanho: idTamanho
				},
				success: onProdutoAdicionadoSucesso.bind(this),
				error: onErrorAoAdicionarItem.bind(this)
			});
			
		} else {
			onErrorAoAdicionarItem.call(this, null, "Selecione um tamanho");
		}
	}

	function onProdutoAdicionadoSucesso() {
		toastr.success('Adicionado ao carrinho com sucesso!');
		this.selectTamanho.val('0');
		this.quantidade.val('1');
		onQuantidadeItensAlterada.call(this);
	}
	
	function onErrorAoAdicionarItem(e, msg){
		
		var mensagem;
		if(e){
			mensagem = e.requiredText;
		} else {
			mensagem = msg;
		}
		
		toastr.error(mensagem);
	}
	
	function onQuantidadeItensAlterada(){
		ElephantSystem.RenderizarQuantidadeItensCarrinho();
	}

	return AdicionarAoCarrinho;

}());


$(function() {

	var adicionarAoCarrinho = new ElephantSystem.AdicionarAoCarrinho();
	adicionarAoCarrinho.iniciar();
	
});
