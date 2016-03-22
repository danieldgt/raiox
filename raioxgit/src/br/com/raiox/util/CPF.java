package br.com.raiox.util;


/**
* Classe com m�todo para valida��o de CPF
*
* Pode ser utilizada livremente e modificada para se integrar � sistemas mais complexos.
*@autor daniel alencar barros tavares {@link danieldgt@gmail.com} + web
* 
* @version 1.0
* @since   20/07/2013
*/
public class CPF {
 
	/** Realiza a valida��o do CPF. 
     * 
     * @param   strCPF n�mero de CPF a ser validado 
     * @return  true se o CPF � v�lido e false se n�o � v�lido 
     */
    public static boolean  isCPF(String cpf) {
       String strCpf = cpf;
        if (strCpf.equals("")) {
            return false;
        } 
//        String string = "cpf".replaceAll("\\(|\\)|\\-", ""); 
        strCpf = cpf.replaceAll("\\(|\\)|\\-|\\.", ""); 
        //System.out.println(cpf);
        int d1, d2;
        int digito1, digito2, resto;
        int digitoCPF;
        String nDigResult;

        d1 = d2 = 0;
        digito1 = digito2 = resto = 0;

        for (int nCount = 1; nCount < strCpf.length() - 1; nCount++) {
            digitoCPF = Integer.valueOf(strCpf.substring(nCount - 1, nCount)).intValue();

            //multiplique a ultima casa por 2 a seguinte por 3 a seguinte por 4 e assim por diante.  
            d1 = d1 + (11 - nCount) * digitoCPF;

            //para o segundo digito repita o procedimento incluindo o primeiro digito calculado no passo anterior.  
            d2 = d2 + (12 - nCount) * digitoCPF;
        }

        //Primeiro resto da divis�o por 11.  
        resto = (d1 % 11);

        //Se o resultado for 0 ou 1 o digito � 0 caso contr�rio o digito � 11 menos o resultado anterior.  
        if (resto < 2) {
            digito1 = 0;
        } else {
            digito1 = 11 - resto;
        }

        d2 += 2 * digito1;

        //Segundo resto da divis�o por 11.  
        resto = (d2 % 11);

        //Se o resultado for 0 ou 1 o digito � 0 caso contr�rio o digito � 11 menos o resultado anterior.  
        if (resto < 2) {
            digito2 = 0;
        } else {
            digito2 = 11 - resto;
        }

        //Digito verificador do CPF que est� sendo validado.  
        String nDigVerific = strCpf.substring(strCpf.length() - 2, strCpf.length());

        //Concatenando o primeiro resto com o segundo.  
        nDigResult = String.valueOf(digito1) + String.valueOf(digito2);

        //comparar o digito verificador do cpf com o primeiro resto + o segundo resto.  
        return nDigVerific.equals(nDigResult);
    }
   /*//Use este trecho para testar a classe
   public static void main(String[] args) {
      System.out.println( CPF.isCPF("00728491346") );
   }
   */
}

