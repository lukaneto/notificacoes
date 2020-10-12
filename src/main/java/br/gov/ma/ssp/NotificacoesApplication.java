package br.gov.ma.ssp;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;

@SpringBootApplication
public class NotificacoesApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificacoesApplication.class, args);
	}

	
	@Bean
	public FixedLocaleResolver getLocaleResolver(){
		return new FixedLocaleResolver(new Locale("pt", "BR"));
	}
	
}
