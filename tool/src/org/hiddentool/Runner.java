package org.hiddentool;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.m2m.atl.core.ATLCoreException;
import org.hiddentool.chain.ChainManager;
import org.hiddentool.chain.transformation.Transformation;
import org.hiddentool.dsl.DSLManager;


public class Runner {
	
	public static void main(String[] args) throws ATLCoreException {

		DSLManager dslManager = new DSLManager();
		dslManager.addTransformations(chain1());
		double chain1_IL = dslManager.calculateInformationLossWithDSL();
		ChainManager.getInstance().getTransformations().clear();
		
		dslManager.addTransformations(chain2());
		double chain2_IL = dslManager.calculateInformationLossWithDSL();
		dslManager.reportBestChain();
	}

	private static List<Transformation> chain1() {
		List<Transformation> transformations = new ArrayList<Transformation>();
		String modelPath = "case_study/Shapes/Shape1.xmi";
		String metamodelPath = "case_study/Shapes/ShapeMM1.ecore";
		String atlTransformationPath = "case_study/Shapes/t1_t3/ShapeMM12ShapeMM2_T1.atl";
		Transformation t = new Transformation(metamodelPath, atlTransformationPath);
		t.setTargetMetamodelPath("case_study/Shapes/ShapeMM2.ecore");
		t.setSourceMetamodelPath(metamodelPath);

		String metamodelPath2 = "case_study/Shapes/ShapeMM3.ecore";
		String atlTransformationPath2 = "case_study/Shapes/t1_t3/ShapeMM22ShapeMM3_T3.atl";

		Transformation t2 = new Transformation(metamodelPath2, atlTransformationPath2);
		// It shall be the source metamodel
		// TODO change the transformation object. It shall contain source and target
		// metamodel
		t2.setSourceMetamodelPath("case_study/Shapes/ShapeMM2.ecore");
		t2.setTargetMetamodelPath(metamodelPath2);

		transformations.add(t);
		transformations.add(t2);
		return transformations;
	}

	private static List<Transformation> chain2() {
		List<Transformation> transformations = new ArrayList<Transformation>();
		String modelPath = "case_study/Shapes/Shape1.xmi";
		String metamodelPath = "case_study/Shapes/ShapeMM1.ecore";
		String atlTransformationPath = "case_study/Shapes/t2_t3/ShapeMM12ShapeMM2_T2.atl";
		String metamodelPath2 = "case_study/Shapes/ShapeMM3.ecore";
		String atlTransformationPath2 = "case_study/Shapes/t2_t3/ShapeMM22ShapeMM3_T3.atl";
		Transformation t = new Transformation(metamodelPath, atlTransformationPath);
		t.setSourceMetamodelPath("case_study/Shapes/ShapeMM1.ecore");
		t.setTargetMetamodelPath("case_study/Shapes/ShapeMM2.ecore");

		Transformation t2 = new Transformation(metamodelPath2, atlTransformationPath2);
		t2.setSourceMetamodelPath("case_study/Shapes/ShapeMM2.ecore");
		t2.setTargetMetamodelPath("case_study/Shapes/ShapeMM3.ecore");

		transformations.add(t);
		transformations.add(t2);
		return transformations;
	}

}
