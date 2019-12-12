package br.com.alura.forum.config.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.alura.forum.modelo.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {
	
	@Value("${forum.jwt.expiration}")//Spring vai lá na propriedade app.properties e injeta nessa variavel
	private String expiration;
	
	@Value("${forum.jwt.secret}")//Spring vai lá na propriedade app.properties e injeta nessa variavel
	private String secret;
	

	public String gerarToken(Authentication authetication) {
		Usuario logado=(Usuario) authetication.getPrincipal();//Recuperar o usuario que está logado
		Date hoje= new Date();
		Date dataexpiracao= new Date(hoje.getTime()+Long.parseLong(expiration));//Vai somar os dois millesegundos e criar uma data para mais um dia
		
		return Jwts.builder()
				.setIssuer("API do fórum da Alura")
				.setSubject(logado.getId().toString())
				.setIssuedAt(hoje)//Ta esperando um Date
				.setExpiration(dataexpiracao)
				.signWith(SignatureAlgorithm.HS256, secret)
				.compact();//Tempo de expiração do token
		
	}

	
	
}
