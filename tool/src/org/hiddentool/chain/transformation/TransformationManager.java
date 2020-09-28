package org.hiddentool.chain.transformation;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.m2m.atl.core.ATLCoreException;
import org.eclipse.m2m.atl.core.IReferenceModel;
import org.eclipse.m2m.atl.core.ModelFactory;
import org.eclipse.m2m.atl.core.emf.EMFModel;
import org.eclipse.m2m.atl.core.emf.EMFModelFactory;
import org.eclipse.m2m.atl.engine.parser.AtlParser;
import org.hiddentool.model.ModelManager;

import anatlyzer.atl.model.ATLModel;
import anatlyzer.atlext.ATL.InPatternElement;
import anatlyzer.atlext.ATL.MatchedRule;
import anatlyzer.atlext.ATL.OutPatternElement;
import anatlyzer.atlext.ATL.Rule;
import anatlyzer.atlext.ATL.SimpleInPatternElement;
import anatlyzer.atlext.ATL.SimpleOutPatternElement;

public class TransformationManager {

	public static int isImpacted(Transformation t, EClass ec) throws ATLCoreException {
		return getSrcEClasses(t).contains(ec.getName()) ? 0 : 1;
	}

	private static List<String> getSrcEClasses(Transformation t) throws ATLCoreException {
		ATLModel atlModel = loadATLTransformation(t);
		List<String> elements = new ArrayList<String>();
		for (MatchedRule mr : atlModel.allObjectsOf(MatchedRule.class)) {
			for (InPatternElement inPatternElement : mr.getInPattern().getElements()) {
				if (inPatternElement instanceof SimpleInPatternElement) {
					SimpleInPatternElement simpleInPatternElement = (SimpleInPatternElement) inPatternElement;
					elements.add(simpleInPatternElement.getType().getName());
				}
			}
		}
		return elements;
	}

	public static ATLModel loadATLTransformation(Transformation atlTransformation) throws ATLCoreException {
		AtlParser atlParser = new AtlParser();
		ModelFactory modelFactory = new EMFModelFactory();
		Resource originalTransformation = null;
		IReferenceModel atlMetamodel = null;
		atlMetamodel = modelFactory.getBuiltInResource("ATL.ecore");
		EMFModel atlDynModel = (EMFModel) modelFactory.newModel(atlMetamodel);
		atlParser.inject(atlDynModel, atlTransformation.getAtlTransformationPath());
		originalTransformation = atlDynModel.getResource();
		ATLModel atlModel = new ATLModel(originalTransformation, originalTransformation.getURI().toFileString(), true);
		return atlModel;
	}

	public static List<EClass> getOutputMetaclasses(Transformation t) throws ATLCoreException {
		ATLModel atlModel = loadATLTransformation(t);
		List<String> mcNames = new ArrayList<>();
		for (Rule rule : atlModel.allObjectsOf(Rule.class)) {
			if (getOutputMetaclassFromRule(rule) != null) {
				mcNames.add(getOutputMetaclassFromRule(rule));
			}
		}

		List<EClass> resultList = new ArrayList<>();
		for (String eClassName : mcNames) {
			for (EClass eClass : ModelManager.getInstance().getAllModelEClasses(t.getTargetMetamodelPath())) {
				if (eClassName.equalsIgnoreCase(eClass.getName())) {
					resultList.add(eClass);
				}
			}
		}
		return resultList;

	}

	public static String getOutputMetaclassFromRule(Rule mr) {
		EList<OutPatternElement> elements = mr.getOutPattern().getElements();
		for (OutPatternElement outPatternElement : elements) {
			if (outPatternElement instanceof SimpleOutPatternElement) {
				SimpleOutPatternElement simpleOutPatternElement = (SimpleOutPatternElement) outPatternElement;
				return simpleOutPatternElement.getType().getName();
			}
		}
		return null;
	}

}
