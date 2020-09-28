package org.hiddentool.dsl;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.m2m.atl.core.ATLCoreException;
import org.hiddentool.chain.ChainManager;
import org.hiddentool.chain.transformation.Transformation;
import org.hiddentool.metamodel.MetamodelManager;
import org.hiddentool.model.ModelManager;

import PreferenceModel.Importance;
import PreferenceModel.MCImportance;
import PreferenceModel.PreferenceModel;
import PreferenceModel.SfImportance;

public class DSLManager {
	
	private PreferenceModel pm;
	private static DecimalFormat df2 = ((DecimalFormat) NumberFormat.getNumberInstance(Locale.US));

	private MetamodelManager mmManager = MetamodelManager.getInstance();
	private ModelManager mManager = ModelManager.getInstance();
	private ChainManager cManager = ChainManager.getInstance();
	
	private Map<Integer, List<Transformation>> chains = new HashMap<>();
	
	private List<Double> ILs = new ArrayList<>(); 
	private static int nChains = 0;

	public int getImportanceOf(EClass eClass) {
		MCImportance mcImp = findMetaclass(eClass);
		int importance = 0;
		importance += mcImp.getSemanticImportance();
		for (SfImportance attr : mcImp.getSfElements()) {
			importance += attr.getSemanticImportance();
		}
		return importance;
	}

	public double calculateInformationLossWithDSL() throws ATLCoreException {
		double il = 0;
		// returns a list with all metaclasses involved within the source model
		List<EClass> listOfMetaclasses = mmManager.getAllEClasses();
		for (Transformation t : cManager.getTransformations()) {
			for (EClass mc : listOfMetaclasses) {
				il += t.isImpacted(mc) * getImportanceOF(mc) * mManager.getNumberOfInstancesOf(mc);
			}
			il /= listOfMetaclasses.size();
			listOfMetaclasses.clear();
			// Sets the t's outputMetaclasses as new sourceMetaclasses
			listOfMetaclasses.addAll(t.getOutputEClasses());
		}
		ILs.add(il);
		return il;
	}

	private int getImportanceOF(EClass mc) {
		List<MCImportance> mcIList = ((Importance) pm.getPrefs().get(0)).getElements().stream()
				.filter(MCImportance.class::isInstance).map(MCImportance.class::cast).collect(Collectors.toList());
		for (MCImportance mcI : mcIList) {
			if (mcI.getReferredElement().contains(mc)) {
				return mcI.getSemanticImportance();
			}
		}
		return 1;
	}

	public MCImportance findMetaclass(EClass eClass) {
		List<MCImportance> mcIList = ((Importance) pm.getPrefs().get(0)).getElements().stream()
				.filter(MCImportance.class::isInstance).map(MCImportance.class::cast).collect(Collectors.toList());
		for (MCImportance mcI : mcIList) {
			if (mcI.getReferredElement().contains(eClass)) {
				return mcI;
			}
		}
		return null;
	}
	
	public void addTransformations(List<Transformation> ts) {
		cManager.getTransformations().addAll(ts);
		chains.put(nChains++, ts);
	}
	
	public void reportBestChain() {

		df2.applyPattern("#.##");

		Double bestIL = ILs.get(0);
		
		int chainID = 0;
		for (Double il : ILs) {
			if(il < bestIL) {
				bestIL = il;
				chainID = ILs.indexOf(il);
			}
		}

		System.out.println("Best chain = Chain" + chainID + " [" + chains.get(chainID).toString() + "] with IL = "+ df2.format(ILs.get(chainID)));
	}

}
