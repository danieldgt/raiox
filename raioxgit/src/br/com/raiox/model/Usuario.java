package br.com.raiox.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import br.com.raiox.generic.IGenericEntity;

/**
 * Pojo Usuario
 * 
 * @author daniel alencar barros tavares
 * @version 1.0
 * 
 * @since 07/03/2016
 */
@Entity
@NamedQueries(value = { @NamedQuery(name = "Usuario.findByEmailSenha", query = "SELECT c FROM Usuario c "
		+ "WHERE c.login = ?1 AND c.senha = ?2") })
@Table(name = "usuario")
public class Usuario extends AbstractEntity implements IGenericEntity<Usuario> {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	@Transient
	public static final String FIND_BY_EMAIL_SENHA = "Usuario.findByEmailSenha";

	@Id
	@Column(name = "id_usuario")
	@GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	private Integer id;

	@Column
	private String senha;

	@Column
	private String login;

	@Column(name = "data_cadastro")
	@Temporal(TemporalType.DATE)
	private Date dataCadastro;

	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_pessoa")
	private Pessoa pessoa;

	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_tipo_usuario")
	private TipoUsuario tipoUsuario;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "uorgs_usuarios", joinColumns = @JoinColumn(name = "id_usuario") , inverseJoinColumns = @JoinColumn(name = "id_uorg") )
	private List<UORG> acessoUorgsUsuarios;

	public TipoUsuario getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(TipoUsuario tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public List<UORG> getAcessoUorgsUsuarios() {
		return acessoUorgsUsuarios;
	}

	public void setAcessoUorgsUsuarios(List<UORG> acessoUorgsUsuarios) {
		this.acessoUorgsUsuarios = acessoUorgsUsuarios;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha.trim();
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Usuario other = (Usuario) obj;
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
		return id + "-" + login;
	}

	/*
	 * Validar entidade
	 */
	public boolean validate() {
		if ((login != null && !login.isEmpty()) && senha != null && !senha.isEmpty()) {
			if (id != null && id == 0) { // campo hidden tem por default o valor
											// 0
				id = null;
			}
			return true;
		} else {
			return false;
		}
	}
}
