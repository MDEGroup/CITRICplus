package org.hiddentool.chain.transformation;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.m2m.atl.core.ATLCoreException;
import org.hiddentool.metamodel.MetamodelManager;

public class Transformation {
	
	private String atlTransformationPath;
	private String sourceMetamodelPath;
	private String targetMetamodelPath;
	private String modelPath;
	
	public Transformation(String metamodelPath, String atlTransformationPath) {
		MetamodelManager.registerMetamodel(metamodelPath);
		this.atlTransformationPath = atlTransformationPath;
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
	
	
	public int isImpacted(EClass ec) throws ATLCoreException {
		return TransformationManager.isImpacted(this, ec);
	}

	public List<EClass> getOutputEClasses() throws ATLCoreException {
		return TransformationManager.getOutputMetaclasses(this);
	}

}
