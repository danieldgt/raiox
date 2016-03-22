package br.com.raiox.controller;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.LazyDataModel;

import br.com.raiox.dao.CampusDAO;
import br.com.raiox.dao.lazy.LazyDataModelCampus;
import br.com.raiox.model.Campus;
import br.com.raiox.util.StringUtil;

@ViewScoped
@ManagedBean(name = "campusController")
public class CampusController implements Serializable {
	private static final long serialVersionUID = 1L;

	private Campus campus = new Campus();
	private CampusDAO campusDAO = new CampusDAO();
	private LazyDataModel<Campus> campuss;

	@PostConstruct
	public void init() {
		campusDAO = new CampusDAO();
		campuss = getCampuss();
	}

	/**
	 * Chama o lazy data model do objeto para realizar consulta sob demanda
	 * 
	 * @return
	 */
	public LazyDataModel<Campus> getCampuss() {
		if (campuss == null) {
			campuss = new LazyDataModelCampus(campusDAO);
		}
		return campuss;
	}

	public Integer tamanhoLista() {
		return campuss.getRowCount();
	}

	public void save() {
		// DAO save the add, load list
		try {
			if (!StringUtil.isNullOrEmpty(campus.validate())) {
				FacesContext.getCurrentInstance().addMessage("growlMessage",
						new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", campus.validate()));
				return;
			}

			campusDAO.update(campus);
			// for="growlMessage"
			FacesContext.getCurrentInstance().addMessage("growlMessage",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensagem", "Salvo com sucesso."));
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("growlMessage",
					new FacesMessage(FacesMessage.SEVERITY_FATAL, "Contate o admin do sistema", e.getMessage()));
			// TODO: handle exception
		}
		campus = new Campus();

	}

	public void edit() {

	}

	public void limpar() {
		this.campus = new Campus();
	}

	public void delete() throws IOException {
		// DAO save the add, load list
		try {
			campusDAO.remove(campus);
			// for="growlMessage"
			FacesContext.getCurrentInstance().addMessage("growlMessage",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensagem", "Removido com sucesso."));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("growlMessage",
					new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erro", "Ocorreu um erro ao remover os dados!"));
			// TODO: handle exception
		}
		campus = new Campus();
	}

	public Campus getCampus() {
		return campus;
	}

	public void setCampus(Campus campus) {
		this.campus = campus;
	}

}
