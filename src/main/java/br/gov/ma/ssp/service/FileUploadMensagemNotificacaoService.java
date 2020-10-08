package br.gov.ma.ssp.service;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;



@Service
public class FileUploadMensagemNotificacaoService {

	
	@Value("${varservidorarquivo}")
	private String varservidorarquivo;
	
	public String salvarDocumento(MultipartFile multipartFile) {
		
		if(Optional.ofNullable(multipartFile).isPresent()) {
			
			String nome = getFormatDate(multipartFile.getName());
			
			 try {

		            byte[] bytes = multipartFile.getBytes();
		            Path path = Paths.get(varservidorarquivo + nome);
		            Files.write(path, bytes);
		            return varservidorarquivo + nome;
		        } catch (IOException e) {
		            e.printStackTrace();
		        }				
	
		}
		
		return "";
	}
	
	private String getFormatDate(String nome) {
		if(Optional.ofNullable(nome).isPresent()) {
			SimpleDateFormat newFormat = new SimpleDateFormat("dd_MM_yyyy_HH_mm_sss");
			String data = newFormat.format(new Date());
			nome = nome.replaceAll(" ", "")
					.replaceAll("/","")
					.replaceAll("*","")
					.replaceAll(";", "")
					.replaceAll(",", "")
					.replaceAll("-", "")
					.replaceAll("", "");
			return nome+"_"+data;
		}
		return "";
	}
	
	
}
