package server;

import basic.Blockchain;

public class ValidateBlockchain implements Runnable{
	
	private Thread mainThread;
	private Object lock;
	private Blockchain blockchain;
	private long seconds;
	
	public ValidateBlockchain(Thread mainThread, Object lock, Blockchain blockchain, long seconds) {
		this.mainThread = mainThread;
		this.lock = lock;
		this.blockchain = blockchain;
		this.seconds = seconds;
	}
	
	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			System.out.println("[ValidateBlockchain] Checking the blockchain validity...");
		
			if(!blockchain.isChainValid(lock)) {
				System.out.println("[ValidateBlockchain] Blockchain not valid. Interrupting main thread");
				mainThread.interrupt();
			}
			System.out.println("[ValidateBlockchain] Blockchain valid.");
			try {
				Thread.sleep(seconds * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
