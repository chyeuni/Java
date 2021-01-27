package MP1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;

class Fruit{
	private String name;
	private double price;
	
	public Fruit(String in_name, double in_price) {
		name = in_name;
		price = in_price;
	}
	public double getPrice() {
		return price;
	}
	public String getName() {
		return name;
	}
}

class FruitBox<T extends Fruit>{
	private int number;
	private Fruit expensive;
	private Fruit cheapest;
	private double average;
	
	public FruitBox (){
		number=0;
		expensive=null;
		cheapest=null;
		average=0;
	}
	public void add(Fruit input) {
		System.out.println(input.getName()+" "+ input.getPrice());
		if(expensive==null)
			expensive=input;
		else if(input.getPrice()>expensive.getPrice())
			expensive=input;
		if(cheapest==null)
			cheapest=input;
		else if(input.getPrice()<cheapest.getPrice())
			cheapest=input;
		average=(average*number+input.getPrice())/(number+1);
		number++;
	}
	public double getMaxPrice() {
		return expensive.getPrice();
	}
	public String getMaxItem() {
		return expensive.getName();
	}
	public double getMinPrice() {
		return cheapest.getPrice();
	}
	public String getMinItem() {
		return cheapest.getName();
	}
	public int getNumItems() {
		return number;
	}
	public double getAvgPrice() {
		return average;
	}
}

class ItemReader extends FruitBox<Fruit>{
	public static boolean fileToBox(String filename, FruitBox<Fruit> box) {
		BufferedReader input = null;
		try {
			input = new BufferedReader(new FileReader(filename));
			while(true) {
				String data = input.readLine();
				if(data==null) break;
				String[] temp = data.split(" ");
				Fruit f = new Fruit(temp[0], Double.parseDouble(temp[1]));
				box.add(f);
			}
			input.close();
			return true;
		}
		catch(FileNotFoundException fnfe) {
			System.out.println("Input file not found.");
			return false;
		}
		catch(IOException ioe) {
			return false;
		}
		finally{ 
			if(input != null)
				try{
					input.close();
				}catch(IOException e){
					e.printStackTrace();
				} 
		}
      
	 }
}

public class Problem14 {
	public static void main(String[] args) {
		FruitBox<Fruit> box = new FruitBox<>();
		boolean rv = ItemReader.fileToBox("input_prob14.txt", box);
		if(rv == false) return;
		box.add(new Fruit("orange", 9.99));
		System.out.println("----------------");
		System.out.println("    Summary");
		System.out.println("----------------");
		System.out.println("number of items: " + box.getNumItems());
		System.out.println("most expensive item: " + box.getMaxItem() + " (" +
				box.getMaxPrice() + ")");
		System.out.println("cheapest item: " + box.getMinItem() + " (" +
				box.getMinPrice() + ")");
		System.out.printf("average price of items: %.2f", box.getAvgPrice());}
}
