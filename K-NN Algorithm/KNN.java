import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class KNN {

	static ArrayList<String[]> instances = new ArrayList<String[]>();
	static ArrayList<String> results = new ArrayList<String>();
	static ArrayList<String[]> queries = new ArrayList<String[]>();
	static ArrayList<String> queryResults = new ArrayList<String>();
	

		
		public static void main(String[] args) throws IOException {
			int k = Integer.parseInt(args[0]);
			String filename = "results_" + k + "_" + args[2];
			PrintWriter out = new PrintWriter(filename);
			String training = args[1];
			String testing = args[2];
			
			for (String line : Files.readAllLines(Paths.get(training))) {
				String[] attributes = line.split(",");
				String[] instance = new String[attributes.length - 1];
				for (int i=0; i < attributes.length - 1; i++) {
					instance[i] = attributes[i];
				}
				instances.add(instance);
				results.add(attributes[attributes.length - 1]);
			} 
			List<Label_Attributes> labelList = new ArrayList<Label_Attributes>();
			List<Label_Distance> resultList = new ArrayList<Label_Distance>();
			for (int i = 0; i < instances.size(); i++) {
				labelList.add(new Label_Attributes(instances.get(i), results.get(i)));
			}
			
			
			for (String line : Files.readAllLines(Paths.get(testing))) {
				String[] attributes = line.split(",");
				String[] query = new String[attributes.length - 1];
				for (int i=0; i < attributes.length - 1; i++) {
					query[i] = attributes[i];
				}
				queries.add(query);
				queryResults.add(attributes[attributes.length - 1]);
			}
			
			
			for(int i = 0; i < queries.size(); i++) {
				String[] query = queries.get(i);
				for(Label_Attributes label : labelList){
					double dist = 0.0;  
					for(int j = 0; j < label.labelAttributes.length; j++){ 
						if (isDouble(label.labelAttributes[j])) {
							dist += Math.pow(Double.parseDouble(label.labelAttributes[j]) - Double.parseDouble(query[j]), 2) ;
						} else {
							if (!(label.labelAttributes[j].equals(query[j]))) {
								dist += 1;
							} else {
								//distance is zero, so no change
							}
						} 	     
					}
					double distance = Math.sqrt(dist);
					resultList.add(new Label_Distance(distance,label.labelName));
				} 
	
				Collections.sort(resultList, new DistanceComparator());
				String[] topKResults = new String[k];
				for(int x = 0; x < k; x++){
					topKResults[x] = resultList.get(x).labelName;
				}
				String majClass = decideLabel(topKResults);
				System.out.println("Test " + i);
				System.out.println("Algorithm says: "+majClass);
				System.out.println("Testing says: " +queryResults.get(i));
				
				for (String attr : queries.get(i)) {
					out.print(attr + ",");
				}
				out.print(queryResults.get(i) + ",");
				out.println(majClass);
			}
			out.close();	

		}
		
		static class Label_Attributes {	
			String[] labelAttributes;
			String labelName;
			
			public Label_Attributes(String[] labelAttributes, String labelName){
				this.labelName = labelName;
				this.labelAttributes = labelAttributes;	    	    
			}
		}
		
            static class Label_Distance {
			String labelName;
			double distance;
			
			public Label_Distance(double distance, String labelName){
				this.labelName = labelName;
				this.distance = distance;	    	    
			}
		}
		
		static boolean isDouble(String str) {
	        try {
	            Double.parseDouble(str);
	            return true;
	        } catch (NumberFormatException e) {
	            return false;
	        }
	    }
		
				static class DistanceComparator implements Comparator<Label_Distance> {
					@Override
					public int compare(Label_Distance a, Label_Distance b) {
						if (a.distance < b.distance) {
							return -1;
						} else if (a.distance == b.distance) {
							return 0;
						} else {
							return 1;
						}
					}
				}
		
		private static String decideLabel(String[] topKResults) {
            Set<String> tempRidOfDuplicates = new HashSet<String>(Arrays.asList(topKResults));
			String[] uniqueResults = tempRidOfDuplicates.toArray(new String[0]);
            int[] counts = new int[uniqueResults.length];
			
			for (int i = 0; i < uniqueResults.length; i++) {
				for (int j = 0; j < topKResults.length; j++) {
					if(topKResults[j].equals(uniqueResults[i])){
						counts[i]++;
					}
				}        
			}

			
			int maxCount = counts[0];
			int ties = 0;
			int maxIndex = 0;
			String maxLabel = uniqueResults[0];
			
			
			for (int i = 1; i < counts.length; i++) {
				if (maxCount < counts[i]) {
					maxCount = counts[i];
					maxIndex = i;
					maxLabel = uniqueResults[i];
				}
			}

			
			for (int i = 0; i < counts.length; i++) {
				if (counts[i] == maxCount) {
					ties++;
				}
			}
			
			
			if (ties > 1) {
				ArrayList<Integer> tieIndices = new ArrayList<Integer>();
				for (int i = 0; i < counts.length; i++) {
					if (counts[i] == maxCount) {
						tieIndices.add(i);
					}
				}
				Random r = new Random();        
				int tieWinnerIndex = tieIndices.get(r.nextInt(tieIndices.size()));
				maxLabel = uniqueResults[tieWinnerIndex];
			}
			return maxLabel;

		}
}
