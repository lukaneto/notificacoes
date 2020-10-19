function requisicaoAjax(url) {

	var resposta = $.ajax({
		type : "GET",
		url : url,
		async : false
	}).done(function(data) {
	}).responseJSON;
	return resposta;
}
		
$('#teste').on('click',function(){

var lista = requisicaoAjax('/notificacao/lista');

	console.log(lista);
});		
		
		