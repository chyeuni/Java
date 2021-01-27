package MP1;

class SubsequenceChecker{
	public static void check(String str1, String str2){
		int i=0;
		int j=0;
		int[] index = new int[str2.length()];
		
		while(i!=str1.length() && j!=str2.length()) {
			if(str1.charAt(i)==str2.charAt(j)) {
				index[j]=i;
				j++;
			}
			i++;
		}
		
		if(j!=str2.length()) {
			System.out.println(str2+ " is not a subsequence of "+ str1);
		}
		else {
			System.out.println(str2+ " is a subsequence of "+ str1);
			for(int k=0; k<str2.length(); k++)
				System.out.printf("%d ", index[k]);
			System.out.println();
		}
	}
}

public class Problem12 {
	public static void main(String[] args) {
		SubsequenceChecker.check("supercalifragilisticexpialidocious", "pads");
		SubsequenceChecker.check("supercalifragilisticexpialidocious", "padsx");
	}
}