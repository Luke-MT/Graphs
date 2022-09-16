package upo.progdin20038688;

public class DynamicProgramming {
	
	
	
	/** Calcola la LCS tra <code>s1</code> e <code>s2</code> utilizzando l'algoritmo visto a lezione.
	 * </br>CONSIGLIO: potete usare i metodi di String per accedere alle posizioni di s1 ed s2.
	 * </br>CONSIGLIO2: potete costruire l'output come un array di caratteri, e poi trasformarlo in stringa,
	 * oppure usare le concatenazioni di stringhe nelle chiamate ricorsive (vedi slide).
	 * 
	 * @param s1 una sequenza di caratteri
	 * @param s2 una sequenza di caratteri
	 * @return una LCS di <code>s1</code> e <code>s2</code>
	 */
	public static String LongestCommonSubsequence(String s1, String s2) {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	/** Risolve il problema dello zaino 0-1 con l'algoritmo di programmazione dinamica visto a lezione.
	 * 
	 * @param weights un vettore contenente in posizione i-esima, per ogni oggetto oi, il suo peso. 
	 * @param values un vettore contenente in posizione i-esima, per ogni oggetto oi, il suo valore. 
	 * @param maxWeight la capienza dello zaino.
	 * @return un vettore di boolean che contiene, in posizione i-esima, true se l'oggetto i-esimo �
	 * incluso nella soluzione, false altrimenti.
	 */
	public static boolean[] getKnapsack01(int[] weights, int[] values, int maxWeight) {
		
		int n = values.length;
		int p = maxWeight;
		
		int [][] v = new int[n+1][p+1];
		boolean [][] k = new boolean[n+1][p+1];

		for(int i = 0; i<n; i++) { 
			v[i][0] = 0;
			k[i][0] = false;
		}
			
		for(int j = 0; j<p; j++) {
			v[0][j] = 0;
			k[0][j] = false;
		}
		
		for(int i = 1; i<n+1; i++) 
			for(int j = 1; j<p+1; j++) {
				v[i][j] = v[i-1][j];
				k[i][j] = false;
				if(j>=weights[i-1])	
					if(v[i][j] < (v[i-1][j-weights[i-1]]+values[i-1])) {
						v[i][j] = v[i-1][j-weights[i-1]]+values[i-1];
						k[i][j] = true;		
					}
			}
		
		boolean [] ans = new boolean[n+1]; 
		int d = p;
		int i = n;
		while (i>0) {
			if(k[i][d]==true) {
				ans[i]=k[i][d];
				d=d-weights[i-1];
			}
			i=i-1;
		}			
		
		return ans;
			
			
		
	}
	
}
