package it.univaq.disim.mde;

import java.util.ArrayList;
import java.util.List;

import it.univaq.disim.mde.manager.Chain;
import it.univaq.disim.mde.manager.ChainManager;
import it.univaq.disim.mde.manager.Transformation;
import it.univaq.disim.mde.utility.Utils;

public class Ant2Maven2XML {

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
		String modelPath = "case_study/Ant2Maven2XML/Ant2Maven.xmi";
		String metamodelPath = "case_study/Ant2Maven2XML/Ant.ecore";
		String atlTransformationPath = "case_study/Ant2Maven2XML/v1.0/Ant2Maven.atl";
		Transformation t = new Transformation(metamodelPath, atlTransformationPath);
		t.setSourceMetamodelPath("case_study/Ant2Maven2XML/Ant.ecore");
		t.setTargetMetamodelPath("case_study/Ant2Maven2XML/Maven.ecore");

		String metamodelPath2 = "case_study/Ant2Maven2XML/Maven.ecore";
		String atlTransformationPath2 = "case_study/Ant2Maven2XML/v1.0/Maven2XML.atl";

		Transformation t2 = new Transformation(metamodelPath2, atlTransformationPath2);
		t2.setSourceMetamodelPath("case_study/Ant2Maven2XML/Maven.ecore");
		t2.setTargetMetamodelPath("case_study/Ant2Maven2XML/XML.ecore");

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
		String modelPath = "case_study/Ant2Maven2XML/Ant2Maven.xmi";
		String metamodelPath = "case_study/Ant2Maven2XML/Ant.ecore";
		String atlTransformationPath = "case_study/Ant2Maven2XML/v1.01/Ant2Maven.atl";
		Transformation t = new Transformation(metamodelPath, atlTransformationPath);
		t.setSourceMetamodelPath("case_study/Ant2Maven2XML/Ant.ecore");
		t.setTargetMetamodelPath("case_study/Ant2Maven2XML/Maven.ecore");

		String metamodelPath2 = "case_study/Ant2Maven2XML/Maven.ecore";
		String atlTransformationPath2 = "case_study/Ant2Maven2XML/v1.01/Maven2XML.atl";

		Transformation t2 = new Transformation(metamodelPath2, atlTransformationPath2);
		t2.setSourceMetamodelPath("case_study/Ant2Maven2XML/Maven.ecore");
		t2.setTargetMetamodelPath("case_study/Ant2Maven2XML/XML.ecore");

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
