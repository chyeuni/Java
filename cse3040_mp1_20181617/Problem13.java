package MP1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

class Text {
	private int[] count;
	
	public Text() {
		count = new int[26];	
		for(int i=0; i<26; i++)
			count[i]=0;
		
	}
	public boolean readTextFromFile(String str){
		FileInputStream input = null;
		try{
			input = new FileInputStream(str);
			int data;
			while((data = input.read()) !=-1) {
				if(data>='a'&&data<='z')
					count[data-'a']++;
				else if(data>='A' && data<='Z')
					count[data-'A']++;
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
	
	public int countChar(char c) {
		return count[c-'a'];
	}
}

public class Problem13 {
	public static void main(String[] args) {
		 Text t = new Text();
		 if(t.readTextFromFile("input_prob13.txt")) {
			 for(char c = 'a'; c <= 'z'; c++) {
				 System.out.println(c + ": " + t.countChar(c));
			 }
		 }
	}
}

