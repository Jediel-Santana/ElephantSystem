var ElephantSystem = ElephantSystem || {};



ElephantSystem.CadastroTamanhoProduto = (function() {

	function CadastroTamanhoProduto() {
		this.form = $('.js-formulario-produto');
		this.url = $('#urlProdutos').val();
		this.selectTamanhos = $('.js-tamanhos');
		this.botaoNovoTamanho = $('.js-novo-tamanho');
		this.containerTamanhosProduto = $('.container-tamanhos-produto');
		this.linhaTamanhoProduto = $('.js-tamanho-produto');
		var tamanhoProduto = $('#tamanho-produto').html();
		this.template = Handlebars.compile(tamanhoProduto);
		this.tamanhos;
		
		this.emFalta = $('.js-produto-em-falta');
		this.quantidade = $('.js-quantidade-tamanho');
	}

	CadastroTamanhoProduto.prototype.iniciar = function() {
		this.botaoNovoTamanho.on('click', onBotaoNovoTamanhoClicado.bind(this));
		this.selectTamanhos.on('change', onChangeSelectTamanho.bind(this));
		this.emFalta.on('change', onCheckboxTamanhoEmFaltaSelecionado.bind(this));
		this.quantidade.on('change', onQuantidadeAlterada.bind(this));
	}
	
	function onCheckboxTamanhoEmFaltaSelecionado(evento){
		var checkbox = $(evento.currentTarget);
		var tamanhoProduto = checkbox.closest('.js-tamanho-produto');
		var quantidade = $(tamanhoProduto).find('.js-quantidade-tamanho');
		
		if(checkbox.is(":checked")){
			$(quantidade).val('0');	
			$(quantidade).attr('disabled', true);	
		} else {
			$(quantidade).val('1');
			$(quantidade).attr('disabled', false);	
		}
	}
	
	function onQuantidadeAlterada(evento){
		evento.preventDefault();
		var input = $(evento.currentTarget);
		console.log(input.val())
		if(input.val() == 0){
			var tamanhoProduto = input.closest('.js-tamanho-produto');
			var check = $(tamanhoProduto).find('.js-produto-em-falta');
			$(check).prop('checked', true);
			input.attr('disabled', true);
		}
	}
	
	function onChangeSelectTamanho(evento) {
		var arrayIdsTamanho = $(evento.currentTarget).val();
		console.log(arrayIdsTamanho);
		var data = this.form.serializeArray();
		data.push({name: 'idsTamanhosUsados', value: arrayIdsTamanho});
		data.push({name: 'alterarTamanhos', value: ''});
		if(arrayIdsTamanho)
			$.post(this.url + '/tamanhos', $.param(data), onTamanhoAlterado.bind(this));
	}
	
	function onTamanhoAlterado(html){
		this.containerTamanhosProduto.html(html);
	}
	
	function configuracoesToast() {

	}

	function onBotaoNovoTamanhoClicado(evento) {
		buscarTamanhosERenderizarTamanho.call(this);
	}

	function buscarTamanhosERenderizarTamanho() {
		this.tamanhosProduto = $('.js-select-tamanho-produto');
		verificaListaTamanhos.call(this);
		
		var idsProdutos = $.map(this.tamanhosProduto, (select) => $(select).val());
		var ultimoSelect = this.tamanhosProduto.get(-1);
		var options = $(ultimoSelect).find('option');
		var valorSelecionadoAgora = $(ultimoSelect).val();
		
		if (valorSelecionadoAgora == 0) {
			$(ultimoSelect).closest('.js-tamanho-produto').addClass('error-group');
			return;
		} else {
			this.tamanhosProduto.attr('readonly', 'true');
			this.tamanhosProduto.attr('tabindex', '-1');
			$(ultimoSelect).closest('.js-tamanho-produto').removeClass('error-group');
			$.ajax({
				url: '/tamanhos',
				type: 'GET',
				success: onTamanhosRetornados.bind(this, idsProdutos, valorSelecionadoAgora)
			});
		}

	}
	
	function verificaListaTamanhos(){
		if(this.tamanhosProduto.length == 5){
			var tamanhos = $('.js-select-tamanho-produto'); 
			tamanhos.attr('readonly', 'true');
			tamanhos.attr('tabindex', '-1');
			this.botaoNovoTamanho.prop('disabled', true);
			return;
		}
	}

	function onTamanhosRetornados(idsTamanhosSelectAnterior, valorSelecionadoAgora, data) {
		data = $(data).filter(function(index, tamanho){
			if($.inArray(String(tamanho.id), idsTamanhosSelectAnterior) == -1)
				return tamanho;
		}.bind(this));

		var html = this.template({ tamanhos: data });
		this.containerTamanhosProduto.append(html);
		verificaListaTamanhos.call(this);
		
		if(data.length == 1){
			var valor = data[0];
			var ultimo = $(".js-select-tamanho-produto").get(-1);
			$(ultimo).val(valor.id);
		}
	}

	return CadastroTamanhoProduto


}());


$(function() {
	var cadastroTamanhoProduto = new ElephantSystem.CadastroTamanhoProduto();
	cadastroTamanhoProduto.iniciar();

});
