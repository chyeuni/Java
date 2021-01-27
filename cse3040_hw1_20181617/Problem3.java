package °úÁ¦1;

import java.util.Scanner;

public class Problem3 {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.printf("Enter a text: ");
		String text = in.nextLine();
		
		char letterC;
		while(true) {
			System.out.printf("Enter a letter: ");
			String letter = in.nextLine();
			if(letter.length()==1) {
				letterC=letter.charAt(0);
				break;
			}
			System.out.printf("You must enter a single letter.\n");
		}
		
		int i=0;
		int count=0;
		char c;
		while(i<text.length()) {
			c=text.charAt(i);
			if(c == letterC) {
				count++;
			}
			i++;
		}
		System.out.printf("There are %d %c's in the text.\n",count, letterC);
		in.close();
		return;
	}
}
