package aa;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

class Book implements Comparable<Book>{
	String title;
	String author;
	String user;
	
	public Book(String title, String author, String user) {
		this.title = title;
		this.author = author;
		this.user = user;
	}
	public void userupdate(String userID) {
		this.user = userID;
	}
	@Override
	public int compareTo(Book b) {
		String array1 = this.title.toLowerCase();
		String array2 = b.title.toLowerCase();
		return array1.compareTo(array2);
	}
}
class Forreturn {
	String temp;
	int index;
	public Forreturn(String temp, int index) {
		this.temp = temp;
		this.index = index;
	}
}

class BookReader{
	static ArrayList<Book> list;
	public BookReader() {
		list= new ArrayList<Book>();
	}
	
	public ArrayList<Book> readBooks (String filename){
		BufferedReader input = null;
		try {
			input = new BufferedReader(new FileReader(filename));
			
			while(true) {
				String data = input.readLine();
				if(data==null) break;
				String[] temp = data.split("\t");
				add(temp[0], temp[1], temp[2]);
			}
			input.close();
			return list;
		}
		catch(FileNotFoundException fnfe) {
			return null;
		}
		catch(IOException ioe) {
			return null;
		}
		finally{ 
			if(input != null) {
				try{
					input.close();
				}catch(IOException e){
					e.printStackTrace();
				} 
			}
		}
	}
	public void writeBooks(String filename) {
		Collections.sort(list);
		BufferedWriter output = null;
		File file = new File(filename);
		FileWriter writer = null;
		try {
			writer = new FileWriter(file);
			output = new BufferedWriter(writer);
			
			for(Book b : list) {
				String message = b.title + "\t"+b.author+'\t'+b.user+'\n';
				output.write(message);
				output.flush();
			}
		} catch(IOException e) {
			
		}
		finally {
			try {
				if(output!=null) output.close();
				if(writer !=null) writer.close();

			}catch(IOException e) {
				
			}
		}
	}
	public void add(String title, String author, String borrow) {
		Book E = new Book(title, author, borrow);
		list.add(E);
	}
	public Forreturn searchtitle(String title) {
		String array1 = title.toLowerCase();
		Forreturn temp;
		for(int i=0; i < list.size(); i++) {
			Book b = list.get(i);
			String array2 = b.title.toLowerCase();
			if(array2.equals(array1)) {
				temp = new Forreturn(b.title, i);
				return temp;
			}
		}
		temp = new Forreturn(null, -1);
		return temp;
	}
	public Forreturn searchtitletoborrow(String title) {
		String array1 = title.toLowerCase();
		Forreturn temp;
		for(int i=0; i < list.size(); i++) {
			Book b = list.get(i);
			String array2 = b.title.toLowerCase();
			if(array2.equals(array1)&&b.user.equals("-")) {
				temp = new Forreturn(b.title, i);
				return temp;
			}
		}
		temp = new Forreturn(null, -1);
		return temp;
	}
	public void update(int index, String userID) {
		Book b = list.get(index);
		b.userupdate(userID);
	}
	public Forreturn searchuser(String title, String user) {
		Forreturn temp;
		String array1 = title.toLowerCase();
		for(int i=0; i<list.size(); i++) {
			Book b = list.get(i);
			String array2 = b.title.toLowerCase();
			if(array2.equals(array1)&&b.user.equals(user)) {
				temp = new Forreturn(b.title, i);
				return temp;
			}
		}
		temp = new Forreturn(null, -1);
		return temp;
	}
	public String info(String userID) {
		String result = "##You are currently borrowing ";
		String booklist = "";
		int count = 0;
		for(int i=0; i<list.size(); i++) {
			Book b = list.get(i);
			if(b.user.equals(userID)) {
				if (booklist.length()!=0)
					booklist = booklist + "\n";
				count++;
				booklist = booklist + count + ". "+b.title+", "+b.author;

			}
		}
		result = result + count+ " books:\n"+booklist;
		return result;
	}
	public String searchstring(String search) {
		String result = "##Your search matched ";
		String booklist = "";
		String array1 = search.toLowerCase();
		int count = 0;
		for(int i=0; i<list.size(); i++) {
			Book b = list.get(i);
			String array2 = b.title.toLowerCase();
			String array3 = b.author.toLowerCase();
			if(array2.contains(array1)||array3.contains(array1)) {
				if (booklist.length()!=0)
					booklist = booklist + "\n";
				count++; 
				booklist = booklist + count + ". "+b.title+", "+b.author;
			}
		}
		result = result + count+ " results.\n"+booklist;
		return result;
	}
}


