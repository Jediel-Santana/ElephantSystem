var ElephantSystem = ElephantSystem || {};



ElephantSystem.FormaPagamento = (function(){
	
	function FormaPagamento(){
		this.containerFormaPagamento = $('.container-forma-pagamento');		
		this.selectFormaPagamento = $('.js-forma-pagamento');		
		this.formaPagamentoConteudo = $('#formaPagamentoConteudo');
		this.botaoEfetuaPagamento = $('.btn-efetua-pagamento');
		var htmlCreditoDebito = $('#forma-pagamento-credito-debito').html();
		var htmlBoleto = $('#forma-pagamento-boleto').html();
		this.templateCreditoDebito = Handlebars.compile(htmlCreditoDebito);
		this.templateBoleto = Handlebars.compile(htmlBoleto);
	}
	
	FormaPagamento.prototype.iniciar = function (){
		this.selectFormaPagamento.on('change', onSelectFormaPagamentoAtualizado.bind(this));
	}
	
	function onSelectFormaPagamentoAtualizado(evento){
		var formaPagamento = this.selectFormaPagamento.val();
		var htmlCreditoDebito = this.templateCreditoDebito({});
		var htmlBoleto = this.templateBoleto({});
		switch(formaPagamento){
			case 'CREDITO':
			case 'DEBITO':
				this.containerFormaPagamento.html(htmlCreditoDebito);			
				break;
			case 'BOLETO':
				this.containerFormaPagamento.html(htmlBoleto);
				this.botaoEfetuaPagamento.removeAttr('disabled');
				break;	
			default:
				this.botaoEfetuaPagamento.prop('disabled', 'true');
				this.containerFormaPagamento.html('');	
		}
	}
		
		
	return FormaPagamento;
	
}());


$(function(){
	var formaPagamento = new ElephantSystem.FormaPagamento();
	formaPagamento.iniciar();
});