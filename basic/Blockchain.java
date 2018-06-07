package basic;

import java.util.ArrayList;

public class Blockchain {
	private ArrayList<Block> chain;
	
	public static int difficulty = 5;

	public Blockchain() {
		chain = new ArrayList<>();
	}
	
	public Blockchain(ArrayList<Block> chain) {
		this.chain = chain;
	}
	
	@Override
	public String toString() {
		String string = "\t --Blockchain--\n";
		for (Block block : chain) {
			string.concat(block.toString());
		}
		return string;
	}
	
	// Add reference to medium tutorial
	public boolean isChainValid(Object lock) {
		Block currentBlock; 
		Block previousBlock;
		String hashTarget = new String(new char[difficulty]).replace('\0', '0');
		
		synchronized (lock) {
			//loop through blockchain to check hashes:
			for(int i=1; i < chain.size(); i++) {
				currentBlock = chain.get(i);
				previousBlock = chain.get(i-1);
				//compare registered hash and calculated hash:
				if(!currentBlock.getHash().equals(currentBlock.calculateHash()) ){
					System.out.println("Current Hashes not equal");			
					return false;
				}
				//compare previous hash and registered previous hash
				if(!previousBlock.getHash().equals(currentBlock.getPrevHash()) ) {
					System.out.println("Previous Hashes not equal");
					return false;
				}
				//check if hash is solved
				if(!currentBlock.getHash().substring( 0, difficulty).equals(hashTarget)) {
					System.out.println("This block hasn't been mined");
					return false;
				}
			}
		}
		return true;
	}
	
	public void addBlocktoChain(Block block, Object lock) {
		block.mineBlock(difficulty);
		synchronized (lock) {
			this.chain.add(block);
		}
	}
	
	public Block getBlockAtIndex(int index) {
		return this.chain.get(index);
	}
	
	public int getChainLength(){
		return this.chain.size();
	}
}
