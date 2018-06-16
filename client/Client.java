package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import basic.Diagnosis;
import basic.Patient;
import basic.Physician;

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
		
		// l or a
		String option = "";
		if (args.length > 0) {
			option = args[0].substring(1);
		}
		try {
			Client client = new Client(new Socket("localhost", 12345));
			
			switch (option) {
			case "l":
				client.listDiagnoses();
				break;
			case "a":
				ArrayList<String> filenames = new ArrayList<>();
				
				for (int i = 1; i < args.length; i++) {
					filenames.add(args[i]);
				}
				
				client.addDiagnoses(filenames);
				break;
			default:
				System.out.println("Invalid option.\nUse: java Client -[option] [files]");
				return;
			}
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
	}
	
	public void addDiagnoses(ArrayList<String> filenames) {
		
		// Message to Server telling to expect to add diagnoses
		writeMsg("1");
		
		// Sends server information about number of diagnoses to expect
		writeMsg(Integer.toString(filenames.size()));
		
		System.out.println("Diagnoses to be sent to the blockchain:");
		
		for (String filename : filenames) {
			// TODO: Read from file JSON style and build Diagnoses
			
			//Diagnosis diagnosis = new Diagnosis(new ArrayList<>(), 0, new Patient("", new Date(), ""), new Physician("", "", "")); 
			
			Diagnosis diagnosis = new Diagnosis(new ArrayList<>(), 100, new Patient("Calvin", new Date(), "1"), new Physician("Pedro", "2", "ELTE"));
			
			System.out.println("");
			System.out.println(diagnosis.toString());
			
			try {
				object_writer.writeObject(diagnosis);
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
