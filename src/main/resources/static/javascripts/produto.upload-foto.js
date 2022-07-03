var ElephantS = ElephantS || {};

ElephantS.UploadFoto = (function() {

	function UploadFoto() {
		this.form = $(".js-formulario-produto");
		this.id = $('#id');
		this.htmlFotoProdutoTemplate = $('#foto-produto').html();
		this.template = Handlebars.compile(this.htmlFotoProdutoTemplate);
		this.groupFoto = $('.group-foto');
		this.inputNomeFoto = $('.js-nome-foto');
		this.inputUrlFoto = $('.js-url-foto');
		this.inputContentType = $('.js-content-type-foto');
		this.novaFoto = $('input[name=novaFoto]');
		this.uploadDrop = $('#upload-drop');
		this.containerFotoProduto = $('.js-container-foto-produto');
		this.linhaFotoProduto = $('.linha-foto-produto');
		this.url = $('#urlProdutos').val();
	}

	UploadFoto.prototype.iniciar = function() {

		var settings = {
			type: 'json',
			filelimit: 3,
			allow: '*.(jpg|jpeg|png)',
			action: this.containerFotoProduto.data('url-foto'),
			complete: onUploadComplete.bind(this),
			beforeSend: adicionarCsrfToker
		}

		UIkit.uploadSelect($('#upload-select'), settings);
		UIkit.uploadDrop(this.uploadDrop, settings);

		renderizarQuantidadeFotosDisponiveis.call(this);


		if (this.groupFoto.length > 0) {
			renderizarFoto.call(this);
			//verificarERenderizarFotosDoProduto.call(this);
			//renderizarFoto.call(this, {nome: this.inputNomeFoto.val(), contentType: this.inputContentType.val(), urlFoto: this.inputUrlFoto.val()});
		}
	}


	function verificarERenderizarFotosDoProduto() {
		this.groupFoto.each(function(i, gf) {
			var nome = $('.js-nome-foto')[i];
			var url = $('.js-url-foto')[i];
			var contentType = $('.js-content-type-foto')[i];
			var resposta = { urlFoto: $(url).val(), nome: $(nome).val(), contentType: $(contentType).val() };
			renderizarFoto.call(this, resposta, false, i);
		}.bind(this));
	}

	function renderizarQuantidadeFotosDisponiveis() {
		this.fotosProduto = $('.js-foto-produto');
		var tamanhoDisponivel = 3 - this.fotosProduto.length;

		if (tamanhoDisponivel == 0) {
			this.uploadDrop.css('display', 'none');
		} else {
			this.uploadDrop.css('display', 'block');
		}
		$('.js-qtd-fotos').html(tamanhoDisponivel);
	}

	function onUploadComplete(resposta) {
		this.novaFoto.val('true');

		var tamanhoFotosProduto = getTamanhoFotosProduto.bind(this);
		$(tamanhoFotosProduto).val(resposta.urlFoto);

		var disponiveis = this.groupFoto.filter('[data-disponivel="true"]');
		//var index = $(disponiveis[0]).data('index');

		adicionarFotoNaLista.call(this, resposta);
	}

	function adicionarFotoNaLista(resposta) {
		incluirParametrosFormulario.call(this, resposta);

		var data = this.form.serialize();
		
		$.post(this.url + '/fotos', data, substituirItens.bind(this));
		excluirParametrosFormulario.call(this);
	}

	function substituirItens(html) {
		this.linhaFotoProduto.html('');
		$('#grupos-foto').replaceWith(html);
		renderizarFoto.call(this);
	}

	function incluirParametrosFormulario(resposta) {
		var input = $("<input>");
		input.attr('type', "hidden");
		input.attr('name', "addFoto");
		this.form.append(input);

		var inputNomeNovaFoto = $("<input type='hidden'>");
		inputNomeNovaFoto.attr('name', 'nomeNovaFoto');
		inputNomeNovaFoto.val(resposta.nome);
		this.form.append(inputNomeNovaFoto);

		var inputContentType = $("<input type='hidden'>");
		inputContentType.attr('name', 'contentTypeNovaFoto');
		inputContentType.val(resposta.contentType);
		this.form.append(inputContentType);

		var inputUrlFoto = $("<input type='hidden'>");
		inputUrlFoto.attr('name', 'urlFotoNovaFoto');
		inputUrlFoto.val(resposta.urlFoto);
		this.form.append(inputUrlFoto);
	}
	
	function excluirParametrosFormulario(){
		$('input[name=nomeNovaFoto]').remove();
		$('input[name=contentTypeNovaFoto]').remove();
		$('input[name=urlFotoNovaFoto]').remove();
		$('input[name=addFoto]').remove();
	}
	
	
	function getQuantidadeDeFotos() {
		return $('.group-foto').length();
	}

	function getTamanhoFotosProduto() {
		var fotos = $('.js-foto-produto');
		var tamanhoFotosProduto = fotos.length;
		return $(this.inputUrlFoto)[tamanhoFotosProduto];
	}

	function renderizarFoto() {

		var grupoFotos = $('.group-foto');
		var fotos = [];
		grupoFotos.each(function(i, gf) {
			gf = $(gf);
			var nomeFoto = gf.find('.js-nome-foto');
			var urlFoto = gf.find('.js-url-foto');
			fotos.push({ nome: $(nomeFoto).val(), urlFoto: $(urlFoto).val() });
		}.bind(this));


		this.fotosProduto = $('.js-foto-produto');
		
		for (var i in fotos) {
			var foto = fotos[i];
			if (this.fotosProduto.length < 3) {
				var htmlFotoProduto = this.template({ url: foto.urlFoto, nome: foto.nome, ordem: i });
				this.linhaFotoProduto.append(htmlFotoProduto);
			}
		}
		
		renderizarQuantidadeFotosDisponiveis.call(this);
		$('.js-remove-foto').on('click', onRemoveFoto.bind(this));
	}

	function onRemoveFoto(evento) {
		var botao = $(evento.currentTarget);
		var urlFoto = $(this.inputUrlFoto[botao.data('foto-ordem')]).val();
		//var urlFoto = ;	
		Swal.fire({
			title: 'Tem certeza que deseja excluir a foto?',
			text: 'Você não poderá mais recupera-la!',
			imageUrl: urlFoto,
			imageWidth: 400,
			imageHeight: 300,
			imageAlt: 'Foto do produto',
			showCancelButton: true,
			confirmButtonColor: '#3085d6',
			cancelButtonText: 'Cancelar',
			cancelButtonColor: '#d33',
			confirmButtonText: 'Sim, exclua agora!'
		}).then(onExcluirConfirmado.bind(this, botao));

	}

	function onExcluirConfirmado(btn, resultado) {
		if (resultado.isConfirmed) {
			btn = $(btn);
			var nomeFoto = btn.data('nome-foto');
			var ordemFoto = btn.data('foto-ordem');
			var divFotoProduto = btn.closest('.js-foto-produto');
			$(divFotoProduto).remove();
			
			renderizarQuantidadeFotosDisponiveis.call(this);
			
				//resposta = $.ajax({
			//	url: this.containerFotoProduto.data('url-foto') + '/' + nomeFoto,
			//	method: 'DELETE',
			//	data: { produto: this.id.val() },
			//	success: function() {
			//		swal.fire({ text: 'Excluido com sucesso!', icon: 'success' });
			//	}
			//});
			
			var input = $('<input name="removeFoto">');
			var index = $('<input name="index">');
			index.val(ordemFoto);
			this.form.append(input);
			this.form.append(index);
			var data = this.form.serialize();
			
			$.post(this.url + '/fotos', data, substituirItens.bind(this));
			$(input).remove();
			$(index).remove();
		}
	}
	
	function onFotoExcluida(html){
	}

	function adicionarCsrfToker(xhr) {
		var token = $('input[name=_csrf]').val();
		var header = $('input[name=_csrf_header]').val();
		xhr.setRequestHeader(header, token);
	}

	return UploadFoto;
	
}());

$(function() {
	var uploadFoto = new ElephantS.UploadFoto();
	uploadFoto.iniciar();
});
