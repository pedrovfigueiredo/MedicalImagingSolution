package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import basic.Blockchain;

public class HandleClients implements Runnable{
	private ServerSocket serverSocket;
	private Object lock;
	private Blockchain blockchain;
	
	public HandleClients(int port, Object lock, Blockchain blockchain){
		this.blockchain = blockchain;
		this.lock = lock;
		try {
			this.serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			Socket clientSocket;
			try {
				clientSocket = serverSocket.accept();
				new Thread(new Server(blockchain, clientSocket, lock)).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("HandleClients returning.");
	}
}
