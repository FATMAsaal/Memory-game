

public class ModeleMemory {

	static int[]grille = {
			1,2,5,6,8,7,3,7,
			2,4,6,5,
			3,1,8,4};
	
	public int[] getMatrice(){
		return this.grille;
	}
	public static int  getValeur(int x) {
		return grille[x];
	}
}

