

$(function(){
	//Filtro Gênero
	var checkboxsSelecaoGenero = $('.js-selecao-filtro-genero');
	var checkboxSelecaoTodosGenero = $('.js-selecao-filtro-genero-todos');
	var multiSelecaoGeneros = new ElephantSystem.Multiselecao(checkboxsSelecaoGenero, checkboxSelecaoTodosGenero);
	multiSelecaoGeneros.iniciar();
	console.log(checkboxsSelecaoGenero)
	
	//Filtro Categoria
	var checkboxsSelecaoCategoria = $('.js-selecao-filtro-categoria');
	var checkboxSelecaoTodosCategoria = $('.js-selecao-filtro-categoria-todos');
	var multiSelecaoCategorias = new ElephantSystem.Multiselecao(checkboxsSelecaoCategoria, checkboxSelecaoTodosCategoria);
	multiSelecaoCategorias.iniciar();
		
	//Filtro Tamanho
	var checkboxsSelecaoTamanho = $('.js-selecao-filtro-tamanho');
	var checkboxSelecaoTodosTamanho = $('.js-selecao-filtro-tamanho-todos');
	var multiSelecaoTamanhos = new ElephantSystem.Multiselecao(checkboxsSelecaoTamanho, checkboxSelecaoTodosTamanho);
	multiSelecaoTamanhos.iniciar();
		
});