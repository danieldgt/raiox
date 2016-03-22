package br.com.raiox.util;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;


/**
 * Classe auxiliar responsável por enviar emails para usuários
 * 
 * @author Thiago Queiroz
 *
 * @since 07/05/2013
 */
public class SendMail {

	
	/**
	 * Envia email simples(somente texto)
	 * 
	 * @throws EmailException
	 */
	public void enviaEmailSimples() throws EmailException {

		SimpleEmail email = new SimpleEmail();
		email.setHostName("smtp.gmail.com"); // o servidor SMTP para envio do e-mail
		email.addTo("thiagoqo@gmail.com", "Thiago Queiroz"); //destinatário
		email.setFrom("thiagoqo@gmail.com", "Eu"); // remetente
		email.setSubject("Teste -> Email simples"); // assunto do e-mail
		email.setMsg("Teste de Email utilizando commons-email"); //conteudo do e-mail
		email.setAuthentication("sisedital", "QXIM+AF2A5Q#hG");
		email.setSmtpPort(465);
		email.setSSL(true);
		email.setTLS(true);
		email.send();	
	}


	/**
	 * Envia email com arquivo anexo
	 * 
	 * @throws EmailException
	 */
	public void enviaEmailComAnexo() throws EmailException{

		// cria o anexo 1.
		EmailAttachment anexo1 = new EmailAttachment();
		anexo1.setPath("teste/teste.txt"); //caminho do arquivo (RAIZ_PROJETO/teste/teste.txt)
		anexo1.setDisposition(EmailAttachment.ATTACHMENT);
		anexo1.setDescription("Exemplo de arquivo anexo");
		anexo1.setName("teste.txt");		

		// configura o email
		MultiPartEmail email = new MultiPartEmail();
		email.setHostName("smtp.gmail.com"); // o servidor SMTP para envio do e-mail
		email.addTo("teste@gmail.com", "Guilherme"); //destinatário
		email.setFrom("teste@gmail.com", "Eu"); // remetente
		email.setSubject("Teste -> Email com anexos"); // assunto do e-mail
		email.setMsg("Teste de Email utilizando commons-email"); //conteudo do e-mail
		email.setAuthentication("sisedital", "QXIM+AF2A5Q#hG");
		email.setSmtpPort(465);
		email.setSSL(true);
		email.setTLS(true);

		email.attach(anexo1);
		email.send();
	}


	/**
	 * Envia email no formato HTML
	 * 
	 * @throws EmailException 
	 * @throws MalformedURLException 
	 */
	public static void enviaEmailFormatoHtml(String nomeDestinatario, String emailDestinatario, String titulo, String mensagem) throws EmailException, MalformedURLException {

		HtmlEmail email = new HtmlEmail();

		// adiciona uma imagem ao corpo da mensagem e retorna seu id
		URL url = new URL("http://www.ifce.edu.br/templates/ifce/images/header/logo/logo_ifce_ceara.png");
		email.embed(url, "IFCE");	
		

		// configura a mensagem para o formato HTML
		email.setHtmlMsg(mensagem);

		// configure uma mensagem alternativa caso o servidor não suporte HTML
		email.setTextMsg("Seu servidor de e-mail não suporta mensagem HTML");

//		email.setHostName("mail.ifce.edu.br"); // o servidor SMTP para envio do e-mail
//		email.setHostName("smtp.gmail.com"); // o servidor SMTP para envio do e-mail
		email.setHostName("smtp.gmail.com"); // o servidor SMTP para envio do e-mail
		email.addTo(emailDestinatario, nomeDestinatario); //destinatário
//		email.setFrom("sisedital@ifce.edu.br", "Equipe de desenvolvimento UAB/IFCE"); // remetente
	//	email.setFrom("madi.uab@gmail.com", "Equipe de desenvolvimento UAB/IFCE"); // remetente
		email.setFrom("proreitoriadeensinoifce@gmail.com", "Equipe de desenvolvimento PROEN/IFCE"); // remetente
		email.setSubject(titulo); // assunto do e-mail

//		email.setAuthentication("madi.uab", "madi1567");
		email.setAuthentication("proreitoriadeensinoifce", "gestao20132016");
		email.setSmtpPort(465);
		email.setSSL(true);
		email.setTLS(true);
		// envia email
		email.send();
	}
	
	/**
	 * @param args
	 * @throws EmailException 
	 * @throws MalformedURLException 
	 */
	public static void main(String[] args) throws EmailException, MalformedURLException {
		new SendMail();
	}
	
	/**
	 * Envia email no formato HTML
	 * 
	 * @throws EmailException 
	 * @throws MalformedURLException 
	 */
	public void enviaEmailSolicitante(String nomeDestinatario, String emailDestinatario, String titulo, String mensagem) throws EmailException, MalformedURLException {

		HtmlEmail email = new HtmlEmail();

		// adiciona uma imagem ao corpo da mensagem e retorna seu id
		URL url = new URL("http://www.ifce.edu.br/templates/ifce/images/header/logo/logo_ifce_ceara.png");
		email.embed(url, "IFCE");	

		// configura a mensagem para o formato HTML
		email.setHtmlMsg(mensagem);

		// configure uma mensagem alternativa caso o servidor não suporte HTML
		email.setTextMsg("Seu servidor de e-mail não suporta mensagem HTML");
		email.setHostName("smtp.gmail.com"); // o servidor SMTP para envio do e-mail
		email.addTo(emailDestinatario, nomeDestinatario); //destinatário
		email.setFrom("proreitoriadeensinoifce@gmail.com", "Equipe de desenvolvimento PROEN/IFCE"); // remetente
		email.setSubject(titulo); // assunto do e-mail
		email.setAuthentication("proreitoriadeensinoifce", "gestao20132016");
		email.setSmtpPort(465);
		email.setSSL(true);
		email.setTLS(true);
		
		// envia email
		email.send();
	}

}
