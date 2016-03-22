package br.com.raiox.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import br.com.raiox.generic.IGenericEntity;
import br.com.raiox.util.StringUtil;

/**
 * Pojo Servidor
 * 
 * @author daniel alencar barros tavares
 * @version 1.0
 * 
 * @since 07/03/2016
 */
@Entity
@NamedQueries(value = { @NamedQuery(name = "Servidor.findByNomeSiape", query = "SELECT c FROM Servidor c "
		+ "WHERE c.pessoa.nome = ?1 AND c.siape = ?2") })
@Table(name = "servidor")
public class Servidor extends AbstractEntity implements IGenericEntity<Servidor> {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	@Transient
	public static final String FIND_BY_NOME_SIAPE = "Servidor.findByNomeSiape";

	@Id
	@Column(name = "id_servidor")
	@GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	private Integer id;

	@Column
	private String siape;

	@Column
	private String titulacao;

	@Column
	private String escolaridade;

	@Column
	private String regimeJuridico;

	@Column
	private String codigoVaga;

	@Column
	private String situacao;

	@Column(name = "dt_ingresso_orgao")
	@Temporal(TemporalType.DATE)
	private Date dtIngressoOrgao;

	@Column(name = "dt_ingresso_servico_publico")
	@Temporal(TemporalType.DATE)
	private Date dtIngressoServicoPublico;

	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_pessoa")
	private Pessoa pessoa;

	/**
	 * O órgão de lotação é aquele ao qual o servidor está administrativamente
	 * vinculado, em virtude da sua forma de ingresso no serviço público. O
	 * órgão de exercício é aquele no qual o servidor está efetivamente
	 * desempenhando suas atividades.
	 * 
	 * http://www.planejamento.gov.br/acesso-a-informacao/recursos-humanos/
	 * servidores
	 */
	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_uorg_lotacao")
	private UORG uorgLotacao;

	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_uorg_exercicio")
	private UORG uorgExercicio;

	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_cargo")
	private Cargo cargo;

	@OneToMany(mappedBy = "servidorIntencao", fetch = FetchType.EAGER)
	@OrderBy("id ASC")
	private Set<IntencaoVoto> intencaoServidors;

	
	public List<IntencaoVoto> getIntencaoServidors() {
		return new ArrayList<IntencaoVoto>(intencaoServidors);
	}

	public void setIntencaoServidors(Set<IntencaoVoto> intencaoServidors) {
		this.intencaoServidors = intencaoServidors;
	}

	public Integer getId() {
		return id;
	}

	public String getRegimeJuridico() {
		return regimeJuridico;
	}

	public String getCodigoVaga() {
		return codigoVaga;
	}

	public void setCodigoVaga(String codigoVaga) {
		this.codigoVaga = codigoVaga;
	}

	public void setRegimeJuridico(String regimeJuridico) {
		this.regimeJuridico = regimeJuridico;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public String getTitulacao() {
		return titulacao;
	}

	public void setTitulacao(String titulacao) {
		this.titulacao = titulacao;
	}

	public String getEscolaridade() {
		return escolaridade;
	}

	public void setEscolaridade(String escolaridade) {
		this.escolaridade = escolaridade;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSiape() {
		return siape;
	}

	public void setSiape(String siape) {
		this.siape = siape;
	}

	public Date getDtIngressoOrgao() {
		return dtIngressoOrgao;
	}

	public void setDtIngressoOrgao(Date dtIngressoOrgao) {
		this.dtIngressoOrgao = dtIngressoOrgao;
	}

	public Date getDtIngressoServicoPublico() {
		return dtIngressoServicoPublico;
	}

	public void setDtIngressoServicoPublico(Date dtIngressoServicoPublico) {
		this.dtIngressoServicoPublico = dtIngressoServicoPublico;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public UORG getUorgLotacao() {
		return uorgLotacao;
	}

	public void setUorgLotacao(UORG uorgLotacao) {
		this.uorgLotacao = uorgLotacao;
	}

	public UORG getUorgExercicio() {
		return uorgExercicio;
	}

	public void setUorgExercicio(UORG uorgExercicio) {
		this.uorgExercicio = uorgExercicio;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Servidor other = (Servidor) obj;
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
		return id + " - " + siape + " - " + pessoa.getNome();
	}

	/*
	 * Validar entidade TODO: quando for fazer a tela de cadastro servidor; o
	 * procedimento deve obrigar o usuário a informar pessoa
	 */
	public String validate() {
		String validate = "";
		if (StringUtil.isNullOrEmpty(siape)) {
			validate += "SIAPE é Obrigatório!";
		}
		if (dtIngressoOrgao == null) {
			validate += " Data IngressoOrgao é Obrigatório!";
		}
		if (dtIngressoServicoPublico == null) {
			validate += " Data Ingresso no Serviço Publico é Obrigatório!";
		}
		if (cargo == null) {
			validate += " Data Ingresso no Serviço Publico é Obrigatório!";
		}
		if (uorgExercicio == null) {
			validate += " UORG Exercício é Obrigatório!";
		}
		if (uorgLotacao == null) {
			validate += " UORG Lotação é Obrigatório!";
		}
		if (pessoa == null) {
			validate += " Pessoa é Obrigatório!";
		}
		return validate;
	}

	public IntencaoVoto getLastIntencaoVoto() {
		List<IntencaoVoto> list = new ArrayList<IntencaoVoto>(intencaoServidors);
		if (list != null && list.size() > 0)
			return list.get(list.size() - 1);
		else
			return null;
	}
}
