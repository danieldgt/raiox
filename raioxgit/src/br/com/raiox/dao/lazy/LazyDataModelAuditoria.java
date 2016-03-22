package br.com.raiox.dao.lazy;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.raiox.generic.AuditoriaDAO;
import br.com.raiox.model.Auditoria;

/**
 * LazyDataModel da tabela veiculo_cor, responsável por realizar busca sob demanda
 * 
 * @author Daniel Alencar 
 *
 * @since 08/03/2016
 */
public class LazyDataModelAuditoria extends LazyDataModel<Auditoria> implements Serializable {  

	private static final long serialVersionUID = 1L;

	private AuditoriaDAO genericDAO;

	private List<Auditoria> listaCampus;  

	public LazyDataModelAuditoria(AuditoriaDAO entregaService) {  
		this.genericDAO = entregaService;
	}  

	@Override  
	public Auditoria getRowData(String rowKey) {  
		Integer id = Integer.valueOf(rowKey);
		for(Auditoria entidade : listaCampus) {  
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
	public Object getRowKey(Auditoria entidade) {  
		return entidade.getId();
	}
	@Override
	public List<Auditoria> load(int primeiro, int registrosPagina, String camposort, 
			SortOrder campoordenar,
			 Map<String,Object> filtros) {
		setRowCount(genericDAO.retornaTotalRegistrosConsulta(
				primeiro, registrosPagina, camposort, campoordenar,
				filtros, Auditoria.class));
		return genericDAO.listarPaginado(primeiro, registrosPagina,
				camposort, campoordenar, filtros, Auditoria.class);
	}
} 
