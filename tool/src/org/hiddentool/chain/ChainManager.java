package org.hiddentool.chain;

import java.util.ArrayList;
import java.util.List;

import org.hiddentool.chain.transformation.Transformation;

public class ChainManager {
	private static ChainManager instance;
	private List<Transformation> transformations = new ArrayList<>();


	public static ChainManager getInstance() {
		if(instance == null) {
			return new ChainManager();
		}
		return instance;
	}

	public List<Transformation> getTransformations() {
		return transformations;
	}
	
	
}
