package it.univaq.disim.mde.manager;

import java.nio.file.Paths;

public class Transformation {
	
	private String atlTransformationPath;
	private String sourceMetamodelPath;
	private String targetMetamodelPath;
	private String modelPath;
	
	public Transformation(String metamodelPath, String atlTransformationPath) {
		MetamodelManager.registerMetamodel(metamodelPath);
		this.atlTransformationPath = atlTransformationPath;
//		this.modelPath = Paths.get(atlTransformationPath).getParent().resolve(Paths.get(atlTransformationPath).getFileName().toString().split("\\.")[0]+".xmi").toString();
	}
	
	public String getAtlTransformationPath() {
		return atlTransformationPath;
	}
	public void setAtlTransformationPath(String atlTransformationPath) {
		this.atlTransformationPath = atlTransformationPath;
	}
	public String getSourceMetamodelPath() {
		return sourceMetamodelPath;
	}
	public void setSourceMetamodelPath(String metamodelPath) {
		this.sourceMetamodelPath = metamodelPath;
	}

	public String getModelPath() {
		return modelPath;
	}

	public void setModelPath(String modelPath) {
		this.modelPath = modelPath;
	}

	public String getTargetMetamodelPath() {
		return targetMetamodelPath;
	}

	public void setTargetMetamodelPath(String targetMetamodelPath) {
		this.targetMetamodelPath = targetMetamodelPath;
	}
	
	
	
	

}
