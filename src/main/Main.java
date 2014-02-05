package main;

public class Main {
	
	public static void main(String args[]){
		//String producao = "S -> 0S0,S -> 1S1,S -> E ";
		//String producao = "S -> (S)S,S -> E";
		String producao = "S -> AS, A -> (S)S, A -> a,A -> E";
		GLC glc = new GLC(producao);
		TabelaM tabela = new TabelaM(glc);
		AutomatoPilha ap = new AutomatoPilha(glc);
	}

}
