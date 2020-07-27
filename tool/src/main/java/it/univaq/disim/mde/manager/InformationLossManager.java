package it.univaq.disim.mde.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;

public class InformationLossManager {

	// Contain all EClass of the sourceMetamoModel
	Map<EClass, Integer> source = new HashMap<>();

	private static final String ANNOTATION_SOURCE = "http://class/importance";
	private static final String ANNOTATION_KEY = "weight";

	private MetamodelManager metamodelManager;
	private ModelManager modelManager;
	private ATLTransformationManager atlManager;

	private Transformation transformation;

	public InformationLossManager(String metamodelPath, String modelPath, String atlTransformationPath) {
		metamodelManager = MetamodelManager.Build(metamodelPath);
		modelManager = ModelManager.Build(metamodelPath, modelPath);
		atlManager = ATLTransformationManager.Build(atlTransformationPath);
	}

	public InformationLossManager(String modelPath) {
		modelManager = ModelManager.Build(modelPath);
	}

	public static InformationLossManager Build(String metamodelPath, String modelPath, String atlTransformationPath) {
		return new InformationLossManager(metamodelPath, modelPath, atlTransformationPath);
	}

	public static InformationLossManager Build(String modelPath) {
		return new InformationLossManager(modelPath);
	}

	public double calculateInformationLossWithAnnotatedMetamodel_NOT_IMPACTED(Transformation transformation) {
		this.metamodelManager = MetamodelManager.Build(transformation.getSourceMetamodelPath());
		this.atlManager = ATLTransformationManager.Build(transformation.getAtlTransformationPath());
		this.transformation = transformation;
		// Contain all missed EClass and their number of instances
		Map<EClass, Integer> notImpactedMCWithInstances = new HashMap<>();

		// If source is empty, it is the first transformation of the chain
		if (source.isEmpty()) {
			// fill the map with the source metamodel EClasses
			for (EClass ec : metamodelManager.getAllEClasses2()) {
				source.put(ec, modelManager.getNumberOfInstancesOf(ec));
			}
		}

		// Contain Target EClass and the number of instances come from the source model
		Map<EClass, Integer> target = new HashMap<>();
		// Contain the source EClass name mapped with the target EClass name
		Map<String, String> mappingInOutClasses = atlManager.getMappingInOutPatterns();

		// fill the map with the target EClasses and their number of instances
		List<EClass> sourceEClasses = metamodelManager.getAllEClasses2();
		for (String inEclassName : mappingInOutClasses.keySet()) {
			for (EClass ec : sourceEClasses) {
				if (ec.getName().equals(inEclassName)) {
					int numberOfSourceInstances = 0;
					for (EClass srcEC : source.keySet()) {
						if (srcEC.getName().equals(ec.getName())) {
							numberOfSourceInstances = source.get(srcEC);
						}
					}

					String targetMetaclassName = mappingInOutClasses.get(inEclassName);
					// is the mm manager of the target metamodel
					MetamodelManager mmTarget = MetamodelManager.Build(transformation.getTargetMetamodelPath());
					EClass targetMetaclass = mmTarget.getEClassFromName(targetMetaclassName);
					target.put(targetMetaclass, numberOfSourceInstances);
				}

			}
		}

		// Create a map <EClass, <EStructuralFeature(i), weight>>
		Map<EClass, Map<EStructuralFeature, Integer>> eclassSFs = new HashMap<>();
		for (EClass ec : sourceEClasses) {
//			Set<String> allImpactedStructuralFeatures = atlManager.getAllImpactedStructuralFeatures();
			Map<EStructuralFeature, Integer> mapSFandWeight = new HashMap<>();

			for (EStructuralFeature eSF : metamodelManager.getAllECLassEStructuralFeatures(ec)) {

				if (!atlManager.getAllImpactedStructuralFeaturesOf(ec).contains(eSF.getName())) {

					// Calculate the weight of a Structural Feature
					for (EAnnotation eSFAnnotation : metamodelManager
							.getAllECLassStructuralFeatureEAnnotations(ec.getName(), eSF.getName())) {
						if (eSFAnnotation.getSource().equalsIgnoreCase(ANNOTATION_SOURCE)) {
							String annotationWeightValue = metamodelManager
									.getWheightValueAnnotationFromKey(eSFAnnotation, ANNOTATION_KEY);
							int weight = Integer.parseInt(annotationWeightValue);
							mapSFandWeight.put(eSF, weight);

						}
					}
					eclassSFs.put(ec, mapSFandWeight);
				}
			}
		}

		// fill the map with missed EClasses and their number of instances
		Map<EClass, Integer> mcsNOTImpactedWeights = new HashMap<>();
		Map<EClass, Integer> impactedMCWithInstances = new HashMap<>();
		// A simple counter of missed EClass instances
		int numberOfNotImpactedInstances = 0;
		for (EClass srcEC : source.keySet()) {
			if (!mappingInOutClasses.keySet().contains(srcEC.getName())) {
				notImpactedMCWithInstances.put(srcEC, source.get(srcEC));
				mcsNOTImpactedWeights.put(srcEC, metamodelManager.getWeightOf(srcEC));
				numberOfNotImpactedInstances += source.get(srcEC);

			} else {
				for (EClass t : target.keySet()) {
					if (t.getName().equals(mappingInOutClasses.get(srcEC.getName()))) {
						impactedMCWithInstances.put(t, target.get(t));
					}
				}
			}
		}

		// is the number of source metamodel EClasses minus the number of missed
		// EClasses
		int notImpactedMetaclass = (source.keySet().size() - mappingInOutClasses.keySet().size() <= 0) ? 0
				: source.keySet().size() - mappingInOutClasses.keySet().size();
		int weights = (new ArrayList<>(mcsNOTImpactedWeights.values()).stream().reduce(0, Integer::sum));

		double ilEsfs = 0.0;
		for (EClass ec : eclassSFs.keySet()) {
			if (notImpactedMCWithInstances.get(ec) != null) {
				ilEsfs += (new ArrayList<>(eclassSFs.get(ec).values()).stream().reduce(0, Integer::sum))
						* notImpactedMCWithInstances.get(ec);
			}
		}

		double il = (((double) notImpactedMetaclass * numberOfNotImpactedInstances * weights) / source.keySet().size())
				+ (ilEsfs / metamodelManager.getAllEStructuralFeatures().size());

		source.clear();
		source.putAll(impactedMCWithInstances);

		return il;
	}

