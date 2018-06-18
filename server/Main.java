package server;

import basic.Blockchain;

public class Main {

	public static void main(String[] args) {
		Object lockClients = new Object();
		Blockchain blockchain = new Blockchain();
		int port = 12345;
		
		// Handling client connections
		System.out.println("Launching thread to handle clients.");
		Thread handleClients = new Thread(new HandleClients(port,
															lockClients,
															blockchain));
		handleClients.start();
		
		//Launch thread to check validity of blockchain from 
		long validationIntervalInSeconds = 30;
		Thread checkBlockchain = new Thread(new ValidateBlockchain(Thread.currentThread(),
																  lockClients,
																  blockchain,
																  validationIntervalInSeconds));
		checkBlockchain.start();
		
		while (!Thread.currentThread().isInterrupted()) {}
		
		checkBlockchain.interrupt();
		handleClients.interrupt();
	}

}
