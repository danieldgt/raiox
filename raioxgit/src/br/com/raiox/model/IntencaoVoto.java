package br.com.raiox.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
 * Pojo IntencaoVoto
 * 
 * @author daniel alencar barros tavares
 * @version 1.0
 * 
 * @since 07/03/2016
 */
@Entity
@NamedQueries(value = {
		@NamedQuery(name = "IntencaoVoto.findByID ", query = "SELECT c FROM IntencaoVoto c " + "WHERE c.id = ?1 ") })
@Table(name = "intencao_voto")
public class IntencaoVoto extends AbstractEntity implements IGenericEntity<IntencaoVoto> {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	@Transient
	public static final String FIND_BY_ID = "IntencaoVoto.findByID";

	@Id
	@Column(name = "id_intencao_voto")
	@GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "obs_intencaoVoto")
	private String observacao;

	@Column(name = "confianca_informacao")
	private Integer confianca;

	@Column(name = "dt_informacao")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dtInformacao;
	
	/*
	 * servidor da intencao de voto
	 */
	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_servidor_intencao")
	private Servidor servidorIntencao;

	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;

	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_candidato")
	private Candidato candidato;

	public Candidato getCandidato() {
		return candidato;
	}

	public void setCandidato(Candidato candidato) {
		this.candidato = candidato;
	}

	public Integer getConfianca() {
		return confianca;
	}

	public void setConfianca(Integer confianca) {
		this.confianca = confianca;
	}

	public Date getDtInformacao() {
		return dtInformacao;
	}

	public void setDtInformacao(Date dtInformacao) {
		this.dtInformacao = dtInformacao;
	}

	public Servidor getServidorIntencao() {
		return servidorIntencao;
	}

	public void setServidorIntencao(Servidor servidorIntencao) {
		this.servidorIntencao = servidorIntencao;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final IntencaoVoto other = (IntencaoVoto) obj;
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
		return id + "";
	}

	/*
	 * Validar entidade TODO: quando for fazer a tela de cadastro servidor; o
	 * procedimento deve obrigar o usuário a informar pessoa
	 */
	public String validate() {
		String validate = "";
		if (candidato == null) {
			validate += "Candidato é Obrigatório!";
		}
		if (servidorIntencao == null) {
			validate += "Servidor é Obrigatório!";
		}
		return validate;
	}
}
