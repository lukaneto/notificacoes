function requisicaoAjax(url) {

	var resposta = $.ajax({
		type : "GET",
		url : url,
		async : false
	}).done(function(data) {
	}).responseJSON;
	return resposta;
}
		
$('#menu').on('click',function(){
	var pagina = requisicaoAjax('/notificacao/pagina?page='+0);
	var total = pagina['quantidadeNaoVisualizada'];
	$("#qtd_notificacao").text(total);
});		
		


$('#menu').click(function(e) {
 	e.preventDefault();
 	e.stopPropagation();
	if($(this).hasClass('open')){
	      $(this).removeClass("open");
	      $.each($(".notifications-wrapper"), function() {
	      	$(this).find("a").remove(); 
		  });
	
	}else{
	      $(this).addClass("open");
	  notificacoesLista();
	}

	 
 });
		
function notificacoesLista(){
var pagina = requisicaoAjax('/notificacao/pagina?page='+0);
	console.log(pagina);
	var elems = [];
	elems = pagina ['notificacaoNaoVisualizada'];
	adicionaNotificacao(elems, 'notification-item');
	elems = [];
	elems = pagina['notificacao'];	
	adicionaNotificacao(elems,'notification-item-visualizado');
}	
		
		
function adicionaNotificacao(elems,config){
 $.each(elems, function(item,el) {
 	
	var notificacao = "<a class='content abrirModal'  data-dados='"+ JSON.stringify(el)+"'"
		  +" onclick='abrirModal($(this))' >"
	      + "<div class="+ config+">"
		  +" <h4 class='item-title'>"+ el.titulo+"</h4>"
		  +" <p class='item-info'>"+el.descricao+"</p>"
		  +" </div>"
		  + "</a>";
		 
	 	$('.notifications-wrapper').append(notificacao);
	 	
	});
}	



function abrirModal(div)
{
	$('#modal_body').find("div").remove(); 

	var dados = div.data('dados');
	console.log('aqui');
	console.log(dados);
	var el = dados;
	$('#modal').modal('show');
	$('#modal_title').text(el.titulo);
	
	var body = "<div class='row'>"
	
	+"<div class='col-sm-12'>  <p>"+el.descricao+" </p> </div>";
	body= body+"</div>";
	$('#modal_body').append(body);
	var imagemElement="";
	$.each(el['listaImagem'], function(item,el) {
		 imagemElement = "<div class='row'><div class='col-sm-12'><img src="+el.imagem+" alt="+el.descricao+"  /></div></div>"
		$('#modal_body').append(imagemElement);
	});
	
	console.log(imagemElement);
	
	var linkElemento ="";
	$.each(el['listaLink'], function(item,el) {
		linkElemento = "<div class='row'><div class='col-sm-12'> <a href="+el.link+"> "+el.titulo+"</a> </div> </div>";
		$('#modal_body').append(linkElemento);
	});
	
	console.log(linkElemento);

}


		