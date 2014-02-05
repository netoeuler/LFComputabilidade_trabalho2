package main;

public class TabelaM {
	
	GLC glc;
	String[][] tabela;	
	
	public TabelaM(GLC glc){
		this.glc = glc;
		tabela = new String[glc.getVariaveis().size()][glc.getTerminais().size()];
		
		construirTabelaM();
		imprimeTabela();
	}
	
	private void construirTabelaM(){
		for (String prod : glc.getProducoes()){
			char variavel = prod.charAt(0);
			String sequencia = prod.substring(4).trim();
			
			int indM = glc.getVariaveis().indexOf(variavel);
			int indT = glc.getTerminais().indexOf(sequencia.charAt(0));
			
			if (sequencia.contains("E"))
				continue;
			else if (glc.getTerminais().contains( sequencia.charAt(0) ))
				tabela[indM][indT] = sequencia;			
		}
		
		for (String prod : glc.getProducoes()){
			char variavel = prod.charAt(0);
			String sequencia = prod.substring(4).trim();
			
			int indM = glc.getVariaveis().indexOf(variavel);			
			
			for (int s = 0 ; s < sequencia.length() ; s++){
				int indV = glc.getVariaveis().indexOf(sequencia.charAt(s));
				
				if (indV == -1)
					continue;
				
				if (glc.getVariaveis().contains( sequencia.charAt(0) ) && indM != indV){
					for (int i = 0 ; i < glc.getTerminais().size() ; i++)
						tabela[indM][i] = tabela[indV][i];				
				}
			}			
		}
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
