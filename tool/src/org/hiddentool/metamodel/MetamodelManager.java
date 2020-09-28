package org.hiddentool.metamodel;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.BasicExtendedMetaData;
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import PreferenceModel.MCImportance;

public class MetamodelManager {
	private static Map<EPackage, List<EClass>> packageToModelElementEClasses = new LinkedHashMap<EPackage, List<EClass>>();
	private static MetamodelManager instance;
	
	public static MetamodelManager getInstance() {
		
		if(instance == null) {
			return new MetamodelManager();
		}
		return instance;
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

	
	public List<EClass> getAllEClasses() {
		List<EClass> allEClasses = new LinkedList<>();
		Registry registry = EPackage.Registry.INSTANCE;
		// for all registered EPackages
		for (java.util.Map.Entry<String, Object> entry : registry.entrySet()) {
			EPackage ePackage = registry.getEPackage(entry.getKey());
			for (EClass eClass : this.getAllEClasses(ePackage)) {
				// no abstracts or interfaces
				if (canHaveInstance(eClass)) {
					allEClasses.add(eClass);
				}
			}
		}
		return allEClasses;
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
	
	public boolean canHaveInstance(EClass eClass) {
		return !eClass.isInterface() && !eClass.isAbstract();
	}



}
