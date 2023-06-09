package weka.classifiers.meta.pbilautoens.domain.bc.prototypes;

import java.util.Arrays;

import weka.classifiers.meta.pbilautoens.ClassifierPrototype;
import weka.classifiers.meta.pbilautoens.ParameterPrototype;
import weka.classifiers.meta.pbilautoens.enums.ParameterType;
import weka.classifiers.meta.pbilautoens.exception.InvalidParameterTypeException;

public class IbkPrototype extends ClassifierPrototype {
	
	public IbkPrototype() throws InvalidParameterTypeException {
		ParameterPrototype e = new ParameterPrototype("E", ParameterType.BOOLEAN);
		e.setPossibilities(Arrays.asList(true,false));
		parameters.put(e.getName(), e);
		
		ParameterPrototype k  = new ParameterPrototype("K", ParameterType.INT);
		k = buildIntParamete(1, 64, 1, k);
		parameters.put(k.getName(), k);
		
		ParameterPrototype i = new ParameterPrototype("I", ParameterType.BOOLEAN);
		i.setPossibilities(Arrays.asList(true,false));
		parameters.put(i.getName(), i);
		
		ParameterPrototype f = new ParameterPrototype("F", ParameterType.BOOLEAN);
		f.setPossibilities(Arrays.asList(true,false));
		parameters.put(f.getName(), f);
		
		ParameterPrototype x = new ParameterPrototype("X", ParameterType.BOOLEAN);
		x.setPossibilities(Arrays.asList(true,false));
		parameters.put(x.getName(), x);
	}
	
}
