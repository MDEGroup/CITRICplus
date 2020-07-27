package it.univaq.disim.mde.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.m2m.atl.core.ATLCoreException;
import org.eclipse.m2m.atl.core.IReferenceModel;
import org.eclipse.m2m.atl.core.ModelFactory;
import org.eclipse.m2m.atl.core.emf.EMFModel;
import org.eclipse.m2m.atl.core.emf.EMFModelFactory;
import org.eclipse.m2m.atl.engine.parser.AtlParser;

import anatlyzer.atl.analyser.AnalysisResult;
import anatlyzer.atl.errors.Problem;
import anatlyzer.atl.errors.ProblemStatus;
import anatlyzer.atl.errors.atl_error.LocalProblem;
import anatlyzer.atl.model.ATLModel;
import anatlyzer.atl.model.ErrorModel;
import anatlyzer.atl.tests.api.AnalysisLoader;
import anatlyzer.atl.types.Metaclass;
import anatlyzer.atl.types.UnresolvedTypeError;
import anatlyzer.atl.util.ATLUtils;
import anatlyzer.atl.util.ATLUtils.ModelInfo;
import anatlyzer.atl.util.AnalyserUtils;
import anatlyzer.atlext.ATL.Binding;
import anatlyzer.atlext.ATL.InPatternElement;
import anatlyzer.atlext.ATL.LazyRule;
import anatlyzer.atlext.ATL.MatchedRule;
import anatlyzer.atlext.ATL.OutPatternElement;
import anatlyzer.atlext.ATL.Rule;
import anatlyzer.atlext.ATL.RuleWithPattern;
import anatlyzer.atlext.ATL.SimpleInPatternElement;
import anatlyzer.atlext.ATL.SimpleOutPatternElement;

public class ATLTransformationManager {
	
	ATLModel atlModel = new ATLModel();
	
