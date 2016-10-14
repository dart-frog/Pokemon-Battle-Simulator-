import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Reference {
	public static double typeEfficacy(int attackerType, int targetType){
		File TEFile = new File("type_efficacy.txt");
		try{
			Scanner input = new Scanner(TEFile);
			input.nextLine();
			Scanner line = new Scanner(input.nextLine());
			while (line.nextInt() != attackerType){
				line = new Scanner(input.nextLine());
			}
			while (line.nextInt() != targetType){
				line = new Scanner(input.nextLine());
				line.next();
			}
			return line.nextInt()/100.0;
		}
		catch(FileNotFoundException e){
			System.out.print("Pokemon info not found");
			e.printStackTrace();
		}
		return 0;
	}
	public static String getTypeFromID(int id){
		String typeString;
		switch (id){
			case 1: typeString = "normal";
					break;
			case 2: typeString = "fighting";
					break;
			case 3: typeString = "flying";
					break;
			case 4: typeString = "poison";
					break;
			case 5: typeString = "ground";
					break;
			case 6: typeString = "rock";
					break;
			case 7: typeString = "bug";
					break;
			case 8: typeString = "ghost";
					break;
			case 9: typeString = "steel";
					break;
			case 10: typeString = "fire";
					break;
			case 11: typeString = "water";
					break;
			case 12: typeString = "grass";
					break;
			case 13: typeString = "electric";
					break;
			case 14: typeString = "psychic";
					break;
			case 15: typeString = "ice";
					break;
			case 16: typeString = "dragon";
				break;
			case 17: typeString = "dark";
				break;
			case 18: typeString = "fairy";
				break;
			default: typeString = "unknown";
				break;
		}
		return typeString;
	}
}
