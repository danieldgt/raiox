package br.com.raiox.controller;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.LazyDataModel;

import br.com.raiox.dao.PessoaDAO;
import br.com.raiox.dao.lazy.LazyDataModelPessoa;
import br.com.raiox.model.Pessoa;
import br.com.raiox.util.StringUtil;

@ViewScoped
@ManagedBean(name = "pessoaController")
public class PessoaController implements Serializable {
	private static final long serialVersionUID = 1L;

	private Pessoa pessoa = new Pessoa();
	private PessoaDAO pessoaDAO = new PessoaDAO();
	private LazyDataModel<Pessoa> pessoas;

	@PostConstruct
	public void init() {
		pessoaDAO = new PessoaDAO();
		pessoas = getPessoas();
	}

	/**
	 * Chama o lazy data model do objeto para realizar consulta sob demanda
	 * 
	 * @return
	 */
	public LazyDataModel<Pessoa> getPessoas() {
		if (pessoas == null) {
			pessoas = new LazyDataModelPessoa(pessoaDAO);
		}
		return pessoas;
	}

	public Integer tamanhoLista() {
		return pessoas.getRowCount();
	}

	public void save() {
		// DAO save the add, load list
		try {
			if (!StringUtil.isNullOrEmpty(pessoa.validate())) {
				FacesContext.getCurrentInstance().addMessage("growlMessage",
						new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", pessoa.validate()));
				return;
			}

			pessoaDAO.update(pessoa);
			// for="growlMessage"
			FacesContext.getCurrentInstance().addMessage("growlMessage",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensagem", "Salvo com sucesso."));
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("growlMessage",
					new FacesMessage(FacesMessage.SEVERITY_FATAL, "Contate o admin do sistema", e.getMessage()));
			// TODO: handle exception
		}
		pessoa = new Pessoa();

	}

	public void edit() {

	}

	public void limpar() {
		this.pessoa = new Pessoa();
	}

	public void delete() throws IOException {
		// DAO save the add, load list
		try {
			pessoaDAO.remove(pessoa);
			// for="growlMessage"
			FacesContext.getCurrentInstance().addMessage("growlMessage",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensagem", "Removido com sucesso."));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("growlMessage",
					new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erro", "Ocorreu um erro ao remover os dados!"));
			// TODO: handle exception
		}
		pessoa = new Pessoa();
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	
}
