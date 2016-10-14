import java.util.Random;

public class Main {
	public static void main(String args[]){
		Random rand = new Random();
    	int randomInt = rand.nextInt(151);
		Trainer player = new Trainer();
		for  (int i =0; i < 1; i++){
			randomInt = rand.nextInt(151);
			player.myPokemon.add(new Pokemon(randomInt + 1));
		}
		randomInt = rand.nextInt(151);
    	Battle.fightPokemon(player, new Pokemon(randomInt + 1));
		
	}
}
