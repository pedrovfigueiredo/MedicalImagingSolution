package basic;

import java.sql.Date;
import java.util.ArrayList;

public class Block {
	private String prevHash;
	private String hash; 
	private ArrayList<Diagnosis> diagnoses;
	private long timestamp;
	private int nonce;
	
	public Block(String prevHash, ArrayList<Diagnosis> diagnoses) {
		super();
		this.prevHash = prevHash;
		this.diagnoses = diagnoses;
		this.timestamp = System.currentTimeMillis();
		this.hash = calculateHash();
	}
	
	@Override
	public String toString() {
		String string = "Time: " + new Date(timestamp).toString() + "\n" +
						"Previous Hash: " + prevHash + "\n" +
						"Hash: " + hash + "\n";
		String diag = "Diagnoses: " + "\n";
		
		for (int i = 0; i < diagnoses.size(); i++) {
			diag.concat(String.valueOf(i + 1) + ":\n" + diagnoses.get(i));
		}
		
		return string + diag;
	}
	
	// Add reference to medium tutorial
	public String calculateHash() {
		String calculatedhash = "";
		
		try {
			calculatedhash = StringUtil.applySha256(hash);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return calculatedhash;
	}
	
	// Add reference to medium tutorial
	public void mineBlock(int difficulty) {
		String target = new String(new char[difficulty]).replace('\0', '0'); //Create a string with difficulty * "0" 
		while(!hash.substring( 0, difficulty).equals(target)) {
			nonce ++;
			hash = calculateHash();
		}
		System.out.println("Block Mined!!! : " + hash);
	}

	public String getPrevHash() {
		return prevHash;
	}

	public void setPrevHash(String prevHash) {
		this.prevHash = prevHash;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public ArrayList<Diagnosis> getDiagnoses() {
		return diagnoses;
	}

	public void setDiagnoses(ArrayList<Diagnosis> diagnoses) {
		this.diagnoses = diagnoses;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	
}
