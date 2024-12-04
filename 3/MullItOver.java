/*Author: Blake Mills
 * This program was written to solve the problem for day 3 during Advent of Code 2024!
 * For more information, navigate to this link: https://adventofcode.com/2024/day/3
 */
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MullItOver {
	public static String inputPath = "input.txt";

	public static void main(String[] args) {
		answer1();
		answer2();
	}

	public static void answer1() {
		ArrayList<Integer> multipliedNumbers = findNumbersFor1();
		int addedProducts = addNumbers(multipliedNumbers);
		System.out.println("The answer to problem 1 is "+ addedProducts);

	}

	public static void answer2() {
		ArrayList<Integer> multipliedNumbers = findNumbersFor2();
		int addedProducts = addNumbers(multipliedNumbers);
		System.out.println("The answer to problem 2 is "+ addedProducts);
	}

	//Helpers
	public static ArrayList<Integer> findNumbersFor1() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(inputPath));
			ArrayList<Character> allChars = getAllChars(br);
			ArrayList<Integer> numbersToAdd = new ArrayList<>();
			int potentialMulStart = 0;

			while(potentialMulStart != -1) {
				potentialMulStart = findMulFunction(potentialMulStart, allChars);
				numbersToAdd.add(calculateMulFunction(potentialMulStart, allChars));
			}

			return numbersToAdd;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		return null;
	}

	public static ArrayList<Integer> findNumbersFor2() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(inputPath));
			ArrayList<Character> allChars = getAllChars(br);
			ArrayList<Integer> numbersToAdd = new ArrayList<>();
			boolean mulsEnabled = true;
			int potentialMulStart = 0;
			int previousMul = 0;

			while(potentialMulStart != -1) {
				potentialMulStart = findMulFunction(potentialMulStart, allChars);
				mulsEnabled = checkForDoAndDont(previousMul, potentialMulStart,allChars, mulsEnabled);
				if(mulsEnabled) {
					numbersToAdd.add(calculateMulFunction(potentialMulStart, allChars));
				}
				previousMul = potentialMulStart;
			}

			return numbersToAdd;




		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		return null;
	}

	public static boolean checkForDoAndDont(int startIndex, int endIndex, ArrayList<Character> list, boolean currentState) {
		boolean state = currentState;
		for(int i = startIndex; i < endIndex; i++) {
			if(list.get(i) == 'd' && list.get(i+1) == 'o' && list.get(i+2) == '('&& list.get(i+3) == ')') {
				state = true;
			}

			if(list.get(i) == 'd' && list.get(i+1) == 'o' && list.get(i+2) == 'n'
					&& list.get(i+3) == '\'' && list.get(i+4) == 't' && list.get(i+5) == '(' && list.get(i+6) == ')') {
				state = false;
			}
		}
		return state;
	}

	public static int calculateMulFunction(int index, ArrayList<Character> list) {
		int endParenthesisIndex = -1;
		int firstNumber = 0;
		int secondNumber = 0;
		String firstNumString = "";
		String secondNumString = "";
		boolean onFirstNumber = true;
		int numberOfCommas = 0;

		// Find the closing parenthesis ')'
		for (int i = index + 1; i < index + 9; i++) {
			if (list.get(i) == ')') {
				endParenthesisIndex = i;
				break;
			}
		}

		if (endParenthesisIndex == -1) {
			return 0; // No closing parenthesis found
		}

		// Extract the numbers inside the parentheses
		for (int i = index + 1; i < endParenthesisIndex; i++) {
			char currentChar = list.get(i);

			switch (currentChar) {
			case '0': case '1': case '2': case '3': case '4': case '5':
			case '6': case '7': case '8': case '9':
				if (onFirstNumber) {
					firstNumString = firstNumString.concat(String.valueOf(currentChar));
				} else {
					secondNumString = secondNumString.concat(String.valueOf(currentChar));
				}
				break;
			case ',':
				if (numberOfCommas == 0) {
					onFirstNumber = false;
					numberOfCommas++;
				} else {
					return 0; // More than one comma, invalid format
				}
				break;
			default:
				return 0; // Invalid character inside parentheses
			}
		}

		if (firstNumString.isEmpty() || secondNumString.isEmpty()) {
			return 0; // Missing numbers
		}

		try {
			firstNumber = Integer.parseInt(firstNumString);
			secondNumber = Integer.parseInt(secondNumString);
		} catch (NumberFormatException e) {
			return 0; // Invalid number format
		}

		return multiplyNumbers(firstNumber, secondNumber); // Return the product
	}

	/**Finds the sequence of characters "mul(", which is the start of the multiply function
	 * 
	 * @param list the list to search for mul(
	 * @return the index of the opening parenthesis for the mul function
	 */
	public static int findMulFunction(int currentMul, ArrayList<Character> list) {
		for(int i = currentMul; i < list.size(); i++) {
			if(list.get(i) == 'm' && list.get(i+1) == 'u' && list.get(i+2) == 'l' && list.get(i+3) == '(') {
				return i+3;
			}
		}
		return -1;
	}

	public static ArrayList<Character> getAllChars(BufferedReader br){
		ArrayList<Character> listOfCharacters = new ArrayList<>();
		int currentAsciiChar;

		try {

			currentAsciiChar = br.read();
			while(currentAsciiChar != -1) {
				char convertedToChar = (char) currentAsciiChar;
				listOfCharacters.add(convertedToChar);
				currentAsciiChar = br.read();

			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return listOfCharacters;
	}

	public static int multiplyNumbers(int x, int y) {
		return x * y;
	}

	public static int addNumbers(ArrayList<Integer> numbers) {
		int addedProducts = 0;
		for (int i = 0; i < numbers.size(); i++) {
			addedProducts = addedProducts + numbers.get(i);
		}
		return addedProducts;
	}

}
