
$(document).ready(function(){
	
  getPrimeiraPagina();
});

function requisicaoAjax(url,tipo) {

	var resposta = $.ajax({
		type : tipo,
		url : url,
		async : false
	}).done(function(data) {
	}).responseJSON;
	return resposta;
}
		
$('#dLabel').on('click',function(){
	getPrimeiraPagina();
});		

function getPrimeiraPagina(){
	var pagina = requisicaoAjax('/notificacao/pagina?page='+0,"GET");
	var total = pagina['quantidadeNaoVisualizada'];
	$("#qtd_notificacao").text(total);
}	


$('#dLabel').click(function(e) {
 	e.preventDefault();
 	e.stopPropagation();
	if($('#menu').hasClass('open')){
	      $('#menu').removeClass("open");
	      $.each($(".notifications-wrapper"), function() {
	      	$(this).find("a").remove(); 
		  });
	
	}else{
	 $('#menu').addClass("open");
	  notificacoesLista(0);
	}

	 
 });
		
function notificacoesLista(page){
	if(page!==''&& page !==undefined && page>=0 ){
		var pagina = requisicaoAjax('/notificacao/pagina?page='+page,"GET");
		console.log(pagina);
		var elems = [];
		elems = pagina ['notificacaoNaoVisualizada'];
		var maxPagina =pagina ['maxPagina'];
		console.log('maxpagina' + maxPagina)
		adicionaNotificacao(elems, 'notification-item',page+1,maxPagina);
		elems = [];
		elems = pagina['notificacao'];	
		adicionaNotificacao(elems,'notification-item-visualizado',page+1,maxPagina);
		
	}

	if(page!==''&& page !==undefined){
	
//	if(page==maxPagina){
//				var botaoCarregarMais = "<span class='' >Sem mais notificacoes</span>"; 
//				$('.notifications-wrapper').append(botaoCarregarMais);	
//			}else{
//				var botaoCarregarMais = "<a type='button' class='view-more-button' onclick='carregaProximaPagina(this,"+page+1+")'>Ver mais</a>"; 
//				$('.notifications-wrapper').append(botaoCarregarMais);
//			}
//		
//	}
		
	
	
}	

	
function carregaProximaPagina(objet, page){
	objeto.remove();
	notificacoesLista(page);
}	
		
function adicionaNotificacao(elems,config,proximaPagina,maxPagina){
	 $.each(elems, function(item,el) {
	 	
		var notificacao = "<a class='content abrirModal'  data-dados='"+ JSON.stringify(el)+"'"
			  +" onclick='abrirModal($(this))' >"
		      + "<div class="+ config+">"
			  +" <h4 class='item-title'>"+ el.titulo+"</h4>"
			  +" <p class='item-info'>"+el.descricao+"</p>"
			  +" <p class='item-info'> enviado em "+el.dataCriacao+"</p>"
			  +" </div>"
			  + "</a>";
			 
		 	$('.notifications-wrapper').append(notificacao);
	});

	
}



function abrirModal(div)
{
	$('#modal_body').find("div").remove(); 

	var dados = div.data('dados');

	console.log(dados.id);
	
	requisicaoAjax('/notificacao/visualizar/'+dados.id,"POST");
	
	
	var el = dados;
	console.log(dados);
	$('#modal').modal('show');
	$('#modal_title').text(dados.titulo);
	
	var body = "<div class='row'> <div class='col-sm-12'>  <p>"+dados.descricao+" </p> </div></div>";
	
	$('#modal_body').append(body);
	
	var imagemElement="";
	
	imagemElement = "<div class='row'><div class='col-sm-12'>"+carregaImagens(dados['listaImagem'])+"</div></div>";
	
	$('#modal_body').append(imagemElement);
	
	
	var linkElemento ="";
	$.each(dados['listaLink'], function(item,el) {
		linkElemento =  "<div class='row'><div class='col-sm-12'> <a href='"+el.link+"' target='_blank'> "+el.titulo+"</a> </div> </div>";
		$('#modal_body').append(linkElemento);
	});
	
	console.log(linkElemento);
	
	
}


function carregaImagens(listaImagens){

if(listaImagens!= undefined && listaImagens!=null && listaImagens.length>0){
	
	var carrossel = "<div id='myCarousel' class='carousel slide' data-ride='carousel'>"
	+"<ol class='carousel-indicators'>";
	
	$.each(listaImagens, function(item,el) {
		var li ="";
		 if(item ==0){
	 		li = "<li data-target='#myCarousel' data-slide-to='"+item+"' class='active'></li>";
		}else{
			li = "<li data-target='#myCarousel' data-slide-to='"+item+"'></li>";
		}
		carrossel = carrossel + li;
	});
	
	carrossel= carrossel +"</ol>";
	 carrossel = carrossel + "<div class='carousel-inner'>";
	 
	 
	 $.each(listaImagens, function(item,el) {
		var li ="";
		var imagemExibir = 'http://127.0.0.1:8887/'+el.imagem.replace("C:","").replace(/[\\"]/g,"/");
		if(item ==0){
	 		li = "<div class='item active'> <img src='"+ imagemExibir +"'  alt='"+el.descricao+"'> </div>";
		}else{
			li = "<div class='item'> <img src='"+ imagemExibir +"'  alt='"+el.descricao+"'> </div>";
		}
		carrossel = carrossel + li;
	});
	
	carrossel = carrossel + "</div>";
	 
	  
	carrossel = carrossel +"<a class='left carousel-control' href='#myCarousel' data-slide='prev'>"
	   + "<span class='glyphicon glyphicon-chevron-left'>  </span>"
	   + "<span class='sr-only'>Previous</span>"
	  +"</a>"
	  +"<a class='right carousel-control' href='#myCarousel' data-slide='next'>"
	  +" <span class='glyphicon glyphicon-chevron-right'></span>"
	   +"<span class='sr-only'>Next</span>"
	  +"</a>"
	+"</div>";
	
	
	
	return carrossel;
	
	}
return '';
}
		