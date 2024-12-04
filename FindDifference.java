/*Author: Blake Mills
 * This program was written to solve the second puzzle for the first day of Advent of Code (AoC)
 * To view the problem, visit  https://adventofcode.com/2024/day/1
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FindDifference {
	public static String inputFile = "locationIds.txt";
	
	public static void main(String[] args) {
		List<Integer> firstList = firstListImport();
		List<Integer> secondList = secondListImport();
		
		Collections.sort(firstList);
		Collections.sort(secondList);
		
		int similarityScore = 0;
		
		for(int i = 0; i < firstList.size(); i++) {
			int compareNumber = firstList.get(i);
			int occurrences = 0;
			for(int num : secondList) {
				if(compareNumber == num) {
					occurrences++;
				}
			}
			similarityScore += (compareNumber * occurrences);
		}
		
		System.out.println(similarityScore);
		
		
	}
	public static List<Integer> firstListImport() {
		List<Integer> first = new ArrayList<>();
		try {
			
			FileReader fr = new FileReader(inputFile);
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();
			
			while(line != null) {
				String[] data = line.split("   ");
				String firstString = data[0];
				int locationId = Integer.parseInt(firstString);
				first.add(locationId);
				line = br.readLine();
			}
			
			fr.close();
			br.close();
			
		}
		catch(IOException e) {
			System.out.println("File read error: "+e);
		}
		return first;
	}
	
	public static List<Integer> secondListImport() {
		List<Integer> second = new ArrayList<>();
		try {
			
			FileReader fr = new FileReader(inputFile);
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();
			
			while(line != null) {
				String[] data = line.split("   ");
				String secondString = data[1];
				int locationId = Integer.parseInt(secondString);
				second.add(locationId);
				line = br.readLine();
			}
			fr.close();
			br.close();
			
			
			
		}
		catch(IOException e) {
			System.out.println("File read error: "+e);
		}
		return second;
	}
}
