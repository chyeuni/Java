package MP1;

class PalindromeChecker{
	public static void check(String input) {
		int length = input.length();
		int state =0;
		for(int i=0; i<length/2; i++) {
			if(input.charAt(i)!=input.charAt(length-1-i)) {
				state=1;
				break;
			}
		}
		if(state==1)
			System.out.println(input+" is not a palindrome");
		else
			System.out.println(input+" is a palindrome");
	}
	
	public static void check(int input) {
		String temp = Integer.toString(input);
		check(temp);
	}

}

public class Problem11 {
	public static void main(String[] args) {
		PalindromeChecker.check("abcde");
		PalindromeChecker.check("abcba");
		PalindromeChecker.check(1234);
		PalindromeChecker.check(12321);
	}
}

