package br.com.raiox.dao.lazy;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.raiox.generic.GenericDAO;
import br.com.raiox.model.IntencaoVoto;

/**
 * LazyDataModel da tabela IntencaoVoto, responsável por realizar busca sob demanda
 * 
 * @author Daniel Alencar 
 *
 * @since 08/03/2016
 */
public class LazyDataModelIntencaoVoto extends LazyDataModel<IntencaoVoto> implements Serializable {  

	private static final long serialVersionUID = 1L;

	private GenericDAO genericDAO;

	private List<IntencaoVoto> listaIntencaoVotos;  

	public LazyDataModelIntencaoVoto(GenericDAO entregaService) {  
		this.genericDAO = entregaService;
	}  

	@Override  
	public IntencaoVoto getRowData(String rowKey) {  
		Integer id = Integer.valueOf(rowKey);
		for(IntencaoVoto entidade : listaIntencaoVotos) {  
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
	public Object getRowKey(IntencaoVoto entidade) {  
		return entidade.getId();
	}

	@Override
	public List<IntencaoVoto> load(int primeiro, int registrosPagina, String camposort, 
			SortOrder campoordenar,
			 Map<String,Object> filtros) {
		setRowCount(genericDAO.retornaTotalRegistrosConsulta(
				primeiro, registrosPagina, camposort, campoordenar,
				filtros, IntencaoVoto.class));
		return genericDAO.listarPaginado(primeiro, registrosPagina,
				camposort, campoordenar, filtros, IntencaoVoto.class);
	}


} 
