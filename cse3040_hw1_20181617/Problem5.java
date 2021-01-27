package °úÁ¦1;

import java.util.Scanner;

public class Problem5 {
	public static void main(String[] args) {
		System.out.printf("Enter exam scores of each student.\n");
		Scanner in = new Scanner(System.in);
		int firstStu=0;
		int firstScore=0;
		int secondStu=0;
		int secondScore=0;
		int score;
		for(int i=0; i<5; i++) {
			System.out.printf("Score of student %d: ",i+1);
			score = in.nextInt();
			
			if(score>firstScore) {
				secondStu=firstStu;
				secondScore=firstScore;
				firstStu=i+1;
				firstScore=score;
			}
			else if(score>secondScore) {
				secondStu=i+1;
				secondScore=score;
			}
			
		}
		
		System.out.printf("The 1st place is student %d with %d points.\n", firstStu, firstScore);
		System.out.printf("The 2nd place is student %d with %d points.\n", secondStu, secondScore);
		in.close();
		return;
	}
}
