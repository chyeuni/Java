package hw2;

import java.util.Scanner;

interface IntSequenceStr {
	boolean hasNext();
	int next();
}

class BinarySequenceStr implements IntSequenceStr{
	private int number;
	private int result;
	public BinarySequenceStr (int num) {
		number=num;
		result=-1;
	}
	public boolean hasNext() {
		if(result==-1)
			return true;
		else
			return false;
	}
	public int next() {
		result = 0;
		for(int i=1; ; i++) {
			int temp = number%2;
			result = (int) (result + temp * Math.pow(10, i-1));
			if(number/2==1) {
				result=(int) (result+Math.pow(10, i));
				break;
			}
			number=number/2;
			
		}
		return result;
	}
}

public class Problem7 {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.print("Enter a positive integer: ");
		String str = in.nextLine();
		int num = Integer.parseInt(str);
		in.close();
		System.out.println("Integer: " + num);
		IntSequenceStr seq = new BinarySequenceStr(num);
		System.out.print("Binary number: ");
		while(seq.hasNext()) System.out.print(seq.next());
		System.out.println(" ");

	}

}
