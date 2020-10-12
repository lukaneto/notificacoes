
	
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
				+"<a  type='button' class='btn btn-danger remover-link-instance' data='identificador='"+qtdLinks
				+" > <i class='fa fa-icon-trash'>  </i> Remover</a>"
			+"</div>"	
		+"</div>"
		+" </div>";
	
		$("#div-lista-links").append(
		
			$str
		);
	
	});
	
	