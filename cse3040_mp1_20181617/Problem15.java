package MP1;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

class Item{
	private String name=null;
	private int count=0;
	
	public Item(String in_word) {
		name=in_word;
		count++;
	}
	public String getName() {
		return name;
	}
	public void inc_count() {
		count++;
	}
	public String toString() {
		return name+" "+count;
	}
}


class MyFileReader{
	public static boolean readDataFromFile(String filename, ArrayList<Item> list){
		FileReader reader=null;
		String word=null;
		try {
			reader = new FileReader(filename);
			while(true) {
				int temp=reader.read();
				if(temp==-1) break;

				if(temp>='A'&&temp<='Z')
					temp=temp+32;
				
				char data = (char)temp;
				
				if(data==' ') {
					int state=0;
					Item I=null;
					for(int i=0; i<list.size(); i++ ) {
						I=list.get(i);
						if(I.getName().equals(word)) {
							I.inc_count();
							state=1;
							break;
						}
					}
					if(state==0) {
						if(word!=null) {
							I = new Item(word);
							list.add(I);
						}
					}
					word=null;
				}
				else {
					if(word==null)
						word=String.valueOf(data);
					else {
						word=word+data;
					}
				}
				
			}
			
			int state=0;
			Item I=null;
			for(int i=0; i<list.size(); i++ ) {
				I=list.get(i);
				if(I.getName().equals(word)) {
					I.inc_count();
					state=1;
					break;
				}
			}
			if(state==0) {
				if(word!=null) {
					I = new Item(word);
					list.add(I);
				}
			}
			word=null;
			
			reader.close();
			return true;
		}
		catch(FileNotFoundException fnfe) {
			return false;
		}
		catch(IOException ioe) {
			return false;
		}
		 finally{ 
	         if(reader != null)
	            try{
	               reader.close();
	            }catch(IOException e){
	            	e.printStackTrace();
	            } 
	      }
	}
}

public class Problem15 {
	public static void main(String[] args) {
		ArrayList<Item> list = new ArrayList<>();
		boolean rv = MyFileReader.readDataFromFile("input_prob15.txt", list);
		if(rv == false) {
			System.out.println("Input file not found.");
			return;
		}
		for(Item it: list) System.out.println(it);
	}
}

