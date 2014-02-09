package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	
	public static void main(String args[]) throws IOException{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String producao = in.readLine();
		
		GLC glc = new GLC(producao);
		TabelaM tabela = new TabelaM(glc);
		AutomatoPilha ap = new AutomatoPilha(glc, tabela);
		
		String expressao = in.readLine();
		while (!expressao.trim().isEmpty()){
			System.out.println(ap.checaValidadeExpressao(expressao));
			expressao = in.readLine();
		}		
		
	}

}

//String producao = "S -> 0S0,S -> 1S1,S -> E ";
//String producao = "S -> (S)S,S -> E";
//String producao = "S -> AS, A -> (S)S, A -> a,A -> E";

//String expressao = "(())";