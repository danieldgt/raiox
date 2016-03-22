package br.com.raiox.model;


import java.io.Serializable;

/**
 * Entidade Abstrata que facilita a criação de converters no JSF
 * Toda entidade que deva ser listada em um combobox deve herdar essa entidade
 * 
 * @author Thiago Queiroz
 *
 * @since 11/03/2013
 */
public abstract class AbstractEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractEntity other = (AbstractEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}