	public List<EClass> getOutputTransformationMetaclasses() {
		if (modelManager != null) {
			return modelManager.getAllModelEClasses();
		}
//		return this.atlManager.getAllOutputMetaclassName();
		return ModelManager.Build(this.transformation.getModelPath()).getAllModelEClasses();
	}

//	private int calculateEClassWeight() {
//		int metaclassNOTImpactedWeight = 0;
//		for (Map.Entry<String, Integer> eClassWeight : metaclassesNOTImpactedWeights.entrySet()) {
//			metaclassNOTImpactedWeight += eClassWeight.getValue();
//		}
//		return metaclassNOTImpactedWeight;
//	}

//	private int calculateEStructuralFeatureWeight() {
//		int sfNOTImpactedWeight = 0;
//		for (Map.Entry<String, Integer> eSFWeight : structuralFeaturesNOTImpactedWeights.entrySet()) {
//			sfNOTImpactedWeight += eSFWeight.getValue();
//		}
//		return sfNOTImpactedWeight;
//	}

//	public double calculateInformationLossWithAnnotatedMetamodel_NOT_IMPACTED() {
//		List<EClass> nModelClasses = getOutputTransformationMetaclasses();
//		List<EClass> nMetamodelClasses = metamodelManager.getAllEClasses2();
//		List<EStructuralFeature> nMetamodelStructuralFeatures = metamodelManager.getAllEStructuralFeatures();
//		// It collects all MCs and SFs NOT impacted by the transformation
//		setNOTImpacedMetaclassesAndStructuralFeatures(nModelClasses);
//		// It counts MCs and SFs weights NOT impacted by the transformation
//		setWeightNOTImpactedMetaclassesAndStructuralFeatures();
//		int nNOTImpactedMetaclasses = nMetamodelClasses.size() - atlImpactedMetaclasses.size();
//		int metaclassNOTImpactedWeight = calculateEClassWeight();
//		int sfNOTImpactedWeight = calculateEStructuralFeatureWeight();
//
//		double il = (((double) nNOTImpactedMetaclasses / nMetamodelClasses.size()) * metaclassNOTImpactedWeight);// +
//		// (((double)atlNOTImpactedStructuralFeatures.size()/nMetamodelStructuralFeatures.size())
//		// * sfNOTImpactedWeight);
//
//		System.out.println(il);
//
//		if (VISUALIZE_CALCULATIONS) {
//			System.out.println("\t(a) # nNOTImpactedMetaclasses(t,m): " + nNOTImpactedMetaclasses);
//			System.out.println("\t(b) # nMetamodelClasses(mm): " + nMetamodelClasses.size());
//			System.out.println("\t(c) # weight(mc): " + metaclassNOTImpactedWeight);
//			System.out
//					.println("\t(d) # nNOTImpactedStructuralFeatures(t,m): " + atlNOTImpactedStructuralFeatures.size());
//			System.out.println("\t(e) # nMetamodelStructuralFeatures(mm): " + nMetamodelStructuralFeatures.size());
//			System.out.println("\t(f) # weight(sf): " + sfNOTImpactedWeight);
//
//			System.out.println("\tIL(t, m, mm) = [(a/b)*c] + [(d/e)*f]");
//			System.out.println("-----------------------------------------------------");
//			System.out.println("\tIL(t, m, mm) = [(" + nNOTImpactedMetaclasses + "/" + nMetamodelClasses.size() + ")*"
//					+ metaclassNOTImpactedWeight + "] + [(" + atlNOTImpactedStructuralFeatures.size() + "/"
//					+ nMetamodelStructuralFeatures.size() + ")*" + sfNOTImpactedWeight + "] = " + il);
//			System.out.println();
//		}
//
//		return il;
//
//	}

	public void resetModelManager() {
		this.modelManager = null;
	}

}
