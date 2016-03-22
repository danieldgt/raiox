package br.com.raiox.controller;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.LazyDataModel;

import br.com.raiox.dao.CargoDAO;
import br.com.raiox.dao.lazy.LazyDataModelCargo;
import br.com.raiox.model.Cargo;
import br.com.raiox.util.StringUtil;

@ViewScoped
@ManagedBean(name = "cargoController")
public class CargoController implements Serializable {
	private static final long serialVersionUID = 1L;

	private Cargo cargo = new Cargo();
	private CargoDAO cargoDAO = new CargoDAO();
	private LazyDataModel<Cargo> cargos;

	@PostConstruct
	public void init() {
		cargoDAO = new CargoDAO();
		cargos = getCargos();
	}

	/**
	 * Chama o lazy data model do objeto para realizar consulta sob demanda
	 * 
	 * @return
	 */
	public LazyDataModel<Cargo> getCargos() {
		if (cargos == null) {
			cargos = new LazyDataModelCargo(cargoDAO);
		}
		return cargos;
	}

	public Integer tamanhoLista() {
		return cargos.getRowCount();
	}

	public void save() {
		// DAO save the add, load list
		try {
			if (!StringUtil.isNullOrEmpty(cargo.validate())) {
				FacesContext.getCurrentInstance().addMessage("growlMessage",
						new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", cargo.validate()));
				return;
			}

			cargoDAO.update(cargo);
			// for="growlMessage"
			FacesContext.getCurrentInstance().addMessage("growlMessage",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensagem", "Salvo com sucesso."));
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("growlMessage",
					new FacesMessage(FacesMessage.SEVERITY_FATAL, "Contate o admin do sistema", e.getMessage()));
			// TODO: handle exception
		}
		cargo = new Cargo();

	}

	public void edit() {

	}

	public void limpar() {
		this.cargo = new Cargo();
	}

	public void delete() throws IOException {
		// DAO save the add, load list
		try {
			cargoDAO.remove(cargo);
			// for="growlMessage"
			FacesContext.getCurrentInstance().addMessage("growlMessage",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensagem", "Removido com sucesso."));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("growlMessage",
					new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erro", "Ocorreu um erro ao remover os dados!"));
			// TODO: handle exception
		}
		cargo = new Cargo();
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	
}
