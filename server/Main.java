package server;

import basic.Blockchain;

public class Main {

	public static void main(String[] args) {
		Object lock = new Object();
		Blockchain blockchain = new Blockchain();
		int port = 12345;
		
		// Handling client connections
		System.out.println("Launching thread to handle clients.");
		Thread handleClients = new Thread(new HandleClients(port, lock, blockchain));
		handleClients.start();
		
		// Building block and mining it
		
		
		
		while (!Thread.currentThread().isInterrupted()) {}
		
		handleClients.interrupt();
	}

}
