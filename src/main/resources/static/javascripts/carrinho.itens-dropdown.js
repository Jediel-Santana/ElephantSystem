var ElephantSystem = ElephantSystem || {};


ElephantSystem.itensCarrinhoDropdown = (function(){
	
	function itensCarrinhoDropdown(){
		this.botaoCarrinho = $('.js-show-header-dropdown');
		this.containerCarrinhoDropdown = $('.container-carrinho-dropdown');
		var html = $('#carrinho-dropdown').html();
		this.template = Handlebars.compile(html);
	}
	
	itensCarrinhoDropdown.prototype.iniciar = function (){
		onBotaoCarrinhoClicado.call(this);
		this.botaoCarrinho.on('click', onBotaoCarrinhoClicado.bind(this));
	}
	
	function onBotaoCarrinhoClicado(){
		
		var resposta = $.ajax({
			url: '/api/public/carrinho/itens',
			method: 'GET',
			contentType: 'application/json',
			success: onItensRecuperados.bind(this)
		});
	}
	
	function onItensRecuperados(produtos){
		var resposta = $.ajax({
			url: '/api/public/carrinho/itens/total',
			method: 'GET',
			success: onTotalRecuperado.bind(this, produtos)
		});
		
		
	}
	
	function onTotalRecuperado(produtos, total){
		produtos.total = ElephantSystem.formatarMoeda(total);
		produtos.urlCarrinho = this.containerCarrinhoDropdown.data('url-carrinho');
		var urlProduto = this.containerCarrinhoDropdown.data('url-produto');
		produtos.forEach(function(produto){
			produto.urlDoProduto =  urlProduto + '/' + produto.idProduto;
			produto.precoUnitario = ElephantSystem.formatarMoeda(produto.precoUnitario);
		});
		
		var html = this.template(produtos);		
		this.containerCarrinhoDropdown.html(html);
		
		$('.js-remove-item-cart').on('click', onRemoveItem.bind(this));
		
	}
	
	function onRemoveItem(evento){
		var botao = $(evento.currentTarget);
		var idProduto = botao.data('id-produto');
		var idTamanho = botao.data('id-tamanho');
		var url = $('#urlCarrinho').val();
		
		$.ajax({
			url: url + '/item/' + idProduto,
			type: 'DELETE',
			data: {
				tamanho: idTamanho
			},
			success: function(){
				var item = botao.closest('.header-cart-item');
				$(item).remove();
				ElephantSystem.RenderizarQuantidadeItensCarrinho();
			}
		});
				
	}
	
	
	return itensCarrinhoDropdown;
	
}());


$(function(){
	var itensCarrinhoDropdown = new ElephantSystem.itensCarrinhoDropdown(); 
	itensCarrinhoDropdown.iniciar();

});	

