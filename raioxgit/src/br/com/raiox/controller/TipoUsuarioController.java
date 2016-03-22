package br.com.raiox.controller;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.LazyDataModel;

import br.com.raiox.dao.TipoUsuarioDAO;
import br.com.raiox.dao.lazy.LazyDataModelTipoUsuario;
import br.com.raiox.model.TipoUsuario;
import br.com.raiox.util.StringUtil;

@ViewScoped
@ManagedBean(name = "tipoUsuarioController")
public class TipoUsuarioController implements Serializable {
	private static final long serialVersionUID = 1L;

	private TipoUsuario tipoUsuario = new TipoUsuario();
	private TipoUsuarioDAO tipoUsuarioDAO = new TipoUsuarioDAO();
	private LazyDataModel<TipoUsuario> tipoUsuarios;

	@PostConstruct
	public void init() {
		tipoUsuarioDAO = new TipoUsuarioDAO();
		tipoUsuarios = getTipoUsuarios();
	}

	/**
	 * Chama o lazy data model do objeto para realizar consulta sob demanda
	 * 
	 * @return
	 */
	public LazyDataModel<TipoUsuario> getTipoUsuarios() {
		if (tipoUsuarios == null) {
			tipoUsuarios = new LazyDataModelTipoUsuario(tipoUsuarioDAO);
		}
		return tipoUsuarios;
	}

	public Integer tamanhoLista() {
		return tipoUsuarios.getRowCount();
	}

	public void save() {
		// DAO save the add, load list
		try {
			if (!StringUtil.isNullOrEmpty(tipoUsuario.validate())) {
				FacesContext.getCurrentInstance().addMessage("growlMessage",
						new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", tipoUsuario.validate()));
				return;
			}

			tipoUsuarioDAO.update(tipoUsuario);
			// for="growlMessage"
			FacesContext.getCurrentInstance().addMessage("growlMessage",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensagem", "Salvo com sucesso."));
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("growlMessage",
					new FacesMessage(FacesMessage.SEVERITY_FATAL, "Contate o admin do sistema", e.getMessage()));
			// TODO: handle exception
		}
		tipoUsuario = new TipoUsuario();

	}

	public void edit() {

	}

	public void limpar() {
		this.tipoUsuario = new TipoUsuario();
	}

	public void delete() throws IOException {
		// DAO save the add, load list
		try {
			tipoUsuarioDAO.remove(tipoUsuario);
			// for="growlMessage"
			FacesContext.getCurrentInstance().addMessage("growlMessage",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensagem", "Removido com sucesso."));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("growlMessage",
					new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erro", "Ocorreu um erro ao remover os dados!"));
			// TODO: handle exception
		}
		tipoUsuario = new TipoUsuario();
	}

	public TipoUsuario getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(TipoUsuario tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	
}
