import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Pokemon {
	int id;
	String name;
	int height;
	int weight; 
	int exp;
	int hp;
	int damage = 0;
	int level = 30;
	int version = 1;
	ArrayList<Integer> types;
	ArrayList<Move> moves; 
	int attack;
	int defense;
	int special_attack;
	int special_defense;
	int speed;
	int attackStage;
	int defenseStage;
	int speedStage;
	int damageTaken;
	int idleTurnsLeft = 0;
	Move lastMove;
	ArrayList<String> status = new ArrayList<String>();
	
	public Pokemon(int id){
		this.id = id;
		readPokemon();
		readStats();
		readTypes();
		readMoves();
	}
	public int getAttack(){
		if (attackStage >= 0){
			return attack * ((attackStage + 2) / 2);
		}
		else{
			int att = (int)(attack * (2.0 / (Math.abs(attackStage) + 2.0)));
			if (attack > 1){	
				return att;
			}
			return 1;
		}
	}
	public int getDefense(){
		if (defenseStage >= 0){
			return defense * ((defenseStage + 2) / 2);
		}
		else{
			int def = (int)(defense * (2.0/ (Math.abs(defenseStage) + 2.0)));
			if (def > 1){		
				return def;
			}
			return 1;
		}
	}
	
	private void readPokemon(){
		File pokeFile = new File("pokemon.txt");
		try{
			Scanner input = new Scanner(pokeFile);
			input.nextLine();
			Scanner line = new Scanner(input.nextLine());
			while (line.nextInt() != id){
				line = new Scanner(input.nextLine());
			}
			name = line.next();
			height = line.nextInt();
			weight = line.nextInt();
			exp = line.nextInt();
		}
		catch(FileNotFoundException e){
			System.out.print("Pokemon info not found");
			e.printStackTrace();
		}
	}
	private void readStats(){
		File statFile = new File("pokemon_stats.txt");
		try{
			Scanner input = new Scanner(statFile);
			input.nextLine();
			Scanner line = new Scanner(input.nextLine());
			while (line.nextInt() != id){
				line = new Scanner(input.nextLine());
			}
			line.next();
			hp = line.nextInt();
			line = new Scanner(input.nextLine());
			line.next();
			attack = line.nextInt();
			line = new Scanner(input.nextLine());
			line.next();
			defense = line.nextInt();
			line = new Scanner(input.nextLine());
			line.next();
			special_attack = line.nextInt();
			line = new Scanner(input.nextLine());
			line.next();
			special_defense = line.nextInt();
			line = new Scanner(input.nextLine());
			line.next();
			speed = line.nextInt();	
		}
		catch(FileNotFoundException e){
			System.out.print("Pokemon info not found");
			e.printStackTrace();
		}
	}
	private void readTypes(){
		File typeFile = new File("pokemon_types.txt");
		types = new ArrayList<Integer>();
		try{
			Scanner input = new Scanner(typeFile);
			input.nextLine();
			Scanner line = new Scanner(input.nextLine());
			while (line.nextInt() != id){
				line = new Scanner(input.nextLine());
			}
			do {
				types.add(line.nextInt());
				line = new Scanner(input.nextLine());
			}while (line.nextInt() == id);
				
			
		}
		catch(FileNotFoundException e){
			System.out.print("Pokemon info not found");
			e.printStackTrace();
		}
	}
	private void readMoves(){
		moves = new ArrayList<Move>();
		File typeFile = new File("pokemon_moves.txt");
		try{
			Scanner input = new Scanner(typeFile);
			input.nextLine();
			Scanner line = new Scanner(input.nextLine());
			while (!(line.nextInt() == id && line.nextInt() == version)){
				line = new Scanner(input.nextLine());
			}
			do {
				//line.next();
				int moveID = line.nextInt();
				line.next();
				if (line.nextInt() <= level){
					moves.add(new Move(moveID));
				}
				line = new Scanner(input.nextLine());
			}while(line.nextInt() == id && line.nextInt() == version);
		}
		catch(FileNotFoundException e){
			System.out.print("Pokemon info not found");
			e.printStackTrace();
		}
		Random rand =  new Random();
		while (moves.size() > 4 ){
			moves.remove(rand.nextInt(moves.size()));
		}
		moves.add(new Move(156));
		moves.add(new Move(92));
	}
	public void applyEffects(Pokemon target, Move myAttack){
		if (myAttack.effectId == 11){
			attackStage += 1;
			System.out.println(name + "'s attack rose");
		}
		else if (myAttack.effectId == 12){
			defense += 1;
			System.out.println(name + "'s defense rose");
		}
		else if (myAttack.effectId == 19){
			target.attackStage -= 1;
			System.out.println(target.name + "'s attack fell");
		}
		else if (myAttack.effectId == 20){
			target.defenseStage -= 1;
			System.out.println(target.name + "'s defense fell");
		}
		if (myAttack.effectId == 51){
			attackStage += 2;
			System.out.println(name + "'s attack rose sharply");
		}
		else if (myAttack.effectId == 52){
			defense += 2;
			System.out.println(name + "'s defense rose sharply");
		}
		else if (myAttack.effectId == 59){
			target.attackStage -= 2;
			System.out.println(target.name + "'s attack fell sharply");
			
		}
		else if (myAttack.effectId == 60){
			target.defense -= 2;
			System.out.println(target.name + "'s defense fell sharply");
		}
		
		
	}

	public void fight(Pokemon target, Move myAttack){
		myAttack.uses += 1;
		Random rand = new Random();
		if (myAttack.effectId == 2){
			target.idleTurnsLeft = rand.nextInt(4) + 1;
			target.status.add("sleep");
		}
		else if (myAttack.effectId == 3){
			if (rand.nextInt(100) < myAttack.chance){
				target.status.add("poisoned");
			}
		}
		else if (myAttack.effectId == 5){
			if (rand.nextInt(100) < myAttack.chance){
				target.status.add("burned");
			}
		}
		else if (myAttack.effectId == 6){
			if (rand.nextInt(100) < myAttack.chance){
				target.status.add("frozen");
			}
		}
		else if (myAttack.effectId == 7){
			if (rand.nextInt(100) < myAttack.chance){
				target.status.add("paralyzed");
			}
		}
		else if (myAttack.effectId == 27){
			idleTurnsLeft = 2;
			status.add("bide");
		}
		else if (myAttack.effectId == 34){
			target.status.add("poisoned");
		}
		else if (myAttack.effectId == 38){
			idleTurnsLeft = 2;
			status.add("rest");
		}
		else if (myAttack.effectId == 83){
			if (target.lastMove != null){	
				myAttack = target.lastMove;
				System.out.println(name + " mimics " + myAttack.name);
			}
			else{
				System.out.println("no move to mimic");
			}
		} 
		applyEffects(target,myAttack);
			if (!myAttack.passive){
				
				if ((rand.nextInt(100) + 1) <= myAttack.accuracy){
				
					double modifier = 3;
					if (types.get(0) == myAttack.typeId){
						modifier *= 1.5;
					}
					else if (types.size() == 2 && types.get(1) == myAttack.typeId){
						modifier *= 1.5;
					}
					for (int j = 0; j < target.types.size(); j++){
						modifier *= Reference.typeEfficacy(myAttack.typeId,target.types.get(j));
					}
					modifier *= (85 + rand.nextInt(16))/ 100.0;
					Double dmg = (((2 * level + 10) / 250) * (getAttack() / target.getDefense()) * myAttack.power + 2) * modifier;
					target.damage += dmg.intValue();   
					if (status.contains("bide")){
						target.damageTaken += dmg.intValue();
					}
					System.out.println("Damage "+dmg.intValue());
				}
				else{
					System.out.println(myAttack.name + " misses");
				}
		}
		lastMove = myAttack;
	}
	public void displayFriendlyPokemon(){
		System.out.println(name);
		System.out.println("hp " + (hp - damage) + "/" + hp);
		for (int i = 0; i < types.size();i++){
			System.out.print(Reference.getTypeFromID(types.get(i)) + " ");
		}
		System.out.println("moves:");
		
		System.out.println();
		for (int i = 0; i < moves.size(); i++){
			moves.get(i).displayMove();
			System.out.println("--------------------");
		}
	}
	public boolean fainted(){
		if (damage >= hp){
			return true;
		}
		else{
			return false;
		}
	}
	public void displayPokemon(){
		System.out.println(name);
		System.out.println("hp " + (hp - damage) + "/" + hp);
		for (int i = 0; i < types.size();i++){
			System.out.print(Reference.getTypeFromID(types.get(i)) + " ");
		}
	}
}
