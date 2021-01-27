package °úÁ¦1;

import java.util.Scanner;

public class Problem4 {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.printf("Enter a text: ");
		String text = in.nextLine();
		
		char letterC;
		String letter;
		while(true) {
			System.out.printf("Enter a string: ");
			letter = in.nextLine();
			if(letter.length()!=0) {
				letterC=letter.charAt(0);
				break;
			}
			System.out.printf("You must enter a string.\n");
		}
		
		int i=0;
		int j=0;
		int count=0;
		char textC;
		for(i=0; i<text.length()-letter.length()+1;i++) {
			textC=text.charAt(i);
			letterC=letter.charAt(0);
			if(textC == letterC) {
				for(j=1; j<letter.length(); j++) {
					textC=text.charAt(i+j);
					letterC=letter.charAt(j);
					if(textC!=letterC)
						break;
				}
				if(j==letter.length())
					count++;
			}
		}
		System.out.printf("There are %d instances of \"%s\".\n",count, letter);
		in.close();
		return;
		
	}
}
