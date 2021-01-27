package °úÁ¦1;

import java.util.Scanner;

public class Problem1 {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.printf("ASCII code teller. Enter a letter: ");
		String letter = in.nextLine();

		if(letter.length()!=1){
			System.out.printf("You must input a single uppercase or lowercase alphabet.");
			in.close();
			return;
		}
		
		char c = letter.charAt(0);
		
		if(c>= 'a' && c<='z') {
			System.out.printf("The ASCII code of %c is %d.\n", c, (int)c);
			
		}
		else if(c>='A' && c<='Z') {
			System.out.printf("The ASCII code of %c is %d.\n", c, (int)c);
		}
		else {
			System.out.println("You must input a single uppercase or lowercase alphabet.");
			in.close();
			return;
		}
		
		in.close();
		return;
	}
}
