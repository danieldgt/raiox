package br.com.raiox.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.LazyDataModel;

import br.com.raiox.dao.UORGDAO;
import br.com.raiox.dao.CampusDAO;
import br.com.raiox.dao.lazy.LazyDataModelUORG;
import br.com.raiox.model.UORG;
import br.com.raiox.model.Campus;
import br.com.raiox.util.StringUtil;

@ViewScoped
@ManagedBean(name = "uorgController")
public class UORGController implements Serializable {
	private static final long serialVersionUID = 1L;

	private UORG uorg = new UORG();
	private Campus campus = new Campus();

	private UORGDAO uorgDAO = new UORGDAO();
	private LazyDataModel<UORG> uorgs;

	private List<Campus> campuss;
	private CampusDAO campusDAO = new CampusDAO();

	@PostConstruct
	public void init() {
		uorgDAO = new UORGDAO();
		uorgs = getUORGs();
		campuss = campusDAO.findAll();

	}

	/**
	 * Chama o lazy data model do objeto para realizar consulta sob demanda
	 * 
	 * @return
	 */
	public LazyDataModel<UORG> getUORGs() {
		if (uorgs == null) {
			uorgs = new LazyDataModelUORG(uorgDAO);
		}
		return uorgs;
	}

	public Integer tamanhoLista() {
		return uorgs.getRowCount();
	}

	public void save() {
		// DAO save the add, load list
		try {
			uorg.setCampus(campus);
			if (!StringUtil.isNullOrEmpty(uorg.validate())) {
				FacesContext.getCurrentInstance().addMessage("growlMessage",
						new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", uorg.validate()));
				return;
			}
			uorgDAO.update(uorg);
			// for="growlMessage"
			FacesContext.getCurrentInstance().addMessage("growlMessage",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensagem", "Salvo com sucesso."));
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("growlMessage",
					new FacesMessage(FacesMessage.SEVERITY_FATAL, "Contate o admin do sistema", e.getMessage()));
			// TODO: handle exception
		}
		uorg = new UORG();
		campus = new Campus();

	}

	public void edit() {
		if (uorg.getCampus() != null && uorg.getCampus().getId() != null){
			campus = uorg.getCampus();
		} else {
			campus = new Campus();
		}
			
	}

	public void limpar() {
		uorg = new UORG();
		campus = new Campus();
	}

	public void delete() throws IOException {
		// DAO save the add, load list
		try {
			uorgDAO.remove(uorg);
			// for="growlMessage"
			FacesContext.getCurrentInstance().addMessage("growlMessage",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensagem", "Removido com sucesso."));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("growlMessage",
					new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erro", "Ocorreu um erro ao remover os dados!"));
			// TODO: handle exception
		}
		uorg = new UORG();
	}

	public UORG getUORG() {
		return uorg;
	}

	public void setUORG(UORG uorg) {
		this.uorg = uorg;
	}

	public List<Campus> getCampuss() {
		return campuss;
	}

	public void setCampuss(List<Campus> campuss) {
		this.campuss = campuss;
	}

	public Campus getCampus() {
		return campus;
	}

	public void setCampus(Campus campus) {
		this.campus = campus;
	}

	public UORG getUorg() {
		return uorg;
	}

	public void setUorg(UORG uorg) {
		this.uorg = uorg;
	}

	public LazyDataModel<UORG> getUorgs() {
		return uorgs;
	}

	public void setUorgs(LazyDataModel<UORG> uorgs) {
		this.uorgs = uorgs;
	}

}
