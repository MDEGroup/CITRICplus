package it.univaq.disim.mde.manager;

import java.util.List;

import it.univaq.disim.mde.utility.Utils;

public class Chain {
	
	private String name;
	private List<Transformation> transformations;
	private double informatioLoss = 0;
	private int rank;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Transformation> getTransformations() {
		return transformations;
	}
	public void setTransformations(List<Transformation> transformations) {
		this.transformations = transformations;
	}
	public double getInformatioLoss() {
		return informatioLoss;
	}
	public void setInformatioLoss(double informatioLoss) {
		this.informatioLoss = informatioLoss;
	}
	
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		int counter = transformations.size();
		for (Transformation transformation : transformations) {
			builder.append(Utils.getNameFromPathWithoutExtension(transformation.getSourceMetamodelPath()));
			if(counter > 1) {
				builder.append("->");
			}
			counter--;
		}
		return builder.toString();
	}
	
	
	

}
