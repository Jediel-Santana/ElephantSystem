var ElephantSystem = ElephantSystem || {};

ElephantSystem.Multiselecao = (function(){
	
	
	function Multiselecao(checkboxSelecao, checkboxTodos){
		this.checkboxsSelecao = checkboxSelecao;
		this.checkboxSelecaoTodos = checkboxTodos;	
	}	

	Multiselecao.prototype.iniciar = function(){
		this.checkboxsSelecao.on("click", onCheckboxSelecionado.bind(this));
		this.checkboxSelecaoTodos.on("click", onCheckboxTodosSelecionado.bind(this));
	}
	
	function onCheckboxSelecionado(){
		var checkboxSelecionados = this.checkboxsSelecao.filter(':checked');
		this.checkboxSelecaoTodos.prop('checked', checkboxSelecionados.length >= this.checkboxsSelecao.length);
		
	}
	
	function onCheckboxTodosSelecionado(){
		var status = this.checkboxSelecaoTodos.prop('checked');
		this.checkboxsSelecao.prop('checked', status);
	}
	
	
	return Multiselecao;
}());