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
	
	private void listDiagnoses() {
		// add other options
		System.out.println("Listing diagnoses...");
		System.out.println(this.blockchain.toString());
		writeMsg(this.blockchain.toString());
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
