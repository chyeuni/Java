package hw2;

interface IntSequence{
	boolean hasNext();
	int next();
}

class FibonacciSequence implements IntSequence{
	public int n1;
	public int n2;
	private int temp;
	public FibonacciSequence () {
		n1=0;
		n2=0;
		temp=0;
	}
	public boolean hasNext() {
			return true;
	}
	public int next() {
		if(n1==0 && n2==0) {
			n2=n2+1;
			return n1;
		}
		else {
			temp=n1+n2;
			n1=n2;
			n2=temp;
			return n1;
		}
	}
}
public class Problem6 {

	public static void main(String[] args) {

		IntSequence seq = new FibonacciSequence();
		for(int i=0; i<20; i++) {
			if(seq.hasNext() == false) break;
			System.out.print(seq.next() + " ");
		}
		System.out.println(" ");
	}
}
