import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Move {
	int id;
	String name;
	int pp;
	int uses = 0;
	int typeId;
	int power;
	int accuracy;
	int effectId;
	int chance;
	boolean passive;
	public Move(int id){
		File movesFile = new File("moves.csv");
		try{
			Scanner input = new Scanner(movesFile);
			input.nextLine();
			String nl = input.nextLine();
			Scanner line = new Scanner(nl);
			line.useDelimiter(",");
			while (line.nextInt() != id){
				nl = input.nextLine();
				line = new Scanner(nl);
				line.useDelimiter(",");
			}
			
			name = line.next();
			line.next();
			typeId = line.nextInt();
			String powerString = line.next();
			if (!powerString.equals("")){
				power = Integer.parseInt(powerString);
				passive = false;
			}
			else{
				passive = true;
			}
			pp = line.nextInt();
			String accuracyString = line.next();
			if (!accuracyString.equals("")){
				accuracy = Integer.parseInt(accuracyString);
			}
			else{
				accuracy = 100;
			}
			line.next();
			line.next();
			line.next();
			effectId  = line.nextInt();
			String chanceString = line.next();
			if (!chanceString.equals("")){
				chance = Integer.parseInt(chanceString);
			}
		}
		catch(FileNotFoundException e){
			System.out.print("Pokemon info not found");
			e.printStackTrace();
		}
	}
	public void displayMove(){
		System.out.println(name);
		System.out.print("("+Reference.getTypeFromID(typeId)+") ");
		if (!passive)
			System.out.print(" pwr: " + power + " acc: " + accuracy + " ");
		System.out.println("pp: " + (pp - uses));
	}
}
