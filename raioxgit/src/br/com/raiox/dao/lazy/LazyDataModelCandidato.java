package br.com.raiox.dao.lazy;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.raiox.generic.GenericDAO;
import br.com.raiox.model.Candidato;

/**
 * LazyDataModel da tabela Candidato, responsável por realizar busca sob demanda
 * 
 * @author Daniel Alencar 
 *
 * @since 08/03/2016
 */
public class LazyDataModelCandidato extends LazyDataModel<Candidato> implements Serializable {  

	private static final long serialVersionUID = 1L;

	private GenericDAO genericDAO;

	private List<Candidato> listaCandidatos;  

	public LazyDataModelCandidato(GenericDAO entregaService) {  
		this.genericDAO = entregaService;
	}  

	@Override  
	public Candidato getRowData(String rowKey) {  
		Integer id = Integer.valueOf(rowKey);
		for(Candidato entidade : listaCandidatos) {  
			if(id.equals(entidade.getId()));  
			return entidade;  
		}  

		return null;  
	}  

	@Override
	public void setRowIndex(int rowIndex) {
		/*
		 * The following is in ancestor (LazyDataModel):
		 * this.rowIndex = rowIndex == -1 ? rowIndex : (rowIndex % pageSize);
		 */
		if (rowIndex == -1 || getPageSize() == 0) {
			super.setRowIndex(-1);
		}
		else
			super.setRowIndex(rowIndex % getPageSize());
	}

	@Override  
	public Object getRowKey(Candidato entidade) {  
		return entidade.getId();
	}

	@Override
	public List<Candidato> load(int primeiro, int registrosPagina, String camposort, 
			SortOrder campoordenar,
			 Map<String,Object> filtros) {
		setRowCount(genericDAO.retornaTotalRegistrosConsulta(
				primeiro, registrosPagina, camposort, campoordenar,
				filtros, Candidato.class));
		return genericDAO.listarPaginado(primeiro, registrosPagina,
				camposort, campoordenar, filtros, Candidato.class);
	}


} 
