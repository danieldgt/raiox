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
 * Pojo Cargo
 * 
 * @author daniel alencar barros tavares
 * @version 1.0
 * 
 * @since 07/03/2016
 */
@Entity
@NamedQueries(value = { @NamedQuery(name = "Cargo.findByID", query = "SELECT c FROM Cargo c " + "WHERE c.id = ?1 ") })
@Table(name = "cargo")
public class Cargo extends AbstractEntity implements IGenericEntity<Cargo> {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	@Transient
	public static final String FIND_BY_ID = "Cargo.findByID";

	@Id
	@Column(name = "id_cargo")
	@GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "ds_cargo")
	private String dsCargo;

	@OneToMany(mappedBy = "cargo", fetch = FetchType.LAZY)
	private List<Servidor> servidor;

	public List<Servidor> getServidor() {
		return servidor;
	}

	public void setServidor(List<Servidor> servidor) {
		this.servidor = servidor;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDsCargo() {
		return dsCargo;
	}

	public void setDsCargo(String dsCargo) {
		this.dsCargo = dsCargo;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Cargo other = (Cargo) obj;
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
		return id + "-" + dsCargo;
	}

	/*
	 * Validar entidade
	 */
	public String validate() {
		String validate = "";
		if (StringUtil.isNullOrEmpty(dsCargo)) {
			validate += "Descrição é Obrigatório!";
		}
		return validate;
	}
}
