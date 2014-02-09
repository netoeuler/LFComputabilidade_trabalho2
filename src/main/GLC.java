package main;

import java.util.ArrayList;
import java.util.HashMap;

public class GLC {
	
	private ArrayList<String> producoes;
	private ArrayList<Character> variaveis;
	private ArrayList<Character> terminais;
	private HashMap<Character, String> hashProducoes;
	
	public GLC(String producao){
		this.producoes = new ArrayList<String>();
		this.variaveis = new ArrayList<Character>();
		this.terminais = new ArrayList<Character>();
		this.hashProducoes = new HashMap<Character, String>();
		
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
			
			if (hashProducoes.containsKey(s.charAt(0)))
				hashProducoes.put(s.charAt(0), hashProducoes.get(s.charAt(0)) + "," + s.substring(4).trim());
			else
				hashProducoes.put(s.charAt(0), s.substring(4).trim());
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
	
	public HashMap<Character, String> getHashProducoes() {
		return hashProducoes;
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
