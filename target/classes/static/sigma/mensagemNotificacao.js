$("input[name='temValidade']").on("click",function(){

	if(this.checked == true){
		$('.validade-notificacao').removeClass('hidden');
	}else{
		$('.validade-notificacao').addClass('hidden');
	}	

});
	
$("#add-link").on("click",function(){

	var qtdLinks=0;
	

	$.each($(".lista-links-inputs"), function() {
		qtdLinks++;
	});

	var $str="<div class='lista-links-inputs'>"
	+" <input type='hidden' name='listaLink["+qtdLinks +"].id' id='listaLink["+qtdLinks +"].id' />"
	+ "<div class='col-sm-5'>"
		+"<div class='form-group'> <label>Informe o titulo do link </label>"
		+" <input type='text' name='listaLink["+qtdLinks +"].titulo' id='listaLink["+ qtdLinks+"].titulo' />"
		+"</div>"
	+"</div>"
	+ "<div class='col-sm-5'>"
		+"<div class='form-group'> <label>Informe o link </label>"
		+" <input type='text' name='listaLink["+qtdLinks +"].link' id='listaLink["+qtdLinks +"].link' />"
		+"</div>"
	+"</div>"
	+ "<div class='col-sm-2'>"
		+"<div class='form-group'>" 
			+"<button  type='button' class='btn btn-danger remover-link-instance' data='identificador='"+qtdLinks
			+" onclick='removeLink($(this))' > <i class='fa fa-icon-trash'>  </i> Remover</button>"
		+"</div>"	
	+"</div>"
	+" </div>";

	$("#div-lista-links").append(
		$str
	);

});
	
	
function removeLink(objeto){
	var pai = objeto.parent('div').parent('div').parent('div').parent('div');
	var index=-1;
	 objeto.parent('div').parent('div').parent('div').find('input').each(function(i, valorMermoMermo){
		  var arrayMatch= valorMermoMermo['name'].match(/[0-9]/gm);
           var valorArray ='';
           $.each(arrayMatch, function(i, valor) {
           		valorArray+=valor;
           });
           index = parseInt(valorArray);
	});
	objeto.parent('div').parent('div').parent('div').remove();	

	pai.find('input').each(function(){
		   
		   if(index=>0){
		   		var nome = $(this); 
			   var arrayMatch= nome.attr('id').match(/[0-9]/gm);
	           var valorArray ='';
	           $.each(arrayMatch, function(i, valor) {
	           		valorArray+=valor;
	           });
	           console.log('index '+index+' valorArray '+ valorArray);
	           if(index <= parseInt(valorArray) ){
	           	   nome.attr('id',nome.attr('id').replace(/[0-9]/gm,parseInt(valorArray)-1 ));
			       nome.attr('name',nome.attr('name').replace(/[0-9]/gm,parseInt(valorArray)-1 ));   
	           } 
		   }
		   
    });

}
	
	