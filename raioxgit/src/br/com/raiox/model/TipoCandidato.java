package br.com.raiox.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.raiox.generic.IGenericEntity;
import br.com.raiox.util.StringUtil;

/**
 * Pojo TipoCandidato
 * 
 * @author daniel alencar barros tavares
 * @version 1.0
 * 
 * @since 07/03/2016
 */
@Entity
@NamedQueries(value = {
		@NamedQuery(name = "TipoCandidato.findByID", query = "SELECT c FROM TipoCandidato c " + "WHERE c.id = ?1 ") })
@Table(name = "tipo_candidato")
public class TipoCandidato extends AbstractEntity implements IGenericEntity<TipoCandidato> {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	@Transient
	public static final String FIND_BY_ID = "TipoCandidato.findByID";

	@Id
	@Column(name = "id_tipo_candidato")
	@GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "ds_tipo_candidato")
	private String dsTipoCandidato;

	@OneToMany(mappedBy = "tipoCandidato", fetch = FetchType.LAZY)
	private List<Eleicao> eleicaos;

	public List<Eleicao> getEleicaos() {
		return eleicaos;
	}

	public void setEleicaos(List<Eleicao> eleicaos) {
		this.eleicaos = eleicaos;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDsTipoCandidato() {
		return dsTipoCandidato;
	}

	public void setDsTipoCandidato(String dsTipoCandidato) {
		this.dsTipoCandidato = dsTipoCandidato;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final TipoCandidato other = (TipoCandidato) obj;
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
		return id + "-" + dsTipoCandidato;
	}

	/*
	 * Validar entidade
	 */
	public String validate() {
		String validate = "";
		if (StringUtil.isNullOrEmpty(dsTipoCandidato)) {
			validate += "Descrição é Obrigatório!";
		}
		return validate;
	}
}
