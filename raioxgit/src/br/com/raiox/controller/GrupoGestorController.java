package br.com.raiox.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.model.DualListModel;

import br.com.raiox.dao.CandidatoDAO;
import br.com.raiox.dao.ServidorDAO;
import br.com.raiox.model.Candidato;
import br.com.raiox.model.Servidor;

@ViewScoped
@ManagedBean(name = "grupoGestorController")
public class GrupoGestorController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private CandidatoDAO candidatoDAO = new CandidatoDAO();

	private ServidorDAO servidorDAO = new ServidorDAO();

	private DualListModel<Servidor> servidores;
	private List<Servidor> servidoresGrupoGestor; // Lista de todos menus,
													// exceto o de permiss�es

	private List<Candidato> candidatos; // lista de todos Tipos de Usu�rio
	private List<Servidor> servidoresGeral; // lista de todos Tipos de Usu�rio

	private Candidato candidato = new Candidato();

	/**
	 * Carrega os dados da tela
	 */
	@PostConstruct
	public void init() {
		try {

			candidatos = candidatoDAO.findAll();
			servidoresGeral = servidorDAO.findAll();

			List<Servidor> citiesSource = new ArrayList<Servidor>();
			citiesSource.addAll(servidoresGeral);

			List<Servidor> citiesTarget = new ArrayList<Servidor>();

			servidores = new DualListModel<Servidor>(citiesSource, citiesTarget);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * M�todo respons�vel por atualizar o PickList da view a partir do menu
	 * selecionado no combobox
	 * 
	 */
	public void carregaPermissoesCandidatoSelecionado(AjaxBehaviorEvent event) {

		if (candidato != null) {

		} else {
			servidores = new DualListModel<Servidor>(new ArrayList<Servidor>(), new ArrayList<Servidor>());
			return;
		}

		try {

			// Pega os grupos de usu�rio que tem permiss�o a um determinado menu
			List<Servidor> listaPermissoesServidor = candidatoDAO.loadServidorByCandidato(candidato.getId());

			// Adiciona todos os grupos de usu�rio na origem por default
			List<Servidor> listaGrupoOrigem = new ArrayList<Servidor>();

			servidoresGeral.removeAll(listaPermissoesServidor);
			listaGrupoOrigem.addAll(servidoresGeral);

			List<Servidor> listaGrupoDestino = listaPermissoesServidor;

			servidores = new DualListModel<Servidor>(listaGrupoOrigem, listaGrupoDestino);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * M�todo respons�vel por finalizar a edi��o dos campos e submeter o
	 * formul�rio
	 * 
	 */
	public void finalizaEdicao() {

		try {

			// retorna o valor somente como String e n�o objeto
			List<Servidor> listaGruposOrigem = servidores.getSource();
			List<Servidor> listaGruposDestino = servidores.getTarget();
			candidato.getGrupoGestor().clear();
			candidato.setGrupoGestor(listaGruposDestino);

			candidatoDAO.update(candidato); // insere nas
											// permiss�es

			// Adiciona mensagem de confirma��o
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Confirma��o", "Permiss�es para o Menu "
					+ candidato.getServidor().getPessoa().getNome() + " atualizadas com sucesso!"));

		} catch (Exception e) {
			e.printStackTrace();
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Erro",
					"Erro ao atualizar permiss�es para o Menu " + candidato.getServidor().getPessoa().getNome() + "!"));
		}

	}

	public DualListModel<Servidor> getServidores() {
		return servidores;
	}

	public void setServidores(DualListModel<Servidor> servidores) {
		this.servidores = servidores;
	}

	public List<Servidor> getServidoresGrupoGestor() {
		return servidoresGrupoGestor;
	}

	public void setServidoresGrupoGestor(List<Servidor> servidoresGrupoGestor) {
		this.servidoresGrupoGestor = servidoresGrupoGestor;
	}

	public List<Candidato> getCandidatos() {
		return candidatos;
	}

	public void setCandidatos(List<Candidato> candidatos) {
		this.candidatos = candidatos;
	}

	public Candidato getCandidato() {
		return candidato;
	}

	public void setCandidato(Candidato candidato) {
		this.candidato = candidato;
	}
}