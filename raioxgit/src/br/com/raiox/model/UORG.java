package br.com.raiox.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.raiox.generic.IGenericEntity;
import br.com.raiox.util.StringUtil;

/**
 * Pojo UORG
 * 
 * @author daniel alencar barros tavares
 * @version 1.0
 * 
 * @since 07/03/2016
 */
@Entity
@NamedQueries(value = { @NamedQuery(name = "UORG.findByID", query = "SELECT c FROM UORG c " + "WHERE c.id = ?1 ") })
@Table(name = "uorg")
public class UORG extends AbstractEntity implements IGenericEntity<UORG> {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	@Transient
	public static final String FIND_BY_ID = "UORG.findByID";

	@Id
	@Column(name = "id_uorg")
	@GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "ds_uorg")
	private String dsUORG;

	@Column(name = "obs_uorg")
	private String observacao;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_campus")
	private Campus campus;

	@OneToMany(mappedBy = "uorgLotacao", fetch = FetchType.LAZY)
	private List<Servidor> servidorLotacaos;

	@OneToMany(mappedBy = "uorgExercicio", fetch = FetchType.LAZY)
	private List<Servidor> serividorExercicios;

	@ManyToMany(mappedBy = "acessoUorgsUsuarios")
	private List<Usuario> usuarios;

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public List<Servidor> getServidorLotacaos() {
		return servidorLotacaos;
	}

	public void setServidorLotacaos(List<Servidor> servidorLotacaos) {
		this.servidorLotacaos = servidorLotacaos;
	}

	public List<Servidor> getSerividorExercicios() {
		return serividorExercicios;
	}

	public void setSerividorExercicios(List<Servidor> serividorExercicios) {
		this.serividorExercicios = serividorExercicios;
	}

	public Campus getCampus() {
		return campus;
	}

	public void setCampus(Campus campus) {
		this.campus = campus;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDsUORG() {
		return dsUORG;
	}

	public void setDsUORG(String dsUORG) {
		this.dsUORG = dsUORG;
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
		final UORG other = (UORG) obj;
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
		return id + "-" + dsUORG + " - " + (campus != null && campus.getId() != null ? campus.getDsCampus() : "");
	}

	/*
	 * Validar entidade
	 */
	public String validate() {
		String validate = "";
		if (StringUtil.isNullOrEmpty(dsUORG)) {
			validate += " Descrição é Obrigatório!";
		}
		if (campus == null) {
			validate += " Campus é Obrigatório!";
		}
		return validate;
	}
}
