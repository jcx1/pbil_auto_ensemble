package weka.classifiers.meta.pbilautoens.domain.bc.prototypes;

import java.util.Arrays;

import weka.classifiers.meta.pbilautoens.ClassifierPrototype;
import weka.classifiers.meta.pbilautoens.ParameterPrototype;
import weka.classifiers.meta.pbilautoens.enums.ParameterType;
import weka.classifiers.meta.pbilautoens.exception.InvalidParameterTypeException;

public class NaiveBayesPrototype extends ClassifierPrototype {
	public NaiveBayesPrototype() throws InvalidParameterTypeException {
		ParameterPrototype d = new ParameterPrototype("D", ParameterType.BOOLEAN);
		d.setPossibilities(Arrays.asList(true,false));
		parameters.put(d.getName(), d);
		
		ParameterPrototype k = new ParameterPrototype("K", ParameterType.BOOLEAN);
		k.setPossibilities(Arrays.asList(true,false));
		parameters.put(k.getName(), k);
		
	}
}
