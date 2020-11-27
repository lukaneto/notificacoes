$("input[name='temValidade']").on("click",function(){

	if(this.checked == true){
		$('.validade-notificacao').removeClass('hidden');
	}else{
		$('.validade-notificacao').addClass('hidden');
	}	

});
	
	
$("#add-link").on("click",function(){

	
	function uuidv4() {
	  return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
	    var r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
	    return v.toString(16);
	  });
	}

	/*$.each($(".lista-links-inputs"), function() {
		qtdLinkss++;
	});*/
	
	var qtdLinks = uuidv4();	
	
	var $str="<div class='lista-links-inputs'>"
	+" <input type='hidden' name='listaLink["+qtdLinks +"].id' id='listaLink["+qtdLinks +"].id' />"
	+ "<div class='col-sm-5'>"
		+"<div class='form-group'> <label>Informe o titulo do link </label>"
		+" <input type='text' name='listaLink["+qtdLinks +"].titulo' id='listaLink["+qtdLinks +"].titulo' onkeypress='capIdTitulo($(this.id))' />" //input titut
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
			+" onclick='removeLink($(this))' id='"+qtdLinks+"'> <i class='fa fa-icon-trash'>  </i> Remover</button>"
		+"</div>"	
	+"</div>"
	+" </div>";

	$("#div-lista-links").append(
		$str
	);
	
	
	
	
	//$("#idDivTituloLink").append('<div id="divDentroTitulo['+qtdLinks+']"></div>');
	
	$("#idDivTituloLink").append('<p id="testeaqui">testeeee</p>');
	
	//pTextLink = 'pDentroTitulo['+qtdLinks+']';
	
	idTextInputBox = $('#' + $.escapeSelector('listaLink['+qtdLinks +'].titulo'));
	
	//console.log(pTextLink);
	//console.log(idTextInputBox);
	
	//$(document).ready(function() {
	
	
	//});
});
	

function capIdTitulo(objeto){
	//console.log('funfando...');
	$("#CourseSelect").change(loadTeachers).change();
	idInputTitulo = objeto.prevObject[0].activeElement.id;
	
	idToTitulo = $('#' + $.escapeSelector(idInputTitulo));
	
	//$(document).on('keyup', idToTitulo, function() {
	     $('#testeaqui').text($(this).val());
	
		 //testeLokao = $('#testeaqui').text();
		 console.log( testeLokao);
	 //});
}

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
	           //console.log('index '+index+' valorArray '+ valorArray);
	           if(index <= parseInt(valorArray) ){
	           	   nome.attr('id',nome.attr('id').replace(/[0-9]/gm,parseInt(valorArray)-1 ));
			       nome.attr('name',nome.attr('name').replace(/[0-9]/gm,parseInt(valorArray)-1 ));   
	           } 
		   }
		   
    });
	
	
	idButtonRemovLink = objeto[0].id;
	
	var idParaRemoverLink= document.getElementById('pDentroTitulo['+idButtonRemovLink+']');
	
	//console.log(objeto[0].id);
	if(idParaRemoverLink!=null){
		idParaRemoverLink.parentNode.removeChild(idParaRemoverLink);	
	}

}


$("#add-midia").on("click",function(){


		var qtdLinks=0;
	

	$.each($(".lista-midia-inputs"), function() {
		qtdLinks++;
	});
	
	
	var $str="<div class='lista-midia-inputs'>"
	+ "<div class='col-sm-10'>"
		+"<div class='form-group'> <label>Adicione a Imagem </label>"
		+" <input type='file' name='listaImagem["+qtdLinks+"]' id='listaImagem["+qtdLinks+"]' class='file' data-show-upload='true' data-show-caption='true' accept='image/*' />"
		+"</div>"
	+"</div>"
	+ "<div class='col-sm-2'>"
		+"<div class='form-group' id='divBotaoRemove'>" 
			+"<button  type='button' class='btn btn-danger' onclick='removeMidia($(this))' id='"+qtdLinks+"' > <i class='fa fa-icon-trash'>  </i> Remover</button>"
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
	//console.log(idMsgNot);
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
	           //console.log('index '+index+' valorArray '+ valorArray);
	           if(index <= parseInt(valorArray) ){
	           	   nome.attr('id',nome.attr('id').replace(/[0-9]/gm,parseInt(valorArray)-1 ));
			       nome.attr('name',nome.attr('name').replace(/[0-9]/gm,parseInt(valorArray)-1 ));   
	           } 
		   }
		   
    });
	
	idButtonRemov = objeto[0].id;
	
	var imagemPreview = document.getElementById('imgSet['+idButtonRemov+']');
	
	//console.log(objeto[0].id);
	if(imagemPreview!=null){
		imagemPreview.parentNode.removeChild(imagemPreview);	
	}
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




						