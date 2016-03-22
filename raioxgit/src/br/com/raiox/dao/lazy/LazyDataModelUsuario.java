package br.com.raiox.dao.lazy;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.raiox.dao.UsuarioDAO;
import br.com.raiox.model.Usuario;

/**
 * LazyDataModel da tabela usuario, responsável por realizar busca sob demanda
 * 
 * @author Daniel Alencar
 *
 * @since 07/03/2016
 */
public class LazyDataModelUsuario extends LazyDataModel<Usuario> implements Serializable {  

	private static final long serialVersionUID = 1L;

	private UsuarioDAO genericDAO;

	private List<Usuario> listaUsuarios;  

	public LazyDataModelUsuario(UsuarioDAO entregaService) {  
		this.genericDAO = entregaService;
	}  

	@Override  
	public Usuario getRowData(String rowKey) {  
		Integer id = Integer.valueOf(rowKey);
		for(Usuario entidade : listaUsuarios) {  
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
	public Object getRowKey(Usuario entidade) {  
		return entidade.getId();
	}

	@Override
	public List<Usuario> load(int primeiro, int registrosPagina, String camposort, 
			SortOrder campoordenar,
			 Map<String,Object> filtros) {
		setRowCount(genericDAO.retornaTotalRegistrosConsulta(
				primeiro, registrosPagina, camposort, campoordenar,
				filtros, Usuario.class));
		return genericDAO.listarPaginado(primeiro, registrosPagina,
				camposort, campoordenar, filtros, Usuario.class);
	}


} 
