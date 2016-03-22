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
 * Pojo TipoUsuario
 * 
 * @author daniel alencar barros tavares
 * @version 1.0
 * 
 * @since 07/03/2016
 */
@Entity
@NamedQueries(value = {
		@NamedQuery(name = "TipoUsuario.findByID", query = "SELECT c FROM TipoUsuario c " + "WHERE c.id = ?1 ") })
@Table(name = "tipo_usuario")
public class TipoUsuario extends AbstractEntity implements IGenericEntity<TipoUsuario> {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	@Transient
	public static final String FIND_BY_ID = "TipoUsuario.findByID";

	@Id
	@Column(name = "id_tipo_usuario")
	@GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	private Integer id;

	@Column
	private String dsTipoUsuario;

	@OneToMany(mappedBy = "tipoUsuario", fetch = FetchType.LAZY)
	private List<Usuario> usuarios;

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

	public String getDsTipoUsuario() {
		return dsTipoUsuario;
	}

	public void setDsTipoUsuario(String dsTipoUsuario) {
		this.dsTipoUsuario = dsTipoUsuario;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final TipoUsuario other = (TipoUsuario) obj;
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
		return id + "-" + dsTipoUsuario;
	}
	
	/*
	 * Validar entidade
	 */
	public String validate(){
		String validate = "";
		if(StringUtil.isNullOrEmpty(dsTipoUsuario)){
			validate += "Descrição é Obrigatório!";
		}
		return validate;
	}
}
