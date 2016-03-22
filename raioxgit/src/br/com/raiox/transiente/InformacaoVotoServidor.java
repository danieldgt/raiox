package br.com.raiox.transiente;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.raiox.model.IntencaoVoto;
import br.com.raiox.model.Servidor;
import br.com.raiox.model.Usuario;

public class InformacaoVotoServidor implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Servidor servidor;
	private IntencaoVoto intencaoVoto;

	public InformacaoVotoServidor() {
		super();
	}

	public InformacaoVotoServidor(Servidor servidor, IntencaoVoto intencaoVoto) {
		super();
		this.servidor = servidor;
		this.intencaoVoto = intencaoVoto;
	}

	public Servidor getServidor() {
		return servidor;
	}

	public void setServidor(Servidor servidor) {
		this.servidor = servidor;
	}

	public IntencaoVoto getIntencaoVoto() {
		return intencaoVoto;
	}

	public void setIntencaoVoto(IntencaoVoto intencaoVoto) {
		this.intencaoVoto = intencaoVoto;
	}

	public static List<InformacaoVotoServidor> listarIntencaoVoto(List<Servidor> servidors, Usuario usuario) {
		List<InformacaoVotoServidor> lista = new ArrayList<>();
		for (Servidor servidor : servidors) {
			IntencaoVoto intencaoVotoT = new IntencaoVoto();
			if (servidor.getIntencaoServidors() != null && servidor.getIntencaoServidors().size() > 0){
				IntencaoVoto intencaoUltima = servidor.getIntencaoServidors().get(servidor.getIntencaoServidors().size() - 1);
				if(intencaoUltima.getUsuario().getId() == usuario.getId())
					intencaoVotoT = servidor.getIntencaoServidors().get(servidor.getIntencaoServidors().size() - 1);
			}

			InformacaoVotoServidor informacaoVotoServidor = new InformacaoVotoServidor(servidor, intencaoVotoT);

			lista.add(informacaoVotoServidor);
		}
		return lista;
	}
}