	public ATLTransformationManager(String atlTransformationPath) {
		try {
			this.atlModel = loadATLTransformation(atlTransformationPath);
		} catch (ATLCoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static ATLTransformationManager Build(String atlTransformationPath) {
		return new ATLTransformationManager(atlTransformationPath);
	}
	
	
	public static void main(String[] args) throws ATLCoreException {
//		String transformationPath = "resources/test/Company2CMS.atl";
		String transformationPath = "case_study/Company2CRM/Company2CRM.atl";
		ATLTransformationManager atlManager = new ATLTransformationManager(transformationPath);
		for (MatchedRule string : atlManager.getAllMatchedRules()) {
			System.out.println(string);
		}
		
		
		
//		List<ATLBinding> atlBindings = ATLTransformationManager.getAllBindings(transformationPath);
//		for (ATLBinding atlBinding : atlBindings) {
//			System.out.println("MC: "+atlBinding.getMetaclassName());
//			for (String string : atlBinding.getBindings()) {
//				System.out.println("\t"+string);
//			}
//		}
		
	}
	
	
	public ATLModel loadATLTransformation(String atlTransformation) throws ATLCoreException {
		AtlParser atlParser = new AtlParser();
		ModelFactory modelFactory = new EMFModelFactory();
		Resource originalTransformation = null;
		IReferenceModel atlMetamodel = null;
		try {
			atlMetamodel = modelFactory.getBuiltInResource("ATL.ecore");
			EMFModel atlDynModel = (EMFModel) modelFactory.newModel(atlMetamodel);
			atlParser.inject(atlDynModel, atlTransformation);
			originalTransformation = atlDynModel.getResource();
		} catch (ATLCoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ATLModel atlModel = new ATLModel(originalTransformation, originalTransformation.getURI().toFileString(), true);
		
		
		
		
		
//		System.out.println("START PROCESS");
//		AtlParser atlParser = new AtlParser();
//		ModelFactory modelFactory = new EMFModelFactory();
//		IReferenceModel atlMetamodel = modelFactory
//				.getBuiltInResource("ATL.ecore");
//		EMFModel atlDynModel = (EMFModel) modelFactory.newModel(atlMetamodel);
//		atlParser.inject(atlDynModel, atlTransformation);
//
//		Resource originalTrafo = atlDynModel.getResource();
//
//		ATLModel atlModel = new ATLModel(originalTrafo, originalTrafo.getURI()
//				.toFileString(), true);
//		
		
		
		
		return atlModel;
	}

	public List<MatchedRule> getAllMatchedRules() {
		return this.atlModel.allObjectsOf(MatchedRule.class);
	}
	
	public List<Rule> getAllRules(){
		return this.atlModel.allObjectsOf(Rule.class);
	}
	public List<RuleWithPattern> getAllRulesWithPattern(){
		return this.atlModel.allObjectsOf(RuleWithPattern.class);
	}
	
//	public static List<MatchedRule> getAllMatchedRules(ATLModel atlModel) {
//		List<MatchedRule> callableMethods = new ArrayList<MatchedRule>();
//		
//		// System.out.println("EXTRACTING CALLABLE ELEMENTS FROM A ATL MODEL");
//		// DEFINE OCL AND HELPER
//		OCL<?, EClassifier, ?, ?, ?, EParameter, ?, ?, ?, Constraint, EClass, EObject> ocl;
//		OCLHelper<EClassifier, ?, ?, Constraint> helper;
//		
//		// INSTANCIATE OCL
//		ocl = OCL.newInstance(EcoreEnvironmentFactory.INSTANCE);
//		// INSTANCIATE NEW HELPER FROM OCLEXPRESSION
//		helper = ocl.createOCLHelper();
//		// SET HELPER CONTEXT
//		helper.setContext(ATLPackage.eINSTANCE.getModule());
//		
//		// CREATE OCLEXPRESSION
//		OCLExpression<EClassifier> expression = null;
//		try {
//			expression = helper.createQuery("MatchedRule.allInstances()");
////			OCLExpression<EClassifier> expression = helper.createQuery("MatchedRule.allInstances()->collect(e | e.name)");
//			// CREATE QUERY FROM OCLEXPRESSION
//			Query<EClassifier, EClass, EObject> query = ocl.createQuery(expression);
//			
//			// EVALUATE OCL
//			HashSet<Object> success = (HashSet<Object>) query.evaluate(atlModel.getRoot());
//			
//			for (Object object : success) {
//				callableMethods.add((MatchedRule) object);
//			}
//		} catch (ParserException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return callableMethods;
//	}
	

	public String getInputMetaclassNameFromMatchedRule(MatchedRule mr) {
		String result = null;
		EList<InPatternElement> elements = mr.getInPattern().getElements();
		for (InPatternElement inPatternElement : elements) {
			if(inPatternElement instanceof SimpleInPatternElement) {
				SimpleInPatternElement simpleInPatternElement = (SimpleInPatternElement) inPatternElement;
				result = simpleInPatternElement.getType().getName();
			}
		}
		return result;
	}
	
	
	/**
	 * Return the Rule bindings only
	 * @param mr
	 * @return
	 */
	public List<String> getInputStructuralFeatureNameFromMatchedRule(MatchedRule mr) {
		List<String> allWrittenFeatures = new ArrayList<String>();
		List<? extends Binding> bindings = mr.getOutPattern().getElements().get(0).getBindings();
		for (Binding binding : bindings) {
			((MatchedRule) binding.eContainer().eContainer().eContainer()).equals(mr);
			String f = binding.getPropertyName();
			if ( f != null ) {
				allWrittenFeatures.add(f);
			}
		}
		return allWrittenFeatures;
	}
	
	
	
	public HashSet<EStructuralFeature> getCurrentCompulsoryFeatures(Metaclass mc) {
		
		if ( mc instanceof UnresolvedTypeError ) {
			return new HashSet<EStructuralFeature>();
		}
		HashSet<EStructuralFeature> compulsoryFeatures = new HashSet<EStructuralFeature>();
		
		for(EStructuralFeature f : mc.getKlass().getEAllStructuralFeatures() ) {			
			if ( f.getLowerBound() != 0 && f.getDefaultValue() == null ) {
				compulsoryFeatures.add(f);
			}
		}
		
		return compulsoryFeatures;
	}
	
	public String getOutputMetaclassNameFromRule(Rule mr) {
		String result = null;
		EList<OutPatternElement> elements = mr.getOutPattern().getElements();
		for (OutPatternElement outPatternElement : elements) {
			if(outPatternElement instanceof SimpleOutPatternElement) {
				SimpleOutPatternElement simpleOutPatternElement = (SimpleOutPatternElement) outPatternElement;
				result = simpleOutPatternElement.getType().getName();
			}
		}
		return result;
	}
	
	public List<String> getAllOutputMetaclassName(){
		List<String> mcNames = new ArrayList<>();
		for (Rule rule : getAllRules()) {
			if(getOutputMetaclassFromRule(rule) != null) {
				mcNames.add(getOutputMetaclassFromRule(rule));
			}
		}
		return mcNames;
	}
	
	public String getOutputMetaclassFromRule(Rule mr) {
		EList<OutPatternElement> elements = mr.getOutPattern().getElements();
		for (OutPatternElement outPatternElement : elements) {
			if(outPatternElement instanceof SimpleOutPatternElement) {
				SimpleOutPatternElement simpleOutPatternElement = (SimpleOutPatternElement) outPatternElement;
				return simpleOutPatternElement.getType().getName();
			}
		}
		return null;
	}
	
	
//	public List<ATLBinding> getAllBindings(){
//		List<ATLBinding> atlBindings = new ArrayList<ATLBinding>();
//		
//		List<MatchedRule> rules = getAllMatchedRules();
//		for (MatchedRule rule : rules) {
//			ATLBinding atlBinding = new ATLBinding();
//			atlBinding.setRuleName(rule.getName());
//			atlBinding.setRuleType(rule.getClass().getName());
//			atlBinding.setInputMetaclassName(getInputMetaclassNameFromMatchedRule(rule));
//			atlBinding.setOutputMetaclassName(getOutputMetaclassNameFromRule(rule));
//			atlBinding.setBindings(getRuleBindings(rule));
//			atlBindings.add(atlBinding);
//		}
//		
//		return atlBindings;
//	}
//	
//	
	public List<Binding> getRuleBindings(Rule rule){
		List<Binding> result = new ArrayList<Binding>();
		EList<OutPatternElement> elements = rule.getOutPattern().getElements();
		for (OutPatternElement outPatternElement : elements) {
				EList<Binding> bb = outPatternElement.getBindings();
				for (Binding binding : bb) {
//					System.out.println(binding.getValue());
//					System.out.println(binding.getPropertyName());
//					if(binding.getValue() instanceof NavigationOrAttributeCallExp) {
//						NavigationOrAttributeCallExp fromBindingSide = (NavigationOrAttributeCallExp) binding.getValue();
////						ruleBinding.setOutput(binding.getPropertyName());
////						ruleBinding.setInput(fromBindingSide.getName());
////						System.out.println("\t["+fromBindingSide+"]"+binding.getPropertyName() +" = "+ fromBindingSide.getName());
//					} else if(binding.getValue() instanceof NavigationOrAttributeCallExpImpl) {
//						NavigationOrAttributeCallExpImpl fromBindingSide = (NavigationOrAttributeCallExpImpl) binding.getValue();
//						ruleBinding.setOutput(binding.getPropertyName());
//						ruleBinding.setInput(fromBindingSide.getName());
////						System.out.println("\t["+fromBindingSide+"]"+binding.getPropertyName()+" = "+ fromBindingSide.getName());
//					} else if(binding.getValue() instanceof IteratorExpImpl) {
//						IteratorExpImpl it = (IteratorExpImpl) binding.getValue();
//						if(it.getSource() instanceof NavigationOrAttributeCallExp) {
//							NavigationOrAttributeCallExp fromBindingSide = (NavigationOrAttributeCallExp) it.getSource();
//							ruleBinding.setOutput(binding.getPropertyName());
//							ruleBinding.setInput(fromBindingSide.getName());
//						}else {
//							ruleBinding.setOutput(binding.getPropertyName());
//						}
//					}
//					else if(binding.getValue() instanceof OperationCallExpImpl) {
//						OperationCallExpImpl op = (OperationCallExpImpl) binding.getValue();
//						
//						if(op.getSource() instanceof VariableExpImpl) {
//							VariableExpImpl var = (VariableExpImpl) op.getSource();
//							System.out.println("@#@#"+var.getAppliedProperty());
//						}
////						NavigationOrAttributeCallExp fromBindingSide = (NavigationOrAttributeCallExp) op.getSource();
////						ruleBinding.setOutput(binding.getPropertyName());
////						ruleBinding.setInput(fromBindingSide.getName());
//					 else {
////						System.out.println(binding.getValue());
//						ruleBinding.setOutput(binding.getPropertyName());
//					} 
//						else if(binding.getValue() instanceof CollectionOperationCallExpImpl) {
//						CollectionOperationCallExpImpl fromBindingSide = (CollectionOperationCallExpImpl) binding.getValue();
//						System.out.println("\t["+fromBindingSide.getClass().getName()+"]"+binding.getPropertyName()+" = "+ fromBindingSide.getArguments().get(0));
//					}
					result.add(binding);
				}
		}
		return result;
	}
	
	
	
	
	public boolean isValidTransformation() {
		boolean result = true;
		ErrorModel errors = getATLErrors();
		if(errors.getProblems().size() > 0) {
			result = false;
		}
		return result;
	}
	
	public ErrorModel getATLErrors() {
		return atlModel.getErrors();
	}
	
	
	public String getInTag() {
		List<ModelInfo> info = ATLUtils.getModelInfo(this.atlModel);
		String result = null;
		int count = 0;
		for (ModelInfo modelInfo : info) {
			if(count == 0) {
				result = modelInfo.getMetamodelName();
			}
			count++;
		}
		return result;
	}
	
	public String getOutTag() {
		
		List<ModelInfo> info = ATLUtils.getModelInfo(this.atlModel);
		String result = null;
		int count = 0;
		for (ModelInfo modelInfo : info) {
			if(count == 1) {
				result = modelInfo.getMetamodelName();
			}
			count++;
		}
		return result;
	}
	
	public void anATLyzerTest() {
		List<ModelInfo> info = ATLUtils.getModelInfo(this.atlModel);
		// Resource[] resources = new Resource[info.size()];
		String[] files = new String[info.size()];
		String[] names = new String[info.size()];
		
		int i = 0;
		for (ModelInfo modelInfo : info) {
			names[i] = modelInfo.getMetamodelName();
			files[i] = modelInfo.getURIorPath();
			i++;
			System.out.println("@#@"+modelInfo.getModelName());
			System.out.println(modelInfo.getMetamodelName());
			System.out.println(modelInfo.getURIorPath());
			System.out.println(modelInfo.isInput());
			System.out.println(modelInfo.isOutput());
		}
		// submit...
		/*
		Resource r1 = null; // get r1 from mdeforge 
		Resource r2 = null; // get r2 from mdeforge 
		*/
		AnalysisLoader loader = AnalysisLoader.fromATLModel(atlModel, files, names);
		AnalysisResult result = loader.analyse();
		
		for (Problem problem : result.getProblems()) {
			if ( problem instanceof LocalProblem ) {
				LocalProblem lp = (LocalProblem) problem;
			
				
				System.out.println( lp.getElement() );				
			}
			
			if ( problem.getStatus() == ProblemStatus.WITNESS_REQUIRED ) {
				// launch constraint solver
				
			}
			
			
			System.out.println( AnalyserUtils.getProblemId(problem) );
			System.out.println( AnalyserUtils.getProblemDescription(problem) );
		}
	}
	
	
	public List<String> getAllImpactedMetaclasses() {
		// TODO Restituire tutte le MC su cui applica la trasformazione
		List<String> elements = new ArrayList<String>();
		for (MatchedRule mr : this.getAllMatchedRules()) {
			for (InPatternElement inPatternElement : mr.getInPattern().getElements()) {
				if(inPatternElement instanceof SimpleInPatternElement) {
					SimpleInPatternElement simpleInPatternElement = (SimpleInPatternElement) inPatternElement;
					elements.add(simpleInPatternElement.getType().getName());
				}
			}
		}
		return elements;
	}

	public Set<String> getAllImpactedStructuralFeaturesOf(EClass ec) {
		// TODO Restituire tutte le SF su cui applica la trasformazione
		//Le SF devono avere la forma nomeClasse#nomeSF
		Set<String> allWrittenFeatures = new HashSet<String>();
		for (MatchedRule mr : this.getAllMatchedRules()) {
			List<? extends Binding> bindings = mr.getOutPattern().getElements().get(0).getBindings();
			for (Binding binding : bindings) {
				((MatchedRule) binding.eContainer().eContainer().eContainer()).equals(mr);
				String f = binding.getPropertyName();
				if ( f != null && getInputMetaclassNameFromMatchedRule(mr).equals(ec.getName())) {
//					String compositeSFName = getInputMetaclassNameFromMatchedRule(mr)+"#"+f;
					allWrittenFeatures.add(f);
				}
			}
		}
		return allWrittenFeatures;
	}
	
	public Set<String> getAllImpactedStructuralFeatures() {
		// TODO Restituire tutte le SF su cui applica la trasformazione
		//Le SF devono avere la forma nomeClasse#nomeSF
		Set<String> allWrittenFeatures = new HashSet<String>();
		for (MatchedRule mr : this.getAllMatchedRules()) {
			List<? extends Binding> bindings = mr.getOutPattern().getElements().get(0).getBindings();
			for (Binding binding : bindings) {
				((MatchedRule) binding.eContainer().eContainer().eContainer()).equals(mr);
				String f = binding.getPropertyName();
				if ( f != null ) {
					String compositeSFName = getInputMetaclassNameFromMatchedRule(mr)+"#"+f;
					allWrittenFeatures.add(compositeSFName);
				}
			}
		}
		return allWrittenFeatures;
	}

	public Integer getAllInstancesOf(EClass eClass, Map<EClass, Integer> source) {
		for (MatchedRule mr : this.getAllMatchedRules()) {
			for (InPatternElement inPatternElement : mr.getInPattern().getElements()) {
				if (inPatternElement instanceof SimpleInPatternElement) {
					if(mr.getOutPattern().getElements().get(0).eClass().equals(eClass)) {
						SimpleInPatternElement simpleInPatternElement = (SimpleInPatternElement) inPatternElement;
						return source.get(simpleInPatternElement.getType().eClass());
					}
				}
			}
		}
		return 0;
	}
	
	
	public Map<String, String> getMappingInOutPatterns(){
		
		Map<String, String> returnValue = new HashMap<>();
		
		for (RuleWithPattern mr : getAllRulesWithPattern()) {
			returnValue.put(getInputMetaclassNameFromRuleWithPattern(mr), getOutputMetaclassNameFromRuleWithPattern(mr));
		}
		return returnValue;
	}


	public String getOutputMetaclassNameFromMatchedRule(MatchedRule mr) {
//		String result = null;
		EList<OutPatternElement> elements = mr.getOutPattern().getElements();
		for (OutPatternElement outPatternElement : elements) {
			if(outPatternElement instanceof SimpleOutPatternElement) {
				SimpleOutPatternElement simpleOutPatternElement = (SimpleOutPatternElement) outPatternElement;
				return simpleOutPatternElement.getType().getName();
			}
		}
		return null;
	}
	
	public String getInputMetaclassNameFromRuleWithPattern(RuleWithPattern mr) {
		String result = null;
		EList<InPatternElement> elements = mr.getInPattern().getElements();
		for (InPatternElement inPatternElement : elements) {
			if(inPatternElement instanceof SimpleInPatternElement) {
				SimpleInPatternElement simpleInPatternElement = (SimpleInPatternElement) inPatternElement;
				result = simpleInPatternElement.getType().getName();
			}
		}
		return result;
	}
	
	public String getOutputMetaclassNameFromRuleWithPattern(RuleWithPattern mr) {
//		String result = null;
		EList<OutPatternElement> elements = mr.getOutPattern().getElements();
		for (OutPatternElement outPatternElement : elements) {
			if(outPatternElement instanceof SimpleOutPatternElement) {
				SimpleOutPatternElement simpleOutPatternElement = (SimpleOutPatternElement) outPatternElement;
				return simpleOutPatternElement.getType().getName();
			}
		}
		return null;
	}
}
