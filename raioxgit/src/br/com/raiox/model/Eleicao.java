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
import br.com.raiox.util.StringUtil;

/**
 * Pojo Eleicao
 * 
 * @author daniel alencar barros tavares
 * @version 1.0
 * 
 * @since 07/03/2016
 */
@Entity
@NamedQueries(value = {
		@NamedQuery(name = "Eleicao.findByID", query = "SELECT c FROM Eleicao c " + "WHERE c.id = ?1 ") })
@Table(name = "eleicao")
public class Eleicao extends AbstractEntity implements IGenericEntity<Eleicao> {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	@Transient
	public static final String FIND_BY_ID = "Eleicao.findByID";

	@Id
	@Column(name = "id_eleicao")
	@GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "ds_eleicao")
	private String dsEleicao;

	@Column(name = "dt_inicio")
	@Temporal(TemporalType.DATE)
	private Date dtInicio;

	@Column(name = "dt_fim")
	@Temporal(TemporalType.DATE)
	private Date dtFim;

	@Column(name = "is_vigente")
	private boolean isVigente;

	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_tipo_candidato")
	private TipoCandidato tipoCandidato;

	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_campus")
	private Campus campus;

	public TipoCandidato getTipoCandidato() {
		return tipoCandidato;
	}

	public void setTipoCandidato(TipoCandidato tipoCandidato) {
		this.tipoCandidato = tipoCandidato;
	}

	public boolean isVigente() {
		return isVigente;
	}

	public void setVigente(boolean isVigente) {
		this.isVigente = isVigente;
	}

	public Date getDtInicio() {
		return dtInicio;
	}

	public void setDtInicio(Date dtInicio) {
		this.dtInicio = dtInicio;
	}

	public Campus getCampus() {
		return campus;
	}

	public void setCampus(Campus campus) {
		this.campus = campus;
	}

	public Date getDtFim() {
		return dtFim;
	}

	public void setDtFim(Date dtFim) {
		this.dtFim = dtFim;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDsEleicao() {
		return dsEleicao;
	}

	public void setDsEleicao(String dsEleicao) {
		this.dsEleicao = dsEleicao;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Eleicao other = (Eleicao) obj;
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
		return id + "-" + dsEleicao + " - " + tipoCandidato.getDsTipoCandidato();
	}

	/*
	 * Validar entidade
	 */
	public String validate() {
		String validate = "";
		if (StringUtil.isNullOrEmpty(dsEleicao)) {
			validate += " Descrição é Obrigatório!";
		}
		if (dtInicio == null) {
			validate += " Data inicio é Obrigatório!";
		}
		if (dtFim == null) {
			validate += " Data Fim é Obrigatório!";
		}
		if (campus == null) {
			validate += " Campus é Obrigatório!";
		}
		return validate;
	}
}
