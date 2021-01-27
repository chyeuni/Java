package hw2;

interface Shape{
	public double Area();
}

class Circle implements Shape{
	private double r;
	public Circle (double input) {
		r=input;
	}
	@Override
	public double Area() {
		return r*r*Math.PI;
	}
	
}

class Square implements Shape{
	private double r;
	public Square (double input) {
		r=input;
	}
	@Override
	public double Area() {
		return r*r;
	}
}

class Rectangle implements Shape{
	private double r1, r2;
	public Rectangle (double input1, double input2) {
		r1=input1;
		r2=input2;
	}
	@Override
	public double Area() {
		return r1*r2;
	}
}


public class Problem8 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Shape[] arr = { new Circle(5.0), new Square(4.0),
						new Rectangle(3.0, 4.0), new Square(5.0)};
		System.out.println("Total area of the shapes is: " + sumArea(arr));

	}
	
	static double sumArea(Shape[] arr) {
		double result = 0;
		
		for(int i=0; i<arr.length; i++) {
			result = result + arr[i].Area();
		}
		return result; 
	}
}
