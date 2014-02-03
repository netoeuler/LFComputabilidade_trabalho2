package main;

import java.util.ArrayList;

public class GLC {
	
	ArrayList<String> producoes;
	ArrayList<Character> variaveis;
	ArrayList<Character> terminais;
	
	public GLC(String producao){
		producoes = new ArrayList<String>();
		variaveis = new ArrayList<Character>();
		terminais = new ArrayList<Character>();
		
		definirVariaveisETerminais(producao);
		//imprime();
	}
	
	private void definirVariaveisETerminais(String producao){
		String[] arrayProducao = producao.split(",");
		
		//Checa quais são as varáveis contidas na produção
		for (String s : arrayProducao){
			s = s.trim();
			producoes.add(s);
			
			if (variaveis.isEmpty())
				variaveis.add(s.charAt(0));
			else if (!variaveis.contains(s.charAt(0)))
				variaveis.add(s.charAt(0));
		}
		
		//Checa quais são os terminais contidos na produção
		for (String s : arrayProducao){
			s = s.trim();
			
			int ini = 0;
			if (s.charAt(s.indexOf('-')+1) == '>')
				ini = s.indexOf('-')+2;
			
			for (int i = ini ; i < s.length() ; i++){
				if (s.charAt(i) == ' ' || s.charAt(i) == '|' || s.charAt(i) == 'E')
					continue;
				
				if (!variaveis.contains(s.charAt(i)) && !terminais.contains(s.charAt(i)))
					terminais.add(s.charAt(i));
			}
		}
	}
	
	public ArrayList<Character> getVariaveis() {
		return variaveis;
	}
	
	public ArrayList<Character> getTerminais() {
		return terminais;
	}
	
	public ArrayList<String> getProducoes() {
		return producoes;
	}
	
	private void imprime(){
		for (Character s : variaveis){
			System.out.print(s+" ");
		}
		System.out.println("");
		for (Character s : terminais){
			System.out.print(s+" ");
		}
	}

}
