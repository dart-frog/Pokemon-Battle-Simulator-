import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Battle {
	public static void fightPokemon(Trainer player,Pokemon enemyPokemon){
		Pokemon myPokemon = pickPokemon(player.myPokemon);
		System.out.println();
		System.out.println(myPokemon.name + " vs " + enemyPokemon.name);
		if (myPokemon.speed > enemyPokemon.speed){
			myTurn(myPokemon, enemyPokemon);
		}
		else if (myPokemon.speed < enemyPokemon.speed) {
			enemyTurn(myPokemon,enemyPokemon);	
		}
		else{
			Random rand = new Random();
			int r = rand.nextInt(2);
			if (r == 1){
				myTurn(myPokemon, enemyPokemon);
			}
			else{
				enemyTurn(myPokemon,enemyPokemon);	
			}
		}
		if (enemyPokemon.fainted()){
			System.out.println("you win");
		}
		else {
			System.out.println();
		}
		
	}
	public static boolean canAttack(Pokemon poke){
		Random rand = new Random();
		if (poke.idleTurnsLeft > 0){
			poke.idleTurnsLeft--;
			if (poke.status.contains("sleep") || poke.status.contains("rest")){
				System.out.println(poke.name + " is asleep");
			}
			else{
				System.out.println(poke.name + " is idle");
			}
			return false;
		}
		else if (poke.status.contains("frozen")){
			System.out.println(poke.name + " is frozen");
			if (rand.nextInt(100) < 25){
				poke.status.remove("frozen");
			}
			return false;
		}
		else if (poke.status.contains("paralyzed")){
			System.out.println(poke.name + " is paralyzed");
			if (rand.nextInt(100) < 25){
				System.out.println(poke.name + "is to paralyzed to attack");
				return false;
			}
		}
		return true;
	}
	public static void wakeUp(Pokemon myPokemon,Pokemon enemyPokemon){
		if (myPokemon.status.contains("bide")){
			enemyPokemon.damage += myPokemon.damageTaken * 2;
			System.out.println("Bide reflected " + myPokemon.damageTaken *2);
			myPokemon.damageTaken = 0;
			myPokemon.status.remove("bide");
		}
		else if (myPokemon.status.equals("rest")){
			myPokemon.damage = 0;
			myPokemon.status.clear();
		}
	}
	public static void applyOngoingDamage(Pokemon poke){
		if (poke.status.contains("poisoned")){
			double dmg = poke.hp * .125;
			poke.damage += (int)dmg;
			System.out.println(poke.name + " took " + (int)dmg + " poison damage");
		}
		else if (poke.status.contains("burned")){
			double dmg = poke.hp * .125;
			poke.damage += (int)dmg;
			System.out.println(poke.name + " took " + (int)dmg + " burned damage");
		}
	}
	public static void myTurn(Pokemon myPokemon,Pokemon enemyPokemon){
		System.out.println("your turn");
		if (canAttack(myPokemon)){
			applyOngoingDamage(myPokemon);
			wakeUp(myPokemon,enemyPokemon);
			Move myMove = pickMove(myPokemon.moves);
			System.out.println(myPokemon.name + " used " + myMove.name);
			myPokemon.fight(enemyPokemon,myMove);
		}
		System.out.println();
		myPokemon.displayPokemon();
		System.out.println();
		enemyPokemon.displayPokemon();
		if (!myPokemon.fainted() && !enemyPokemon.fainted()){
			enemyTurn(myPokemon, enemyPokemon);
		}
		
	}
	public static void enemyTurn(Pokemon myPokemon,Pokemon enemyPokemon){
		System.out.println("enemy turn");
		if (canAttack(enemyPokemon)){	
			applyOngoingDamage(enemyPokemon);
			wakeUp(enemyPokemon,myPokemon);
			Move enemyMove = pickEnemyMove(enemyPokemon.moves);
			System.out.println(enemyPokemon.name + " used " + enemyMove.name);
			enemyPokemon.fight(myPokemon,enemyMove);
		}
		myPokemon.displayPokemon();
		System.out.println();
		enemyPokemon.displayPokemon();
		System.out.println();
		if (!myPokemon.fainted() && !enemyPokemon.fainted()){
			myTurn(myPokemon, enemyPokemon);
		}
	}
	public static Move pickEnemyMove(ArrayList<Move> moves){
		Random rand = new Random();
		Move move = moves.get(rand.nextInt(moves.size()));
		return move;
	}
	public static Move pickMove(ArrayList<Move> moves){
		System.out.println("Choose your move");
		for (int i = 0; i < moves.size(); i++){
			System.out.print((i+1) + " ");
			moves.get(i).displayMove();
		}
		Scanner input = new Scanner(System.in);
		int answer = input.nextInt();
		if (answer <= moves.size() && answer >= 1){
			
			if (!((moves.get(answer -1).pp - moves.get(answer -1).uses) <= 0)){
				return moves.get(answer-1);
			}
			else{
				System.out.println("out of uses");
				return pickMove(moves);
			}
		}
		System.out.println("please pick a valid move");
		return pickMove(moves);
	}
	public static Pokemon pickPokemon(ArrayList<Pokemon> pokemons){
		System.out.println("Choose your Pokemon");
		for (int i = 0; i < pokemons.size(); i++){
			System.out.print((i + 1) + " ");
			pokemons.get(i).displayPokemon();
			System.out.println();
		}
		Scanner input = new Scanner(System.in);
		int answer = input.nextInt();
		if (answer <= pokemons.size() && answer >= 1){
			return pokemons.get(answer-1);
		}
		System.out.println("please pick a valid pokemon");
		return pickPokemon(pokemons);
	}
}