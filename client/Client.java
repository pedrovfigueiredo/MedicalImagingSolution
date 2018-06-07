package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {
	Socket internalSocket;
	Scanner reader;
	PrintWriter writer;
	ObjectInputStream object_reader;
	ObjectOutputStream object_writer;
	
	int option;
	
	// java Client -l
	// java Client -a file1.diag file2.diag 
	public static void main(String[] args) {
		
		
		
		try {
			new Client(new Socket("localhost", 12345));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Client(Socket socket) {
		internalSocket = socket;
		try {
			reader = new Scanner(internalSocket.getInputStream());
			writer = new PrintWriter(internalSocket.getOutputStream());
			object_writer =  new ObjectOutputStream(internalSocket.getOutputStream());
			object_reader = new ObjectInputStream(internalSocket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Client created.");
		
		writeMsg("ready");
		
	}
	
	public String readMsg() {
		while(!reader.hasNext()){}
		
		String message = reader.nextLine();
		return message;
	}
	
	public void writeMsg(String message) {
		writer.println(message);
		writer.flush();
	}
}
