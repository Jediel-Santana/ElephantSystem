var ElephantSystem = ElephantSystem || {};

ElephantSystem.TabelaItensCarrinho = (function (){

	function TabelaItensCarrinho(){
		this.selectTamanhoProduto = $('.js-select-tamanho-produto');
		this.numberQuantidadeProduto = $('.js-number-quantidade-produto');
		this.valorTotalProduto = $('.js-valor-total-item');
		this.btnAlterarQuantidade = $('.btn-produto-alterar-quantidade');
		this.btnRemoveItem = $('.js-remove-item');
		this.urlCarrinho = $('#urlCarrinho').val();
	}
	
	TabelaItensCarrinho.prototype.iniciar = function(){
		this.btnAlterarQuantidade.on('click', onNumberQuantidadeProdutoAlterado.bind(this));
		this.selectTamanhoProduto.on('change', onSelectTamanhoProdutoAlterado.bind(this));
		this.btnRemoveItem.on('click', onBotaoRemoveItemClicado.bind(this));
	}
	
	function onBotaoRemoveItemClicado(evento){
		evento.preventDefault();
		
		var botao = $(evento.currentTarget);
		var idProduto = botao.data('id-produto');
		var idTamanho = botao.data('id-tamanho');
		
		var resposta = $.ajax({
			url: this.urlCarrinho + '/item/' + idProduto,
			type: 'DELETE',
			data: {tamanho: idTamanho}
			});
		resposta.done(function(){
			var item = botao.closest('.js-item-carrinho'); 
			$(item).remove();
			ElephantSystem.RenderizarQuantidadeItensCarrinho();
		}.bind(this));	
		
			
	}
	
	function onSelectTamanhoProdutoAlterado(evento){
		var checkbox = $(evento.target);
		var idProduto = checkbox.data('id-produto');
		var idTamanhoAtual = checkbox.data('id-tamanho-atual');
		var idTamanhoNovo = checkbox.val();
		
		
		$.ajax({
			url: this.urlCarrinho + '/item/' + idProduto + '/tamanho',
			method: 'PUT',
			data: {
				idTamanhoAtual: idTamanhoAtual,
				idTamanhoNovo: idTamanhoNovo
			}
		});
	}
	
	function onNumberQuantidadeProdutoAlterado(evento){
		evento.preventDefault();
		
		var trParent = $(evento.target).closest('.js-item-carrinho');
		var idTamanhoProduto =  trParent.find('.js-select-tamanho-produto').data('id-tamanho-atual');
		var idProduto =  trParent.find('.js-select-tamanho-produto').data('id-produto');
		var quantidade =  trParent.find('.js-number-quantidade-produto').val();
		quantidade = quantidade >= 1 ? quantidade : 1;
		var tagValorTotal = trParent.find('.js-valor-total-item')
			
		var resposta = $.ajax({
			url: this.urlCarrinho + '/item/' + idProduto + '/quantidade',
			type: 'PUT',
			dataType: 'json',
			
			data: {
				idTamanho: idTamanhoProduto,
				quantidade: quantidade,
			},
			success: onQuantidadeAlterada.bind(this, tagValorTotal)
		});
	}
	
	function onQuantidadeAlterada(tagValorTotal, retorno){
		alterarValorTotal.call(this, tagValorTotal, retorno.valorTotal);		
	}
	
	function alterarValorTotal(tagValorTotal, valor){
		tagValorTotal.text("R$ " + ElephantSystem.formatarMoeda(valor));
	}
	
	return TabelaItensCarrinho;
}());


$(function(){
	
	var tabelaItensCarrinho = new ElephantSystem.TabelaItensCarrinho();
	tabelaItensCarrinho.iniciar();
	
});


