package main;

import java.util.HashMap;

public class TabelaM {
	
	private GLC glc;
	private String[][] tabela;
	private HashMap<String, String> primeiro;
	private HashMap<String, String> sequencia;
	
	public TabelaM(GLC glc){
		this.glc = glc;
		this.tabela = new String[glc.getVariaveis().size()][glc.getTerminais().size()];
		this.primeiro = new HashMap<String, String>();
		this.sequencia = new HashMap<String, String>();
		
		construirTabelaM();
		//imprimeTabela();
	}	
	
	private void construirTabelaM(){
		construirConjuntoPrimeiro();
		construirConjuntoSequencia();
		
		for (String p : glc.getProducoes()){			
			
			char A = p.charAt(0);			
			String w = getApenasExpressao(p);
			String priW = primeiro.get(w);			
			
			for (int i = 0 ; i < priW.length() ; i++){
				char a = priW.charAt(i);
				if (isTerminal(a)){
					if (tabela[glc.getVariaveis().indexOf(A)][glc.getTerminais().indexOf(a)] == null)
						tabela[glc.getVariaveis().indexOf(A)][glc.getTerminais().indexOf(a)] = w;
					else
						tabela[glc.getVariaveis().indexOf(A)][glc.getTerminais().indexOf(a)] += w;
				}
			}
			
			if (priW.contains("E")){
				String seq = sequencia.get(A+"");
				for (int i = 0 ; i < seq.length() ; i++){
					char a = seq.charAt(i);
					if (a == '$')
						continue;
					
					int posA;
					if (glc.getTerminais().contains(a))
						posA = glc.getTerminais().indexOf(a);
					else
						posA = glc.getVariaveis().indexOf(a);
					
					if (tabela[glc.getVariaveis().indexOf(A)][posA] == null)
						tabela[glc.getVariaveis().indexOf(A)][posA] = w;
					else
						tabela[glc.getVariaveis().indexOf(A)][posA] += w;
				}
			}
			
		}
	}
	
	private void construirConjuntoPrimeiro(){
		//Caso 1
		//Primeiro(a) | a é terminal
		for (Character a : glc.getTerminais())
			primeiro.put(a+"", a+"");
		
		for (String prod : glc.getProducoes()){
			if (getApenasExpressao(prod).equals("E"))
				primeiro.put("E", "E");
		}
		
		//Caso 2 - INÍCIO
		//Primeiro(A) | A não é terminal		 
		for (Character a : glc.getVariaveis())
			primeiro.put(a+"", "");		
		
		boolean houveAlteracao = false;
		
		while (!houveAlteracao){
			for (String prod : glc.getProducoes()){
				char A = prod.charAt(0);
				String w = getApenasExpressao(prod);
							
				int k = 0;
				boolean Continue = true;
				
				while (Continue && k <= w.length()){
					if (w.charAt(k) == 'E')
						break;		
					
					String elemK = w.charAt(k)+"";
					String primeiroA = primeiro.get(A+"");
					String acrescentar = (primeiroA + primeiro.get(elemK).replace("E", " ").trim()).trim();
					acrescentar = getCadeiaSemRepeticao(acrescentar);
					
					if (!primeiroA.equals(acrescentar))
						houveAlteracao = true;
					
					primeiro.put(A+"", acrescentar);
					if (!primeiro.get(elemK).contains("E"))
						Continue = false;
					
					k++;
				}
				
				if (Continue){
					primeiro.put(A+"", primeiro.get(A+"")+"E");
					houveAlteracao = true;
				}
			}
		}			
		//Caso 2 - FIM
		
		//Caso 3
		//Primeiro(w) | w -> X_1X_2...X_n
		for (String prod : glc.getProducoes()){
			String w = getApenasExpressao(prod);
			
			//Se só contém 1 elemento, não é uma cadeia
			if (w.length() == 1)
				continue;
			
			boolean vazio = false;
				
			primeiro.put(w,primeiro.get(w.charAt(0)+"").replace("E", " ").trim());
			
			if (primeiro.get(w.charAt(0)+"").contains("E"))
				vazio = true;				
			
			for (int i = 1 ; i < w.length() ; i++){
				if (!vazio)
					break;
				
				for (int k = 0 ; k < i-1; k++)
					if (!primeiro.get(w.charAt(k)+"").contains("E"))
						vazio = false;
				
				if (vazio){
					String seq = primeiro.get(w.charAt(i)+"").replace("E", " ").trim();
					String primeiroW = primeiro.get(w);
					String acrescentar = getCadeiaSemRepeticao(primeiroW + seq);
					primeiro.put(w, acrescentar);
				}
			}
			
			if (vazio){
				String seq = primeiro.get(w)+"E";
				String primeiroW = primeiro.get(w);
				String acrescentar = getCadeiaSemRepeticao(primeiroW + seq);
				primeiro.put(w, acrescentar);
			}
		}		
	}
	
