var ElephantSystem = ElephantSystem || {}; 

ElephantSystem.Endereco = (function (){
	
	function Endereco(){
		this.botaoConsultaCep = $('.js-consulta-cep');
		this.inputCep = $('.js-venda-endereco-cep');
		this.inputEstado = $('.js-venda-endereco-estado');
		this.inputCidade = $('.js-venda-endereco-cidade');
		this.inputBairro = $('.js-venda-endereco-bairro');
		this.inputLogradouro = $('.js-venda-endereco-logradouro');
		this.inputNumero = $('.js-venda-endereco-numero');
		this.inputComplemento = $('.js-venda-endereco-complemento');
	}
	
	Endereco.prototype.iniciar = function(){
		this.botaoConsultaCep.on('click', onBotaoConsultaCepClicado.bind(this));
		estados.call(this);
	}
	
	function estados(){
		var estado = $('#estado');
		var cidade = $('#cidade');
		console.log(estado);
		console.log(cidade);
		console.log();
		
		new dgCidadesEstados(document.getElementById('estado'), cidade, true);
	}
		
	function onBotaoConsultaCepClicado(evento){
		var botao = $(evento.currentTarget);
		var urlCep = botao.data("url-cep");
		
		console.log(this.inputCep);
		console.log(this.inputCep.val());
		$.ajax({
			url: urlCep + "/" + this.inputCep.val(),
			type: 'GET',
			success: onCepBuscado.bind(this),
			error: onErroAoBuscarCep(this)
		}); 
	}
	
	function onCepBuscado(endereco){
		this.inputCidade.val(endereco.localidade);
		this.inputBairro.val(endereco.bairro);
		this.inputLogradouro.val(endereco.logradouro);
		this.inputEstado.val(endereco.uf);
		this.inputCidade.val(endereco.localidade);
	}
	
	function onErroAoBuscarCep(){
		
	}
		
		
		
	return Endereco;
}());


$(function(){
	var endereco = new ElephantSystem.Endereco();
	endereco.iniciar();
});