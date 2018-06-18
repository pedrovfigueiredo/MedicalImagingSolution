package basic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;

import com.google.gson.Gson;

public class GSONUtils {
	/*
	 * Based on the following Medium Tutorial:
	 * https://medium.com/@ssaurel/parse-and-write-json-data-in-java-with-gson-dd8d1285b28
	 */
	public static ArrayList<Diagnosis> getDiagnosesFromJSON(String filename) {
		
		ArrayList<Diagnosis> diagnoses = new ArrayList<>();
		
		Gson gson = new Gson();
		BufferedReader br = null;
		try {
		   br = new BufferedReader(new FileReader(filename));
		   Result_JSON result = gson.fromJson(br, Result_JSON.class);
		if (result != null) {
		     for (Diagnosis d : result.getDiagnoses()) {
		    	 
		    	 ArrayList<String> images = new ArrayList<>();
		    	 
		    	 for (String curr_filename : d.getImages()) {
		    		 images.add(encoder(curr_filename));
		    	 }
		    	 
		    	 diagnoses.add(new Diagnosis(images, d.getTimestamp(), d.getPatient(), d.getPhysician()));
		     }
		   }
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		  } finally {
		    if (br != null) {
		     try {
		      br.close();
		     } catch (IOException e) {
		      e.printStackTrace();
		     }
		  }
		 }
		
		return diagnoses;
	}
	
	/*
	 * Encoding image to string was taken from the tutorial hosted on:
	 * http://javasampleapproach.com/java/java-advanced/java-8-encode-decode-an-image-base64
	 */
	public static String encoder(String imagePath) {
		String base64Image = "";
		File file = new File(imagePath);
		try (FileInputStream imageInFile = new FileInputStream(file)) {
			// Reading a Image file from file system
			byte imageData[] = new byte[(int) file.length()];
			imageInFile.read(imageData);
			base64Image = Base64.getEncoder().encodeToString(imageData);
		} catch (FileNotFoundException e) {
			System.out.println("Image not found" + e);
		} catch (IOException ioe) {
			System.out.println("Exception while reading the Image " + ioe);
		}
		return base64Image;
	}
}
