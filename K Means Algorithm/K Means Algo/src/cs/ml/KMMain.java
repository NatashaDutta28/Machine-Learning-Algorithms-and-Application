package cs.ml;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import au.com.bytecode.opencsv.CSVReader;


public class KMMain {
private int kCluster = 0;
List<Point> pointDataLst = new ArrayList<Point>();
public static int dataSize = 0;
public static List<Point> centroidLst = new ArrayList<Point>();
public static HashMap<String,Cluster> clusterMap;
DecimalFormat df= new DecimalFormat("#.###");
private double sSE = 0.0;

	public static void main(String[] args) throws IOException {
		KMMain kmMain = new KMMain();
		String inputFile = "";
		String outputFile = "";
		if (args.length > 0 && args.length == 3) {
			kmMain.kCluster = Integer.parseInt(args[0]);
			inputFile = args[1];
			outputFile = args[2];
			System.out.println(" K :" + kmMain.kCluster + " Input File:" + inputFile + " Output File:  " + outputFile);
			kmMain.pointDataLst = kmMain.extractDataFrmCSV(inputFile); // get
																		// data
			// create K clusters
			clusterMap = new HashMap<String, Cluster>();
			// Generate initial clusters
			kmMain.generateClusters(kmMain.kCluster);
			// call K-Means
			kmMain.kMeans(kmMain.pointDataLst, kmMain.kCluster);
			// calculate sum of squared Error
			kmMain.computeSSE();
			// Output cluster details
			kmMain.outputClusterDetails(outputFile);
			

		} else {
			System.out.println("Please specify the arguments in below order");
			System.out.println("<K>    <Input File>    <Output File>");
		}

	}

	public void kMeans(List<Point> pointDataLst, int k) {
		System.out.println("Starting K-Means..");
		selectCentroid(k);// select initial k random centroids
		for (int i = 0; i < 25; i++) {

			// assign data points to centroid
			for (int j = 0; j < pointDataLst.size(); j++) {
				int currentClusterId = pointDataLst.get(j).getClusterId();
				int cId = getClosestCentroid(pointDataLst.get(j).getX(), pointDataLst.get(j).getY());
				if (currentClusterId != cId) {
					// put the data point in the cluster corresponding to the cluster id					
					clusterMap.get(Integer.toString(cId)).clusterDataMap.put(pointDataLst.get(j).getId(),
							pointDataLst.get(j));
					pointDataLst.get(j).setClusterId(cId);
					if (currentClusterId != -1)
						clusterMap.get(Integer.toString(currentClusterId)).clusterDataMap
								.remove(pointDataLst.get(j).getId());
				}
			}

			// recalculate centroid for all the clusters
			boolean isCentSame = recalculateCentroid();
			if (isCentSame) {
				// if centroid not changing ,stop k-means iterations
				break;
			}
		}
		System.out.println("K-Means successfully Executed !");
	}

	public void computeSSE() {
		// for each cluster
		HashMap<Integer, Point> clusterData = new HashMap<Integer, Point>();		
		double dist = 0.0;
		for (int i = 0; i < centroidLst.size(); i++) {
			clusterData = clusterMap.get(Integer.toString(i)).getClusterDataMap();
			Iterator it = clusterData.entrySet().iterator();
			double centX = centroidLst.get(i).getX();
			double centY = centroidLst.get(i).getY();
			while (it.hasNext())// calculate sum of Xs and Ys for all data points in a cluster				
			{
				Entry entry = (Entry) it.next();
				Point p = (Point) entry.getValue();
				dist = Double.parseDouble(df.format(calculateEucledianDistance(p.getX(), p.getY(), centX, centY)));
				sSE = Double.parseDouble(df.format(sSE + (dist * dist)));
			}
		}		
	}

	public void outputClusterDetails(String fileName) throws IOException {
		File f = new File(fileName);
		if (!f.isFile()) {
			try {
				f.createNewFile();
			} catch (Exception e) {
				System.out.println("Error : " + e.getMessage());
			}
			
		}
			FileWriter fileWriter = new FileWriter(fileName, true);
			HashMap<Integer, Point> clusterData = new HashMap<Integer, Point>();
			String newLine = System.getProperty("line.separator");
			String result = "";
			fileWriter.write(newLine);
			fileWriter.write("Cluster -" + " Data Points");
			fileWriter.write(newLine);
			fileWriter.write("--------------------------");
			fileWriter.write(newLine);
			for (int i = 0; i < centroidLst.size(); i++) {
				clusterData = clusterMap.get(Integer.toString(i)).getClusterDataMap();
				result = "{ " + Integer.toString(i + 1) + " }   - " + clusterData.keySet().toString();
				fileWriter.write(result);
				fileWriter.write(newLine);
				fileWriter.write(newLine);
			}			
			result = "Sum of Squared Error :  " + Double.toString(sSE);
			fileWriter.write(result);
			fileWriter.write(newLine);
			fileWriter.close();
		
	}

