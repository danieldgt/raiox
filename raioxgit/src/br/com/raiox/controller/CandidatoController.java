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

import br.com.raiox.dao.CandidatoDAO;
import br.com.raiox.dao.EleicaoDAO;
import br.com.raiox.dao.ServidorDAO;
import br.com.raiox.dao.lazy.LazyDataModelCandidato;
import br.com.raiox.model.Candidato;
import br.com.raiox.model.Eleicao;
import br.com.raiox.model.Servidor;
import br.com.raiox.util.StringUtil;

@ViewScoped
@ManagedBean(name = "candidatoController")
public class CandidatoController implements Serializable {
	private static final long serialVersionUID = 1L;

	private Candidato candidato = new Candidato();
	private Eleicao eleicao = new Eleicao();
	private Servidor servidor = new Servidor();

	private LazyDataModel<Candidato> candidatos;

	private List<Eleicao> eleicaos;
	private List<Servidor> servidors;
	private EleicaoDAO eleicaoDAO = new EleicaoDAO();
	private CandidatoDAO candidatoDAO = new CandidatoDAO();
	private ServidorDAO servidorDAO = new ServidorDAO();

	@PostConstruct
	public void init() {
		candidatoDAO = new CandidatoDAO();
		candidatos = getCandidatos();
		eleicaos = eleicaoDAO.findAll();
		servidors = servidorDAO.getServidoresNivelSuperior();

	}

	/**
	 * Chama o lazy data model do objeto para realizar consulta sob demanda
	 * 
	 * @return
	 */
	public LazyDataModel<Candidato> getCandidatos() {
		if (candidatos == null) {
			candidatos = new LazyDataModelCandidato(candidatoDAO);
		}
		return candidatos;
	}

	public Integer tamanhoLista() {
		return candidatos.getRowCount();
	}

	public void save() {
		// DAO save the add, load list
		try {
			candidato.setEleicao(eleicao);
			candidato.setServidor(servidor);
			if (!StringUtil.isNullOrEmpty(candidato.validate())) {
				FacesContext.getCurrentInstance().addMessage("growlMessage",
						new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", candidato.validate()));
				return;
			}
			
			candidatoDAO.update(candidato);
			// for="growlMessage"
			FacesContext.getCurrentInstance().addMessage("growlMessage",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensagem", "Salvo com sucesso."));
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("growlMessage",
					new FacesMessage(FacesMessage.SEVERITY_FATAL, "Contate o admin do sistema", e.getMessage()));
			// TODO: handle exception
		}
		candidato = new Candidato();
		eleicao = new Eleicao();
		servidor = new Servidor();
	}

	public void edit() {
		if (candidato.getEleicao() != null && candidato.getEleicao().getId() != null) {
			eleicao = candidato.getEleicao();
		}
		if (candidato.getServidor() != null && candidato.getServidor().getId() != null) {
			servidor = candidato.getServidor();
		}
	}

	public void limpar() {
		servidor = new Servidor();
		candidato = new Candidato();
		eleicao = new Eleicao();
	}

	public void delete() throws IOException {
		// DAO save the add, load list
		try {
			candidatoDAO.remove(candidato);
			// for="growlMessage"
			FacesContext.getCurrentInstance().addMessage("growlMessage",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensagem", "Removido com sucesso."));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("growlMessage",
					new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erro", "Ocorreu um erro ao remover os dados!"));
			// TODO: handle exception
		}
		candidato = new Candidato();
	}

	public Candidato getCandidato() {
		return candidato;
	}

	public void setCandidato(Candidato candidato) {
		this.candidato = candidato;
	}

	public List<Eleicao> getEleicaos() {
		return eleicaos;
	}

	public void setEleicaos(List<Eleicao> eleicaos) {
		this.eleicaos = eleicaos;
	}

	public Eleicao getEleicao() {
		return eleicao;
	}

	public void setEleicao(Eleicao eleicao) {
		this.eleicao = eleicao;
	}

	public Servidor getServidor() {
		return servidor;
	}

	public void setServidor(Servidor servidor) {
		this.servidor = servidor;
	}

	public List<Servidor> getServidors() {
		return servidors;
	}

	public void setServidors(List<Servidor> servidors) {
		this.servidors = servidors;
	}

	public void setCandidatos(LazyDataModel<Candidato> candidatos) {
		this.candidatos = candidatos;
	}

}
