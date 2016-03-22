package br.com.raiox.controller;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.LazyDataModel;

import br.com.raiox.dao.UsuarioDAO;
import br.com.raiox.dao.lazy.LazyDataModelUsuario;
import br.com.raiox.generic.MD5;
import br.com.raiox.model.Usuario;

@ViewScoped
@ManagedBean(name = "usuarioController")
public class UsuarioController implements Serializable {
	private static final long serialVersionUID = 1L;

	private Usuario usuario = new Usuario();
	private UsuarioDAO usarioDAO = new UsuarioDAO();
	private LazyDataModel<Usuario> usuarios;

	private String confirmacaoSenha;

	@PostConstruct
	public void init() {
		usarioDAO = new UsuarioDAO();
		usuarios = getUsuarios();
	}

	/**
	 * Chama o lazy data model do objeto para realizar consulta sob demanda
	 * 
	 * @return
	 */
	public LazyDataModel<Usuario> getUsuarios() {
		if (usuarios == null) {
			usuarios = new LazyDataModelUsuario(usarioDAO);
		}
		return usuarios;
	}

	public Integer tamanhoLista() {
		return usuarios.getRowCount();
	}

	public void save() {
		// DAO save the add, load list
		try {
			if (usuario.validate()) {
				
				usuario.setSenha(MD5.md5(usuario.getSenha()));
				
				usarioDAO.update(usuario);
				// for="growlMessage"
				FacesContext.getCurrentInstance().addMessage("growlMessage",
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensagem", "Salvo com sucesso."));
			} else {

				FacesContext.getCurrentInstance().addMessage("growlMessage",
						new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", "Login e/o Senha obrigatório."));
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("growlMessage",
					new FacesMessage(FacesMessage.SEVERITY_FATAL, "Contate o admin do sistema", e.getMessage()));
			// TODO: handle exception
		}
		usuario = new Usuario();
	}

	public void edit() {

	}

	public void limpar() {
		this.usuario = new Usuario();
	}

	public void delete(Usuario usuario) throws IOException {
		// DAO save the add, load list
		try {
			usarioDAO.remove(usuario);
			// for="growlMessage"
			FacesContext.getCurrentInstance().addMessage("growlMessage",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensagem", "Removido com sucesso."));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("growlMessage",
					new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erro", "Ocorreu um erro ao remover os dados!"));
			// TODO: handle exception
		}
		usuario = new Usuario();
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getConfirmacaoSenha() {
		return confirmacaoSenha;
	}

	public void setConfirmacaoSenha(String confirmacaoSenha) {
		this.confirmacaoSenha = confirmacaoSenha;
	}

}
