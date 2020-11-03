function requisicaoAjax(url,tipo) {

	var resposta = $.ajax({
		type : tipo,
		url : url,
		async : false
	}).done(function(data) {
	}).responseJSON;
	return resposta;
}
$('.biometria').on('click',function(){
	
	var id = $(this).data('id');
	var resultado = requisicaoAjax('http://localhost:8082/finger/novo/'+id,'GET');
	
	alert("Grave a biometria para "+ id);
});	

