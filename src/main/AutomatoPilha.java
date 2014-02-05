package main;

import java.util.ArrayList;
import java.util.Stack;

public class AutomatoPilha {
	
	GLC glc;
	
	Stack<Character> pilha;
	ArrayList<String> qLaco;
	
	public AutomatoPilha(GLC glc){
		this.glc = glc;		
		pilha = new Stack<Character>();
		qLaco = new ArrayList<String>();
		
		construirAutomatoPilha();
		//imprimeQLaco();
	}
	
	private void construirAutomatoPilha(){
		//pilha.add('$');
		
		for (String s : glc.getProducoes()){
			s = s.trim();
			char variavel = s.charAt(0);
			int ini = 0;
			
			if (s.charAt(s.indexOf('-')+1) == '>')
				ini = s.indexOf('-')+2;
			
			String producao = "E,"+variavel+"->"+s.charAt(s.length()-1);
			for (int i = s.length()-2 ; i > ini ; i--){
				producao += " ";
				producao += "E,E->"+s.charAt(i);
			}
			
			qLaco.add(producao);			
		}
	}	
	
	public void imprimeQLaco(){
		for (String s : qLaco){
			System.out.println(s);
		}
	}

}
