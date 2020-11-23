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
		+" <input type='text' style='50px' name='listaLink["+qtdLinks +"].link' id='listaLink["+qtdLinks +"].link' />"
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


$("#add-midia").on("click",function(){


		var qtdLinks=0;
	

	$.each($(".lista-midia-inputs"), function() {
		qtdLinks++;
	});
	
	
	var $str="<div class='lista-midia-inputs'>"
	+ "<div class='col-sm-10'>"
		+"<div class='form-group'> <label>Adicione a Imagem </label>"
		+" <input type='file' name='listaImagem["+qtdLinks+"]' id='listaImagem["+qtdLinks+"]' class='file' data-show-upload='true' data-show-caption='true' />"
		+"</div>"
	+"</div>"
	+ "<div class='col-sm-2'>"
		+"<div class='form-group' id='divBotaoRemove'>" 
			+"<button  type='button' class='btn btn-danger' onclick='removeMidia($(this))' name='"+qtdLinks+"' > <i class='fa fa-icon-trash'>  </i> Remover</button>"
		+"</div>"	
	+"</div>"
	+" </div>";

	$("#div-lista-midias").append(
		$str
	);
	
	
	$("#imagensPreview").append('<img id="imgSet['+qtdLinks+']" src="#" alt="" class="stiloImagem" />');
	idImgNot = $('#' + $.escapeSelector('imgSet['+qtdLinks+']'));
	window.readURL = function(input){

    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
            $(idImgNot).attr('src', e.target.result);
        }

        reader.readAsDataURL(input.files[0]);
		//console.log("parte1");
    }
}

	//var idMsgNot = document.getElementById("'listaImagem["+qtdLinks+"]'");
	
	idMsgNot = $('#' + $.escapeSelector('listaImagem['+qtdLinks+']'));
	//console.log('funfou aqui: '+idMsgNot);
	$(idMsgNot).change(function(){
		//console.log("parte2");
    readURL(this);
});
	
});

function removeMidia(objeto){
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
	
	
	
	var imagemPreview = document.getElementById('imgSet[0]');
	console.log(testeee);
	//if(imagemPreview!=null && imagemPreview.length > 0){
	imagemPreview.parentNode.removeChild(imagemPreview);	
	//}
}




$('select[name="funcionario_select"]').change(function() {
		var tipo = $(this).val();
		switch (tipo) {
		case 'Selecione':
			$('#pesquisa_funcionario').val('');
			$('#pesquisa_funcionario').prop('disabled', true);
			$('#pesquisa_funcionario').attr('placeholder', 'Selecione o tipo da pesquisa');
			$('#btn_pesquisa_funcionario').prop('disabled', true);
			break;
		case 'NO':
			$('#pesquisa_funcionario').prop('disabled', false);
			$('#btn_pesquisa_funcionario').prop('disabled', false);
			$('#pesquisa_funcionario').val('');
			$('#pesquisa_funcionario').unmask();
			$('#pesquisa_funcionario').attr('placeholder', 'Digite o nome ou parte do nome');
			break;
		case 'NE':
			$('#pesquisa_funcionario').prop('disabled', false);
			$('#btn_pesquisa_funcionario').prop('disabled', false);
			$('#pesquisa_funcionario').val('');
			$('#pesquisa_funcionario').unmask();
			$('#pesquisa_funcionario').attr('placeholder', 'Digite o nome exato da pessoa física');
			break;
		case 'MM':
			$('#pesquisa_funcionario').prop('disabled', false);
			$('#btn_pesquisa_funcionario').prop('disabled', false);
			$('#pesquisa_funcionario').val('');
			$('#pesquisa_funcionario').unmask();
			$('#pesquisa_funcionario').attr('placeholder','Digite o nome exato da mãe da pessoa física');
			break;
		case 'CPF':
			$('#pesquisa_funcionario').prop('disabled', false);
			$('#btn_pesquisa_funcionario').prop('disabled', false);
			$('#pesquisa_funcionario').val('');
			$('#pesquisa_funcionario').mask('000.000.000-00');
			$('#pesquisa_funcionario').attr('placeholder', '000.000.000-00');
			break;
		case 'MA':
			$('#pesquisa_funcionario').prop('disabled', false);
			$('#btn_pesquisa_funcionario').prop('disabled', false)
			$('#pesquisa_funcionario').val('');
			$('#pesquisa_funcionario').unmask();
			$('#pesquisa_funcionario').attr('placeholder', 'Digite a matricula pessoa física');
			break;
		}
});




						