import java.util.ArrayList;
import java.util.Random;

public class Main
{
	private static ArrayList<BuscaParalela> listaThreads = new ArrayList<BuscaParalela>();
	
	public static void main(String[] args)
	{
		/*Write a Java class that allows parallel search in an array of integer. 
		It provides the following static method: public static int parallelSearch(int x, int[] A, int numThreads) 
		This method creates as many threads as specified by numThreads,
		divides the array A into that many parts,  and gives each thread a part of the array to search for sequentially.
		If any thread finds x, then it returns an index i such that A[i] = x. Otherwise, the method returns -1.*/
		int[] lista = null;
		int valorPesq = 7658;
		int numThreads = 20;
		String retorno;
		
		lista = preencherLista(lista);
		retorno = parallelSearch(valorPesq, lista, numThreads);
		
		if (retorno.equals("-1")) {
			System.out.println("O valor '" + valorPesq + "' não está presente no vetor");
		}
		else
		{
			System.out.println(retorno);
		}
	}
	
	public static String parallelSearch(int valorPesq, int[] lista, int numThreads)
	{
		String retorno = "-1";
		int tamSubVetores = (lista.length/numThreads);
		int auxPos = 0;
		BuscaParalela busca;
		for (int i = 1; i <= numThreads; i++)
		{
			//ultima parte
			if (i == numThreads)
			{
				//System.out.println(auxPos + " até " + (lista.length-1) + " -> Thread " + i);
				busca = new BuscaParalela(valorPesq, lista, auxPos, lista.length-auxPos, i);
			}
			else
			{
				//System.out.println(auxPos + " até " + (tamSubVetores*(i)-1) + " -> Thread " + i);
				busca = new BuscaParalela(valorPesq, lista, auxPos, tamSubVetores, i);
			}
			auxPos = tamSubVetores*i;
			listaThreads.add(busca);
		}
		
		for (int i = 0; i < listaThreads.size(); i++) {
			listaThreads.get(i).start();
			try {
				listaThreads.get(i).setListaThreads(listaThreads);
				listaThreads.get(i).join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if (retorno.equals("-1")) {
				retorno = listaThreads.get(i).getIndex();
			}
		}
		
		return retorno;
	}
	
	private static int[] preencherLista(int[] lista) {
		//Inicializa o ArrayList
		lista = new int[10006];
		//Instancia um novo elemento da Classe Random
		Random r = new Random();
		//Adiciona 10005 elementos a essa lista
		for (int i = 0; i < 10006; i++) {
			lista[i] = r.nextInt(10000);
			//System.out.println(lista[i]);
		}
		return lista;
	}
}
