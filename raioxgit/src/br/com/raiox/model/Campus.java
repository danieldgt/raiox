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
 * Pojo Campus
 * 
 * @author daniel alencar barros tavares
 * @version 1.0
 * 
 * @since 07/03/2016
 */
@Entity
@NamedQueries(value = { @NamedQuery(name = "Campus.findByID", query = "SELECT c FROM Campus c " + "WHERE c.id = ?1 ") })
@Table(name = "campus")
public class Campus extends AbstractEntity implements IGenericEntity<Campus> {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	@Transient
	public static final String FIND_BY_ID = "Campus.findByID";

	@Id
	@Column(name = "id_campus")
	@GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "ds_campus")
	private String dsCampus;

	@OneToMany(mappedBy = "campus", fetch = FetchType.LAZY)
	private List<UORG> uorgs;

	public List<UORG> getUorgs() {
		return uorgs;
	}

	public void setUorgs(List<UORG> uorgs) {
		this.uorgs = uorgs;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDsCampus() {
		return dsCampus;
	}

	public void setDsCampus(String dsCampus) {
		this.dsCampus = dsCampus;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Campus other = (Campus) obj;
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
		return id + "-" + dsCampus;
	}

	/*
	 * Validar entidade
	 */
	public String validate() {
		String validate = "";
		if (StringUtil.isNullOrEmpty(dsCampus)) {
			validate += "Descrição é Obrigatório!";
		}
		return validate;
	}
}