	@SuppressWarnings("rawtypes")
	public boolean recalculateCentroid()
	{
		HashMap<Integer,Point> clusterData = new HashMap<Integer,Point>();
		double sumX=0.0;
		double sumY = 0.0;
		boolean flag = true;
		List<Point> newCentLst = new ArrayList<Point>();
		for(int l=0;l<centroidLst.size();l++)
		{
			sumX=0.0;
			sumY=0.0;
			clusterData = clusterMap.get(Integer.toString(l)).getClusterDataMap(); //get data points for current cluster
			Iterator it = clusterData.entrySet().iterator();			
			double count = clusterData.entrySet().size();
			while(it.hasNext())//calculate sum of Xs and Ys for all data points in a cluster
			{				
				Entry entry = (Entry)it.next();
				Point p = (Point)entry.getValue();
				sumX = sumX + p.getX();
				sumY = sumY + p.getY();
			}
			double avgX= Double.parseDouble(df.format((sumX/count)));//average X co-ordinates
			double avgY= Double.parseDouble(df.format((sumY/count)));//average y co-ordinates
			
			clusterMap.get(Integer.toString(l)).setCentX(avgX);
			clusterMap.get(Integer.toString(l)).setCentY(avgY);
			
			//add new centroid to centroidLst
			Point p1= new Point();
			p1.setX(avgX);
			p1.setY(avgY);
			p1.setId(l);
			newCentLst.add(p1);
			
		}			
		for(int d = 0; d< newCentLst.size();d++)
		{			
			if(!centroidLst.contains(newCentLst.get(d)))
			{
				flag= false;
			}
		}
		centroidLst = newCentLst;
		return flag;
	}

	public int getClosestCentroid(double x, double y) {
		double temp = 0;
		double dist = 1000;
		int centId = -1;
		for (int j = 0; j < centroidLst.size(); j++) {
			temp = calculateEucledianDistance(x, y, centroidLst.get(j).getX(), centroidLst.get(j).getY());
			if (temp < dist) {
				dist = temp;
				centId = centroidLst.get(j).getId();
			}
		}

		return centId;
	}

	public void selectCentroid(int k) {
		int i = 0;
		Point p = null;
		while (i != k) {
			Point newPoint = new Point();
			do {
				Random r = new Random();
				int randomCent = r.nextInt(dataSize) + 1;
				p = pointDataLst.get(randomCent - 1);
			} while (centroidLst.contains(p));
	
			newPoint.setId(i);
			newPoint.setX(p.getX());
			newPoint.setY(p.getY());
			newPoint.setClusterId(-1);
			centroidLst.add(newPoint);
			i++;
		}
	}
	public double calculateEucledianDistance(double x,double y,double x1,double y1)
	{	
		double dist = 0.0;
		double resultY = Double.parseDouble(df.format(Math.abs (y - y1)));
	    double resultX = Double.parseDouble(df.format(Math.abs (x- x1)));    
	    dist = Double.parseDouble(df.format(Math.sqrt((resultY)*(resultY) +(resultX)*(resultX)))); //calculate Euclidean distance	 
		return dist;
	}
	public void generateClusters(int kCluster)
	{
		for(int i=0;i<kCluster;i++)
		{
			Cluster c = new Cluster();
			c.setId(i);
			clusterMap.put(Integer.toString(i),c );			
		}
	}
	
	//save the data from csv file into a list of points
	public List<Point> extractDataFrmCSV(String fileName) throws IOException {
		CSVReader csvReader = new CSVReader(new FileReader(new File(fileName)));
		List<String[]> list = csvReader.readAll();

		List<Point> pointLst = new ArrayList<Point>();
		dataSize = list.size() - 1;
		for (int j = 1; j < list.size(); j++) // j is counter for no of
												// instances in the data set
		{
			Point p = new Point();
			p.id = Integer.parseInt(list.get(j)[0]);
			p.x = Double.parseDouble(list.get(j)[1]);
			p.y = Double.parseDouble(list.get(j)[2]);
			p.clusterId = -1;
			pointLst.add(p);
		}
		return pointLst;

	}

}
