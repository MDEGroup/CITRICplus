package it.univaq.disim.mde.manager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.impl.DynamicEObjectImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.BasicExtendedMetaData;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceFactoryImpl;
import org.eclipse.epsilon.ecl.parse.Ecl_EolParserRules.returnStatement_return;
import org.eclipse.ocl.OCL;
import org.eclipse.ocl.ParserException;
import org.eclipse.ocl.Query;
import org.eclipse.ocl.ecore.Constraint;
import org.eclipse.ocl.ecore.EcoreEnvironmentFactory;
import org.eclipse.ocl.expressions.OCLExpression;
import org.eclipse.ocl.helper.OCLHelper;


public class MetamodelManager{
	
	/**
	 * Set of all EClasses that are contained in EPackages that are currently
	 * registered in the EPackage registry.
	 * 
	 * @see #getAllEClasses()
	 */
	private static List<EClass> allEClasses;
	/**
	 * Set of all EPackages that are currently registered in the EPackage registry
	 * and not contained in any other package.
	 * 
	 * @see #getAllRootEPackages()
	 */
	private Set<EPackage> rootModelPackages;

	/**
	 * Map that maps EReferences to all possible EClasses that can be contained when
	 * using them.
	 * 
	 * @see #getAllEContainments(EReference)
	 */
	private static Map<EReference, List<EClass>> allEContainments = new LinkedHashMap<EReference, List<EClass>>();

	/**
	 * Map that maps EClasses to their subclasses.
	 * 
	 * @see #getAllSubEClasses(EClass)
	 */
	private static Map<EClass, List<EClass>> eClassToSubEClasses = new LinkedHashMap<EClass, List<EClass>>();

	private static Map<EPackage, List<EClass>> packageToModelElementEClasses = new LinkedHashMap<EPackage, List<EClass>>();
	private static Map<EPackage, List<EAnnotation>> packageToModelElementEAnnotations = new LinkedHashMap<EPackage, List<EAnnotation>>();

	
	private static ResourceSet resourceSet = new ResourceSetImpl();
	private static Resource metamodelResource;
	private static MetamodelManager metamodelManager;
	
	
	public MetamodelManager(String metamodelPath){
		 metamodelResource = registerMetamodel(metamodelPath);
	}
	
	public static MetamodelManager Build(String metamodelPath) {
		metamodelManager = new MetamodelManager(metamodelPath);
		return metamodelManager;
	}
	
	
	
	public Resource loadMetamodelResource(String uri) {
		Resource resource = resourceSet.getResource(URI.createFileURI(uri), true);
		return resource;
	}
	
	/*
	 * Let's say you're building a stand-alone tool that needs to run ATL
	 * transformation, you need to 'manually' register your metamodels) 
	 * This method does two things, it initializes an Ecore parser and then programmatically looks for
	 * the package definition on it, obtains the NsUri and registers it.
	 */
	public String lazyMetamodelRegistration(String metamodelPath){
		
		File fileName = new File(metamodelPath);
		URI uri = URI.createFileURI(fileName.getAbsolutePath());
		
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
   	
	    ResourceSet rs = new ResourceSetImpl();
	    // Enables extended meta-data, weird we have to do this but well...
	    final ExtendedMetaData extendedMetaData = new BasicExtendedMetaData(EPackage.Registry.INSTANCE);
	    rs.getLoadOptions().put(XMLResource.OPTION_EXTENDED_META_DATA, extendedMetaData);
	
	    Resource r = rs.getResource(uri, true);
	    EObject eObject = r.getContents().get(0);
	    // A meta-model might have multiple packages we assume the main package is the first one listed
	    if (eObject instanceof EPackage) {
	        EPackage p = (EPackage)eObject;
//	        System.out.println(p.getNsURI());
	        EPackage.Registry.INSTANCE.put(p.getNsURI(), p);
	        return p.getNsURI();
	    }
	    return null;
	}
	
