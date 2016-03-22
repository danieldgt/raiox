package br.com.raiox.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpSession;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;

import br.com.raiox.dao.CandidatoDAO;
import br.com.raiox.dao.IntencaoVotoDAO;
import br.com.raiox.dao.ServidorDAO;
import br.com.raiox.dao.UsuarioDAO;
import br.com.raiox.exception.DAOException;
import br.com.raiox.model.Candidato;
import br.com.raiox.model.IntencaoVoto;
import br.com.raiox.model.Servidor;
import br.com.raiox.model.UORG;
import br.com.raiox.model.Usuario;
import br.com.raiox.transiente.InformacaoVotoServidor;

@ViewScoped
@ManagedBean(name = "informacaoVotoController")
public class InformacaoVotoController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	UsuarioDAO usuarioDAO = new UsuarioDAO();
	private IntencaoVoto intencaoVoto = new IntencaoVoto();
	private Servidor servidor = new Servidor();
	private Usuario usuario = new Usuario();
	private UORG uorg = new UORG();
	private List<UORG> uorgs;

	private IntencaoVotoDAO intencaoVotoDAO = new IntencaoVotoDAO();

	private List<Servidor> servidors;
	private ServidorDAO servidorDAO = new ServidorDAO();

	List<InformacaoVotoServidor> listaServidorUltimaIntencao;
	private InformacaoVotoServidor informacaoVotoServidor = new InformacaoVotoServidor();

	private CandidatoDAO candidatoDAO = new CandidatoDAO();
	private Candidato Candidato = new Candidato();
	private List<Candidato> candidatos;

	@PostConstruct
	public void init() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);

		UsuarioLogado usuarioLogado = (UsuarioLogado) session.getAttribute("usuarioLogado");
		usuario = usuarioLogado.getUsuario();
		candidatos = candidatoDAO.findAll();
		/*
		 * TODO: o usuário pode ser vinculado a mais de 1 servidor. dessa forma
		 * estou supondo que caso os servidores vinculados a uma pessoa
		 * pertençam a mesma UORG, por isso estou pegando primeiro da lista. Um
		 * melhoria será informar qual servidor tem perfil principal vinculado a
		 * um usuário. Neste momento do sistema, esse é um requisito dispensável
		 * e não possue necessidade para tal critério.
		 */
		uorg = usuario.getPessoa().getServidors().get(0).getUorgLotacao();
		servidors = servidorDAO.getServidoresPorUorgLotacao(uorg.getId());
		uorgs = usuarioDAO.loadUORGByUsuario(usuario.getId());
		if (!uorgs.contains(uorg))
			uorgs.add(uorg);

		System.out.println("USUÁRIO ENTROU NA TELA DE INFORMAÇÃO DE VOTO: " + usuario.getPessoa().getNome() + " as: "
				+ new Date());

		listaServidorUltimaIntencao = InformacaoVotoServidor.listarIntencaoVoto(servidors, usuario);
	}

	public Integer tamanhoLista() {
		return servidors.size();
	}

	public void listarServidoresPorUorg() {
		servidors = servidorDAO.getServidoresPorUorgLotacao(uorg.getId());
		listaServidorUltimaIntencao = InformacaoVotoServidor.listarIntencaoVoto(servidors, usuario);
		tamanhoLista();
	}

	public IntencaoVoto getIntencaoVoto() {
		return intencaoVoto;
	}

	public void setIntencaoVoto(IntencaoVoto intencaoVoto) {
		this.intencaoVoto = intencaoVoto;
	}

	public List<Servidor> getServidors() {
		return servidors;
	}

	public void setServidors(List<Servidor> servidors) {
		this.servidors = servidors;
	}

	public Servidor getServidor() {
		return servidor;
	}

	public void setServidor(Servidor servidor) {
		this.servidor = servidor;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public UORG getUorg() {
		return uorg;
	}

	public void setUorg(UORG uorg) {
		this.uorg = uorg;
	}

	public List<InformacaoVotoServidor> getListaServidorUltimaIntencao() {
		return listaServidorUltimaIntencao;
	}

	public void setListaServidorUltimaIntencao(List<InformacaoVotoServidor> listaServidorUltimaIntencao) {
		this.listaServidorUltimaIntencao = listaServidorUltimaIntencao;
	}

	public InformacaoVotoServidor getInformacaoVotoServidor() {
		return informacaoVotoServidor;
	}

	public void setInformacaoVotoServidor(InformacaoVotoServidor informacaoVotoServidor) {
		this.informacaoVotoServidor = informacaoVotoServidor;
	}

	public List<UORG> getUorgs() {
		return uorgs;
	}

	public void setUorgs(List<UORG> uorgs) {
		this.uorgs = uorgs;
	}

	public Candidato getCandidato() {
		return Candidato;
	}

	public void setCandidato(Candidato candidato) {
		Candidato = candidato;
	}

	public List<Candidato> getCandidatos() {
		return candidatos;
	}

	public void setCandidatos(List<Candidato> candidatos) {
		this.candidatos = candidatos;
	}

	public void onValueQuantityChange(ValueChangeEvent event) {
		Object t = (Object) event.getNewValue();
		// System.out.println(t);
	}

	public void onCellEdit(CellEditEvent event) {
		// valor antes da ediçao
		Object informacaoAntiga = event.getOldValue();
		// valor editado
		List informacaoNova = (List) event.getNewValue();

		// linha que foi alterada
		InformacaoVotoServidor rowEditInformacaoVotoServidor = (InformacaoVotoServidor) ((DataTable) event.getSource())
				.getRowData();

		if (informacaoNova != null && !informacaoNova.equals(informacaoAntiga)) {

			if ((Candidato) informacaoNova.get(1) != null) {
				IntencaoVoto intencaoVoto = new IntencaoVoto();
				intencaoVoto.setCandidato((Candidato) informacaoNova.get(1));
				intencaoVoto.setConfianca((Integer) informacaoNova.get(0));
				intencaoVoto.setDtInformacao(new Date());
				intencaoVoto.setObservacao("Gerado Datagrid CellEdit");
				intencaoVoto.setUsuario(usuario);
				intencaoVoto.setServidorIntencao(rowEditInformacaoVotoServidor.getServidor());

				try {
					intencaoVotoDAO.add(intencaoVoto);
					listarServidoresPorUorg();
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Obrigado pela informação!");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			} else {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta",
						"Por favor, informar Precisão e Cor");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}

		}

	}
}
