package main;

public class Main {
	
	public static char PALAVRA_VAZIA = 'E'; 
	
	public static void main(String args[]){
		//String producao = "S -> aABb, A -> aA | E, B -> bB | E ";
		String producao = "S -> 0S0,S -> 1S1,S -> E ";
		GLC glc = new GLC(producao);
		AutomatoPilha ap = new AutomatoPilha(glc);
	}

}
