package br.gov.ma.ssp.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.gov.ma.ssp.model.dto.FileNotificacaoDto;



@Service
public class FileUploadMensagemNotificacaoService {

	
	//@Value("${varservidorarquivo}")
	private final String VAR_SERVIDOR_ARQUIVO= "C:\\Users\\sspma\\Documents\\opt\\data\\sigma-desenvolvimento\\Notificacoes\\";
	
	public FileNotificacaoDto salvarDocumento(MultipartFile multipartFile) {
		
		if(Optional.ofNullable(multipartFile).isPresent()) {
			
			String nome = getFormatDate(multipartFile.getOriginalFilename());
			
			 try {

				 	byte[] bytes = multipartFile.getBytes();
		            Path path = Paths.get(VAR_SERVIDOR_ARQUIVO + nome);
		            Files.write(path, bytes);
		            FileNotificacaoDto nomeFile = new FileNotificacaoDto();
		            nomeFile.setNome(nome);
		            nomeFile.setImagem(VAR_SERVIDOR_ARQUIVO + nome);
		           
		            return nomeFile;
		        } catch (IOException e) {
		            e.printStackTrace();
		        }				
	
		}
		
		return null;
	}
	
	private String getFormatDate(String nome) {
		if(Optional.ofNullable(nome).isPresent()) {
			SimpleDateFormat newFormat = new SimpleDateFormat("dd_MM_yyyy_HH_mm_sss");
			String data = newFormat.format(new Date());
			nome = nome.replaceAll(" ", "")
					.replaceAll("/","")
					.replaceAll("\\*","")
					.replaceAll(";", "")
					.replaceAll(",", "")
					.replaceAll("-", "")
					.replaceAll("", "");
			return data+"_"+nome;
		}
		return "";
	}
	
	
}
