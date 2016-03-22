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

import br.com.raiox.dao.CargoDAO;
import br.com.raiox.dao.PessoaDAO;
import br.com.raiox.dao.ServidorDAO;
import br.com.raiox.dao.UORGDAO;
import br.com.raiox.dao.lazy.LazyDataModelServidor;
import br.com.raiox.model.Campus;
import br.com.raiox.model.Cargo;
import br.com.raiox.model.Pessoa;
import br.com.raiox.model.Servidor;
import br.com.raiox.model.UORG;
import br.com.raiox.util.StringUtil;

@ViewScoped
@ManagedBean(name = "servidorController")
public class ServidorController implements Serializable {
	private static final long serialVersionUID = 1L;

	private Servidor servidor = new Servidor();
	private Cargo cargo = new Cargo();
	private UORG uorgExercicio = new UORG();
	private UORG uorgLotacao = new UORG();
	private Pessoa pessoa = new Pessoa();

	private PessoaDAO pessoaDAO = new PessoaDAO();
	private UORGDAO uorgDAO = new UORGDAO();
	private ServidorDAO servidorDAO = new ServidorDAO();
	private CargoDAO cargoDAO = new CargoDAO();

	private LazyDataModel<Servidor> servidors;

	private List<Cargo> cargos;
	private List<Campus> campuss;
	private List<UORG> UORGs;
	private List<Pessoa> pessoas;

	@PostConstruct
	public void init() {
		servidorDAO = new ServidorDAO();
		servidors = getServidors();
		cargos = cargoDAO.findAll();
		UORGs = uorgDAO.findAll();
		pessoas = pessoaDAO.findAll();
	}

	/**
	 * Chama o lazy data model do objeto para realizar consulta sob demanda
	 * 
	 * @return
	 */
	public LazyDataModel<Servidor> getServidors() {
		if (servidors == null) {
			servidors = new LazyDataModelServidor(servidorDAO);
		}
		return servidors;
	}

	public Integer tamanhoLista() {
		return servidors.getRowCount();
	}

	public void save() {
		// DAO save the add, load list
		try {
			servidor.setCargo(cargo);
			servidor.setUorgExercicio(uorgExercicio);
			servidor.setUorgLotacao(uorgLotacao);
			servidor.setPessoa(pessoa);

			if (!StringUtil.isNullOrEmpty(servidor.validate())) {
				FacesContext.getCurrentInstance().addMessage("growlMessage",
						new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", servidor.validate()));
				return;
			}
			servidorDAO.update(servidor);
			// for="growlMessage"
			FacesContext.getCurrentInstance().addMessage("growlMessage",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensagem", "Salvo com sucesso."));
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("growlMessage",
					new FacesMessage(FacesMessage.SEVERITY_FATAL, "Contate o admin do sistema", e.getMessage()));
			// TODO: handle exception
		}
		servidor = new Servidor();
		cargo = new Cargo();
		uorgExercicio = new UORG();
		uorgLotacao = new UORG();
		pessoa = new Pessoa();
	}

	public void edit() {
		if (servidor.getCargo() != null && servidor.getCargo().getId() != null) {
			cargo = servidor.getCargo();
		}
		if (servidor.getPessoa() != null && servidor.getPessoa().getId() != null) {
			pessoa = servidor.getPessoa();
		}
		if (servidor.getUorgExercicio() != null && servidor.getUorgExercicio().getId() != null) {
			uorgExercicio = servidor.getUorgExercicio();
		}
		if (servidor.getUorgLotacao() != null && servidor.getUorgLotacao().getId() != null) {
			uorgLotacao = servidor.getUorgLotacao();
		}
	}

	public void limpar() {
		pessoa = new Pessoa();
		servidor = new Servidor();
		cargo = new Cargo();
		uorgExercicio = new UORG();
		uorgLotacao = new UORG();
		pessoa = new Pessoa();
	}

	public void delete() throws IOException {
		// DAO save the add, load list
		try {
			servidorDAO.remove(servidor);
			// for="growlMessage"
			FacesContext.getCurrentInstance().addMessage("growlMessage",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensagem", "Removido com sucesso."));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("growlMessage",
					new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erro", "Ocorreu um erro ao remover os dados!"));
			// TODO: handle exception
		}
		servidor = new Servidor();
	}

	public Servidor getServidor() {
		return servidor;
	}

	public void setServidor(Servidor servidor) {
		this.servidor = servidor;
	}

	public List<Cargo> getCargos() {
		return cargos;
	}

	public void setCargos(List<Cargo> cargos) {
		this.cargos = cargos;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public UORG getUorgExercicio() {
		return uorgExercicio;
	}

	public void setUorgExercicio(UORG uorgExercicio) {
		this.uorgExercicio = uorgExercicio;
	}

	public UORG getUorgLotacao() {
		return uorgLotacao;
	}

	public void setUorgLotacao(UORG uorgLotacao) {
		this.uorgLotacao = uorgLotacao;
	}

	public void setServidors(LazyDataModel<Servidor> servidors) {
		this.servidors = servidors;
	}

	public List<UORG> getUORGs() {
		return UORGs;
	}

	public void setUORGs(List<UORG> uORGs) {
		UORGs = uORGs;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

}
