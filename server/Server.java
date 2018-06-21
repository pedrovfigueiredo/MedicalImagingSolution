package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import basic.Block;
import basic.Blockchain;
import basic.Diagnosis;
import basic.Patient;
import basic.Physician;

public class Server implements Runnable {
	Blockchain blockchain;
	Socket clientSocket;
	Scanner reader;
	PrintWriter writer;
	ObjectInputStream object_reader;
	ObjectOutputStream object_writer;
	Object lock;

	public Server(Blockchain blockchain, Socket clientSocket, Object lock) {
		this.blockchain = blockchain;
		this.clientSocket = clientSocket;
		this.lock = lock;
		try {
			reader = new Scanner(clientSocket.getInputStream());
			writer = new PrintWriter(clientSocket.getOutputStream());
			object_reader = new ObjectInputStream(clientSocket.getInputStream());
			object_writer =  new ObjectOutputStream(clientSocket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Created server object.");
	}

	@Override
	public void run() {

		int response = Integer.parseInt(readMsg());
		
		switch (response) {
		case 1: // Add diagnosis
			addDiagnoses();
			break;
		case 2: // List diagnoses
			listDiagnoses();
			break;
		case 3:
			listDiagnoses(readMsg());
		default: // Invalid option
			writeMsg("Invalid option.");
			break;
		}
		
		closeConnection();
	}
	
	private void addDiagnoses() {
		ArrayList<Diagnosis> diagnoses = new ArrayList<>();
		int diagonesCount = Integer.parseInt(readMsg());
		
		try {
			for (int i = 0; i < diagonesCount; i++) {
				diagnoses.add((Diagnosis) object_reader.readObject());
					
				System.out.println("Added diagnosis:\n" + diagnoses.get(i));
			}
				
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		
		String lastHash = null;
		
		if (this.blockchain.getChainLength() > 0){
			lastHash = blockchain.getBlockAtIndex(this.blockchain.getChainLength() - 1).getHash();
		}
		
		Block block = new Block(lastHash, diagnoses);
		this.blockchain.addBlocktoChain(block, lock);
		writeMsg("Added diagnoses to the system.");
	}
	
	// List all diagnoses (print blockchain)
	private void listDiagnoses() {
		System.out.println("Listing diagnoses...");
		System.out.println(this.blockchain.toString());
		writeMsg(this.blockchain.toString());
	}
	
	private void listDiagnoses(String id_or_name) {
		
		ArrayList<Diagnosis> matches = new ArrayList<>();
		String result = "Diagnoses matching " + id_or_name + ":\n";
		
		int chain_length = this.blockchain.getChainLength();
		
		for (int i = 0; i < chain_length; i++) {
			Block block = this.blockchain.getBlockAtIndex(i);
			
			for (Diagnosis diagnosis : block.getDiagnoses()) {
				Patient patient = diagnosis.getPatient();
				Physician physician = diagnosis.getPhysician();
				
				if (patient.getId().equals(id_or_name) ||
					patient.getName().equals(id_or_name) ||
					physician.getId().equals(id_or_name) ||
					physician.getName().equals(id_or_name)) 
				{
					matches.add(diagnosis);
				}
			}
		}
		
		if (matches.isEmpty()) {
			writeMsg("No diagnoses for this ID or name were found.");
			return;
		}
		
		for(int i = 0; i < matches.size(); i++) {
			result = result.concat(String.valueOf(i + 1) + ":\n" + matches.get(i));
		}
		
		System.out.println(result);
		writeMsg(result);
	}
	
	private void closeConnection() {
		this.reader.close();
		this.writer.close();
		
		try {
			this.clientSocket.close();
			this.object_reader.close();
			this.object_writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Thread returning.");
	}
	
	private String readMsg() {
		while(!reader.hasNext()){}
		
		String message = reader.nextLine();
		return message;
	}
	
	private void writeMsg(String message) {
		writer.println(message);
		writer.flush();
	}

}
