package it.univaq.disim.mde;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import it.univaq.disim.mde.manager.Chain;
import it.univaq.disim.mde.manager.ChainManager;
import it.univaq.disim.mde.manager.Transformation;
import it.univaq.disim.mde.utility.Utils;

public class Shapes {

	private static DecimalFormat df2 = ((DecimalFormat) NumberFormat.getNumberInstance(Locale.US));

	public static void main(String[] args) {

		List<Chain> allChains = new ArrayList<Chain>();

		Chain chain1 = chain1();
		allChains.add(chain1);
		Chain chain2 = chain2();
		allChains.add(chain2);

		ChainManager chainManager = new ChainManager();
		chainManager.calculateBestChain(allChains);
	}

	private static Chain chain1() {
		Chain chain = new Chain();
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
		t2.setSourceMetamodelPath("case_study/Shapes/ShapeMM2.ecore");
		t2.setTargetMetamodelPath(metamodelPath2);

		ChainManager chainManager = new ChainManager();
		transformations.add(t);
		transformations.add(t2);
		double calculateChainRank = chainManager.calculateChainRank(modelPath, transformations);
		chain.setInformatioLoss(calculateChainRank);
		chain.setName(Utils.getNameFromPathWithoutExtension(atlTransformationPath) + "_"
				+ Utils.getNameFromPathWithoutExtension(atlTransformationPath2));
		chain.setTransformations(transformations);
		return chain;
	}

	private static Chain chain2() {
		Chain chain = new Chain();
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

		ChainManager chainManager = new ChainManager();
		transformations.add(t);
		transformations.add(t2);
		double calculateChainRank = chainManager.calculateChainRank(modelPath, transformations);
		chain.setInformatioLoss(calculateChainRank);
		chain.setName(Utils.getNameFromPathWithoutExtension(atlTransformationPath) + "_"
				+ Utils.getNameFromPathWithoutExtension(atlTransformationPath2));
		chain.setTransformations(transformations);
		return chain;
	}

}
