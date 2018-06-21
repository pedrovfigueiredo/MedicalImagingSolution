package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import basic.Diagnosis;
import basic.GSONUtils;

public class Client {
	Socket internalSocket;
	Scanner reader;
	PrintWriter writer;
	ObjectInputStream object_reader;
	ObjectOutputStream object_writer;
	
	int option;
	
	// java Client -l
	// java Client -a file1.json 
	public static void main(String[] args) {
		
		// l or a
		String option = "";
		if (args.length > 0) {
			option = args[0].substring(1);
		}
		
		if (!isOptionValid(option)) {
			promptErrorAndReturn();
		}
		
		try {
			Client client = new Client(new Socket("localhost", 12345));
			
			switch (option) {
			case "l":
				if (args.length > 1) {
					client.listDiagnoses(args[1]);
				}else {
					client.listDiagnoses();
				}
				break;
			case "a":
				if (args.length == 2) {
					client.addDiagnoses(args[1]);
				}else {
					promptErrorAndReturn();
				}
				break;
			default:
				promptErrorAndReturn();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void promptErrorAndReturn() {
		System.out.println("Invalid option.\nUse: java Client -[option] [file]");
		return;
	}
	
	private static boolean isOptionValid(String option) {
		if (option.equals("l") || option.equals("a")) {
			return true;
		}
		
		return false;
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
	}
	
	public void addDiagnoses(String filename) {
		
		// Message to Server telling to expect to add diagnoses
		writeMsg("1");
		
		ArrayList<Diagnosis> diagnoses = GSONUtils.getDiagnosesFromJSON(filename);
		
		// Sends server information about number of diagnoses to expect
		writeMsg(Integer.toString(diagnoses.size()));
		
		System.out.println("Diagnoses to be sent to the blockchain:");
		
		for (Diagnosis d : diagnoses) {
			
			System.out.println("");
			System.out.println(d.toString());
			
			try {
				object_writer.writeObject(d);
				object_writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		// Prints to user the successful message coming from server
		System.out.println(readMsg());
		
		
	}
	
	public void listDiagnoses() {
		// Message to Server telling list diagnoses
		writeMsg("2");
		
		// Prints server response to user
		System.out.println(readMsg());
	}
	
	public void listDiagnoses(String id_or_name) {
		// Message to Server telling list diagnoses according to id or name
		writeMsg("3");
		writeMsg(id_or_name);
		
		// Prints server response to user
		System.out.println(readMsg());
	}
	
	public String readMsg() {
		while(!reader.hasNext()){}
		String message = reader.nextLine();
		
		while(reader.hasNext()) {
			message = message.concat("\n" + reader.nextLine());
		}
		
		return message + "\n";
	}
	
	public void writeMsg(String message) {
		writer.println(message);
		writer.flush();
	}
}
