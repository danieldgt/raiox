package br.com.raiox.model;

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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import br.com.raiox.generic.IGenericEntity;
import br.com.raiox.util.StringUtil;

/**
 * Pojo Candidato
 * 
 * @author daniel alencar barros tavares
 * @version 1.0
 * 
 * @since 07/03/2016
 */
@Entity
@NamedQueries(value = {
		@NamedQuery(name = "Candidato.findByID ", query = "SELECT c FROM Candidato c " + "WHERE c.id = ?1 ") })
@Table(name = "candidato")
public class Candidato extends AbstractEntity implements IGenericEntity<Candidato> {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	@Transient
	public static final String FIND_BY_ID = "Candidato.findByID";

	@Id
	@Column(name = "id_candidato")
	@GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "obs_candidato")
	private String observacao;

	@Column(name = "color")
	private String color;

	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_servidor")
	private Servidor servidor;

	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_eleicao")
	private Eleicao eleicao;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "grupo_gesto_candidato_servidor", joinColumns = @JoinColumn(name = "id_candidato") , inverseJoinColumns = @JoinColumn(name = "id_servidor") )
	private List<Servidor> grupoGestor;

	public List<Servidor> getGrupoGestor() {
		return grupoGestor;
	}

	public void setGrupoGestor(List<Servidor> grupoGestor) {
		this.grupoGestor = grupoGestor;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Servidor getServidor() {
		return servidor;
	}

	public void setServidor(Servidor servidor) {
		this.servidor = servidor;
	}

	public Eleicao getEleicao() {
		return eleicao;
	}

	public void setEleicao(Eleicao eleicao) {
		this.eleicao = eleicao;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Candidato other = (Candidato) obj;
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
		return id + " - " + servidor.getPessoa().getNome();
	}

	/*
	 * Validar entidade TODO: quando for fazer a tela de cadastro servidor; o
	 * procedimento deve obrigar o usuário a informar pessoa
	 */
	public String validate() {
		String validate = "";
		if (StringUtil.isNullOrEmpty(observacao)) {
			validate += "observacao é Obrigatório!";
		}
		if (servidor == null) {
			validate += " Servidor Publico é Obrigatório!";
		}
		if (eleicao == null) {
			validate += " Eleição de concorrência é Obrigatório!";
		}
		return validate;
	}
}