	private void construirConjuntoSequencia(){
		boolean houveAlteracao = false;
		char variavelInicial = glc.getProducoes().get(0).charAt(0);
		
		sequencia.put(variavelInicial+"", "$");
		
		//for cada não-terminal A <> variável inicial
		for (Character prod : glc.getVariaveis()){
			if (prod != variavelInicial)
				sequencia.put(prod+"", "");
		}
		
		while (!houveAlteracao){
			//for cada produção A -> X_1X_2...X_n do
			for (String prod : glc.getProducoes()){
				char A = prod.charAt(0);
				prod = getApenasExpressao(prod);
				if (prod.length() == 1)
					continue;
				
				//for each X_i que for não terminal do
				for (int i = 0 ; i < prod.length() - 1; i++){
					if (!isTerminal(prod.charAt(i))){
						String seq = getPrimeiroDaCadeia(prod.substring(i+1)).replace("E", " ").trim();
						
						if (sequencia.containsKey(prod.charAt(i)+"")){
							seq = sequencia.get(prod.charAt(i)+"")+seq;
							houveAlteracao = true;
						}
							
						sequencia.put(prod.charAt(i)+"", seq);
						
						if (getPrimeiroDaCadeia(prod.substring(i+1)).contains("E"))
							sequencia.put(prod.charAt(i)+"", sequencia.get(A+""));					
					}
				}
			}
		}		
	}
	
	/**
	 * Retorna o conjunto primeiro de uma cadeia.
	 * Se houver mais de um não-terminal na expressão, faz a
	 * união entre os conjuntos primeiro.
	 * @param w
	 * @return
	 */
	private String getPrimeiroDaCadeia(String w){
		if (w.length() == 1)
			return w;
		
		String resultado = "";
		for (int i = 0 ; i < w.length() ; i++){
			resultado += primeiro.get(w.charAt(i)+"");
		}
		
		return resultado;
	}
	
	/**
	 * Formata a cadeia pra evitar que haja repetição
	 * de elementos nela.
	 * @param cadeia
	 * @return
	 */
	private String getCadeiaSemRepeticao(String cadeia){
		String seq = "";
		
		for (int i = 0 ; i < cadeia.length() ; i++){
			if (!seq.contains(cadeia.charAt(i)+""))
				seq += cadeia.charAt(i);
		}
		
		return seq;
	}
	
	private boolean isTerminal(Character c){
		for (Character a : glc.getTerminais())
			if (a == c)
				return true;
		
		return false;
	}
	
	/**
	 * Retorna apenas a expressão da produção,
	 * ou seja, o que tem vier depois de ->
	 * @param exp
	 * @return
	 */
	private String getApenasExpressao(String exp){
		int ini = 0;
		if (exp.charAt(exp.indexOf('-')+1) == '>')
			ini = exp.indexOf('-')+2;
		
		return exp.substring(ini).trim();
	}
	
	public String[][] getTabela() {
		return tabela;
	}
	
	private void imprimeTabela(){
		int m = glc.getVariaveis().size();
		int t = glc.getTerminais().size();
		
		System.out.print("  ");
		for (Character s : glc.getTerminais())
			System.out.print(s+" ");
		System.out.println(" ");
		
		for (int i = 0 ; i < m ; i++){
			System.out.print(glc.getVariaveis().get(i)+" ");
			for (int j = 0 ; j < t ; j++){
				String elem = tabela[i][j] == null ? " " : tabela[i][j];
				System.out.print("["+elem+"] ");
			}
			System.out.println(" ");
		}				
	}

}