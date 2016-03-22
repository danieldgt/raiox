package br.com.raiox.generic;

import java.util.List;

import br.com.raiox.exception.DAOException;
import br.com.raiox.model.Usuario;

public class UsuarioDAO extends GenericDAO<Usuario> {
	// Verifica se usuário existe ou se pode logar
	public Usuario isUsuarioReadyToLogin(String email, String senha) {
		try {
			email = email.toLowerCase().trim();

			List retorno = listByNamedQuery(Usuario.FIND_BY_EMAIL_SENHA,
					email.trim(), MD5.md5(senha));

			if (retorno.size() == 1) {
				Usuario userFound = (Usuario) retorno.get(0);
				return userFound;
			}

			return null;
		} catch (DAOException e) {
			e.printStackTrace();
			try {
				throw new DAOException(e.getMessage());
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} 
		return null;

	}
}
