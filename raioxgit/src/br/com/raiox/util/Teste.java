package br.com.raiox.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

import br.com.raiox.dao.CargoDAO;
import br.com.raiox.dao.PessoaDAO;
import br.com.raiox.dao.ServidorDAO;
import br.com.raiox.dao.UORGDAO;
import br.com.raiox.exception.DAOException;
import br.com.raiox.model.Pessoa;
import br.com.raiox.model.Servidor;
import br.com.raiox.model.UORG;

public class Teste {
	public static void main(String[] args) {
		ServidorDAO servidorDAO = new ServidorDAO();
		PessoaDAO pessoaDAO = new PessoaDAO();
		CargoDAO cargoDAO = new CargoDAO();
		UORGDAO uorgdao = new UORGDAO();

		String arquivoServidor = "C://ProjetoJSPServlet//raiox//src//br//com//raiox//util//servidor.txt";
		BufferedReader br = null;
		String linha = "";
		String csvDivisor = ";";

		try {

			br = new BufferedReader(new FileReader(arquivoServidor));
			while ((linha = br.readLine()) != null) {
				Servidor servidor = new Servidor();
				// 0-11
				/*
				 * 0 Matricula Siape 1 Nome Servidor 2 CPF 3 NOME ESCOLARIDADE 4
				 * Descricao Titulacao 5 Regime Juridico 6 Situacao 7 Descricao
				 * Cargo 8 UORG 9 Ocor Ingres Orgao Data; 10 Ocor Ingres Serv
				 * Pub Data; 11 Codigo Vaga;
				 */
				String[] servidorTxt = linha.split(csvDivisor);
				servidor.setSiape(servidorTxt[0]);
				servidor.setPessoa(pessoaDAO.getPessoaFromCPF(servidorTxt[2]));
				servidor.setEscolaridade(servidorTxt[3]);
				servidor.setTitulacao(servidorTxt[4]);
				servidor.setRegimeJuridico(servidorTxt[5]);
				servidor.setSituacao(servidorTxt[6]);
				servidor.setCargo(cargoDAO.getCargoByDsCargo(servidorTxt[7]));

				UORG uorg = uorgdao.getUORGByDsUORG(servidorTxt[8]);
				if (uorg != null && uorg.getId() != null && uorg.getId() > 0) {
					servidor.setUorgExercicio(uorg);
					servidor.setUorgLotacao(uorg);
				} else {
					// uorg = new UORG();
					// uorg.setDsUORG(servidorTxt[8]);
					// uorg.setObservacao("Criador por importação de dados");
					// try {
					// uorgdao.add(uorg);
					// } catch (DAOException e) {
					// // TODO Auto-generated catch block
					// e.printStackTrace();
					// }
					// servidor.setUorgExercicio(uorg);
					// servidor.setUorgLotacao(uorg);
				}
				try {
					servidor.setDtIngressoOrgao(new Date(servidorTxt[9]));
					servidor.setDtIngressoServicoPublico(new Date(servidorTxt[10]));
				} catch (Exception e) {
					// TODO: handle exception
				}
				servidor.setCodigoVaga(servidorTxt[11]);

				try {
					if (servidor.getPessoa() != null && servidor.getCargo() != null)
						servidor = servidorDAO.update(servidor);
					// System.out.println(servidor);
					System.out.println("SALVO SUCESSO!!!!!! ################################");
				} catch (DAOException e) {
					System.out.println("servidor [Matricula Siape= " + servidorTxt[servidorTxt.length - 2] + " , name="
							+ servidorTxt[servidorTxt.length - 1] + "]");
					System.out.println("ERRO ERRO ERRO!!!!!! ################################");
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