	public static Resource registerMetamodel(String path) {
		File fileName = new File(path);
		URI uri = URI.createFileURI(fileName.getAbsolutePath());
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
		ResourceSet rs = new ResourceSetImpl();
		// enable extended metadata
		final ExtendedMetaData extendedMetaData = new BasicExtendedMetaData(rs.getPackageRegistry());
		rs.getLoadOptions().put(XMLResource.OPTION_EXTENDED_META_DATA, extendedMetaData);
		Resource r = rs.getResource(uri, true);
		List<EObject> eObject = r.getContents();
		for (EObject eObject2 : eObject) {
			if (eObject2 instanceof EPackage) {
				EPackage p = (EPackage) eObject2;
				registerSubPackage(p);
			}
		}
//		System.out.println(path + " registered!");
		return r;
	}

	private static void registerSubPackage(EPackage p) {
		EPackage.Registry.INSTANCE.put(p.getNsURI(), p);
		for (EPackage pack : p.getESubpackages()) {
			registerSubPackage(pack);
		}
	}
	
	public List<EClass> getMeta_metamodelEClasses(String inputMetamodel) {
		List<EPackage> loadedMetaModelEPackages = new ArrayList<EPackage>();
		loadedMetaModelEPackages = loadMetamodel(inputMetamodel);
		List<EClass> eClasses = new ArrayList<EClass>();
		for (EPackage ePackage : loadedMetaModelEPackages) {
			eClasses.addAll(metamodelManager.getAllEClasses(ePackage));
		}
		return eClasses;
	}
	
//	public static List<ModelStructuralFeature> getAllMetamodelStructuralFeaturesAndReferences(String inputMetamodel) {
//		
//		List<ModelStructuralFeature> mmSFList = new ArrayList<ModelStructuralFeature>();
//		Resource resource = load_resourceSet.getResource(URI.createURI(inputMetamodel), true);
//
//		if (resource.isLoaded() && resource.getErrors() != null) {
//			TreeIterator<EObject> eAllContents = resource.getAllContents();
//			while (eAllContents.hasNext()) {
//				
//				EObject next = eAllContents.next();
//				if (next instanceof EPackage) {
////					EPackage ePackage = (EPackage) next;
////					System.out.println("EPackage: "+ePackage.getName());
//				} else if (next instanceof EClass) {
//					EClass eClass = (EClass) next;
//					if(!eClass.isAbstract()) {
//						ModelStructuralFeature mmSF = new ModelStructuralFeature();
////						System.out.println("EClass: "+eClass.getName());
//						mmSF.seteClass(eClass);
//						EList<EStructuralFeature> eStructuralFeatures = eClass.getEAllStructuralFeatures();
//						Set<EReference> refList = new HashSet<EReference>();
//						Set<EStructuralFeature> sFList = new HashSet<EStructuralFeature>();
//						Set<String> allSF = new HashSet<String>();
//						for (EStructuralFeature eStructuralFeature : eStructuralFeatures) {
//							if(eStructuralFeature instanceof EReference) {
//								EReference ref = (EReference) eStructuralFeature;
//								refList.add(ref);
//								allSF.add(ref.getName());
////								System.out.println("\tRef: "+eStructuralFeature.getName());
//							}else {
//								EStructuralFeature eSF = eStructuralFeature;
//								sFList.add(eSF);
//								allSF.add(eSF.getName());
////								System.out.println("\tSF: "+eStructuralFeature.getName());
//							}
//						}
//						mmSF.setAllEStructuralFeatures(allSF);
//						mmSF.seteReferences(refList);
//						mmSF.seteStructuralFeatures(sFList);
//						mmSFList.add(mmSF);
//					}
//				} 
//			}
//		}
//		return mmSFList;
//	}
	
	
	public List<EStructuralFeature> getAllEStructuralFeatures(String inputMM){
		
		Resource resource = resourceSet.getResource(URI.createURI(inputMM), true);
		EObject rootPackage = (EObject) resource.getContents().get(0);
		
		List<EStructuralFeature> structuralFeaturesList = new ArrayList<EStructuralFeature>();

		// DEFINE OCL AND HELPER
		OCL<?, EClassifier, ?, ?, ?, EParameter, ?, ?, ?, Constraint, EClass, EObject> ocl;
		OCLHelper<EClassifier, ?, ?, Constraint> helper;

		// INSTANCIATE OCL
		ocl = OCL.newInstance(EcoreEnvironmentFactory.INSTANCE);
		// INSTANCIATE NEW HELPER FROM OCLEXPRESSION
		helper = ocl.createOCLHelper();
		// SET HELPER CONTEXT
		helper.setContext(EcorePackage.eINSTANCE.getEPackage());

		// CREATE OCLEXPRESSION
		OCLExpression<EClassifier> expression;
		try {
			expression = helper.createQuery("EStructuralFeature.allInstances()");
			// CREATE QUERY FROM OCLEXPRESSION
			Query<EClassifier, EClass, EObject> query = ocl.createQuery(expression);
			
			// EVALUATE OCL
			
			HashSet<Object> success = (HashSet<Object>) query.evaluate(rootPackage);
			for (Object object : success) {
				structuralFeaturesList.add((EStructuralFeature) object);
			}
			
			
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return structuralFeaturesList;
	}
	
	public List<EClass> getAllEClasses_OCL() {
		
//		Resource resource = resourceSet.getResource(URI.createURI(inputMM), true);
		EObject rootPackage = (EObject) metamodelResource.getContents().get(0);

		// DEFINE OCL AND HELPER
		OCL<?, EClassifier, ?, ?, ?, EParameter, ?, ?, ?, Constraint, EClass, EObject> ocl;
		OCLHelper<EClassifier, ?, ?, Constraint> helper;

		// INSTANCIATE OCL
		ocl = OCL.newInstance(EcoreEnvironmentFactory.INSTANCE);
		// INSTANCIATE NEW HELPER FROM OCLEXPRESSION
		helper = ocl.createOCLHelper();
		// SET HELPER CONTEXT
		helper.setContext(EcorePackage.eINSTANCE.getEPackage());

		// CREATE OCLEXPRESSION
		OCLExpression<EClassifier> expression = null;
		try {
			expression = helper.createQuery("EClass.allInstances())");
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// CREATE QUERY FROM OCLEXPRESSION
		Query<EClassifier, EClass, EObject> query = ocl.createQuery(expression);

		// EVALUATE OCL
//		int success = (int) query.evaluate(rootPackage);
//		return success;
		HashSet<Object> success = (HashSet<Object>) query.evaluate(rootPackage);
		List<EClass> callableMethods = new ArrayList<EClass>();
		for (Object object : success) {
				callableMethods.add((EClass) object);
		}
		return callableMethods;
	}
	
	public List<EPackage> getAllEPackages(){
		List<EPackage> ePackageList = new ArrayList<EPackage>();
		
		
		
		ResourceSetImpl resourceSet = new ResourceSetImpl();
		for (EObject obj : metamodelResource.getContents()) {
			if (obj instanceof EPackage) {						
				resourceSet.getPackageRegistry().put(((EPackage)obj).getNsURI(), ((EPackage)obj).getEFactoryInstance().getEPackage());
				ePackageList.add((EPackage)obj);
			}
		}
	
		List<EPackage> subpackages = loadSubpackages(ePackageList.get(0));
		for (EPackage spck : subpackages) {
			if (!ePackageList.contains(spck)) {
				ePackageList.add(spck);
			}
		}
		
		return ePackageList;
	}
	
	public List<EPackage> loadMetamodel(String uri) {
		List<EPackage> metamodel = null;
		try {
			metamodel = new ArrayList<EPackage>();
			
			// check if it is already registered
			EPackage pck = EPackage.Registry.INSTANCE.getEPackage(uri);
			
			// otherwise
			if (pck==null) {
				EPackage.Registry.INSTANCE.put(uri, EPackage.class);
				if (Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().size() == 0)
					Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
				
				ResourceSetImpl resourceSet = new ResourceSetImpl();
				Resource        resource    = resourceSet.getResource(URI.createFileURI(uri), true);
				for (EObject obj : resource.getContents()) {
					if (obj instanceof EPackage) {						
						resourceSet.getPackageRegistry().put(((EPackage)obj).getNsURI(), ((EPackage)obj).getEFactoryInstance().getEPackage());
						metamodel.add((EPackage)obj);
					}
				}
			}
			else metamodel.add(pck);

			List<EPackage> subpackages = loadSubpackages(metamodel.get(0));
			for (EPackage spck : subpackages) {
				if (!metamodel.contains(spck)) {
					metamodel.add(spck);
				}
			}
		}
		catch (Exception e) {
			
		}
		return metamodel;
	}
	
	private static List<EPackage> loadSubpackages(EPackage pck) {
		List<EPackage> subpackages = new ArrayList<EPackage>();
		for (EPackage spck : pck.getESubpackages()) {
			subpackages.add(spck);
			List<EPackage> nextSubpackages = loadSubpackages(spck);
			for (EPackage nspck : nextSubpackages) {
				if (!subpackages.contains(nspck)) {
					subpackages.add(nspck);
				}
			}
		}
		return subpackages;
	}
	
	public void serializeMetaModel(EPackage metamodelEPackage, String outputMetamodelPath) {

		ResourceSet resourceSet = new ResourceSetImpl();

		// Register XML Factory implementation to handle .ecore files
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecore", new XMLResourceFactoryImpl());

		// Create empty resource with the given URI
		Resource resource = resourceSet.createResource(URI.createURI(outputMetamodelPath));

		// Add bookStoreEPackage to contents list of the resource
		resource.getContents().add(metamodelEPackage);

		try {
			// Save the resource
			resource.save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public List<EStructuralFeature> getAllEStructuralFeatures(){
		List<EStructuralFeature> allMetamodelEStructuralFeatures = new ArrayList<EStructuralFeature>();
		for (EClass eClass : metamodelManager.getAllEClasses2()) {
			for (EStructuralFeature eStructuralFeature : eClass.getEAllStructuralFeatures()) {
				allMetamodelEStructuralFeatures.add(eStructuralFeature);
			}
		}
		return allMetamodelEStructuralFeatures; 
	}
	
	
	
	
	public List<EAnnotation> getAllEAnnotations(){
		
		List<EAnnotation> allMetamodelEAnnotations = new ArrayList<EAnnotation>();
		
		for (EPackage ePackage : metamodelManager.getAllEPackages()) {
			allMetamodelEAnnotations.addAll(getAllEAnnotations(ePackage));
		}
		
		return allMetamodelEAnnotations;
		
	}
	
	
	public List<EAnnotation> getAllEAnnotations(EPackage ePackage) {
		if (packageToModelElementEAnnotations.containsKey(ePackage)) {
			return packageToModelElementEAnnotations.get(ePackage);
		}
		if (ePackage == null) {
			packageToModelElementEAnnotations.put(ePackage, new LinkedList<EAnnotation>());
			return packageToModelElementEAnnotations.get(ePackage);
		}
		List<EAnnotation> result = new LinkedList<EAnnotation>();
		// obtain all EClasses from sub packages
		for (EPackage subPackage : ePackage.getESubpackages()) {
			result.addAll(getAllEAnnotations(subPackage));
		}
		// obtain all EClasses that are direct contents of the EPackage
		for (EAnnotation annotation : ePackage.getEAnnotations()) {
			if (annotation instanceof EAnnotation) {
				result.add((EAnnotation) annotation);
			}
		}
		// save the result for upcoming method calls
		packageToModelElementEAnnotations.put(ePackage, result);
		return result;
	}
	
	
	public EMap<String, String> getAnnotationDetails(EAnnotation eAnnotation) {
		EMap<String, String> eAnnotationDetails = eAnnotation.getDetails();
		System.out.println("Key: " + eAnnotationDetails.keySet());
		System.out.println("Value: " + eAnnotationDetails.values());
		return eAnnotationDetails;
	}
	
	
	
	public List<EAnnotation> getAllECLassEAnnotations(String eClassName) {
		EClass eClass = getEClassFromName(eClassName);
		return eClass.getEAnnotations();
	}
	
	public List<EAnnotation> getAllECLassEAnnotations(EClass eClass) {
		return eClass.getEAnnotations();
	}
	
	public List<EAnnotation> getAllEAttributeEAnnotations(EAttribute eAttribute) {
		return eAttribute.getEAnnotations();
	}
	
	public EList<EStructuralFeature> getAllECLassEStructuralFeatures(EClass eClass) {
		return eClass.getEAllStructuralFeatures();
	}
	
	
	public String getWheightValueAnnotationFromKey(EAnnotation eAnnotation, String key) {
		EMap<String, String> eAnnotationDetails = eAnnotation.getDetails();
		
		String resultValue = null;
		
		Set<String> keySet = eAnnotationDetails.keySet();
		for (String keyIt : keySet) {
			if(keyIt.equalsIgnoreCase(key)) {
				 resultValue = eAnnotationDetails.get(keyIt);
			}
		}
		
		return resultValue;
	}
	
	public Set<EAnnotation> getAllECLassStructuralFeatureEAnnotations(String eClassName, String sfName) {
		Set<EAnnotation> resultAnnotations = new HashSet<EAnnotation>();
		EClass eClass = getEClassFromName(eClassName);
		EList<EStructuralFeature> eAllStructuralFeatures = eClass.getEAllStructuralFeatures();
		for (EStructuralFeature eStructuralFeature : eAllStructuralFeatures) {
			if(eStructuralFeature.getName().equalsIgnoreCase(sfName)) {
				 resultAnnotations.addAll(eStructuralFeature.getEAnnotations());
			}
		}
		return resultAnnotations;
	}
	
	
	public List<EClass> getAllEClasses2() {
		
		List<EClass> allMetamodelEClasses = new ArrayList<EClass>();
		TreeIterator<EObject> eAllContents = metamodelResource.getAllContents();
		while (eAllContents.hasNext()) {
			EObject next = eAllContents.next();
			if (next instanceof EClass && !((EClass) next).isAbstract()) {
				EClass eClass = (EClass) next;
				allMetamodelEClasses.add(eClass);
			}
		}
		return allMetamodelEClasses;
	}
	
	
	/**
	 * Iterates over all registered EPackages in order to retrieve all available
	 * EClasses, excluding abstract classes and interfaces, and returns them as a
	 * Set.
	 * 
	 * @return a set of all EClasses that are contained in registered EPackages
	 * @see Registry
	 */
	public List<EClass> getAllEClasses() {
		// were all EClasses computed before?
		if (allEClasses != null) {
			return allEClasses;
		}
		allEClasses = new LinkedList<EClass>();
		Registry registry = EPackage.Registry.INSTANCE;
		// for all registered EPackages
		for (java.util.Map.Entry<String, Object> entry : registry.entrySet()) {
			EPackage ePackage = registry.getEPackage(entry.getKey());
			for (EClass eClass : metamodelManager.getAllEClasses(ePackage)) {
				// no abstracts or interfaces
				if (canHaveInstance(eClass)) {
					allEClasses.add(eClass);
				}
			}
		}
		return allEClasses;
	}
	
	public EClass getEClassFromName(String eClassName) {
		allEClasses = new LinkedList<EClass>();
		Registry registry = EPackage.Registry.INSTANCE;
		// for all registered EPackages
		for (java.util.Map.Entry<String, Object> entry : registry.entrySet()) {
			EPackage ePackage = registry.getEPackage(entry.getKey());
			for (EClass eClass : metamodelManager.getAllEClasses(ePackage)) {
				if(eClass.getName().equalsIgnoreCase(eClassName)){
					return eClass;
				}
			}
		}
		return null;
	}

	public List<EClass> getAllEClasses(EPackage ePackage) {
		if (packageToModelElementEClasses.containsKey(ePackage)) {
			return packageToModelElementEClasses.get(ePackage);
		}
		if (ePackage == null) {
			packageToModelElementEClasses.put(ePackage, new LinkedList<EClass>());
			return packageToModelElementEClasses.get(ePackage);
		}
		List<EClass> result = new LinkedList<EClass>();
		// obtain all EClasses from sub packages
		for (EPackage subPackage : ePackage.getESubpackages()) {
			result.addAll(getAllEClasses(subPackage));
		}
		// obtain all EClasses that are direct contents of the EPackage
		for (EClassifier classifier : ePackage.getEClassifiers()) {
			if (classifier instanceof EClass) {
				result.add((EClass) classifier);
			}
		}
		// save the result for upcoming method calls
		packageToModelElementEClasses.put(ePackage, result);
		return result;
	}

	/**
	 * Returns whether <code>eClass</code> can be instantiated or not. An EClass can
	 * be instantiated, if it is neither an interface nor abstract.
	 * 
	 * @param eClass
	 *            the EClass in question
	 * @return whether <code>eClass</code> can be instantiated or not.
	 */
	public boolean canHaveInstance(EClass eClass) {
		return !eClass.isInterface() && !eClass.isAbstract();
	}

	/**
	 * Returns the EPackage to the specified <code>nsURI</code>.
	 * 
	 * @param nsURI
	 *            the NsUri of the EPackage to get
	 * @return the EPackage belonging to <code>nsURI</code>
	 * @see Registry#getEPackage(String)
	 */
	public EPackage getEPackageFromNsURI(String nsURI) {
		return EPackage.Registry.INSTANCE.getEPackage(nsURI);
	}
	
	public EPackage getEPackage(Resource resource) {
		EPackage ePackage = null;
		for (EObject eobj : resource.getContents()) {
			System.out.println(eobj);
			if(eobj instanceof EPackage) {
				ePackage =  (EPackage) eobj;
			}
		}
		return ePackage;
	}

	/**
	 * Returns all EPackages on the root level that are currently registered in the
	 * registry.
	 * 
	 * @return a Set of all root EPackages
	 * @see Registry
	 */
	public Set<EPackage> getAllRootEPackages() {
		// were the root packages computed before?
		if (rootModelPackages != null) {
			return rootModelPackages;
		}
		rootModelPackages = new LinkedHashSet<EPackage>();
		Registry registry = EPackage.Registry.INSTANCE;

		for (java.util.Map.Entry<String, Object> entry : registry.entrySet()) {
			EPackage ePackage = registry.getEPackage(entry.getKey());
			// is the current EPackage a root package?
			if (ePackage.getESuperPackage() == null) {
				rootModelPackages.add(ePackage);
			}
		}
		return rootModelPackages;
	}

	/**
	 * Returns all possible EClasses, excluding abstract classes and interfaces,
	 * that can be contained when using <code>reference</code>.
	 * 
	 * @param reference
	 *            the EReference to get containable EClasses for
	 * @return a set of all EClasses that can be contained when using
	 *         <code>reference</code>
	 */
	public List<EClass> getAllEContainments(EReference reference) {
		if (allEContainments.containsKey(reference)) {
			return allEContainments.get(reference);
		}
		if (reference == null) {
			allEContainments.put(reference, new LinkedList<EClass>());
			return allEContainments.get(reference);
		}
		List<EClass> result = new LinkedList<EClass>();
		EClass referenceType = reference.getEReferenceType();
		// no abstracts or interfaces
		if (metamodelManager.canHaveInstance(referenceType)) {
			result.add(referenceType);
		}
		// 'referenceType: EObject' allows all kinds of EObjects
		if (EcorePackage.eINSTANCE.getEObject().equals(referenceType)) {
			return metamodelManager.getAllEClasses();
		}
		// all sub EClasses can be referenced as well
		result.addAll(getAllSubEClasses(referenceType));
		// save the result for upcoming method calls
		allEContainments.put(reference, result);
		return result;
	}

	/**
	 * Returns all subclasses of an EClass, excluding abstract classes and
	 * interfaces.
	 * 
	 * @param eClass
	 *            the EClass to get subclasses for
	 * @return all subclasses of <code>eClass</code>
	 */
	public List<EClass> getAllSubEClasses(EClass eClass) {
		if (eClassToSubEClasses.containsKey(eClass)) {
			return eClassToSubEClasses.get(eClass);
		}
		if (eClass == null) {
			eClassToSubEClasses.put(eClass, new LinkedList<EClass>());
			return eClassToSubEClasses.get(eClass);
		}
		List<EClass> allEClasses = metamodelManager.getAllEClasses();
		List<EClass> result = new LinkedList<EClass>();
		for (EClass possibleSubClass : allEClasses) {
			// is the EClass really a subClass, while not being abstract or an
			// interface?
			if (eClass.isSuperTypeOf(possibleSubClass) && metamodelManager.canHaveInstance(possibleSubClass)) {
				result.add(possibleSubClass);
			}
		}
		// save the result for upcoming method calls
		eClassToSubEClasses.put(eClass, result);
		return result;
	}

	public static boolean validateObject(Resource resource) {
		EObject eo = resource.getContents().get(0);
		Diagnostic diagnostic = Diagnostician.INSTANCE.validate(eo);
		if (diagnostic.getSeverity() == Diagnostic.ERROR || diagnostic.getSeverity() == Diagnostic.WARNING) {
			System.err.println(diagnostic.getMessage());
			for (Iterator i = diagnostic.getChildren().iterator(); i.hasNext();) {
				Diagnostic childDiagnostic = (Diagnostic) i.next();
				switch (childDiagnostic.getSeverity()) {
				case Diagnostic.ERROR:
				case Diagnostic.WARNING:
					System.err.println("\t" + childDiagnostic.getMessage());
				}
			}
			return false;
		}
		return true;
	}


	public EPackage getResourceEPackage(Resource resource) {
		EObject rootPackage = (EObject) resource.getContents().get(0);
		return (EPackage) rootPackage;
	}

	public String objectToString(Object object) {
		if (object instanceof String) {
			return "'" + object + "'";
		}
		if (object instanceof DynamicEObjectImpl) {
			EClass eclass = ((DynamicEObjectImpl) object).eClass();
			if (eclass != null) {
				String type = eclass.getName();
				EPackage epackage = eclass.getEPackage();
				while (epackage != null) {
					type = epackage.getName() + "." + type;
					epackage = epackage.getESuperPackage();
				}
				String args = "";
				for (EAttribute att : eclass.getEAllAttributes()) {
					args = args + ", " + att.getName() + "=" + objectToString(((DynamicEObjectImpl) object).eGet(att));
				}
				return type + "@" + Integer.toHexString(object.hashCode()) + " (dynamic" + args + ")";
			}
		}
		// object could be null
		return String.valueOf(object);
	}

	public EClass getClassByName(String ecName) {
		for (EClass ec : getAllEClasses2()) {
			if(ec.getName().equals(ecName))
				return ec;
		}
		return null;
	}
	
	public int getWeightOf(EClass ec) {
		String ANNOTATION_SOURCE = "http://class/importance"; 
		String ANNOTATION_KEY = "weight";
		for (EAnnotation eClassAnnotation : getAllECLassEAnnotations(ec.getName())) {
			if (eClassAnnotation.getSource().equalsIgnoreCase(ANNOTATION_SOURCE)) {
				String annotationWeightValue = metamodelManager.getWheightValueAnnotationFromKey(eClassAnnotation, ANNOTATION_KEY);
				return Integer.parseInt(annotationWeightValue);
			}
		}
		return 0;
	}

}