public class Server {
	HashMap<String, DataOutputStream> clients;
	static BookReader bookreader = new BookReader();
	
	Server() {
		clients = new HashMap<>();
		Collections.synchronizedMap(clients);
	}

	public void start(String port) {
		ServerSocket serverSocket = null;
		Socket socket = null;
		try {
			serverSocket = new ServerSocket(Integer.parseInt(port));
			System.out.println("server has started.");
			while (true) {
				socket = serverSocket.accept();
				//System.out.println("a new connection from [" + socket.getInetAddress() + ":" + socket.getPort() + "]");
				ServerReceiver thread = new ServerReceiver(socket);
				thread.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void sendToAll(String msg, String msg_name) {
		//int count=0;
		if(msg=="")
			return;
		//System.out.println("msg_name : " + msg_name);
		Iterator<String> it = clients.keySet().iterator();
		while (it.hasNext()) {
			try {
				//count++;
				//System.out.println(count);
				DataOutputStream out = (DataOutputStream) clients.get(it.next());
				out.writeUTF(msg);

			} catch (IOException e) {
			}
		}
	}

	public static void main(String args[]) {
		if(args.length !=1) {
			System.out.println("Please give the port number as an argument.");
			System.exit(0);
		}
		bookreader.readBooks("books.txt");
		new Server().start(args[0]);
	}

	class ServerReceiver extends Thread {
		Socket socket;
		DataInputStream in;
		DataOutputStream out;

		ServerReceiver(Socket socket) {
			//System.out.println("receive start");
			this.socket = socket;
			try {
				in = new DataInputStream(socket.getInputStream());
				out = new DataOutputStream(socket.getOutputStream());
			} catch (IOException e) {
			}
		}

		public void run() {
			String name = "";
			//System.out.println("receive run");
			try {
				name = in.readUTF();
				//sendToAll("#"+name+" has joined.",name);
				clients.put(name, out);

				while (in != null) {
					String msg = "";
					
					String command = "";
					command = in.readUTF();
					msg = commandcheck(socket, command);
					//System.out.println(msg);
					sendToAll(msg, name);
					bookreader.writeBooks("books.txt");
				}
			} catch (IOException e) {
				// ignore
			} finally {
				clients.remove(name);

			}
		}
		
		String commandcheck(Socket socket, String command){
			//System.out.println(command);
			String temp[] = command.split("##");
			if (temp[0].equals("add")) {
				Forreturn result = bookreader.searchtitle(temp[1]);
				if(result.index!=-1) {
					return temp[3]+"##The book already exists in the list.\n"+temp[3]+">> ";
				}
				else {
					bookreader.add(temp[1],temp[2], "-");
					return temp[3]+"##A new book added to the list.\n"+temp[3]+">> ";
				}

			}
			else if(temp[0].equals("borrow")) {
				Forreturn result = bookreader.searchtitletoborrow(temp[1]);
				if(result.index==-1) {
					return temp[2]+"##The book is not available.\n"+temp[2]+">> ";
				}
				else {
					bookreader.update(result.index, temp[2]);
					
					return temp[2]+"##You borrowed a book. - "+result.temp+"\n"+temp[2]+">> ";
				}
			}
			else if(temp[0].equals("return")) {
				Forreturn result = bookreader.searchuser(temp[1], temp[2]);
				if(result.index==-1) {
					return temp[2]+"##You did not borrow the book.\n"+temp[2]+">> ";
				}
				bookreader.update(result.index, "-");
				return temp[2]+ "##You returned a book. - "+result.temp+"\n"+temp[2]+">> ";
			}
			else if(temp[0].equals("info")) {
				return temp[1]+bookreader.info(temp[1])+"\n"+temp[1]+">> ";
			}
			else if(temp[0].equals("search")) {
				return temp[2]+bookreader.searchstring(temp[1])+"\n"+temp[2]+">> ";
			}
			return command;
		}
	}
	
	
	
}
