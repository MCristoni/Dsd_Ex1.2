import java.util.ArrayList;

public class BuscaParalela extends Thread
{
	int tamPesq;
	int[] vetor;
	int idVetor;
	int auxPos;
	int valorPesq;
	String posPesq = "-1";
	ArrayList<BuscaParalela> listaThreads = new ArrayList<BuscaParalela>();

	public BuscaParalela(int valorPesq, int[] lista, int auxPos, int tamPesq, int idVetor)
	{
		this.valorPesq = valorPesq;
		this.vetor = lista;
		this.auxPos = auxPos;
		this.tamPesq = tamPesq;
		this.idVetor = idVetor;
	}

	@Override
	public void run()
	{
		for (int i = auxPos; i < auxPos+tamPesq; i++)
		{
			if(this.isInterrupted())
			{
				break;
			}

			if (vetor[i] == valorPesq)
			{
				//System.out.println("ACHOU na pos " + i + " Thread " + idVetor);
				posPesq = "Valor '"+ valorPesq + "' encontrado na posição " + i + " na Thread " + idVetor;
				for (int j = 0; j < (listaThreads.size()); j++) {
					if (this != listaThreads.get(j)) {
						listaThreads.get(j).interrupt();
						//System.out.println("interrompeu a thread " + (j+1));
					}
				}
				break;
			}
		}
	}

	public String getIndex()
	{
		return posPesq;
	}

	public ArrayList<BuscaParalela> getListaThreads() {
		return listaThreads;
	}

	public void setListaThreads(ArrayList<BuscaParalela> listaThreads) {
		this.listaThreads = listaThreads;
	}
}
