package br.com.raiox.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.com.raiox.generic.IGenericEntity;
import br.com.raiox.util.CPF;
import br.com.raiox.util.StringUtil;

/**
 * Pojo Pessoa
 * 
 * @author daniel alencar barros tavares
 * @version 1.0
 * 
 * @since 07/03/2016
 */
@Entity
@NamedQueries(value = { @NamedQuery(name = "Pessoa.findByID", query = "SELECT c FROM Pessoa c " + "WHERE c.id = ?1 ") })
@Table(name = "pessoa")
public class Pessoa extends AbstractEntity implements IGenericEntity<Pessoa> {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	@Transient
	public static final String FIND_BY_ID = "Pessoa.findByID";

	@Id
	@Column(name = "id_pessoa")
	@GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "nome")
	private String nome;
	
	@Column(name = "sexo")
	private String sexo;

	@Column(name = "cpf", unique = true)
	private String cpf;

	@OneToMany(mappedBy = "pessoa", fetch = FetchType.LAZY)
	private List<Usuario> usuarios;

	@OneToMany(mappedBy = "pessoa", fetch = FetchType.EAGER)
	private Set<Servidor> servidors;

	@Column(name = "dt_nascimento")
	@Temporal(TemporalType.DATE)
	private Date dtNascimento;

	public Date getDtNascimento() {
		return dtNascimento;
	}

	public void setDtNascimento(Date dtNascimento) {
		this.dtNascimento = dtNascimento;
	}

	public List<Servidor> getServidors() {
		return new ArrayList<>(servidors);
	}

	public void setServidors(Set<Servidor> servidors) {
		this.servidors = servidors;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Pessoa other = (Pessoa) obj;
		if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 89 * hash + (this.id != null ? this.id.hashCode() : 0);
		return hash;
	}

	@Override
	public String toString() {
		return id + "-" + nome;
	}

	/*
	 * Validar entidade
	 */
	public String validate() {
		String validate = "";
		if (StringUtil.isNullOrEmpty(nome)) {
			validate += "Nome Obrigatório!";
		}
		if (StringUtil.isNullOrEmpty(cpf)) {
			validate += " CPF Obrigatório!";
			return validate;
		}
		if (!CPF.isCPF(cpf)) {
			validate += " CPF Inválido!";
		}
		cpf = cpf.replaceAll("\\(|\\)|\\-|\\.", "");

		return validate;
	}

}
