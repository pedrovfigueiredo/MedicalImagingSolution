package server;

import basic.Blockchain;

public class Main {

	public static void main(String[] args) {
		Object lockClients = new Object();
		Blockchain blockchain = new Blockchain();
		int port = 12345;
		
		// Handling client connections
		System.out.println("Launching thread to handle clients.");
		Thread handleClients = new Thread(new HandleClients(port, lockClients, blockchain));
		handleClients.start();
		
		// TODO: Launch thread to check validity of blockchain
		
		
		
		while (!Thread.currentThread().isInterrupted()) {}
		
		handleClients.interrupt();
	}

}
