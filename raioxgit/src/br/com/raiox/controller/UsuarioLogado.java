package br.com.raiox.controller;

import java.net.MalformedURLException;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.mail.EmailException;

import br.com.raiox.dao.UsuarioDAO;
import br.com.raiox.exception.DAOException;
import br.com.raiox.filter.SessionContext;
import br.com.raiox.generic.MD5;
import br.com.raiox.model.Usuario;
import br.com.raiox.util.SendMail;
import br.com.raiox.util.StringUtil;

/**
 * Controla o LOGIN e LOGOUT do Usuário
 */
@ManagedBean(name = "usuarioLogado")
@SessionScoped
public class UsuarioLogado {

	private static final long serialVersionUID = 1L;

	private String email;
	private String login;
	private String senha;
	private Usuario usuarioLogado;
	private UsuarioDAO usuarioDAO;
	private boolean loggedIn;

	@PostConstruct
	public void init() {
		System.out.println("init");
		usuarioDAO = new UsuarioDAO();
	}

	/**
	 * Retorna usuario logado
	 */
	public Usuario getUsuario() {
		return usuarioLogado;
	}

	public String doLogin() throws DAOException {
		Usuario usuarioFound = usuarioDAO.isUsuarioReadyToLogin(login, senha);

		if (usuarioFound == null) {
			// essa mensagem irá ser carregada na tag<h:message
			// for="growlMessage" />
			FacesContext.getCurrentInstance().addMessage("growlMessage",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Problema de Login", "Login ou Senha errado!"));

			FacesContext.getCurrentInstance().validationFailed();

			return "/login.xhtml";
		}

		loggedIn = true;
		usuarioLogado = usuarioFound;
		return "/view/home.xhtml?faces-redirect=true";

	}

	public String doLogout() {
		usuarioLogado = null;
		loggedIn = false;
		SessionContext.getInstance().encerrarSessao();
		return "/login.xhtml?faces-redirect=true";
	}

	public void solicitarNovaSenha() {
		try {
			usuarioLogado.setSenha(MD5.md5(StringUtil.gerarNovaSenha()));
			usuarioDAO.update(usuarioLogado);
			try {
				SendMail.enviaEmailFormatoHtml(login, email, "RECUPERAR SENHA", "RECUPERAR SENHA");
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (EmailException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			FacesContext.getCurrentInstance().addMessage("growlMessage", new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Error Message", "Login ou Senha errado, tente novamente!"));

		} catch (DAOException e) {
			e.getMessage();
			FacesContext.getCurrentInstance().validationFailed();
		}
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
	

}
