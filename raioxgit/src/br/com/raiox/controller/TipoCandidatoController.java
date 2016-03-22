package br.com.raiox.controller;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.LazyDataModel;

import br.com.raiox.dao.TipoCandidatoDAO;
import br.com.raiox.dao.lazy.LazyDataModelTipoCandidato;
import br.com.raiox.model.TipoCandidato;
import br.com.raiox.util.StringUtil;

@ViewScoped
@ManagedBean(name = "tipoCandidatoController")
public class TipoCandidatoController implements Serializable {
	private static final long serialVersionUID = 1L;

	private TipoCandidato tipoCandidato = new TipoCandidato();
	private TipoCandidatoDAO tipoCandidatoDAO = new TipoCandidatoDAO();
	private LazyDataModel<TipoCandidato> tipoCandidatos;

	@PostConstruct
	public void init() {
		tipoCandidatoDAO = new TipoCandidatoDAO();
		tipoCandidatos = getTipoCandidatos();
	}

	/**
	 * Chama o lazy data model do objeto para realizar consulta sob demanda
	 * 
	 * @return
	 */
	public LazyDataModel<TipoCandidato> getTipoCandidatos() {
		if (tipoCandidatos == null) {
			tipoCandidatos = new LazyDataModelTipoCandidato(tipoCandidatoDAO);
		}
		return tipoCandidatos;
	}

	public Integer tamanhoLista() {
		return tipoCandidatos.getRowCount();
	}

	public void save() {
		// DAO save the add, load list
		try {
			if (!StringUtil.isNullOrEmpty(tipoCandidato.validate())) {
				FacesContext.getCurrentInstance().addMessage("growlMessage",
						new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", tipoCandidato.validate()));
				return;
			}

			tipoCandidatoDAO.update(tipoCandidato);
			// for="growlMessage"
			FacesContext.getCurrentInstance().addMessage("growlMessage",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensagem", "Salvo com sucesso."));
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("growlMessage",
					new FacesMessage(FacesMessage.SEVERITY_FATAL, "Contate o admin do sistema", e.getMessage()));
			// TODO: handle exception
		}
		tipoCandidato = new TipoCandidato();

	}

	public void edit() {

	}

	public void limpar() {
		this.tipoCandidato = new TipoCandidato();
	}

	public void delete() throws IOException {
		// DAO save the add, load list
		try {
			tipoCandidatoDAO.remove(tipoCandidato);
			// for="growlMessage"
			FacesContext.getCurrentInstance().addMessage("growlMessage",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensagem", "Removido com sucesso."));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("growlMessage",
					new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erro", "Ocorreu um erro ao remover os dados!"));
			// TODO: handle exception
		}
		tipoCandidato = new TipoCandidato();
	}

	public TipoCandidato getTipoCandidato() {
		return tipoCandidato;
	}

	public void setTipoCandidato(TipoCandidato tipoCandidato) {
		this.tipoCandidato = tipoCandidato;
	}

	
}
