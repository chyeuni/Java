package °úÁ¦1;

import java.util.Random;
import java.util.Scanner;

public class Problem2 {
	public static void main(String[] args) {
	Random generator = new Random();
	int number = generator.nextInt();
	
	number=number%100;
	int min = 1;
	int max = 100;
	
	Scanner in = new Scanner(System.in);
	for(int i=1; ; i++) {
		System.out.printf("[%d] Guess a number (%d-%d): ", i, min, max);
		int letter = in.nextInt();
		
		if(letter<min || letter>max) {
			System.out.printf("Not in range!\n");
			i--;
		}
		else if(letter<number) {
			System.out.printf("Too small!\n");
			min=letter+1;
		}
		else if(letter>number) {
			System.out.printf("Too large!\n");
			max=letter-1;
		}
		if(letter==number) {
			System.out.printf("Correct! Number of guesses: %d\n", i);
			break;
		}
	}
	
		in.close();
		return;
	}
}
