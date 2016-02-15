README.TXT

This file lists the instructions to run the Machine Learning Algorithm K Means.


---Library used---
Open CSV is used for reading the data from CSV.
JAR details - opencsv-2.4.jar

----Class Details----
1. KMMain.Java
   This is the main class which calls different functions like kMeans,computeSSE

2. Cluster.Java
   This class implements the idea of Cluster and contains details about clusters like cluster id, cluster centroid etc.

3. Point.Java
   This class represents a Point in a dataset.This has attributes like x value,y value , Id of cluster it belongs to etc.


----Run in Command Line----
Command line run format given in assignment -
./k-means <numberOfClusters> <input-file-name> <output-file-name>
  

1. On the command line go till the project directory and run the following command to compile the files.
                  
javac -d bin -sourcepath src -cp ./lib/* src/utd/cs/ml/KMMain.java

2. I have kept the data file at the path C:\Assignment5\data\ so give the full path for the data files on command line as shown below. 

To run the k means program use the following command 

java -cp bin;lib/* utd/cs/ml/KMMain 4 "C:\Assignment5\data\data.csv" "Output.txt"

----Results----
After you run the program ,you will get all the results in the file Output.txt

Sample Result - 
Cluster - Data Points
--------------------------
{ 1 }   - [65, 6, 75, 12, 13, 77, 78, 19, 20, 24, 88, 27, 29, 93, 97, 34, 39, 41, 51, 53, 54, 55, 57, 58, 63]

{ 2 }   - [68, 70, 71, 72, 11, 82, 83, 22, 28, 92, 95, 96, 36, 37, 48]

{ 3 }   - [33, 100, 69, 40, 74, 42, 14, 80, 18, 50, 84, 21, 25, 26, 91]

{ 4 }   - [64, 32, 3, 4, 38, 16, 52, 56, 59, 60]

{ 5 }   - [17, 2, 98, 85, 8, 73, 89, 10, 61, 94]

{ 6 }   - [66, 35, 9, 44, 76, 45, 46, 47, 49, 81, 86, 23, 90, 30]

{ 7 }   - [67, 5, 7, 87, 43, 62, 15]

{ 8 }   - [1, 99, 31, 79]

Sum of Squared Error :  0.889



-----------Sum of Squared Error Results-------------

	K 	SSE
1. 	12	0.562
2.	8	0.889	
3.	10	0.771
4.	16	0.477
5.	20	0.385

-----------------------------------------------------------------------------------