package it.univaq.disim.mde.manager;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ChainManager {

	private static DecimalFormat df2 = ((DecimalFormat) NumberFormat.getNumberInstance(Locale.US));

	public double calculateChainRank(String inputModel, List<Transformation> transformations) {

		InformationLossManager ilManager = InformationLossManager.Build(inputModel);
		double il = 0;
		for (Transformation transformation : transformations) {
//			System.out.println(Utils.getNameFromPathWithoutExtension(transformation.getAtlTransformationPath()));
			double tmpIL = 0;
			tmpIL = ilManager.calculateInformationLossWithAnnotatedMetamodel_NOT_IMPACTED(transformation);
			il += tmpIL;
			// ilManager.resetModelManager();
		}

		return il;
	}

	public void calculateBestChain(List<Chain> allChains) {

		df2.applyPattern("#.##");

		Chain bestChain = new Chain();
		boolean isFirstElement = true;
		int count = 1;
		for (Chain chain : allChains) {
			chain.setRank(count);
			System.out.println("Chain" + chain.getRank() + ":" + chain.toString() + " IL = "
					+ df2.format(chain.getInformatioLoss()));
			if (isFirstElement) {
				bestChain = chain;
				isFirstElement = false;
			} else {
				if (chain.getInformatioLoss() < bestChain.getInformatioLoss()) {
					bestChain = chain;
				}
			}
			count++;
		}

		System.out.println("Best chain = Chain" + bestChain.getRank() + " [" + bestChain.toString() + "] with IL = "
				+ df2.format(bestChain.getInformatioLoss()));
	}
}
