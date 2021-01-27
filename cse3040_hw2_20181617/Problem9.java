package hw2;


class Point {
	private double d[];
	private int dimension;
	
	public Point(double [] input){
		dimension = input.length;
		d=new double[dimension];
		for(int i=0; i<dimension; i++) {
			d[i]=input[i];
		}
	}
	public int getdimension() {
		return dimension;
	}
	public double getd(int i) {
		return d[i];
	}
}

class EuclideanDistance{
	public static double getDist(Point p1, Point p2) {
		if (p1.getdimension() != p2.getdimension()) {
			return -1;
		}
		else {
			double sum = 0;
			for(int i=0; i<p1.getdimension(); i++) {
				sum=sum+(p1.getd(i)-p2.getd(i))*(p1.getd(i)-p2.getd(i));
			}
			return Math.sqrt(sum);
		}
	}
}

class ManhattanDistance{
	public static double getDist(Point p1, Point p2) {
		if (p1.getdimension() != p2.getdimension()) {
			return -1;
		}
		else {
			double sum = 0;
			for(int i=0; i<p1.getdimension(); i++) {
				sum=sum+Math.abs(p1.getd(i)-p2.getd(i));
			}
			return sum;
		}
	}
}

public class Problem9 {

	public static void main(String[] args) {
		Point p1 = new Point(new double[] {1.0, 2.0, 3.0});
		Point p2 = new Point(new double[] {4.0, 5.0, 6.0});
		System.out.println("Euclidean Distance: " + EuclideanDistance.getDist(p1, p2));
		System.out.println("Manhattan Distance: " + ManhattanDistance.getDist(p1, p2));
		Point p3 = new Point(new double[] {1.0, 2.0, 3.0});
		Point p4 = new Point(new double[] {4.0, 5.0});
		System.out.println("Euclidean Distance: " + EuclideanDistance.getDist(p3, p4));
		System.out.println("Manhattan Distance: " + ManhattanDistance.getDist(p3, p4));
	}

}
