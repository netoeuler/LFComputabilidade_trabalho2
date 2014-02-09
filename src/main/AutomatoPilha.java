package main;

import java.util.ArrayList;
import java.util.Stack;

public class AutomatoPilha {
	
	private GLC glc;
	
	private Stack<Character> pilha;
	private ArrayList<String> qLaco;
	private TabelaM tabelaM;	
	
	public AutomatoPilha(GLC glc, TabelaM tabela){
		this.glc = glc;		
		this.tabelaM = tabela;
		this.pilha = new Stack<Character>();
		this.qLaco = new ArrayList<String>();
		
		//construirAutomatoPilha();
		//imprimeQLaco();
	}
	
	private void construirAutomatoPilha(){	
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
	
	public int checaValidadeExpressao(String expressao){
		char varInicial = glc.getVariaveis().get(0);
		expressao = expressao+"$";
		pilha.add('$');
		pilha.add(varInicial);
		
		for (int i = 0 ; i < expressao.length()-1 ; i++){
			char a = expressao.charAt(i);
			char A = pilha.pop();
			int posA = glc.getVariaveis().indexOf(A) == -1 ? 0 : glc.getVariaveis().indexOf(A); 
			String p = tabelaM.getTabela()[posA][glc.getTerminais().indexOf(a)];
			p = p.replace("E", "");
			
			if (!p.isEmpty()){
				for (int j = p.length()-1 ; j >= 0 ; j--)
					pilha.add(p.charAt(j));			
			}
			
			char pop = pilha.pop();
			if (pop != a)
				pilha.add(pop);
		}
		
		char pop = pilha.pop();		
		if (pop != varInicial)
			pilha.add(pop);
		
		if (pilha.pop() == '$')
			return 1; //true
		else
			return 0; //false
	}
	
	public void imprimeQLaco(){
		for (String s : qLaco){
			System.out.println(s);
		}
	}

}
