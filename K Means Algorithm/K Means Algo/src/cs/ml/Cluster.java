package cs.ml;

import java.util.HashMap;

public class Cluster {
	int id;

	double centX;

	public double getCentX() {
		return centX;
	}

	public void setCentX(double centX) {
		this.centX = centX;
	}

	public double getCentY() {
		return centY;
	}

	public void setCentY(double centY) {
		this.centY = centY;
	}

	double centY;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public HashMap<Integer, Point> getClusterDataMap() {
		return clusterDataMap;
	}

	public void setClusterDataMap(HashMap<Integer, Point> clusterDataMap) {
		this.clusterDataMap = clusterDataMap;
	}

	HashMap<Integer, Point> clusterDataMap = new HashMap<Integer, Point>();
}
