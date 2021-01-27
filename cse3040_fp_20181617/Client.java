package aa;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Client {
	static String userID;
	static Thread sender;
	static Thread receiver;
	static int state = 0;
	static class ClientSender extends Thread {
		Socket socket;
		DataOutputStream out;
		DataInputStream in;
		String name;
		String command = "";
		String title;
		String author;
		String borrow;
		String Return;
		String search;
		int wait=0;
		ClientSender(Socket socket, String name) {
			this.socket = socket;
			try {
				out = new DataOutputStream(socket.getOutputStream());
				in = new DataInputStream(socket.getInputStream());
				this.name = name;
			} catch (Exception e) {
			}
		}

		@SuppressWarnings("all")
		public void run() {
			Scanner scanner = new Scanner(System.in);

			try {
				//System.out.println("?");

				// userID입력
				while (out != null) {
					System.out.printf("Enter userID>> ");
					command = scanner.nextLine();
					int result = checkUserID(command);
					if (result == 0) {
						state = 10;
						userID = command;
						out.writeUTF(userID);
						System.out.printf(userID + ">> ");
						break;
					}
				}
				command = "";
				// 명령어 입력
				
				while (out != null) {
					//System.out.println("start "+state);
				
					command = scanner.nextLine();
					if(command.length()==0) {
						//System.out.println("if문");
						state = 10;
						System.out.printf("[available commands]\nadd: add a new book to the list of books.\n"
								+ "borrow: borrow a book from the library.\n"
								+ "return: return a book to the library.\n"
								+ "info: show list of books I am currently borrowing.\n"
								+ "search: search for books.\n"+userID + ">> ");
					}
					else if (command.equals("add")) {
						System.out.printf("add-title> ");
						command = scanner.nextLine();
						if (command.length() == 0) {
							state = 10;
							System.out.printf(userID + ">> ");
							continue;
						} else {
							title = command;
							System.out.printf("add-author> ");
							command = scanner.nextLine();
							if (command.length() == 0) {
								state = 10;
								System.out.printf(userID + ">> ");
								continue;
							} else {
								author = command;
								out.writeUTF("add" + "##" + title + "##" + author + "##" + userID);
								state = 1;
								//receive();
							}
						}

					} else if (command.equals("borrow")) {
						System.out.printf("borrow-title> ");
						command = scanner.nextLine();
						if (command.length() == 0) {
							state = 10;
							System.out.printf(userID + ">> ");
							continue;
						} else {
							borrow = command;
							out.writeUTF("borrow" + "##" + borrow + "##" + userID);
							state = 1;
							//receive();
						}
					} else if (command.equals("return")) {
						System.out.printf("return-title> ");
						command = scanner.nextLine();
						if (command.length() == 0) {
							state = 10;
							System.out.printf(userID + ">> ");
							continue;
						} else {
							Return = command;
							out.writeUTF("return" + "##" + Return + "##" + userID);
							state = 1;
							//receive();
						}
					} else if (command.equals("info")) {
						out.writeUTF("info" + "##" + userID);
						state = 1;
						//receive();
					} else if (command.equals("search")) {
						while (true) {
							System.out.printf("search-string>");
							command = scanner.nextLine();
							if (command.length() == 0) {
								state = 1;
								break;
							} else if (command.length() > 2) {
								state = 2;
								break;
							} else {
								System.out.println("Search string must be longer than 2 characters.");
							}
						}

						if (state == 2) {
							search = command;
							out.writeUTF("search" + "##" + command + "##" + userID);
							state = 1;
							//receive();
						} else if (state == 1) {
							state = 10;
							System.out.printf(userID + ">> ");
							continue;
						}

					} else {
						//out.writeUTF(command);
						state = 10;
						System.out.printf("[available commands]\nadd: add a new book to the list of books.\n"
								+ "borrow: borrow a book from the library.\n"
								+ "return: return a book to the library.\n"
								+ "info: show list of books I am currently borrowing.\n"
								+ "search: search for books.\n"+userID + ">> ");
					}
				}
			} catch (IOException e) {
			}
		}

/*
		void receive() {
			try {
				String input = in.readUTF();
				// System.out.println(input);
				String[] temp = input.split("##");
				if (temp[0].equals(userID) && input != "")
					System.out.println(temp[1]);
			} catch (IOException e) {
			}

			return;
		}
*/
	}

	static class ClientReceiver extends Thread {
		Socket socket;
		DataInputStream in;

		ClientReceiver(Socket socket) {
			this.socket = socket;
			try {
				in = new DataInputStream(socket.getInputStream());
			} catch (IOException e) {
			}
		}

		public void run() {
			while (in != null) {
				try {
					//System.out.println("!");
					String input = in.readUTF();
					String temp[]=input.split("##");
					//System.out.println(input);
					if (temp[0].equals(userID) && input != "") {
						System.out.printf(temp[1]);
						state = 10;
						//System.out.printf(userID+">> ");
					}
					//sender.run();
				} catch (IOException e) {
				}
			}
		}
	}
	
	public static void main(String args[]) {
		if (args.length != 2) {
			System.out.println("Please give the IP address and port number as arguments.");
			System.exit(0);
		}
		try {
			String serverIp = args[0];

			Socket socket = new Socket(serverIp, Integer.parseInt(args[1]));
			sender = new Thread(new ClientSender(socket, args[0]));
			receiver = new Thread(new ClientReceiver(socket));
			sender.start();
			receiver.start();
		} catch (ConnectException ce) {
			System.out.println("Connection establishment failed.");
		} catch (Exception e) {
		}
	}

	static int checkUserID(String userID) {
		String[] array = userID.split(" ");
		if (array.length != 1 || userID.length() == 0) {
			System.out.println("UserID must be a single word with lowercase alphabets and numbers.");
			return -1;
		} else if (Pattern.matches("^[0-9a-z]*$", userID) == false) {
			System.out.println("UserID must be a single word with lowercase alphabets and numbers.");
			return -1;
		} else {
			System.out.println("Hello " + userID + "!");
			return 0;
		}
	}
}
