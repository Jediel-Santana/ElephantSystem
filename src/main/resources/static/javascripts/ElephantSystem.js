var ElephantSystem = ElephantSystem || {};

numeral.language('pt-br');
ElephantSystem.formatarMoeda = function(valor){
	return numeral(valor).format('0,0.00');
}


ElephantSystem.MaskNumber = (function (){
	
	function Decimal(){
		this.decimal = $('.js-decimal');
		this.inteiro = $('.js-plain');
	}
	
	Decimal.prototype.iniciar = function(){
		this.decimal.maskNumber({thousands: '.', decimal: ','});
		this.inteiro.maskNumber({integer: true, decimal: ','});
	}
	
	return Decimal;
}());


ElephantSystem.MaskDate = (function(){
	
	function MaskDate(){
		this.inputDate = $('.js-date');
	}
	
	MaskDate.prototype.enable = function() {
		this.inputDate.datepicker({
			orientation: 'bottom',
			language: 'pt-BR',
			autoclose: true
		});
	}
	
	return MaskDate;
}());

ElephantSystem.Toastr = (function (){
	
	function Toastr(){
		
	}
	
	Toastr.prototype.iniciar = function (){
		
	}
	
	return Toastr;
	
}());

ElephantSystem.Security = (function(){
	
	function Security(){
		this.token = $('input[name=_csrf]').val();
		this.header = $('input[name=_csrf_header]').val();
	}
	
	Security.prototype.enable = function(){
		$( document ).ajaxSend(function( event, jqxhr, settings ) {
			console.log(this.header);
			console.log(this.token);
			jqxhr.setRequestHeader(this.header, this.token);
		}.bind(this));
	}
		
	return Security;
}());

$(function(){
	var toastr = new ElephantSystem.Toastr();
	toastr.iniciar();
	
	var maskNumber = new ElephantSystem.MaskNumber();
	maskNumber.iniciar();
	
	var security = new ElephantSystem.Security();
	security.enable();
	
	var maskDate = new ElephantSystem.MaskDate();
	maskDate.enable();
});