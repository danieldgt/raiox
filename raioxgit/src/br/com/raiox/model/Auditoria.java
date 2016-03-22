package br.com.raiox.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.raiox.generic.IGenericEntity;
import br.com.raiox.util.TransactionType;

@Entity
@Table(name = "auditoria")
public class Auditoria extends AbstractEntity implements IGenericEntity<Auditoria> {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_auditoria")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "transaction_type", length = 10)
	@Enumerated(EnumType.STRING)
	private TransactionType transactionType;

	@Column(name = "entidade", length = 80)
	private String entityName;

	@Column(name = "registro")
	private Long registryId;
	
	@Column(name = "to_string", columnDefinition="TEXT")
	private String toString;

	@Column(name = "dt_operacao")
	@Temporal(TemporalType.TIMESTAMP)
	private Date operationDate;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public Long getRegistryId() {
		return registryId;
	}

	public void setRegistryId(Long registryId) {
		this.registryId = registryId;
	}

	public Date getOperationDate() {
		return operationDate;
	}

	public void setOperationDate(Date operationDate) {
		this.operationDate = operationDate;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getToString() {
		return toString;
	}

	public void setToString(String toString) {
		this.toString = toString;
	}

	@Override
	public String toString() {
		return id.toString();
	}
}