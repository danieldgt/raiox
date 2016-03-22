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

import br.com.raiox.dao.CampusDAO;
import br.com.raiox.dao.EleicaoDAO;
import br.com.raiox.dao.TipoCandidatoDAO;
import br.com.raiox.dao.lazy.LazyDataModelEleicao;
import br.com.raiox.model.Campus;
import br.com.raiox.model.Eleicao;
import br.com.raiox.model.TipoCandidato;
import br.com.raiox.util.StringUtil;

@ViewScoped
@ManagedBean(name = "eleicaoController")
public class EleicaoController implements Serializable {
	private static final long serialVersionUID = 1L;

	private Eleicao eleicao = new Eleicao();
	private TipoCandidato tipoCandidato = new TipoCandidato();
	private Campus campus = new Campus();

	private EleicaoDAO eleicaoDAO = new EleicaoDAO();
	private CampusDAO campusDAO = new CampusDAO();
	private LazyDataModel<Eleicao> eleicaos;

	private List<TipoCandidato> tipoCandidatos;
	private List<Campus> campuss;
	private TipoCandidatoDAO tipoCandidatoDAO = new TipoCandidatoDAO();

	@PostConstruct
	public void init() {
		eleicaoDAO = new EleicaoDAO();
		eleicaos = getEleicaos();
		tipoCandidatos = tipoCandidatoDAO.findAll();
		campuss = campusDAO.findAll();

	}

	/**
	 * Chama o lazy data model do objeto para realizar consulta sob demanda
	 * 
	 * @return
	 */
	public LazyDataModel<Eleicao> getEleicaos() {
		if (eleicaos == null) {
			eleicaos = new LazyDataModelEleicao(eleicaoDAO);
		}
		return eleicaos;
	}

	public Integer tamanhoLista() {
		return eleicaos.getRowCount();
	}

	public void save() {
		// DAO save the add, load list
		try {
			eleicao.setTipoCandidato(tipoCandidato);
			eleicao.setCampus(campus);
			if (!StringUtil.isNullOrEmpty(eleicao.validate())) {
				FacesContext.getCurrentInstance().addMessage("growlMessage",
						new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", eleicao.validate()));
				return;
			}
			eleicaoDAO.update(eleicao);
			// for="growlMessage"
			FacesContext.getCurrentInstance().addMessage("growlMessage",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensagem", "Salvo com sucesso."));
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("growlMessage",
					new FacesMessage(FacesMessage.SEVERITY_FATAL, "Contate o admin do sistema", e.getMessage()));
			// TODO: handle exception
		}
		eleicao = new Eleicao();
		campus = new Campus();
		tipoCandidato = new TipoCandidato();

	}

	public void edit() {
		if (eleicao.getTipoCandidato() != null && eleicao.getTipoCandidato().getId() != null) {
			tipoCandidato = eleicao.getTipoCandidato();
		}
	}

	public void limpar() {
		eleicao = new Eleicao();
		campus = new Campus();
		tipoCandidato = new TipoCandidato();
	}

	public void delete() throws IOException {
		// DAO save the add, load list
		try {
			eleicaoDAO.remove(eleicao);
			// for="growlMessage"
			FacesContext.getCurrentInstance().addMessage("growlMessage",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensagem", "Removido com sucesso."));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("growlMessage",
					new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erro", "Ocorreu um erro ao remover os dados!"));
			// TODO: handle exception
		}
		eleicao = new Eleicao();
	}

	public Eleicao getEleicao() {
		return eleicao;
	}

	public void setEleicao(Eleicao eleicao) {
		this.eleicao = eleicao;
	}

	public List<TipoCandidato> getTipoCandidatos() {
		return tipoCandidatos;
	}

	public void setTipoCandidatos(List<TipoCandidato> tipoCandidatos) {
		this.tipoCandidatos = tipoCandidatos;
	}

	public TipoCandidato getTipoCandidato() {
		return tipoCandidato;
	}

	public void setTipoCandidato(TipoCandidato tipoCandidato) {
		this.tipoCandidato = tipoCandidato;
	}

	public Campus getCampus() {
		return campus;
	}

	public void setCampus(Campus campus) {
		this.campus = campus;
	}

	public List<Campus> getCampuss() {
		return campuss;
	}

	public void setCampuss(List<Campus> campuss) {
		this.campuss = campuss;
	}

}
