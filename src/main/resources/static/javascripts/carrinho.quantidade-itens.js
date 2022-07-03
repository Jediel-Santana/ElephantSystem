
ElephantSystem.RenderizarQuantidadeItensCarrinho = (function renderizarQuantidadeItensCarrinho(){
		var quantidadeItensCarrinho = $('.js-qtd-itens-carrinho');
		$.ajax({
			url: '/api/public/carrinho/itens/quantidadeTotal',
			type: 'GET',
			success: function(quantidade){
				quantidadeItensCarrinho.text(quantidade);
			}.bind(this)
		});
	});
		

$(function(){
	ElephantSystem.RenderizarQuantidadeItensCarrinho();
});
