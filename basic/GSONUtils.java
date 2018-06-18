package basic;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.google.gson.Gson;

public class GSONUtils {
	public static ArrayList<Diagnosis> getDiagnosesFromJSON(String filename) {
		
		ArrayList<Diagnosis> diagnoses = new ArrayList<>();
		
		Gson gson = new Gson();
		BufferedReader br = null;
		try {
		   br = new BufferedReader(new FileReader("data.json"));
		   Result_JSON result = gson.fromJson(br, Result_JSON.class);
		if (result != null) {
		     for (Diagnosis_JSON d : result.getDiagnoses()) {
		    	 
		    	 ArrayList<Image> images = new ArrayList<>();
		    	 
		    	 for (String curr_filename : d.getImages()) {
		    		 File sourceimage = new File(curr_filename);
		    		 try {
						images.add(ImageIO.read(sourceimage));
					} catch (IOException e) {
						e.printStackTrace();
					}
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
}
