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

import br.com.raiox.dao.UORGDAO;
import br.com.raiox.dao.UsuarioDAO;
import br.com.raiox.model.UORG;
import br.com.raiox.model.Usuario;

@ViewScoped
@ManagedBean(name = "usuarioUorgController")
public class UsuarioUORGController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private UsuarioDAO usuarioDAO = new UsuarioDAO();

	private UORGDAO servidorDAO = new UORGDAO();

	private DualListModel<UORG> servidores;
	private List<UORG> servidoresGrupoGestor; // Lista de todos menus,
													// exceto o de permiss�es

	private List<Usuario> usuarios; // lista de todos Tipos de Usu�rio
	private List<UORG> servidoresGeral; // lista de todos Tipos de Usu�rio

	private Usuario usuario = new Usuario();

	/**
	 * Carrega os dados da tela
	 */
	@PostConstruct
	public void init() {
		try {

			usuarios = usuarioDAO.findAll();
			servidoresGeral = servidorDAO.findAll();

			List<UORG> citiesSource = new ArrayList<UORG>();
			citiesSource.addAll(servidoresGeral);

			List<UORG> citiesTarget = new ArrayList<UORG>();

			servidores = new DualListModel<UORG>(citiesSource, citiesTarget);

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
	public void carregaPermissoesUsuarioSelecionado(AjaxBehaviorEvent event) {

		if (usuario != null) {

		} else {
			servidores = new DualListModel<UORG>(new ArrayList<UORG>(), new ArrayList<UORG>());
			return;
		}

		try {

			// Pega os grupos de usu�rio que tem permiss�o a um determinado menu
			List<UORG> listaPermissoesUORG = usuarioDAO.loadUORGByUsuario(usuario.getId());

			// Adiciona todos os grupos de usu�rio na origem por default
			List<UORG> listaGrupoOrigem = new ArrayList<UORG>();

			servidoresGeral.removeAll(listaPermissoesUORG);
			listaGrupoOrigem.addAll(servidoresGeral);

			List<UORG> listaGrupoDestino = listaPermissoesUORG;

			servidores = new DualListModel<UORG>(listaGrupoOrigem, listaGrupoDestino);

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
			List<UORG> listaGruposOrigem = servidores.getSource();
			List<UORG> listaGruposDestino = servidores.getTarget();
			usuario.getAcessoUorgsUsuarios().clear();
			usuario.setAcessoUorgsUsuarios(listaGruposDestino);

			usuarioDAO.update(usuario); // insere nas
											// permiss�es

			// Adiciona mensagem de confirma��o
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Confirma��o", "Permiss�es para o Menu "
					+ usuario.getPessoa().getNome() + " atualizadas com sucesso!"));

		} catch (Exception e) {
			e.printStackTrace();
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Erro",
					"Erro ao atualizar permiss�es para o Menu " + usuario.getPessoa().getNome() + "!"));
		}

	}

	public DualListModel<UORG> getUORGes() {
		return servidores;
	}

	public void setUORGes(DualListModel<UORG> servidores) {
		this.servidores = servidores;
	}

	public List<UORG> getUORGesGrupoGestor() {
		return servidoresGrupoGestor;
	}

	public void setUORGesGrupoGestor(List<UORG> servidoresGrupoGestor) {
		this.servidoresGrupoGestor = servidoresGrupoGestor;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}