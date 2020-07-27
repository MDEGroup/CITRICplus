package it.univaq.disim.mde;

import java.util.ArrayList;
import java.util.List;

import it.univaq.disim.mde.manager.Chain;
import it.univaq.disim.mde.manager.ChainManager;
import it.univaq.disim.mde.manager.Transformation;
import it.univaq.disim.mde.utility.Utils;

public class PathExp2PetriNet2XML {

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
		String modelPath = "case_study/PathExp2PetriNet2XML/PathExp2PetriNet.xmi";
		String metamodelPath = "case_study/PathExp2PetriNet2XML/PathExp.ecore";
		String atlTransformationPath = "case_study/PathExp2PetriNet2XML/v0.6/PathExp2PetriNet.atl";
		Transformation t = new Transformation(metamodelPath, atlTransformationPath);
		t.setSourceMetamodelPath("case_study/PathExp2PetriNet2XML/PathExp.ecore");
		t.setTargetMetamodelPath("case_study/PathExp2PetriNet2XML/PetriNet.ecore");

		String metamodelPath2 = "case_study/PathExp2PetriNet2XML/PetriNet.ecore";
		String atlTransformationPath2 = "case_study/PathExp2PetriNet2XML/v0.6/PetriNet2XML.atl";

		Transformation t2 = new Transformation(metamodelPath2, atlTransformationPath2);
		t2.setSourceMetamodelPath("case_study/PathExp2PetriNet2XML/PetriNet.ecore");
		t2.setTargetMetamodelPath("case_study/PathExp2PetriNet2XML/XML.ecore");

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
		String modelPath = "case_study/PathExp2PetriNet2XML/PathExp2PetriNet.xmi";
		String metamodelPath = "case_study/PathExp2PetriNet2XML/PathExp.ecore";
		String atlTransformationPath = "case_study/PathExp2PetriNet2XML/v0.9/PathExp2PetriNet.atl";
		Transformation t = new Transformation(metamodelPath, atlTransformationPath);
		t.setSourceMetamodelPath("case_study/PathExp2PetriNet2XML/PathExp.ecore");
		t.setTargetMetamodelPath("case_study/PathExp2PetriNet2XML/PetriNet.ecore");

		String metamodelPath2 = "case_study/PathExp2PetriNet2XML/PetriNet.ecore";
		String atlTransformationPath2 = "case_study/PathExp2PetriNet2XML/v0.9/PetriNet2XML.atl";

		Transformation t2 = new Transformation(metamodelPath2, atlTransformationPath2);
		t2.setSourceMetamodelPath("case_study/PathExp2PetriNet2XML/PetriNet.ecore");
		t2.setTargetMetamodelPath("case_study/PathExp2PetriNet2XML/XML.ecore");

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
