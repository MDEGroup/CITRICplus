package org.hiddentool.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

public class ModelManager {

	private Resource resource = null;
	private static ModelManager instance;

	public static ModelManager getInstance() {
		if (instance == null) {
			return new ModelManager();
		}
		return instance;
	}

	public int getNumberOfInstancesOf(EClass ec) {
		int count = 0;
		for (EClass e : getAllModelEClasses()) {
			if (e.getName().equals(ec.getName()))
				count++;
		}
		return count;
	}

	public List<EClass> getAllModelEClasses() {
		List<EClass> result = new ArrayList<EClass>();
		TreeIterator<EObject> eAllContents = resource.getAllContents();
		while (eAllContents.hasNext()) {
			EObject next = eAllContents.next();
			EClass eClass = next.eClass();
			if (eClass instanceof EClass) {
				result.add(eClass);
			}
		}
		return result;
	}
	
	public List<EClass> getAllModelEClasses(String inputModelPath) {
		List<EClass> result = new ArrayList<EClass>();
		ResourceSet rst = new ResourceSetImpl();
		Resource rs = rst.createResource(URI.createFileURI(inputModelPath));
		TreeIterator<EObject> eAllContents = rs.getAllContents();
		while (eAllContents.hasNext()) {
			EObject next = eAllContents.next();
			EClass eClass = next.eClass();
			if (eClass instanceof EClass) {
				result.add(eClass);
			}
		}
		return result;
	}

}
