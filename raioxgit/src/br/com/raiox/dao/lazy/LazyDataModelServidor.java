package br.com.raiox.dao.lazy;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.raiox.dao.ServidorDAO;
import br.com.raiox.model.Servidor;

/**
 * LazyDataModel da tabela usuario, responsável por realizar busca sob demanda
 * 
 * @author Daniel Alencar
 *
 * @since 07/03/2016
 */
public class LazyDataModelServidor extends LazyDataModel<Servidor> implements Serializable {  

	private static final long serialVersionUID = 1L;

	private ServidorDAO genericDAO;

	private List<Servidor> listaServidors;  

	public LazyDataModelServidor(ServidorDAO entregaService) {  
		this.genericDAO = entregaService;
	}  

	@Override  
	public Servidor getRowData(String rowKey) {  
		Integer id = Integer.valueOf(rowKey);
		for(Servidor entidade : listaServidors) {  
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
	public Object getRowKey(Servidor entidade) {  
		return entidade.getId();
	}

	@Override
	public List<Servidor> load(int primeiro, int registrosPagina, String camposort, 
			SortOrder campoordenar,
			 Map<String,Object> filtros) {
		setRowCount(genericDAO.retornaTotalRegistrosConsulta(
				primeiro, registrosPagina, camposort, campoordenar,
				filtros, Servidor.class));
		return genericDAO.listarPaginado(primeiro, registrosPagina,
				camposort, campoordenar, filtros, Servidor.class);
	}


} 
